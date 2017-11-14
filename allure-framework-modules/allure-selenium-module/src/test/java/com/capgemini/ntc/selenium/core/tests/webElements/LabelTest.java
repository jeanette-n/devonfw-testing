package com.capgemini.ntc.selenium.core.tests.webElements;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

import com.capgemini.ntc.selenium.core.BasePage;
import com.capgemini.ntc.selenium.core.enums.PageSubURLsEnum;
import com.capgemini.ntc.test.core.BaseTest;

/**
 * Created by LKURZAJ on 03.03.2017.
 */
//@Category(TestsSelenium.class)
public class LabelTest extends BaseTest {
    private static By text1Label = By.cssSelector("span.bcd");

    @Test
    public void test(){
        // check if label is displayed
        assertTrue(BasePage.getDriver().elementLabel(LabelTest.text1Label).isDisplayed());

        // check if label has properly text
        assertEquals("Text1",BasePage.getDriver().elementLabel(LabelTest.text1Label).getText());

        // check if label has properly class field
        assertEquals("bcd",BasePage.getDriver().elementLabel(LabelTest.text1Label).getClassName());
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
