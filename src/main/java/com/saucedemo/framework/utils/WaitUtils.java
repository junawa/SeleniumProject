package com.saucedemo.framework.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Explicit wait operations shared by page objects. */
public final class WaitUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(final WebDriver driver, final Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    public WebElement untilVisible(final By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement untilPresent(final By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement untilClickable(final By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public List<WebElement> untilAllVisible(final By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public boolean untilInvisible(final By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean untilUrlContains(final String expectedUrlFragment) {
        return wait.until(ExpectedConditions.urlContains(expectedUrlFragment));
    }

    public boolean untilTitleContains(final String expectedTitleFragment) {
        return wait.until(ExpectedConditions.titleContains(expectedTitleFragment));
    }

    public void untilPageReady() {
        wait.until(webDriver -> "complete".equals(((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")));
    }
}
