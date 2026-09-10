package com.saucedemo.framework.api;

import com.saucedemo.framework.config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/** Central API defaults. Authentication belongs here when an API requires it. */
public final class RequestSpecificationFactory {

    private RequestSpecificationFactory() {
    }

    public static RequestSpecification create() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getInstance().getApiBaseUri())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
