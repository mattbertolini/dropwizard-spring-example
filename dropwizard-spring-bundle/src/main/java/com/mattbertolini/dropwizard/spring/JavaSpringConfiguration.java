package com.mattbertolini.dropwizard.spring;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;

public class JavaSpringConfiguration extends SpringConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSpringConfiguration.class);

    @NotEmpty
    private Set<Class<?>> contextConfigClasses;

    @JsonProperty("contextConfigClasses")
    public Set<Class<?>> getContextConfigClasses() {
        return contextConfigClasses;
    }

    @JsonProperty("contextConfigClasses")
    public void setContextConfigClasses(Set<Class<?>> contextConfigClasses) {
        this.contextConfigClasses = contextConfigClasses;
    }

    @Override
    protected ConfigurableApplicationContext createContext() {
        LOGGER.debug("Creating {} with config classes {}", AnnotationConfigApplicationContext.class.getSimpleName(), contextConfigClasses);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(contextConfigClasses.toArray(new Class[0]));
        if (!Strings.isNullOrEmpty(getDisplayName())) {
            applicationContext.setDisplayName(getDisplayName());
        }
        return applicationContext;
    }
}
