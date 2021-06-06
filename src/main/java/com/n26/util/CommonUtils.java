package com.n26.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {

	public static boolean isNull(Object object) {
		return object == null;
	}

	public static boolean isEmpty(String string) {
		return string == null || "".equalsIgnoreCase(string);
	}

	public static <T> T convertJsonToObject(String json, Class<T> classType) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(json, classType);
		} catch (Exception ex) {
			return null;
		}
	}

	public static <T> String converObjectToJson(T object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			if (null != object) {
				return mapper.writeValueAsString(object);
			}
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

}
