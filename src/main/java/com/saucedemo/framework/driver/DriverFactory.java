package com.saucedemo.framework.driver;

import java.util.Locale;
import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

/**
 * Creates and owns one WebDriver instance per executing test thread.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void createDriver(final String browser, final boolean headless) {
        if (DRIVER.get() != null) {
            throw new IllegalStateException("A WebDriver is already assigned to this thread.");
        }

        DRIVER.set(createBrowser(browser, headless));
    }

    public static WebDriver getDriver() {
        final WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("No WebDriver is assigned to the current thread.");
        }
        return driver;
    }

    public static void quitDriver() {
        final WebDriver driver = DRIVER.get();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DRIVER.remove();
        }
    }

    private static WebDriver createBrowser(final String browser, final boolean headless) {
        final String normalizedBrowser = Objects.requireNonNull(browser, "Browser must not be null")
                .trim()
                .toLowerCase(Locale.ROOT);

        return switch (normalizedBrowser) {
            case "chrome" -> new ChromeDriver(createChromeOptions(headless));
            case "edge" -> new EdgeDriver(createEdgeOptions(headless));
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser + ". Supported browsers: chrome, edge.");
        };
    }

    private static ChromeOptions createChromeOptions(final boolean headless) {
        final ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080",
                    "--no-sandbox", "--disable-dev-shm-usage");
        } else {
            options.addArguments("--start-maximized");
        }
        return options;
    }

    private static EdgeOptions createEdgeOptions(final boolean headless) {
        final EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080",
                    "--no-sandbox", "--disable-dev-shm-usage");
        } else {
            options.addArguments("--start-maximized");
        }
        return options;
    }
}
