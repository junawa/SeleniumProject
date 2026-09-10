package tests.e2e;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import com.saucedemo.framework.workflows.SauceDemoWorkflow;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class TC_E2E_PurchaseProduct extends BaseTest {

    @Test(description = "A shopper can complete the end-to-end purchase journey")
    public void shopperCanPurchaseProduct() {
        final SauceDemoWorkflow workflow = new SauceDemoWorkflow(getDriver());

        Assert.assertTrue(workflow.loginAs(TestDataFactory.getStandardUser())
                        .addProductToCart(TestDataFactory.getBoltTShirt())
                        .openCart()
                        .completeCheckout(TestDataFactory.getValidCustomer())
                        .isOrderComplete(),
                "End-to-end purchase should complete successfully.");
    }
}
