package com.mattbertolini.dropwizard.spring.context;

import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import javax.ws.rs.ext.Provider;

public class JerseyProviderBeanPostProcessor implements BeanPostProcessor, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyProviderBeanPostProcessor.class);
    private final Environment environment;

    public JerseyProviderBeanPostProcessor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Provider.class)) {
            LOGGER.debug("Registering JAX-RS provider '{}' to Dropwizard", bean.getClass().getName());
            environment.jersey().register(bean);
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
