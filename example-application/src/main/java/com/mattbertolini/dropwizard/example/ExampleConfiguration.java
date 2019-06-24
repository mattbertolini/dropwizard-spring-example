package com.mattbertolini.dropwizard.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattbertolini.dropwizard.spring.JavaSpringConfiguration;
import io.dropwizard.Configuration;

import javax.validation.Valid;

public class ExampleConfiguration extends Configuration {
    @Valid
    private JavaSpringConfiguration springConfiguration = new JavaSpringConfiguration();

    @JsonProperty("spring")
    public JavaSpringConfiguration getSpringConfiguration() {
        return springConfiguration;
    }

    @JsonProperty("spring")
    public void setSpringConfiguration(JavaSpringConfiguration springConfiguration) {
        this.springConfiguration = springConfiguration;
    }
}
