package com.saucedemo.framework.listeners;

import java.io.ByteArrayInputStream;

import com.saucedemo.framework.driver.DriverFactory;
import com.saucedemo.framework.utils.ScreenshotUtils;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Adds safe failure diagnostics and concise lifecycle logging to TestNG execution. */
public final class TestListener implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(final ITestResult result) {
        LOGGER.info("Starting test: {}", result.getMethod().getQualifiedName());
    }

    @Override
    public void onTestSuccess(final ITestResult result) {
        LOGGER.info("Passed test: {}", result.getMethod().getQualifiedName());
    }

    @Override
    public void onTestFailure(final ITestResult result) {
        LOGGER.error("Failed test: {}", result.getMethod().getQualifiedName(), result.getThrowable());
        attachBrowserDiagnostics();
    }

    @Override
    public void onTestSkipped(final ITestResult result) {
        LOGGER.warn("Skipped test: {}", result.getMethod().getQualifiedName());
    }

    @Override
    public void onStart(final ITestContext context) {
        LOGGER.info("Starting suite: {}", context.getName());
    }

    @Override
    public void onFinish(final ITestContext context) {
        LOGGER.info("Finished suite: {}", context.getName());
    }

    private void attachBrowserDiagnostics() {
        try {
            final WebDriver driver = DriverFactory.getDriver();
            Allure.addAttachment("Current URL", "text/plain", driver.getCurrentUrl(), ".txt");
            Allure.addAttachment("Failure screenshot", "image/png",
                    new ByteArrayInputStream(ScreenshotUtils.capture(driver)), ".png");
        } catch (IllegalStateException exception) {
            LOGGER.debug("No browser diagnostics available for this test.");
        } catch (RuntimeException exception) {
            LOGGER.warn("Unable to attach browser diagnostics.", exception);
        }
    }
}
