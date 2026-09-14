package com.saucedemo.framework.testdata.readers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Reads classpath JSON resources and converts them to typed models. */
public final class JsonDataReader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> Map<String, T> readMap(final String resourcePath, final TypeReference<Map<String, T>> type) {
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Test-data resource was not found: " + resourcePath);
            }
            return objectMapper.readValue(input, type);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to parse test-data resource: " + resourcePath, exception);
        }
    }

    public String readRaw(final String resourcePath) {
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Test-data resource was not found: " + resourcePath);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read test-data resource: " + resourcePath, exception);
        }
    }
}
