package com.saucedemo.framework.pages;

import java.util.List;

import com.saucedemo.framework.base.BasePage;
import com.saucedemo.framework.testdata.models.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class ProductsPage extends BasePage {

    private static final By INVENTORY_CONTAINER = By.cssSelector("[data-test='inventory-container']");
    private static final By PRODUCT_NAMES = By.cssSelector("[data-test='inventory-item-name']");
    private static final By SORT_DROPDOWN = By.cssSelector("[data-test='product-sort-container']");
    private static final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");

    public ProductsPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(INVENTORY_CONTAINER);
    }

    public List<String> getProductNames() {
        return getTexts(PRODUCT_NAMES);
    }

    public boolean containsProduct(final ProductData product) {
        return getProductNames().contains(product.name());
    }

    public ProductsPage addProductToCart(final ProductData product) {
        click(By.cssSelector("[data-test='add-to-cart-" + product.productId() + "']"));
        return this;
    }

    public ProductDetailsPage openProduct(final ProductData product) {
        click(By.xpath("//div[@data-test='inventory-item-name' and normalize-space()='"
                + product.name() + "']"));
        return new ProductDetailsPage(driver);
    }

    public ProductsPage sortBy(final String visibleOption) {
        selectDropdownByVisibleText(SORT_DROPDOWN, visibleOption);
        return this;
    }

    public CartPage openCart() {
        click(CART_LINK);
        return new CartPage(driver);
    }
}
