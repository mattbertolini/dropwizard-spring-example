package com.mattbertolini.dropwizard.spring.context;

import com.mattbertolini.dropwizard.spring.ServletEnvironmentInitializer;
import io.dropwizard.setup.Environment;

public class ServletEnvironmentInitializerBeanPostProcessor extends AbstractGenericBeanPostProcessor<ServletEnvironmentInitializer> {
    private final Environment environment;

    protected ServletEnvironmentInitializerBeanPostProcessor(Environment environment) {
        super(ServletEnvironmentInitializer.class);
        this.environment = environment;
    }

    @Override
    protected ServletEnvironmentInitializer doPostProcessAfterInitialization(ServletEnvironmentInitializer bean, String beanName) {
        bean.onStartup(environment.servlets());
        return bean;
    }
}
