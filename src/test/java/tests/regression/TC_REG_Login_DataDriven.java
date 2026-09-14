package tests.regression;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.config.ConfigManager;
import com.saucedemo.framework.pages.LoginPage;
import com.saucedemo.framework.pages.ProductsPage;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import com.saucedemo.framework.testdata.models.UserData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public final class TC_REG_Login_DataDriven extends BaseTest {

    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][] {
                { TestDataFactory.getStandardUser(), true },
                { TestDataFactory.getLockedUser(), false },
                { TestDataFactory.getInvalidUser(), false }
        };
    }

    @Test(dataProvider = "loginScenarios", description = "Login behaviour matches the supplied user data")
    public void loginBehaviourMatchesUserType(final UserData user, final boolean shouldSucceed) {
        final LoginPage loginPage = new LoginPage(getDriver()).open(ConfigManager.getInstance().getBaseUrl());
        final ProductsPage productsPage = loginPage.login(user);

        if (shouldSucceed) {
            Assert.assertTrue(productsPage.isDisplayed(),
                    "Products page should be displayed for valid credentials.");
        } else {
            Assert.assertTrue(loginPage.isErrorDisplayed(),
                    "An error message should be displayed for invalid credentials.");
        }
    }
}
