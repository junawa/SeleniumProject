package tests.regression;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import com.saucedemo.framework.workflows.SauceDemoWorkflow;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class TC_REG_Cart extends BaseTest {

    @Test(description = "A shopper can remove an item from the cart")
    public void shopperCanRemoveProductFromCart() {
        final SauceDemoWorkflow workflow = new SauceDemoWorkflow(getDriver());

        workflow.loginAs(TestDataFactory.getStandardUser())
                .addProductToCart(TestDataFactory.getBackpack())
                .openCart()
                .removeProductFromCart(TestDataFactory.getBackpack());

        Assert.assertFalse(workflow.cartPage().containsProduct(TestDataFactory.getBackpack()),
                "Cart should no longer contain the removed product.");
    }
}
