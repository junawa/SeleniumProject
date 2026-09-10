package tests.regression;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.pages.CheckoutPage;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import com.saucedemo.framework.testdata.models.CheckoutData;
import com.saucedemo.framework.workflows.SauceDemoWorkflow;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public final class TC_REG_CheckoutValidation extends BaseTest {

    @DataProvider(name = "invalidCustomers")
    public Object[][] invalidCustomers() {
        return new Object[][] {
                { TestDataFactory.getMissingFirstNameCustomer() },
                { TestDataFactory.getMissingLastNameCustomer() },
                { TestDataFactory.getMissingPostalCodeCustomer() }
        };
    }

    @Test(dataProvider = "invalidCustomers", description = "Checkout requires all mandatory customer fields")
    public void checkoutRejectsMissingMandatoryData(final CheckoutData customer) {
        final SauceDemoWorkflow workflow = new SauceDemoWorkflow(getDriver());
        workflow.loginAs(TestDataFactory.getStandardUser())
                .addProductToCart(TestDataFactory.getBackpack())
                .openCart();

        final CheckoutPage checkoutPage = workflow.cartPage().beginCheckout();
        checkoutPage.continueWith(customer);

        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Checkout should show an error when required customer information is missing.");
    }
}
