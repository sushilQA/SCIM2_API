package org.testing.TestSteps;

import java.io.IOException;
import java.util.Map;

import org.testing.resources.UserData;
import org.testing.utilities.ApiValidation;
import org.testing.utilities.AuthContext;
import org.testing.utilities.RandomNumberGenerator;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class Update_User_Sync {

	ApiValidation apiValidation = new ApiValidation();
	GetUsers getUsers = new GetUsers();

	private static final String EXTENSION = "urn:aehsc:scim:api:extension:2.0:UserExtension";

	public void updateUserSync_modify(APIRequestContext request, String URL, ExtentTest extentTest,
			int expectedStatusCode, String userId) throws IOException, InterruptedException {

		System.out.println("\n ******************** Update User - Sync Success - Modify ********************\n");

		try {
			// 1. Fetch the current state of the user
			APIResponse getResponse = getUsers.fetchUser(request, URL, userId);
			JsonNode userData = new ObjectMapper().readTree(getResponse.text());
			UserData data = UserData.fromExisting(userData);

			// 2. Snapshot BEFORE any overrides
			Map<String, String> oldSnapshot = data.snapshot();

			// 3. Override only the fields this test is actually validating
			data.setTitle(userId);
			data.setMiddleName(userId);
			data.setEmail(userId + RandomNumberGenerator.randomNumber() + "@yopmail.com");
			data.setFamilyName(userId);
			data.setCustomProperty("currentPositionId", userId);
			data.setCustomProperty("companyNumber", userId);
			data.setCustomProperty("workStyle", "Home");
			data.setCustomProperty("scim_state", userId);
			String currentLocation = (String) data.getCustomProperty("locationCode");
			String newLocation;
			if ("90018".equals(currentLocation)) {
				newLocation = "90015";
			} else if ("90015".equals(currentLocation)) {
				newLocation = "90018";
			} else {
				System.out.println("Unexpected current locationCode '" + currentLocation + "', using 90018");
				newLocation = "90018";
			}
			data.setCustomProperty("locationCode", newLocation);
			System.out.println("Update User - Modify Payload - " + data.toJson());

			// 4. Snapshot AFTER overrides
			Map<String, String> newSnapshot = data.snapshot();

			// 5. Send the update
			APIResponse response = request.put(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create()
							.setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json")
							.setData(data.toJson()));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode);
			apiValidation.logAttributeChanges(extentTest, oldSnapshot, newSnapshot);

		} catch (IOException e) {
			System.out.println("updateUserSync_modify failed (I/O): " + e.getMessage());
			throw e;
		} catch (RuntimeException e) {
			System.out.println("updateUserSync_modify failed (Playwright): " + e.getMessage());
			throw e;
		}
	}
	
	public void updateUserSync_userNotExist(APIRequestContext request, String URL, ExtentTest extentTest,
			int expectedStatusCode, String expectedMessage, String nonExistentUserId) throws IOException {

		System.out.println("\n ******************** Update User - Negative - User Not Exist ********************\n");

		try {
			UserData data = UserData.sample(nonExistentUserId);   // no GET first - build directly, nothing to fetch
			data.setTitle(nonExistentUserId + RandomNumberGenerator.randomNumber());
			System.out.println("Update User - Negative - User Not Exist Payload - " + data.toJson());
			APIResponse response = request.put(URL + "/api/scim/v2/users/" + nonExistentUserId,
					RequestOptions.create()
							.setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json")
							.setData(data.toJson()));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode, expectedMessage);

		} catch (RuntimeException e) {
			System.out.println("updateUserSync_userNotExist failed (Playwright): " + e.getMessage());
			throw e;
		}
	}
}