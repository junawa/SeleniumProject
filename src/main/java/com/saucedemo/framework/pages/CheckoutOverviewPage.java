package com.saucedemo.framework.pages;

import com.saucedemo.framework.base.BasePage;
import com.saucedemo.framework.testdata.models.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class CheckoutOverviewPage extends BasePage {

    private static final By OVERVIEW_CONTAINER = By.cssSelector("[data-test='checkout-summary-container']");
    private static final By PRODUCT_NAMES = By.cssSelector("[data-test='inventory-item-name']");
    private static final By FINISH_BUTTON = By.cssSelector("[data-test='finish']");

    public CheckoutOverviewPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(OVERVIEW_CONTAINER);
    }

    public boolean containsProduct(final ProductData product) {
        return getTexts(PRODUCT_NAMES).contains(product.name());
    }

    public CheckoutCompletePage finishCheckout() {
        click(FINISH_BUTTON);
        return new CheckoutCompletePage(driver);
    }
}
