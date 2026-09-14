package com.saucedemo.framework.utils;

import java.time.Instant;
import java.util.UUID;

/** Creates per-invocation unique data only when a target application requires it. */
public final class RandomDataGenerator {

    private RandomDataGenerator() {
    }

    public static String uniqueEmail(final String prefix) {
        return prefix + "+" + Instant.now().toEpochMilli() + "-" + UUID.randomUUID() + "@example.test";
    }

    public static String uniqueValue(final String prefix) {
        return prefix + "-" + UUID.randomUUID();
    }
}
