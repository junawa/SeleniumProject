package com.saucedemo.framework.pages;

import com.saucedemo.framework.base.BasePage;
import com.saucedemo.framework.testdata.models.UserData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class LoginPage extends BasePage {

    private static final By USERNAME_FIELD = By.cssSelector("[data-test='username']");
    private static final By PASSWORD_FIELD = By.cssSelector("[data-test='password']");
    private static final By LOGIN_BUTTON = By.cssSelector("[data-test='login-button']");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(final WebDriver driver) {
        super(driver);
    }

    public LoginPage open(final String baseUrl) {
        driver.get(baseUrl);
        waitForElement(LOGIN_BUTTON);
        return this;
    }

    public boolean isDisplayed() {
        return isDisplayed(LOGIN_BUTTON);
    }

    public ProductsPage login(final UserData user) {
        type(USERNAME_FIELD, user.username());
        type(PASSWORD_FIELD, user.password());
        click(LOGIN_BUTTON);
        return new ProductsPage(driver);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }
}
