package com.capgemini.ntc.selenium.pages.features.navigationbar;

import org.openqa.selenium.By;

import com.capgemini.ntc.selenium.core.BasePage;
import com.capgemini.ntc.selenium.core.newDrivers.INewWebDriver;
import com.capgemini.ntc.selenium.pages.enums.PageSubURLsEnum;
import com.capgemini.ntc.selenium.pages.enums.PageTitlesEnum;
import com.capgemini.ntc.selenium.pages.features.MainPage;
import com.capgemini.ntc.test.core.logger.BFLogger;

public class NavigationBar extends BasePage {

	private static final By selectorNavBarHome = By.cssSelector("li[id='menu-item-38']");

	public NavigationBar(INewWebDriver driver, MainPage parent) {
		super(/*driver, */parent);
	}

	@Override
	public boolean isLoaded() {
		return isUrlAndPageTitleAsCurrentPage(PageSubURLsEnum.MAIN_PAGE);
	}

	@Override
	public void load() {
	}

	@Override
	public String pageTitle() {
		return PageTitlesEnum.MAIN_PAGE.toString();
	}

	public void clickNavBarHome() {
		getDriver().elementButton(selectorNavBarHome).click();
		BFLogger.logInfo("Clicking on Navigation bar link - Home");
		getDriver().waitForPageLoaded();
	}

}
