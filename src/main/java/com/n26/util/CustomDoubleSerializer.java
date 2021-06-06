package com.n26.util;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class CustomDoubleSerializer extends JsonSerializer<Double>{

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(null == value) {
			gen.writeString("0.00");
		} else {
			DecimalFormat df = new DecimalFormat("#.00");
			df.setRoundingMode(RoundingMode.HALF_UP);
			gen.writeString(df.format(value));
		}
		
	}

}
