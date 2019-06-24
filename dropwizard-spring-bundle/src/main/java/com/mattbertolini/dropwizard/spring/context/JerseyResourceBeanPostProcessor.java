package com.mattbertolini.dropwizard.spring.context;

import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import javax.ws.rs.Path;

public class JerseyResourceBeanPostProcessor implements BeanPostProcessor, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyResourceBeanPostProcessor.class);
    
    private final Environment environment;

    public JerseyResourceBeanPostProcessor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (classAnnotatedWithPath(bean)) {
            LOGGER.debug("Registering JAX-RS resource '{}' with Dropwizard", bean.getClass().getName());
            environment.jersey().register(bean);
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    private boolean classAnnotatedWithPath(Object bean) {
        return bean.getClass().isAnnotationPresent(Path.class);
    }
}
