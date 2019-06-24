# Dropwizard Application With Spring DI Container

This is a prototype of how I might integrate the Spring DI container with a Dropwizard application if I needed to. This 
includes a custom JAX-RS `ParamConverterProvider` to delegate to a Spring `ConversionService`.