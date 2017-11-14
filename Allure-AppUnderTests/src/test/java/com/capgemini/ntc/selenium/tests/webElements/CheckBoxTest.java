package com.capgemini.ntc.selenium.tests.webElements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;

import com.capgemini.ntc.selenium.core.BasePage;
import com.capgemini.ntc.selenium.core.newDrivers.elementType.CheckBox;
import com.capgemini.ntc.selenium.pages.enums.PageSubURLsEnum;
import com.capgemini.ntc.selenium.testSuites.testType.TestsSelenium;
import com.capgemini.ntc.test.core.BaseTest;

/**
 * Created by LKURZAJ on 06.03.2017.
 */
@Category(TestsSelenium.class)
public class CheckBoxTest extends BaseTest {

    private static final By hobbyCheckBoxSelector = By.cssSelector("li.fields.pageFields_1:nth-child(3) div.radio_wrap");
    CheckBox checkBoxElement;

    @Test
    public void testCheckBoxByIndex(){
        checkBoxElement.setCheckBoxByIndex(0);
        assertTrue(checkBoxElement.isCheckBoxSetByIndex(0));
        checkBoxElement.unsetCheckBoxByIndex(0);
        assertFalse(checkBoxElement.isCheckBoxSetByIndex(0));
    }

    @Test
    public void testCheckBoxByValue(){
        checkBoxElement.setCheckBoxByValue("reading");
        assertTrue(checkBoxElement.isCheckBoxSetByValue("reading"));
        checkBoxElement.unsetCheckBoxByValue("reading");
        assertFalse(checkBoxElement.isCheckBoxSetByValue("reading"));
    }

    @Test
    public void testCheckBoxByText(){
        checkBoxElement.setCheckBoxByText("Cricket");
        assertTrue(checkBoxElement.isCheckBoxSetByText("Cricket"));
        checkBoxElement.unsetCheckBoxByText("Cricket");
        assertFalse(checkBoxElement.isCheckBoxSetByText("Cricket"));

    }

    @Test
    public void testNumberOfCheckBoxes(){
        assertEquals(checkBoxElement.getTextList().size(), 3);
    }

    @Test
    public void testCheckBoxAllValues(){
        checkBoxElement.setAllCheckBoxes();
        assertTrue(checkBoxElement.isAllCheckboxesSet());
        checkBoxElement.unsetAllCheckBoxes();
        assertFalse(checkBoxElement.isAllCheckboxesSet());
    }

    @Override
    public void setUp() {
        BasePage.getDriver().get(PageSubURLsEnum.WWW_FONT_URL.subURL() + PageSubURLsEnum.REGISTRATION.subURL());
        this.checkBoxElement = BasePage.getDriver().elementCheckbox(CheckBoxTest.hobbyCheckBoxSelector);
    }

    @Override
    public void tearDown() {
    }
}
