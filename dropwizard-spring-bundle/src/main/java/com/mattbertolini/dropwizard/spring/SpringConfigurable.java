package com.mattbertolini.dropwizard.spring;


import io.dropwizard.Configuration;

public interface SpringConfigurable<T extends Configuration> {
    default SpringConfiguration getSpringConfiguration(T configuration) {
        return new JavaSpringConfiguration();
    }
}
