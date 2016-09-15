package com.klutzer.wineshop;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.klutzer.wineshop.business.Money;

@SuppressWarnings("serial")
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		configure(SerializationFeature.INDENT_OUTPUT, true);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		setSerializationInclusion(Include.NON_NULL);
		registerModule(new JodaModule());
		
		// informando ao Jackson como ele deve parsear o tipo Money
		SimpleModule module = new SimpleModule();
		module.addSerializer(Money.class, new MoneySerializer());
		module.addDeserializer(Money.class, new MoneyDeserializer());
		registerModule(module);
	}
	
	private class MoneySerializer extends JsonSerializer<Money> {

		@Override
		public void serialize(Money value, JsonGenerator gen, SerializerProvider serializers)
				throws IOException, JsonProcessingException {
			gen.writeString(value.toString());
		}
		
	}
	
	private class MoneyDeserializer extends JsonDeserializer<Money> {

		@Override
		public Money deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			return Money.valueOf(new BigDecimal(p.getText()));
		}
		
	}
}
