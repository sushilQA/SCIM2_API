package org.testing.TestSteps;

import java.io.IOException;

import org.testing.utilities.ApiValidation;
import org.testing.utilities.AuthContext;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class GetUsers {

	ApiValidation apiValidation = new ApiValidation();

	public void getAllUsers(APIRequestContext request, String URL, String startIndex, String count)
			throws IOException, InterruptedException {

		System.out.println("\n ******************** Get All Users ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users",
					RequestOptions.create().setQueryParam("startIndex", startIndex).setQueryParam("count", count)
							.setHeader("Authorization", "Bearer " + AuthContext.accessToken));

			System.out.println("Request URL: " + response.url());
			System.out.println("Response status: " + response.status());
			apiValidation.apiValidation(response);

		} catch (RuntimeException e) {
			System.out.println("Unexpected error in getAllUsers: " + e.getMessage());
			throw e;
		}
	}

	public void getAllUsersWithExpiredORInvalidAccessToken(APIRequestContext request, String URL, String startIndex,
			String count) throws IOException, InterruptedException {

		System.out.println(
				"\n ******************** Get All Users with Invalid / Expired Access Token ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users",
					RequestOptions.create().setQueryParam("startIndex", startIndex).setQueryParam("count", count)
							.setHeader("Authorization", "Bearer " + AuthContext.expiredToken));

			System.out.println("Request URL: " + response.url());
			System.out.println("Response status: " + response.status());
			apiValidation.apiValidation(response);

		} catch (RuntimeException e) {
			System.out.println("Unexpected error in getAllUsersWithExpiredORInvalidAccessToken: " + e.getMessage());
			throw e;
		}
	}

	public void getSingleUser(APIRequestContext request, String URL, String userId)
			throws IOException, InterruptedException {

		System.out.println("\n ******************** Get Single User ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken));

			System.out.println("Request URL: " + response.url());
			System.out.println("Response status: " + response.status());
			apiValidation.apiValidation(response);

		} catch (RuntimeException e) {
			System.out.println("Unexpected error in getSingleUser: " + e.getMessage());
			throw e;
		}
	}

	public void getSingleUserWithExpiredOrInvalidAccessToken(APIRequestContext request, String URL, String userId)
			throws IOException, InterruptedException {

		System.out.println(
				"\n ******************** Get Single User With Expired Or Invalid Access Token ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.expiredToken));

			System.out.println("Request URL: " + response.url());
			System.out.println("Response status: " + response.status());
			apiValidation.apiValidation(response);

		} catch (RuntimeException e) {
			System.out.println("Unexpected error in getSingleUserWithExpiredOrInvalidAccessToken: " + e.getMessage());
			throw e;
		}
	}

	public void userNotExist(APIRequestContext request, String URL, String userId)
			throws IOException, InterruptedException {

		System.out.println("\n ******************** Get Single User - User Not Exist ********************\n");
		try {
			APIResponse response = request.get(URL + "/api/scim/v2/users/" + userId,
					RequestOptions.create().setHeader("Authorization", "Bearer " + AuthContext.accessToken));

			System.out.println("Request URL: " + response.url());
			System.out.println("Response status: " + response.status());
			apiValidation.apiValidation(response);

		} catch (RuntimeException e) {
			System.out.println("Unexpected error in userNotExist: " + e.getMessage());
			throw e;
		}
	}

}