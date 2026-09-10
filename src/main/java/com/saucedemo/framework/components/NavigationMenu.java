package com.saucedemo.framework.components;

import com.saucedemo.framework.base.BasePage;
import com.saucedemo.framework.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Shared SauceDemo navigation menu interactions. */
public final class NavigationMenu extends BasePage {

    private static final By MENU_BUTTON = By.xpath("//button[@id='react-burger-menu-btn']");
    private static final By LOGOUT_LINK = By.cssSelector("[data-test='logout-sidebar-link']");

    public NavigationMenu(final WebDriver driver) {
        super(driver);
    }

    public LoginPage logout() {
        click(MENU_BUTTON);
        click(LOGOUT_LINK);
        return new LoginPage(driver);
    }
}
