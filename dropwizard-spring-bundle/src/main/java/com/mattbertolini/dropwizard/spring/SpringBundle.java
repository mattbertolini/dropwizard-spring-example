package com.mattbertolini.dropwizard.spring;

import com.mattbertolini.dropwizard.spring.context.DropwizardEnvironmentBeanFactoryPostProcessor;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * A Dropwizard {@code ConfiguredBundle} that initializes a Spring {@code ApplicationContext}.
 *
 * @param <T> the type of configuration class for this application
 */
public class SpringBundle<T extends Configuration> implements ConfiguredBundle<T>, SpringConfigurable<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBundle.class);

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // Do nothing
    }

    @Override
    public void run(T configuration, Environment environment) {
        SpringConfiguration springConfiguration = getSpringConfiguration(configuration);
        ConfigurableApplicationContext applicationContext = springConfiguration.createApplicationContext();
        applicationContext.addBeanFactoryPostProcessor(new DropwizardEnvironmentBeanFactoryPostProcessor(configuration, environment));
        registerApplicationContextWithLifecycle(applicationContext, environment);
        LOGGER.debug("Refreshing {}, '{}'", applicationContext.getClass().getSimpleName(), applicationContext.getId());
        applicationContext.refresh();
    }

    private void registerApplicationContextWithLifecycle(ConfigurableApplicationContext applicationContext, Environment environment) {
        environment.lifecycle().manage(new Managed() {
            @Override
            public void start() {
                // Do nothing. The starting/refreshing of the Spring context must be done before the Dropwizard lifecyle executes.
            }

            @Override
            public void stop() {
                LOGGER.debug("Shutting down {} '{}'", applicationContext.getClass().getSimpleName(), applicationContext.getId());
                applicationContext.stop();
            }
        });
    }
}
