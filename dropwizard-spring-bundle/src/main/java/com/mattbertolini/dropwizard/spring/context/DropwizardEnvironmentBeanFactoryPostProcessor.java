package com.mattbertolini.dropwizard.spring.context;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * A Spring {@code BeanFactoryPostProcessor} that registers all of the Dropwizard and JAX-RS specific beans defined in the Spring
 * context with the Dropwizard environment.
 */
public class DropwizardEnvironmentBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DropwizardEnvironmentBeanFactoryPostProcessor.class);
    
    private final Configuration configuration;
    private final Environment environment;

    public DropwizardEnvironmentBeanFactoryPostProcessor(Configuration configuration, Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        registerSingleton(beanFactory, "applicationConfiguration", configuration);
        registerSingleton(beanFactory, "metricRegistry", environment.metrics());
        registerSingleton(beanFactory, "objectMapper", environment.getObjectMapper());
        registerSingleton(beanFactory, "validator", environment.getValidator());

        registerBeanPostProcessors(beanFactory);
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        registerSingleton(beanFactory, "jerseyResourceBeanPostProcessor", new JerseyResourceBeanPostProcessor(environment));
        registerSingleton(beanFactory, "jerseyFeatureBeanPostProcessor", new FeatureBeanPostProcessor(environment));
        registerSingleton(beanFactory, "jerseyProviderBeanPostProcessor", new JerseyProviderBeanPostProcessor(environment));
        registerSingleton(beanFactory, "managedBeanPostProcessor", new ManagedBeanPostProcessor(environment));
        registerSingleton(beanFactory, "lifeCycleBeanPostProcessor", new LifeCycleBeanPostProcessor(environment));
        registerSingleton(beanFactory, "healthCheckBeanPostProcessor", new HealthCheckBeanPostProcessor(environment));
        registerSingleton(beanFactory, "servletEnvironmentInitializerBeanPostProcessor", new ServletEnvironmentInitializerBeanPostProcessor(environment));
        registerSingleton(beanFactory, "taskBeanPostProcessor", new TaskBeanPostProcessor(environment));
    }

    private void registerSingleton(ConfigurableListableBeanFactory beanFactory, String beanName, Object singletonObject) {
        LOGGER.debug("Registering singleton {} with bean name {}", singletonObject.getClass().getName(), beanName);
        beanFactory.registerSingleton(beanName, singletonObject);
    }
}
