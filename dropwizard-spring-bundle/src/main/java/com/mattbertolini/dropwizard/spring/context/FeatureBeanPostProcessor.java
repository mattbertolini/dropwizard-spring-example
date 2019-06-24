package com.mattbertolini.dropwizard.spring.context;

import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Feature;

/**
 * A Spring {@code BeanPostProcessor} that registers the given JAX-RS {@code Feature} with Dropwizard.
 */
public class FeatureBeanPostProcessor extends AbstractGenericBeanPostProcessor<Feature> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureBeanPostProcessor.class);
    
    private final Environment environment;

    protected FeatureBeanPostProcessor(Environment environment) {
        super(Feature.class);
        this.environment = environment;
    }

    @Override
    protected Feature doPostProcessAfterInitialization(Feature bean, String beanName) {
        LOGGER.debug("Registering JAX-RS feature '{}' with Dropwizard", bean.getClass().getName());
        environment.jersey().register(bean);
        return bean;
    }
}
