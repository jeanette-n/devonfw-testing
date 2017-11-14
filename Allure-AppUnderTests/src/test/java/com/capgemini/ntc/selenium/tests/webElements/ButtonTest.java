package com.capgemini.ntc.selenium.tests.webElements;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;

import com.capgemini.ntc.selenium.core.BasePage;
import com.capgemini.ntc.selenium.pages.enums.PageSubURLsEnum;
import com.capgemini.ntc.selenium.testSuites.testType.TestsSelenium;
import com.capgemini.ntc.test.core.BaseTest;

/**
 * Created by LKURZAJ on 03.03.2017.
 */
@Category(TestsSelenium.class)
public class ButtonTest extends BaseTest {

    private static By buttonSubmit = By.cssSelector("button#submit");

    @Test
    public void test(){
        // check if element is displayed
        assertTrue(BasePage.getDriver().elementButton(ButtonTest.buttonSubmit).isDisplayed());

        // check if element type equals Button
        assertEquals( "Button" , BasePage.getDriver().elementButton(ButtonTest.buttonSubmit).getElementTypeName());

        // click on button and verify if url was changed
        BasePage.getDriver().elementButton(ButtonTest.buttonSubmit).click();
        assertTrue(BasePage.getDriver().getCurrentUrl().contains("&submit="));
    }

    @Override
    public void setUp() {
        BasePage.getDriver().get(PageSubURLsEnum.TOOLS_QA.subURL() + PageSubURLsEnum.AUTOMATION_PRACTICE_FORM.subURL());
        return;
    }

    @Override
    public void tearDown() {
        // TODO Auto-generated method stub
    }
}
