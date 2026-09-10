package tests.smoke;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import com.saucedemo.framework.workflows.SauceDemoWorkflow;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class TC_SMOKE_Cart extends BaseTest {

    @Test(description = "A shopper can add a product and see it in the cart")
    public void shopperCanAddProductToCart() {
        final SauceDemoWorkflow workflow = new SauceDemoWorkflow(getDriver());

        workflow.loginAs(TestDataFactory.getStandardUser())
                .addProductToCart(TestDataFactory.getBikeLight())
                .openCart();

        Assert.assertTrue(workflow.cartPage().containsProduct(TestDataFactory.getBikeLight()),
                "Cart should contain the selected product.");
    }
}
