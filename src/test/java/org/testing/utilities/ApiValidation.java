package org.testing.utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.microsoft.playwright.APIResponse;

public class ApiValidation {

	// Existing method - no assertions, just prints/logs the response
	public void apiValidation(APIResponse response) {
		extractAndPrint(response, null, null);
	}

	// Overload - validates ONLY the expected message, status code is not checked
	public void apiValidation(APIResponse response, String expectedMessage) {
		extractAndPrint(response, null, expectedMessage);
	}

	// Overload - validates ONLY the expected status code, message is not checked
	public void apiValidation(APIResponse response, int expectedStatusCode) {
		extractAndPrint(response, expectedStatusCode, null);
	}

	// Overload - validates status code and expected message via TestNG assertions
	public void apiValidation(APIResponse response, int expectedStatusCode, String expectedMessage) {
		extractAndPrint(response, expectedStatusCode, expectedMessage);
	}

	private void extractAndPrint(APIResponse response, Integer expectedStatusCode, String expectedMessage) {

		int statusCode = response.status();
		String body = response.text();
		JSONObject jsonObject = null;
		String actualMessage = null;

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
						actualMessage = messages.getJSONObject(0).getString("messageDisplayText");
						System.out.println("Error Message: " + actualMessage);
					} else {
						System.out.println("400 error - 'messages' array is empty:\n" + body);
					}
				} else if (jsonObject != null && jsonObject.has("errors")) {
					// SCIM-style error: { "errors": [ { "details": "..." } ] }
					JSONArray errors = jsonObject.getJSONArray("errors");
					if (errors.length() > 0) {
						actualMessage = errors.getJSONObject(0).getString("details");
						System.out.println("Error Message: " + actualMessage);
					} else {
						System.out.println("400 error - 'errors' array is empty:\n" + body);
					}
				} else {
					System.out.println("400 error - unrecognized response format:\n" + body);
				}

			} else if (statusCode == 401) {
				actualMessage = "Invalid OR Expired Access Token";
				System.out.println(actualMessage);

			} else if (statusCode == 404) {
				if (jsonObject != null && jsonObject.has("details")) {
					// SCIM-style not-found error: { "status": 404, "details": "..." }
					actualMessage = jsonObject.getString("details");
					System.out.println("Error Message: " + actualMessage);
				} else {
					System.out.println("404 error - unrecognized response format:\n" + body);
				}

			} else {
				System.out.println("Response Code is =" + statusCode);
				if (jsonObject != null && jsonObject.has("message")) {
					actualMessage = jsonObject.get("message").toString();
					System.out.println(actualMessage);
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

		// Assertions - only run when expected values are provided (overloaded call)
		if (expectedStatusCode != null) {
			Assert.assertEquals(statusCode, expectedStatusCode.intValue(),
					"Status code mismatch! Expected: " + expectedStatusCode + ", Actual: " + statusCode);
		}

		if (expectedMessage != null) {
			Assert.assertNotNull(actualMessage,
					"Expected message '" + expectedMessage + "' but no message was found in response.");
			Assert.assertTrue(actualMessage.contains(expectedMessage), "Message mismatch! Expected to contain: '"
					+ expectedMessage + "', Actual: '" + actualMessage + "'");
		}
	}
}