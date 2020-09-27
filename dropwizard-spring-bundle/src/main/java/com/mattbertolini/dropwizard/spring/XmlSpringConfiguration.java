package com.mattbertolini.dropwizard.spring;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class XmlSpringConfiguration extends SpringConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSpringConfiguration.class);

    @NotEmpty
    private Set<String> contextConfigLocations;

    @JsonProperty("contextConfigLocations")
    public Set<String> getContextConfigLocations() {
        return contextConfigLocations;
    }

    @JsonProperty("contextConfigLocations")
    public void setContextConfigLocations(Set<String> contextConfigLocations) {
        this.contextConfigLocations = contextConfigLocations;
    }

    @Override
    protected ConfigurableApplicationContext createContext() {
        LOGGER.debug("Creating {} with config files {}", ClassPathXmlApplicationContext.class.getSimpleName(), contextConfigLocations);
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        applicationContext.setConfigLocations(contextConfigLocations.toArray(new String[0]));
        if (!Strings.isNullOrEmpty(getDisplayName())) {
            applicationContext.setDisplayName(getDisplayName());
        }
        return applicationContext;
    }
}
