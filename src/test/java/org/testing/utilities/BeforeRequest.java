package org.testing.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class BeforeRequest {
	public static void printRequest(Object object) {
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonRequest = ow.writeValueAsString(object);
			System.out.println(jsonRequest);
		} catch (Exception ex) {

		}
	}
}
