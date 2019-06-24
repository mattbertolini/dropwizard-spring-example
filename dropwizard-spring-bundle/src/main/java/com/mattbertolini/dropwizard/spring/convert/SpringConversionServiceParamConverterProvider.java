package com.mattbertolini.dropwizard.spring.convert;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * A JAX-RS {@code ParamConverterProvider} that delegates conversion responsibilities to the given Spring
 * {@code ConversionService}.
 */
@Provider
public class SpringConversionServiceParamConverterProvider implements ParamConverterProvider {
    private static final TypeDescriptor STRING_TYPE = TypeDescriptor.valueOf(String.class);
    private final ConversionService conversionService;

    public SpringConversionServiceParamConverterProvider(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        TypeDescriptor converterType = new TypeDescriptorAdapter(genericType, annotations);
        // Let JAX-RS handle creating the collections. Otherwise it doesn't use the multivalued extractor and we only
        // get one value to convert.
        if (converterType.isArray() || converterType.isCollection() || converterType.isMap()) {
            return null;
        }
        if (conversionService.canConvert(STRING_TYPE, converterType)) {
            return new ConversionServiceParamConverterAdapter<>(conversionService, converterType);
        }
        return null;
    }

    private static class ConversionServiceParamConverterAdapter<T> implements ParamConverter<T> {
        private final ConversionService conversionService;
        private final TypeDescriptor converterType;

        ConversionServiceParamConverterAdapter(ConversionService conversionService, TypeDescriptor converterType) {
            this.conversionService = conversionService;
            this.converterType = converterType;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T fromString(String value) {
            if (value == null) {
                throw new IllegalArgumentException("Argument cannot be null.");
            }
            try {
                return (T) conversionService.convert(value, STRING_TYPE, converterType);
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to convert string value '" + value + "' to " + converterType.getName(), e);
            }
        }

        @Override
        public String toString(T value) {
            if (value == null) {
                throw new IllegalArgumentException("Argument cannot be null.");
            }
            try {
                return (String) conversionService.convert(value, converterType, STRING_TYPE);
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to convert type " + converterType.getName() + " value '" + value + "' to string.", e);
            }
        }
    }

    private static class TypeDescriptorAdapter extends TypeDescriptor {

        TypeDescriptorAdapter(Type type, Annotation[] annotations) {
            super(ResolvableType.forType(type), null, annotations);
        }
    }
}
