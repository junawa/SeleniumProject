package com.saucedemo.framework.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/** Thin client exposing the common HTTP verbs without coupling them to UI tests. */
public final class ApiClient {

    private final RequestSpecification specification;

    public ApiClient() {
        specification = RequestSpecificationFactory.create();
    }

    public Response get(final String path) {
        return RestAssured.given().spec(specification).when().get(path);
    }

    public Response post(final String path, final Object body) {
        return RestAssured.given().spec(specification).body(body).when().post(path);
    }

    public Response put(final String path, final Object body) {
        return RestAssured.given().spec(specification).body(body).when().put(path);
    }

    public Response patch(final String path, final Object body) {
        return RestAssured.given().spec(specification).body(body).when().patch(path);
    }

    public Response delete(final String path) {
        return RestAssured.given().spec(specification).when().delete(path);
    }
}
