package com.mattbertolini.dropwizard.example;

import com.mattbertolini.dropwizard.spring.SpringApplication;
import com.mattbertolini.dropwizard.spring.SpringConfiguration;

public class ExampleApplication extends SpringApplication<ExampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }

    @Override
    public SpringConfiguration getSpringConfiguration(ExampleConfiguration configuration) {
        return configuration.getSpringConfiguration();
    }
}
