package com.saucedemo.framework.testdata.factory;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.saucedemo.framework.testdata.models.CheckoutData;
import com.saucedemo.framework.testdata.models.ProductData;
import com.saucedemo.framework.testdata.models.UserData;
import com.saucedemo.framework.testdata.readers.JsonDataReader;

/** Provides typed, fresh test data without exposing physical JSON resources to tests. */
public final class TestDataFactory {

    private static final String USERS_RESOURCE = "testdata/ui/users.json";
    private static final String PRODUCTS_RESOURCE = "testdata/ui/products.json";
    private static final String CHECKOUT_RESOURCE = "testdata/ui/checkout.json";
    private static final JsonDataReader JSON_READER = new JsonDataReader();

    private TestDataFactory() {
    }

    public static UserData getStandardUser() {
        return getUser("standardUser");
    }

    public static UserData getLockedUser() {
        return getUser("lockedUser");
    }

    public static UserData getProblemUser() {
        return getUser("problemUser");
    }

    public static UserData getInvalidUser() {
        return getUser("invalidUser");
    }

    public static ProductData getBackpack() {
        return getProduct("backpack");
    }

    public static ProductData getBikeLight() {
        return getProduct("bikeLight");
    }

    public static ProductData getBoltTShirt() {
        return getProduct("boltTShirt");
    }

    public static CheckoutData getValidCustomer() {
        return getCheckoutData("validCustomer");
    }

    public static CheckoutData getMissingFirstNameCustomer() {
        return getCheckoutData("missingFirstName");
    }

    public static CheckoutData getMissingLastNameCustomer() {
        return getCheckoutData("missingLastName");
    }

    public static CheckoutData getMissingPostalCodeCustomer() {
        return getCheckoutData("missingPostalCode");
    }

    public static String getCreatePostPayload() {
        return JSON_READER.readRaw("testdata/api/requests/create-post.json");
    }

    private static UserData getUser(final String key) {
        return getRequired(readUsers(), key, "user");
    }

    private static ProductData getProduct(final String key) {
        return getRequired(readProducts(), key, "product");
    }

    private static CheckoutData getCheckoutData(final String key) {
        return getRequired(readCheckoutData(), key, "checkout data");
    }

    private static Map<String, UserData> readUsers() {
        return JSON_READER.readMap(USERS_RESOURCE, new TypeReference<>() { });
    }

    private static Map<String, ProductData> readProducts() {
        return JSON_READER.readMap(PRODUCTS_RESOURCE, new TypeReference<>() { });
    }

    private static Map<String, CheckoutData> readCheckoutData() {
        return JSON_READER.readMap(CHECKOUT_RESOURCE, new TypeReference<>() { });
    }

    private static <T> T getRequired(final Map<String, T> data, final String key, final String dataType) {
        final T value = data.get(key);
        if (value == null) {
            throw new IllegalArgumentException("No " + dataType + " exists for key: " + key);
        }
        return value;
    }
}
