package com.saucedemo.framework.pages;

import com.saucedemo.framework.base.BasePage;
import com.saucedemo.framework.testdata.models.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class CartPage extends BasePage {

    private static final By CART_LIST = By.cssSelector("[data-test='cart-list']");
    private static final By CART_PRODUCT_NAMES = By.cssSelector("[data-test='inventory-item-name']");
    private static final By CHECKOUT_BUTTON = By.cssSelector("[data-test='checkout']");

    public CartPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(CART_LIST);
    }

    public boolean containsProduct(final ProductData product) {
        return findTexts(CART_PRODUCT_NAMES).contains(product.name());
    }

    public CartPage removeProduct(final ProductData product) {
        final By removeButton = By.cssSelector("[data-test='remove-" + product.productId() + "']");
        click(removeButton);
        waits.untilInvisible(removeButton);
        return this;
    }

    public CheckoutPage beginCheckout() {
        click(CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }
}
