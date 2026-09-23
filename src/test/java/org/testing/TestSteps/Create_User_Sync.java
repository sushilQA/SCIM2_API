package org.testing.TestSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testing.utilities.ApiValidation;
import org.testing.utilities.AuthContext;
import org.testing.utilities.JsonTemplateReader;
import org.testing.utilities.RandomNumberGenerator;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class Create_User_Sync {

	ApiValidation apiValidation = new ApiValidation();

	public void createUserSyncSuccess(APIRequestContext request, String URL, ExtentTest extentTest,
			int expectedStatusCode) throws IOException, InterruptedException {

		System.out.println("\n ******************** Create User - Sync Success ********************\n");
		try {
			Map<String, String> tokens = new HashMap<>();
			tokens.put("{{userName}}", "SCIM" + RandomNumberGenerator.randomNumber());
			String jsonPayload = JsonTemplateReader.getJsonWithReplacedTokens(
					"../SCIM2_API/src/test/java/org/testing/resources/CreateUserBody.json", tokens);

			APIResponse response = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode);

		} catch (IOException e) {
			System.out.println("createUserSyncSuccess failed: " + e.getMessage());
			throw e;
		}
	}

	public void createUserSyncFailedDueToInvalidOrExpiredAccessToken(APIRequestContext request, String URL,
			ExtentTest extentTest, int expectedStatusCode, String expectedMessage)
			throws IOException, InterruptedException {

		System.out.println(
				"\n ******************** Create User - Sync Failed - Invalid Or Expired Access Token ********************\n");
		try {
			Map<String, String> tokens = new HashMap<>();
			tokens.put("{{userName}}", "SCIM" + RandomNumberGenerator.randomNumber());
			String jsonPayload = JsonTemplateReader.getJsonWithReplacedTokens(
					"../SCIM2_API/src/test/java/org/testing/resources/CreateUserBody.json", tokens);

			APIResponse response = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.expiredToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode, expectedMessage);

		} catch (IOException e) {
			System.out.println("createUserSyncFailedDueToInvalidOrExpiredAccessToken failed: " + e.getMessage());
			throw e;
		}
	}

	public void createUserSyncFailedUserAlreadyExist(APIRequestContext request, String URL, ExtentTest extentTest,
			int expectedStatusCode, String expectedMessage) throws IOException, InterruptedException {

		System.out
				.println("******************** Create User - Sync Failed - User Already Exist ********************\n");
		try {
			String existingUserId = "SCIM" + RandomNumberGenerator.randomNumber();

			Map<String, String> tokens = new HashMap<>();
			tokens.put("{{userName}}", existingUserId);
			String jsonPayload = JsonTemplateReader.getJsonWithReplacedTokens(
					"../SCIM2_API/src/test/java/org/testing/resources/CreateUserBody.json", tokens);

			// First call - creates the user successfully
			APIResponse firstResponse = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));
			System.out.println("First creation status: " + firstResponse.status());

			// Second call - same userId, should fail (already exists)
			APIResponse secondResponse = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			apiValidation.apiValidation(secondResponse, extentTest, expectedStatusCode, expectedMessage);

		} catch (IOException e) {
			System.out.println("createUserSyncFailedUserAlreadyExist failed: " + e.getMessage());
			throw e;
		}
	}

	public void createUserSyncFailedDueToMissedId(APIRequestContext request, String URL, ExtentTest extentTest,
			int expectedStatusCode, String expectedMessage) throws IOException, InterruptedException {

		System.out.println(
				"\n ******************** Create User - Sync Failed - Missed Id in Payload ********************\n");
		try {
			Map<String, String> tokens = new HashMap<>();
			tokens.put("{{userName}}", "SCIM" + RandomNumberGenerator.randomNumber());
			String jsonPayload = JsonTemplateReader.getJsonWithReplacedTokens(
					"../SCIM2_API/src/test/java/org/testing/resources/CreateUserBodyMissedId.json", tokens);

			APIResponse response = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode, expectedMessage);

		} catch (IOException e) {
			System.out.println("createUserSyncFailedDueToMissedId failed: " + e.getMessage());
			throw e;
		}
	}

	public void createUserSyncFailedDueNoAuth(APIRequestContext request, String URL, ExtentTest extentTest,
			int expectedStatusCode) throws IOException, InterruptedException {

		System.out
				.println("\n ******************** Create User - Sync Failed - No Auth Defined ********************\n");
		try {
			Map<String, String> tokens = new HashMap<>();
			tokens.put("{{userName}}", "SCIM" + RandomNumberGenerator.randomNumber());
			String jsonPayload = JsonTemplateReader.getJsonWithReplacedTokens(
					"../SCIM2_API/src/test/java/org/testing/resources/CreateUserBody.json", tokens);

			APIResponse response = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.noAuthToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode);

		} catch (IOException e) {
			System.out.println("createUserSyncSuccess failed: " + e.getMessage());
			throw e;
		}
	}

}