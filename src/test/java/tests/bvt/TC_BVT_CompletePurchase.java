package tests.bvt;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import com.saucedemo.framework.workflows.SauceDemoWorkflow;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class TC_BVT_CompletePurchase extends BaseTest {

    @Test(description = "A standard shopper can complete a basic purchase")
    public void standardUserCanCompletePurchase() {
        final SauceDemoWorkflow workflow = new SauceDemoWorkflow(getDriver());

        Assert.assertTrue(workflow.loginAs(TestDataFactory.getStandardUser())
                        .addProductToCart(TestDataFactory.getBackpack())
                        .openCart()
                        .completeCheckout(TestDataFactory.getValidCustomer())
                        .isOrderComplete(),
                "Purchase confirmation should be displayed after checkout.");
    }
}
