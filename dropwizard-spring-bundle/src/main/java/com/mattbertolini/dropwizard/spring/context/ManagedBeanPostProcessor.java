package com.mattbertolini.dropwizard.spring.context;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ManagedBeanPostProcessor extends AbstractGenericBeanPostProcessor<Managed> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagedBeanPostProcessor.class);

    private final Environment environment;

    public ManagedBeanPostProcessor(Environment environment) {
        super(Managed.class);
        this.environment = environment;
    }

    @Override
    protected Managed doPostProcessAfterInitialization(Managed bean, String beanName) {
        LOGGER.debug("Adding managed class '{}' to Dropwizard lifecycle", bean.getClass().getName());
        environment.lifecycle().manage(bean);
        return bean;
    }
}
