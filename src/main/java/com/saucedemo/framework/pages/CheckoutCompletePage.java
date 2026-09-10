package com.saucedemo.framework.pages;

import com.saucedemo.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class CheckoutCompletePage extends BasePage {

    private static final By COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");

    public CheckoutCompletePage(final WebDriver driver) {
        super(driver);
    }

    public boolean isOrderComplete() {
        return isDisplayed(COMPLETE_HEADER);
    }

    public String getConfirmationMessage() {
        return getText(COMPLETE_HEADER);
    }
}
