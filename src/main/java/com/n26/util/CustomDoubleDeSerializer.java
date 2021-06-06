package com.n26.util;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.n26.errors.ResponseCode;
import com.n26.exception.ApplicationException;

@JsonComponent
public class CustomDoubleDeSerializer extends JsonDeserializer<Double> {

	@Override
	public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String doubleValue = p.getText();
		if (CommonUtils.isEmpty(doubleValue)) {
			throw new ApplicationException(ResponseCode.PARSE_EXCEPTION);
		}
		try {
			return Double.valueOf(doubleValue);

		} catch (Exception ex) {
			throw new ApplicationException(ResponseCode.PARSE_EXCEPTION);
		}
	}

}
