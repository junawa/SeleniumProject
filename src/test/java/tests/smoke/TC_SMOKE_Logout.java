package tests.smoke;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.pages.LoginPage;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import com.saucedemo.framework.workflows.SauceDemoWorkflow;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class TC_SMOKE_Logout extends BaseTest {

    @Test(description = "A logged-in user can log out")
    public void userCanLogOut() {
        final LoginPage loginPage = new SauceDemoWorkflow(getDriver())
                .loginAs(TestDataFactory.getStandardUser())
                .logout();

        Assert.assertTrue(loginPage.isDisplayed(), "Login page should be displayed after logout.");
    }
}
