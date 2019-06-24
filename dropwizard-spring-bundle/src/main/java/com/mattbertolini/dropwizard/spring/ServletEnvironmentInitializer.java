package com.mattbertolini.dropwizard.spring;

import io.dropwizard.jetty.setup.ServletEnvironment;

public interface ServletEnvironmentInitializer {
    void onStartup(ServletEnvironment servletEnvironment);
}
