package tests.bvt;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.config.ConfigManager;
import com.saucedemo.framework.pages.LoginPage;
import com.saucedemo.framework.pages.ProductsPage;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class TC_BVT_Login_ValidCredentials extends BaseTest {

    @Test(description = "A standard user can log in and reach the product catalogue")
    public void standardUserCanLogIn() {
        final ProductsPage productsPage = new LoginPage(getDriver())
                .open(ConfigManager.getInstance().getBaseUrl())
                .login(TestDataFactory.getStandardUser());

        Assert.assertTrue(productsPage.isDisplayed(),
                "Products page should be displayed after a successful login.");
    }
}
