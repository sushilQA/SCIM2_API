package org.testing.utilities;

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
			System.out.println("Response body is not valid JSON, skipping JSON parsing.");
		}

		if (statusCode >= 200 && statusCode < 300) {
			System.out.println("Response is given below :\n" + body);

		} else {
			System.out.println("Response Code is =" + statusCode);
			if (jsonObject != null && jsonObject.has("message")) {
				System.out.println(jsonObject.get("message").toString());
			} else {
				System.out.println("No 'message' field found in response body.");
			}
			System.out.println("\nAnd the response is given below :\n" + body);
		}

	}

}