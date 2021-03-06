package com.capgemini.ntc.test.core;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.capgemini.ntc.test.core.BaseTestWatcher.TestClassRule;
import com.capgemini.ntc.test.core.analytics.AnalyticsCore;
import com.capgemini.ntc.test.core.base.environment.EnvironmentModule;
import com.capgemini.ntc.test.core.base.environment.IEnvironmentService;
import com.capgemini.ntc.test.core.base.runtime.RuntimeParametersCore;
import com.capgemini.ntc.test.core.logger.BFLogger;
import com.capgemini.ntc.test.core.testRunners.ParallelTestClassRunner;
import com.google.inject.Guice;

@RunWith(ParallelTestClassRunner.class)
public abstract class BaseTest implements IBaseTest {
    
    private static IEnvironmentService environmentService;
    private static GoogleAnalytics analytics;
    
    public BaseTest() {
        
        setPropertiesSettings();
        setRuntimeParametersCore();
        setEnvironmetInstance();
        setAnalytics();
        
    }
    
    public static IEnvironmentService getEnvironmentService() {
        return environmentService;
    }
    
    public static GoogleAnalytics getAnalytics() {
        return BaseTest.analytics;
    }
    
    public static void setEnvironmentService(IEnvironmentService environmentService) {
        BaseTest.environmentService = environmentService;
    }
    
    @BeforeClass
    public static final void setUpClass() throws MalformedURLException {
    }
    
    @AfterClass
    public static final void tearDownClass() {
        
    }
    
    @After
    public void tearDownTestLast() {
        
    }
    
    @Override
    abstract public void setUp();
    
    @Override
    abstract public void tearDown();
    
    @Rule
    public TestWatcher testWatcher = new BaseTestWatcher(this);
    
    @ClassRule
    public static TestClassRule classRule = new TestClassRule();
    
    private void setEnvironmetInstance() {
        // Environment variables either from environmnets.csv or any other input data.
        IEnvironmentService environmetInstance = Guice.createInjector(new EnvironmentModule())
                .getInstance(IEnvironmentService.class);
        environmetInstance.setEnvironment(RuntimeParametersCore.ENV.getValue());
        BaseTest.setEnvironmentService(environmetInstance);
    }
    
    private void setRuntimeParametersCore() {
        // Read System or maven parameters
        BFLogger.logDebug(RuntimeParametersCore.ENV.toString());
    }
    
    private void setPropertiesSettings() {
        /*
         * For now there is no properties settings file for Core module. In future, please have a look on Selenium
         * Module PropertiesSelenium propertiesSelenium = Guice.createInjector(PropertiesSettingsModule.init())
         * .getInstance(PropertiesSelenium.class);
         */
        
    }
    
    private void setAnalytics() {
        analytics = AnalyticsCore.GOOGLE.getInstance();
        getAnalytics().pageView("/allure-core-module/src/main/java/com/capgemini/ntc/test/core", "BaseTest", "BaseTest core")
                .postAsync();
        
    }
    
}
