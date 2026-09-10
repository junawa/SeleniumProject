package com.saucedemo.framework.pages;

import com.saucedemo.framework.base.BasePage;
import com.saucedemo.framework.testdata.models.CheckoutData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class CheckoutPage extends BasePage {

    private static final By CHECKOUT_FORM = By.cssSelector("[data-test='checkout-info-container']");
    private static final By FIRST_NAME_FIELD = By.cssSelector("[data-test='firstName']");
    private static final By LAST_NAME_FIELD = By.cssSelector("[data-test='lastName']");
    private static final By POSTAL_CODE_FIELD = By.cssSelector("[data-test='postalCode']");
    private static final By CONTINUE_BUTTON = By.cssSelector("[data-test='continue']");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public CheckoutPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(CHECKOUT_FORM);
    }

    public CheckoutOverviewPage continueWith(final CheckoutData customer) {
        type(FIRST_NAME_FIELD, customer.firstName());
        type(LAST_NAME_FIELD, customer.lastName());
        type(POSTAL_CODE_FIELD, customer.postalCode());
        click(CONTINUE_BUTTON);
        return new CheckoutOverviewPage(driver);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(ERROR_MESSAGE);
    }
}
