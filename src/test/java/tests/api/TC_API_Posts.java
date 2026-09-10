package tests.api;

import com.saucedemo.framework.api.ApiClient;
import com.saucedemo.framework.testdata.factory.TestDataFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class TC_API_Posts {

    @Test(description = "A post can be retrieved through the reusable API client")
    public void canGetPostById() {
        final Response response = new ApiClient().get("/posts/1");

        Assert.assertEquals(response.statusCode(), 200, "GET post should return HTTP 200.");
        Assert.assertEquals(response.jsonPath().getInt("id"), 1, "Response should contain post 1.");
    }

    @Test(description = "A post payload can be created from external API test data")
    public void canCreatePostFromJsonPayload() {
        final Response response = new ApiClient().post("/posts", TestDataFactory.getCreatePostPayload());

        Assert.assertEquals(response.statusCode(), 201, "POST should return HTTP 201.");
    }
}
