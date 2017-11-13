package com.capgemini.ntc.selenium.tests.samples.environment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.capgemini.ntc.selenium.core.enums.ServicesURLsEnum;
import com.capgemini.ntc.selenium.core.exceptions.BFComponentStateException;
import com.capgemini.ntc.test.core.base.environments.SpreadsheetEnvironmentService;
import com.capgemini.ntc.test.core.base.environments.ParametersManager;
import com.capgemini.ntc.test.core.exceptions.BFInputDataException;
import com.capgemini.ntc.test.core.logger.BFLogger;

public class EnvTest {

	SpreadsheetEnvironmentService systemUnderTest;

	@Test
	public void getServiceAddressShouldReturnCorrectServiceAddressForDefaultEnvironment() {
		systemUnderTest = SpreadsheetEnvironmentService.INSTANCE;

		String actualAddress = systemUnderTest.getServiceAddress("DMA_URL");
		String expectedAddress = "https://homepage.company.com/ftgw/dpcs/dma/";
		assertEquals(expectedAddress, actualAddress);
	}

	@Test(expected = BFComponentStateException.class)
	public void getServiceAddressShouldThrowExceptionWhenServiceNotFound() {
		systemUnderTest = SpreadsheetEnvironmentService.INSTANCE;

		systemUnderTest.getServiceAddress("NOT_EXISTING_SERVICE");
	}

	@Test
	public void setEnvironmentShouldSucceedWhenEnvironmentExist() {
		systemUnderTest = SpreadsheetEnvironmentService.INSTANCE;

		systemUnderTest.set("DEV2");
	}

	@Test
	public void getServiceAddressShouldReturnCorrectServiceAddressForSelectedEnvironment() {
		systemUnderTest = SpreadsheetEnvironmentService.INSTANCE;

		String serviceName = "MY_RESEARCH_URL_VALUE";

		systemUnderTest.set("DEV2");
		String actualAddress = systemUnderTest.getServiceAddress(serviceName);
		String expectedAddress = "https://myresearchqa2.company.com/";
		assertEquals(expectedAddress, actualAddress);

		systemUnderTest.set("DEV1");
		actualAddress = systemUnderTest.getServiceAddress(serviceName);
		expectedAddress = "https://myresearchqa1.company.com/";
		assertEquals(expectedAddress, actualAddress);

		systemUnderTest.set("DEV2");
		actualAddress = systemUnderTest.getServiceAddress(serviceName);
		expectedAddress = "https://myresearchqa2.company.com/";
		assertEquals(expectedAddress, actualAddress);
	}

	@Test(expected = BFInputDataException.class)
	public void setEnvironmentShouldThrowExceptionWhenEnvironmentNotFound() {
		systemUnderTest = SpreadsheetEnvironmentService.INSTANCE;

		systemUnderTest.set("DEV999");
	}

	@Test
	public void ServicesURLsEnumIsReturningCorrectAddresses() {
		systemUnderTest = ParametersManager.INSTANCE.environment();

		systemUnderTest.set("DEV1");
		String actualAddress = ServicesURLsEnum.WWW_FONT_URL.getAddress();
		String expectedAddress = "https://myresearchqa1.company.com/";
		assertEquals(expectedAddress, actualAddress);

		systemUnderTest.set("DEV2");
		actualAddress = ServicesURLsEnum.WWW_FONT_URL.getAddress();
		expectedAddress = "https://myresearchqa2.company.com/";
		assertEquals(expectedAddress, actualAddress);
	}

	@Test
	public void envLogTest() {
		BFLogger.logEnv("----- test -----");
	}

}