package com.n26.util;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.n26.errors.ResponseCode;
import com.n26.exception.ApplicationException;

@JsonComponent
public class CustomDateDeSerializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String dateString = p.getText();
		try {
			if (CommonUtils.isEmpty(dateString)) {
				throw new ApplicationException(ResponseCode.PARSE_EXCEPTION);
			}
			TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(dateString);
			Instant i = Instant.from(ta);
			Date date = Date.from(i);
			return date;
		} catch (Exception ex) {
			throw new ApplicationException(ResponseCode.PARSE_EXCEPTION);
		}
	}

}
