package com.saucedemo.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/** Loads immutable-at-runtime framework configuration for the selected environment. */
public final class ConfigManager {

    private static final String DEFAULT_ENVIRONMENT = "local";
    private static final ConfigManager INSTANCE = new ConfigManager();
    private final Properties properties = new Properties();

    private ConfigManager() {
        final String environment = System.getProperty("env", DEFAULT_ENVIRONMENT);
        final String resourcePath = "config/" + environment + ".properties";

        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file was not found: " + resourcePath);
            }
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load configuration: " + resourcePath, exception);
        }
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public String getBaseUrl() {
        return getRequired("baseUrl");
    }

    public String getBrowser() {
        return getRequired("browser");
    }

    public int getTimeoutSeconds() {
        return Integer.parseInt(getRequired("timeout"));
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getRequired("headless"));
    }

    public String getApiBaseUri() {
        return getRequired("apiBaseUri");
    }

    public String getRequired(final String key) {
        final String value = get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Required configuration value is missing: " + key);
        }
        return value.trim();
    }

    public String get(final String key) {
        Objects.requireNonNull(key, "Configuration key must not be null");
        final String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        final String environmentValue = System.getenv(key.replaceAll("([a-z])([A-Z])", "$1_$2")
                .toUpperCase());
        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue;
        }
        return properties.getProperty(key);
    }
}
