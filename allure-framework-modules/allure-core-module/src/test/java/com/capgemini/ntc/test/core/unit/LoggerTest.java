package com.capgemini.ntc.test.core.unit;

import static org.junit.Assert.*;

import org.junit.*;

import com.capgemini.ntc.test.core.BaseTest;
import com.capgemini.ntc.test.core.logger.BFLogger;

public class LoggerTest extends BaseTest {

	@BeforeClass
	public static void beforeClass() {
		System.out.println("LoggerTest.beforeClass()");
		BFLogger.logDebug("LoggerTest.beforeClass()");
	}

	@Test
	public void testPassed() throws InterruptedException {
		BFLogger.logDebug("Debug-TestPassed");
		BFLogger.logError("Error-TestPassed");
		BFLogger.logInfo("Info-TestPassed");
//		TimeUtils.waitSeconds(2);
	}

	@Test
	public void testFailed() throws Exception {
		BFLogger.logDebug("Debug-TestFailed");
		fail("Fail test");
		BFLogger.logError("Error-TestFailed");
		BFLogger.logInfo("Info-TestFailed");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("LoggerTest.afterClass()");
		BFLogger.logDebug("LoggerTest.afterClass()");
	}

	@Override
	public void setUp() {
		System.out.println("LoggerTest.setUp()");
		BFLogger.logDebug("LoggerTest.setUp()");
	}

	@Override
	public void tearDown() {
		System.out.println("LoggerTest.tearDown()");
		BFLogger.logDebug("LoggerTest.tearDown()");
	}

}
