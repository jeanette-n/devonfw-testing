package com.capgemini.ntc.selenium.tests.pages.demo.main.registration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.ntc.selenium.pages.features.registration.RegistryPage;
import com.capgemini.ntc.test.core.BaseTest;
import com.capgemini.ntc.test.core.logger.BFLogger;

public class RegistryPageTest extends BaseTest {

	private RegistryPage registryPage;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Override
    @Before
	public void setUp() {
		BFLogger.logInfo("[Step 1] As a standard user I will open Registry Page,  So that my I can fill data");
		registryPage = new RegistryPage();
	}
	@Override
    @After
	public void tearDown() {
	}
	@Test
	public void QCID_12342343_StayOnResistryPage() {

		BFLogger.logInfo(
				"[Step 2] As a standard user I click Submit button,  So that I will stay on Registry page");
		registryPage.clickSubmit();
		assertThat(true, is(registryPage.isLoaded()));
	}
	@Test
	public void QCID_3784343_DataAreSaved() {

		BFLogger.logInfo(
				"[Step 2] As a standard user I click Submit button,  So that my data will be saved");
		registryPage.clickSubmit();
		assertThat(false, is(registryPage.isButtonSubmitDisplayed()));
	}
}
