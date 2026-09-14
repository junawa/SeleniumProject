package com.saucedemo.framework.testdata.models;

/** Immutable customer information for checkout validation. */
public record CheckoutData(String firstName, String lastName, String postalCode) {
}
