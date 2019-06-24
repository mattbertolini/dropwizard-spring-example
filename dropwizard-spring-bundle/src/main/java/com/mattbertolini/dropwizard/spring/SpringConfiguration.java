package com.mattbertolini.dropwizard.spring;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Set;

public abstract class SpringConfiguration {
    /**
     * Unique ID for the spring context
     */
    private String id;
    private String displayName;
    private Set<String> activeProfiles;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("activeProfiles")
    public Set<String> getActiveProfiles() {
        return activeProfiles;
    }

    @JsonProperty("activeProfiles")
    public void setActiveProfiles(Set<String> activeProfiles) {
        this.activeProfiles = activeProfiles;
    }

    protected abstract ConfigurableApplicationContext createContext();

    ConfigurableApplicationContext createApplicationContext() {
        ConfigurableApplicationContext applicationContext = createContext();
        if (!Strings.isNullOrEmpty(id)) {
            applicationContext.setId(id);
        }

        if (activeProfiles != null) {
            applicationContext.getEnvironment().setActiveProfiles(activeProfiles.toArray(new String[0]));
        }
        return applicationContext;
    }
}
