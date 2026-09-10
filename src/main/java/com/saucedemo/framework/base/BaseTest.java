package com.saucedemo.framework.base;

import com.saucedemo.framework.driver.DriverFactory;
import com.saucedemo.framework.config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * TestNG base class responsible only for browser lifecycle management.
 */
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpDriver() {
        DriverFactory.createDriver(ConfigManager.getInstance().getBrowser(),
                ConfigManager.getInstance().isHeadless());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver() {
        DriverFactory.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
