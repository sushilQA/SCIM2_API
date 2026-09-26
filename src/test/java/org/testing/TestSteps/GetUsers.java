package org.testing.TestSteps;

import java.io.IOException;

import org.testing.utilities.ApiValidation;
import org.testing.utilities.AuthContext;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class GetUsers {

	ApiValidation apiValidation = new ApiValidation();

	public void getAllUsers(APIRequestContext request, String URL, String startIndex, String count,
			ExtentTest extentTest, int expectedStatusCode) throws IOException, InterruptedException {

		System.out.println("\n ******************** Get All Users ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users",
					RequestOptions.create().setQueryParam("startIndex", startIndex).setQueryParam("count", count)
							.setHeader("Authorization", "Bearer " + AuthContext.accessToken));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode);

		} catch (RuntimeException e) {
			System.out.println("getAllUsers failed: " + e.getMessage());
			throw e;
		}
	}

	public void getAllUsersWithExpiredORInvalidAccessToken(APIRequestContext request, String URL, String startIndex,
			String count, ExtentTest extentTest, int expectedStatusCode) throws IOException, InterruptedException {

		System.out.println(
				"\n ******************** Get All Users with Invalid / Expired Access Token ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users",
					RequestOptions.create().setQueryParam("startIndex", startIndex).setQueryParam("count", count)
							.setHeader("Authorization", "Bearer " + AuthContext.expiredToken));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode);

		} catch (RuntimeException e) {
			System.out.println("getAllUsersWithExpiredORInvalidAccessToken failed: " + e.getMessage());
			throw e;
		}
	}

	public void getSingleUser(APIRequestContext request, String URL, String userId, ExtentTest extentTest,
			int expectedStatusCode) throws IOException, InterruptedException {

		System.out.println("\n ******************** Get Single User ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode);

		} catch (RuntimeException e) {
			System.out.println("getSingleUser failed: " + e.getMessage());
			throw e;
		}
	}

	public void getSingleUserWithExpiredOrInvalidAccessToken(APIRequestContext request, String URL, String userId,
			ExtentTest extentTest, int expectedStatusCode) throws IOException, InterruptedException {

		System.out.println(
				"\n ******************** Get Single User With Expired Or Invalid Access Token ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.expiredToken));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode);

		} catch (RuntimeException e) {
			System.out.println("getSingleUserWithExpiredOrInvalidAccessToken failed: " + e.getMessage());
			throw e;
		}
	}

	public void userNotExist(APIRequestContext request, String URL, String userId, ExtentTest extentTest,
			int expectedStatusCode, String expectedMessage) throws IOException, InterruptedException {

		System.out.println("\n ******************** Get Single User - User Not Exist ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken));

			apiValidation.apiValidation(response, extentTest, expectedStatusCode, expectedMessage);

		} catch (RuntimeException e) {
			System.out.println("userNotExist failed: " + e.getMessage());
			throw e;
		}
	}

	// Plain fetch - no assertion/logging, used as a precondition step in other
	// flows (e.g. Update User)
	public APIResponse fetchUser(APIRequestContext request, String URL, String userId)
			throws IOException, InterruptedException {

		try {
			return request.get(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken));

		} catch (RuntimeException e) {
			System.out.println("fetchUser failed: " + e.getMessage());
			throw e;
		}
	}

}