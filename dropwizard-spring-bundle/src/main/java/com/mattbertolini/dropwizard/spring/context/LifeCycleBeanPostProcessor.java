package com.mattbertolini.dropwizard.spring.context;

import io.dropwizard.setup.Environment;
import org.eclipse.jetty.util.component.LifeCycle;

public class LifeCycleBeanPostProcessor extends AbstractGenericBeanPostProcessor<LifeCycle> {
    private final Environment environment;

    public LifeCycleBeanPostProcessor(Environment environment) {
        super(LifeCycle.class);
        this.environment = environment;
    }

    @Override
    protected LifeCycle doPostProcessAfterInitialization(LifeCycle bean, String beanName) {
        environment.lifecycle().manage(bean);
        return bean;
    }
}
