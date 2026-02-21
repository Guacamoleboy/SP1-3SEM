package app.util;

import app.exception.ApiException;
import app.exception.ResourceNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// ################################### //
//      .properties file handler       //
// ################################### //

// Created by: Guacamoleboy
// ________________________
// Last updated: 21/02-2026
// By: Guacamoleboy

public class Util {

    // Attributes

    // _________________________________________________________________

    public static String getPropertyValue(String propertyName, String resourceName)  {

        // Try-with-resource to auto close after try-catch is done (avoids .finally manual close()).
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(resourceName)) {

            // Validation if the .properties FILE exist
            if (inputStream == null) {
                throw new ResourceNotFoundException(
                    "Property file",
                    resourceName
                );
            }

            // Setup & Load
            Properties properties = new Properties();
            properties.load(inputStream);

            // Check if the property exist (line in .properties file)
            String value = properties.getProperty(propertyName);
            if (value == null) {
                throw new ResourceNotFoundException(
                    "Property",
                    String.format("%s in %s", propertyName, resourceName)
                );
            }

            // return as trim() for whitespace removal in case there's any.
            return value.trim();

        } catch (IOException ex) {
            // Input Output (I/O) exception handling
            throw new ApiException(
                500,
                String.format("Could not read property %s from %s", propertyName, resourceName),
                String.valueOf(ex)
            );
        }

    }

    // _________________________________________________________________

}