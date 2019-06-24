package com.mattbertolini.dropwizard.example;

import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A quick class implementing {@code Managed} to check if Spring-defined managed classes are added to the Dropwizard
 * environment.
 */
public class LoggingManaged implements Managed {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingManaged.class);

    @Override
    public void start() throws Exception {
        LOGGER.info("Staring managed");
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("Stopping managed");
    }
}
