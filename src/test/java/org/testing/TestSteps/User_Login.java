package org.testing.TestSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testing.utilities.ApiValidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class User_Login {

	ApiValidation apiValidation = new ApiValidation();

	public void userLogin(APIRequestContext request, String URL) throws IOException, InterruptedException {

		System.out.println("******************** User OAuth ********************\n");

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> payload = new HashMap<>();
		payload.put("client_id", "sushiladmin");
		payload.put("client_secret", "sushilAgr12@");
		payload.put("scope", "scim");
		String jsonPayload = objectMapper.writeValueAsString(payload);
		APIResponse response = request.post(URL + "/api/scim/oauth/token",
				RequestOptions.create()
						.setQueryParam("grant_type", "password")
						.setQueryParam("username", "sushiladmin")
						.setQueryParam("password", "sushilAgr12@")
						.setHeader("Content-Type", "application/json")
						.setData(jsonPayload));

		System.out.println("Request URL: " + response.url());
		System.out.println("Response status: " + response.status());
		apiValidation.apiValidation(response);

	}

}