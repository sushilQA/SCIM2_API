package org.testing.TestSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testing.utilities.ApiValidation;
import org.testing.utilities.AuthContext;
import org.testing.utilities.JsonTemplateReader;
import org.testing.utilities.RandomNumberGenerator;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class Create_User_Sync {

	ApiValidation apiValidation = new ApiValidation();

	public void createUserSyncSuccess(APIRequestContext request, String URL) throws IOException, InterruptedException {

		System.out.println("\n ******************** Create User - Sync Success ********************\n");
		try {
			Map<String, String> tokens = new HashMap<>();
			tokens.put("{{userName}}", "SCIM" + RandomNumberGenerator.randomNumber());
			String jsonPayload = JsonTemplateReader.getJsonWithReplacedTokens(
					"../SCIM2_API/src/test/java/org/testing/resources/CreateUserBody.json", tokens);

			APIResponse response = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			System.out.println("Request URL: " + response.url());
			System.out.println("Response status: " + response.status());
			apiValidation.apiValidation(response);

		} catch (IOException e) {
			System.out.println("createUserSyncSuccess failed: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("Unexpected error in createUserSyncSuccess: " + e.getMessage());
			throw e;
		}
	}

	public void createUserSyncFailedDueToInvalidOrExpiredAccessToken(APIRequestContext request, String URL)
			throws IOException, InterruptedException {

		System.out.println(
				"\n ******************** Create User - Sync Failed - Invalid/Expired Access Token ********************\n");
		try {
			Map<String, String> tokens = new HashMap<>();
			tokens.put("{{userName}}", "SCIM" + RandomNumberGenerator.randomNumber());
			String jsonPayload = JsonTemplateReader.getJsonWithReplacedTokens(
					"../SCIM2_API/src/test/java/org/testing/resources/CreateUserBody.json", tokens);

			APIResponse response = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.expiredToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			System.out.println("Request URL: " + response.url());
			System.out.println("Response status: " + response.status());
			apiValidation.apiValidation(response);

		} catch (IOException e) {
			System.out.println("createUserSyncFailedDueToInvalidOrExpiredAccessToken failed: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println(
					"Unexpected error in createUserSyncFailedDueToInvalidOrExpiredAccessToken: " + e.getMessage());
			throw e;
		}
	}

	public void createUserSyncFailedUserAlreadyExist(APIRequestContext request, String URL)
			throws IOException, InterruptedException {

		System.out
				.println("******************** Create User - Sync Failed - User Already Exist ********************\n");
		try {
			String existingUserId = "SCIM" + RandomNumberGenerator.randomNumber();

			Map<String, String> tokens = new HashMap<>();
			tokens.put("{{userName}}", existingUserId);
			String jsonPayload = JsonTemplateReader.getJsonWithReplacedTokens(
					"../SCIM2_API/src/test/java/org/testing/resources/CreateUserBody.json", tokens);

			// Create User First Call - successfully
			APIResponse firstResponse = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));
			System.out.println("First creation status: " + firstResponse.status());

			// Create User Second Call - User Already Exist Case
			APIResponse secondResponse = request.post(URL + "/api/scim/v2/users",
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			System.out.println("Second creation status (expected failure): " + secondResponse.status());
			apiValidation.apiValidation(secondResponse);

		} catch (IOException e) {
			System.out.println("createUserSyncFailedUserAlreadyExist failed: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("Unexpected error in createUserSyncFailedUserAlreadyExist: " + e.getMessage());
			throw e;
		}
	}

	public void createUserSyncFailedDueToMissedId(APIRequestContext request, String URL)
			throws IOException, InterruptedException {

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

			System.out.println("Request URL: " + response.url());
			System.out.println("Response status: " + response.status());
			apiValidation.apiValidation(response);

		} catch (IOException e) {
			System.out.println("createUserSyncFailedDueToMissedId failed: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("Unexpected error in createUserSyncFailedDueToMissedId: " + e.getMessage());
			throw e;
		}
	}

}