package com.mattbertolini.dropwizard.spring;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * An extension of the Dropwizard {@code Application} class that automatically initializes the SpringBundle. It is
 * assumed that all resources will be defined in the Spring context so no run method is allowed to be overridden.
 *
 * @param <T> the type of configuration class for this application
 */
public class SpringApplication<T extends Configuration> extends Application<T> implements SpringConfigurable<T> {
    @Override
    public void initialize(Bootstrap<T> bootstrap) {
        //noinspection Convert2Diamond
        bootstrap.addBundle(new SpringBundle<T>() {
            @Override
            public SpringConfiguration getSpringConfiguration(T configuration) {
                return SpringApplication.this.getSpringConfiguration(configuration);
            }
        });
    }

    @Override
    public final void run(T configuration, Environment environment) {
        // Do nothing. Everything is handled by SpringBundle.
    }
}
