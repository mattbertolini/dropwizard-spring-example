package com.mattbertolini.dropwizard.spring.context;

import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

public class TaskBeanPostProcessor extends AbstractGenericBeanPostProcessor<Task> implements Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskBeanPostProcessor.class);
    private final Environment environment;

    protected TaskBeanPostProcessor(Environment environment) {
        super(Task.class);
        this.environment = environment;
    }

    @Override
    protected Task doPostProcessAfterInitialization(Task bean, String beanName) {
        LOGGER.debug("Adding task '{}' to Dropwizard admin environment", bean.getClass().getName());
        environment.admin().addTask(bean);
        return bean;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
