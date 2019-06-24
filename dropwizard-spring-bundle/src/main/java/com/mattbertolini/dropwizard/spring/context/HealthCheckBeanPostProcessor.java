package com.mattbertolini.dropwizard.spring.context;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HealthCheckBeanPostProcessor extends AbstractGenericBeanPostProcessor<HealthCheck> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckBeanPostProcessor.class);
    
    private final Environment environment;

    protected HealthCheckBeanPostProcessor(Environment environment) {
        super(HealthCheck.class);
        this.environment = environment;
    }

    @Override
    protected HealthCheck doPostProcessAfterInitialization(HealthCheck bean, String beanName) {
        LOGGER.debug("Adding health check '{}' to Dropwizard health check registry", bean.getClass().getName());
        environment.healthChecks().register(beanName, bean);
        return bean;
    }
}
