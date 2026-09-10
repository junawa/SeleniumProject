package tests.regression;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.saucedemo.framework.base.BaseTest;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import com.saucedemo.framework.workflows.SauceDemoWorkflow;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class TC_REG_ProductSorting extends BaseTest {

    @Test(description = "Products can be sorted by name from Z to A")
    public void productsCanBeSortedByNameDescending() {
        final SauceDemoWorkflow workflow = new SauceDemoWorkflow(getDriver());
        final List<String> actualNames = workflow.loginAs(TestDataFactory.getStandardUser())
                .productsPage()
                .sortBy("Name (Z to A)")
                .getProductNames();
        final List<String> expectedNames = new ArrayList<>(actualNames);
        expectedNames.sort(Comparator.reverseOrder());

        Assert.assertEquals(actualNames, expectedNames,
                "Products should appear in descending alphabetical order.");
    }
}
