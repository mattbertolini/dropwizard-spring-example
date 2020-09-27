package com.mattbertolini.dropwizard.example;


import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import com.mattbertolini.dropwizard.spring.convert.SpringConversionServiceParamConverterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configuration
public class SpringContext {
    @Bean
    public LoggingManaged loggingManaged() {
        return new LoggingManaged();
    }

    @Bean
    public ThreadDeadlockHealthCheck threadDeadlockHealthCheck() {
        return new ThreadDeadlockHealthCheck();
    }

    // Unfortunately IntelliJ doesn't know about bean post processors so it thinks ExampleConfiguration isn't in the context.
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public ExampleResource exampleResource(ExampleConfiguration exampleConfiguration) {
        return new ExampleResource(exampleConfiguration);
    }

    @Bean
    public FormattingConversionServiceFactoryBean conversionService() {
        return new FormattingConversionServiceFactoryBean();
    }

    @Bean
    public SpringConversionServiceParamConverterProvider springParamConverterProvider(ConversionService conversionService) {
        return new SpringConversionServiceParamConverterProvider(conversionService);
    }
}
