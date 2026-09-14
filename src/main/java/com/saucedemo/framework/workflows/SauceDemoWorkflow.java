package com.saucedemo.framework.workflows;

import com.saucedemo.framework.components.NavigationMenu;
import com.saucedemo.framework.config.ConfigManager;
import com.saucedemo.framework.pages.CartPage;
import com.saucedemo.framework.pages.CheckoutCompletePage;
import com.saucedemo.framework.pages.CheckoutPage;
import com.saucedemo.framework.pages.CheckoutOverviewPage;
import com.saucedemo.framework.pages.LoginPage;
import com.saucedemo.framework.pages.ProductsPage;
import com.saucedemo.framework.testdata.models.CheckoutData;
import com.saucedemo.framework.testdata.models.ProductData;
import com.saucedemo.framework.testdata.models.UserData;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Stateful, per-test composition of meaningful SauceDemo business actions. */
public final class SauceDemoWorkflow {

    private static final Logger LOGGER = LoggerFactory.getLogger(SauceDemoWorkflow.class);
    private final WebDriver driver;
    private ProductsPage productsPage;
    private CartPage cartPage;

    public SauceDemoWorkflow(final WebDriver driver) {
        this.driver = driver;
    }

    @Step("Log in")
    public SauceDemoWorkflow loginAs(final UserData user) {
        LOGGER.info("Logging in as user: {}", user.username());
        productsPage = new LoginPage(driver).open(ConfigManager.getInstance().getBaseUrl()).login(user);
        return this;
    }

    @Step("Add {product.name} to cart")
    public SauceDemoWorkflow addProductToCart(final ProductData product) {
        LOGGER.info("Adding product to cart: {}", product.name());
        requireProductsPage().addProductToCart(product);
        return this;
    }

    @Step("Open cart")
    public SauceDemoWorkflow openCart() {
        LOGGER.info("Opening cart");
        cartPage = requireProductsPage().openCart();
        return this;
    }

    @Step("Remove {product.name} from cart")
    public SauceDemoWorkflow removeProductFromCart(final ProductData product) {
        requireCartPage().removeProduct(product);
        return this;
    }

    @Step("Complete checkout")
    public CheckoutCompletePage completeCheckout(final CheckoutData customer) {
        LOGGER.info("Completing checkout");
        final CheckoutPage checkoutPage = requireCartPage().beginCheckout();
        final CheckoutOverviewPage overviewPage = checkoutPage.continueWith(customer);
        return overviewPage.finishCheckout();
    }

    @Step("Log out")
    public LoginPage logout() {
        LOGGER.info("Logging out");
        return new NavigationMenu(driver).logout();
    }

    public ProductsPage productsPage() {
        return requireProductsPage();
    }

    public CartPage cartPage() {
        return requireCartPage();
    }

    private ProductsPage requireProductsPage() {
        if (productsPage == null) {
            throw new IllegalStateException("Login must be completed before this action.");
        }
        return productsPage;
    }

    private CartPage requireCartPage() {
        if (cartPage == null) {
            throw new IllegalStateException("Cart must be opened before this action.");
        }
        return cartPage;
    }
}
