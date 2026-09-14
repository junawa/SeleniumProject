package com.saucedemo.framework.testdata.models;

import java.math.BigDecimal;

/** Immutable expected product information. */
public record ProductData(String productId, String name, BigDecimal price, String description) {
}
