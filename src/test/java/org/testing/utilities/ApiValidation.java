package org.testing.utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.microsoft.playwright.APIResponse;

public class ApiValidation {

	// No assertion - just logs
	public void apiValidation(APIResponse response, ExtentTest extentTest) {
		extractAndPrint(response, extentTest, null, null);
	}

	// Validates ONLY the expected message
	public void apiValidation(APIResponse response, ExtentTest extentTest, String expectedMessage) {
		extractAndPrint(response, extentTest, null, expectedMessage);
	}

	// Validates ONLY the expected status code
	public void apiValidation(APIResponse response, ExtentTest extentTest, int expectedStatusCode) {
		extractAndPrint(response, extentTest, expectedStatusCode, null);
	}

	// Validates status code and expected message
	public void apiValidation(APIResponse response, ExtentTest extentTest, int expectedStatusCode, String expectedMessage) {
		extractAndPrint(response, extentTest, expectedStatusCode, expectedMessage);
	}

	private void extractAndPrint(APIResponse response, ExtentTest extentTest, Integer expectedStatusCode, String expectedMessage) {

		int statusCode = response.status();
		String body = response.text();
		JSONObject jsonObject = null;
		String actualMessage = null;

		// --- Request/Response basic details (informational only - not PASS/FAIL) ---
		extentTest.info("Request URL: " + response.url());
		extentTest.info("Response Status Code: " + statusCode);

		// --- Full response body as formatted JSON code block ---
		try {
			extentTest.info(MarkupHelper.createCodeBlock(body, CodeLanguage.JSON));
		} catch (Exception e) {
			extentTest.info("Raw Response Body: " + body);
		}

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
					JSONArray messages = jsonObject.getJSONArray("messages");
					if (messages.length() > 0) {
						actualMessage = messages.getJSONObject(0).getString("messageDisplayText");
						System.out.println("Error Message: " + actualMessage);
					} else {
						System.out.println("400 error - 'messages' array is empty:\n" + body);
					}
				} else if (jsonObject != null && jsonObject.has("errors")) {
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

			if (actualMessage != null) {
				extentTest.info("Extracted Message: " + actualMessage);
			}

		} catch (Exception e) {
			extentTest.warning("Error while parsing response for status " + statusCode + ": " + e.getMessage());
			System.out.println("Error while parsing/validating response for status " + statusCode + ": " + e.getMessage());
			System.out.println("Raw response body:\n" + body);
		}

		// --- Assertions ---
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