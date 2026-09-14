package com.saucedemo.framework.pages;

import com.saucedemo.framework.base.BasePage;
import com.saucedemo.framework.testdata.models.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class ProductDetailsPage extends BasePage {

    private static final By PRODUCT_NAME = By.cssSelector("[data-test='inventory-item-name']");
    private static final By ADD_TO_CART_BUTTON = By.cssSelector("[data-test^='add-to-cart-']");
    private static final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");

    public ProductDetailsPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isShowing(final ProductData product) {
        return product.name().equals(getText(PRODUCT_NAME));
    }

    public ProductDetailsPage addProductToCart() {
        click(ADD_TO_CART_BUTTON);
        return this;
    }

    public CartPage openCart() {
        click(CART_LINK);
        return new CartPage(driver);
    }
}
