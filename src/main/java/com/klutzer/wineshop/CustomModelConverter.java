package com.klutzer.wineshop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JavaType;
import com.klutzer.wineshop.business.Money;

import io.swagger.converter.ModelConverter;
import io.swagger.converter.ModelConverterContext;
import io.swagger.models.Model;
import io.swagger.models.properties.DecimalProperty;
import io.swagger.models.properties.Property;
import io.swagger.util.Json;

public class CustomModelConverter implements ModelConverter {

	@Override
	public Property resolveProperty(Type type, ModelConverterContext context, Annotation[] annotations,
			Iterator<ModelConverter> chain) {
		JavaType javaType = Json.mapper().constructType(type);
        if (javaType != null) {
            Class<?> cls = javaType.getRawClass();
            // quando for Money, usa DecimalProperty
            if (Money.class.isAssignableFrom(cls)) {
                return new DecimalProperty();
            }
        }
        if (chain.hasNext()) {
            return chain.next().resolveProperty(type, context, annotations, chain);
        } else {
            return null;
        }
	}

	@Override
	public Model resolve(Type type, ModelConverterContext context, Iterator<ModelConverter> chain) {
		if (chain.hasNext()) {
	        return chain.next().resolve(type, context, chain);
	    } else {
	        return null;
	    }
	}

}
