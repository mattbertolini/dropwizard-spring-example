package com.mattbertolini.dropwizard.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public abstract class AbstractGenericBeanPostProcessor<T> implements BeanPostProcessor {
    private final Class<T> clazz;

    protected AbstractGenericBeanPostProcessor(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected T doPostProcessBeforeInitialization(T bean, String beanName) {
        return bean;
    }

    protected T doPostProcessAfterInitialization(T bean, String beanName) {
        return bean;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (clazz.isAssignableFrom(bean.getClass())) {
            return doPostProcessBeforeInitialization((T) bean, beanName);
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (clazz.isAssignableFrom(bean.getClass())) {
            return doPostProcessAfterInitialization((T) bean, beanName);
        }
        return bean;
    }
}
