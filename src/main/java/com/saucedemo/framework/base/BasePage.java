package com.saucedemo.framework.base;

import java.time.Duration;
import java.util.List;

import com.saucedemo.framework.config.ConfigManager;
import com.saucedemo.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/** Reusable, non-business-specific browser interactions for page objects. */
public abstract class BasePage {

    private static final int NAVIGATION_ATTEMPTS = 2;
    protected final WebDriver driver;
    protected final WaitUtils waits;

    protected BasePage(final WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver,
                Duration.ofSeconds(ConfigManager.getInstance().getTimeoutSeconds()));
    }

    protected void click(final By locator) {
        waits.untilClickable(locator).click();
    }

    protected void type(final By locator, final String value) {
        final WebElement element = waits.untilVisible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected String getText(final By locator) {
        return waits.untilVisible(locator).getText();
    }

    protected List<String> getTexts(final By locator) {
        return waits.untilAllVisible(locator).stream().map(WebElement::getText).toList();
    }

    protected List<String> findTexts(final By locator) {
        return driver.findElements(locator).stream().map(WebElement::getText).toList();
    }

    protected boolean isDisplayed(final By locator) {
        try {
            return waits.untilVisible(locator).isDisplayed();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    protected WebElement waitForElement(final By locator) {
        return waits.untilVisible(locator);
    }

    protected void waitForPageLoad() {
        waits.untilPageReady();
    }

    /**
     * Opens a page, retrying once when a remote browser renderer times out during navigation.
     */
    protected void navigateTo(final String url) {
        for (int attempt = 1; attempt <= NAVIGATION_ATTEMPTS; attempt++) {
            try {
                driver.get(url);
                return;
            } catch (TimeoutException exception) {
                if (attempt == NAVIGATION_ATTEMPTS) {
                    throw exception;
                }
            }
        }
        throw new IllegalStateException("Navigation retry loop did not run.");
    }

    protected void selectDropdownByVisibleText(final By locator, final String text) {
        new Select(waits.untilVisible(locator)).selectByVisibleText(text);
    }

    protected void scrollToElement(final By locator) {
        final WebElement element = waits.untilPresent(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
}
