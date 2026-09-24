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

public class Update_User_Sync {

	ApiValidation apiValidation = new ApiValidation();

	public void updateUserSync_modify(APIRequestContext request, String URL, ExtentTest extentTest,
			int expectedStatusCode, String userId) throws IOException, InterruptedException {

		System.out.println("\n ******************** Create User - Sync Success With Group ********************\n");
		try {

			String jsonPayload = JsonTemplateReader.getJsonBody("../SCIM2_API/src/test/java/org/testing/resources/CreateUserGroupBody.json");

			APIResponse response = request.put(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken)
							.setHeader("Content-Type", "application/json").setData(jsonPayload));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode);

		} catch (IOException e) {
			System.out.println("createUserSyncSuccess failed: " + e.getMessage());
			throw e;
		}
	}
}