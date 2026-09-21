package org.testing.utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.playwright.APIResponse;

public class ApiValidation {

	public void apiValidation(APIResponse response) {

		int statusCode = response.status();
		String body = response.text();
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(body);
		} catch (Exception e) {
			System.out.println("Response body is not valid JSON, skipping JSON parsing: " + e.getMessage());
		}

		try {
			if (statusCode >= 200 && statusCode < 300) {
				System.out.println("Response is given below :\n" + body);

			} else if (statusCode == 400) {
				if (jsonObject != null && jsonObject.has("messages")) {
					// OAuth-style error: { "messages": [ { "messageDisplayText": "..." } ] }
					JSONArray messages = jsonObject.getJSONArray("messages");
					if (messages.length() > 0) {
						System.out
								.println("Error Message: " + messages.getJSONObject(0).getString("messageDisplayText"));
					} else {
						System.out.println("400 error - 'messages' array is empty:\n" + body);
					}
				} else if (jsonObject != null && jsonObject.has("errors")) {
					// SCIM-style error: { "errors": [ { "details": "..." } ] }
					JSONArray errors = jsonObject.getJSONArray("errors");
					if (errors.length() > 0) {
						System.out.println("Error Message: " + errors.getJSONObject(0).getString("details"));
					} else {
						System.out.println("400 error - 'errors' array is empty:\n" + body);
					}
				} else {
					System.out.println("400 error - unrecognized response format:\n" + body);
				}

			} else if (statusCode == 401) {
				System.out.println("Invalid OR Expired Access Token");

			} else if (statusCode == 404) {
				if (jsonObject != null && jsonObject.has("details")) {
					// SCIM-style not-found error: { "status": 404, "details": "..." }
					System.out.println("Error Message: " + jsonObject.getString("details"));
				} else {
					System.out.println("404 error - unrecognized response format:\n" + body);
				}

			} else {
				System.out.println("Response Code is =" + statusCode);
				if (jsonObject != null && jsonObject.has("message")) {
					System.out.println(jsonObject.get("message").toString());
				} else {
					System.out.println("No 'message' field found in response body.");
				}
				System.out.println("\nAnd the response is given below :\n" + body);
			}

		} catch (Exception e) {
			System.out.println(
					"Error while parsing/validating response for status " + statusCode + ": " + e.getMessage());
			System.out.println("Raw response body:\n" + body);
		}
	}
}