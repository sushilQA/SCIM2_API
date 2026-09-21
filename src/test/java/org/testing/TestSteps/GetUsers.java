package org.testing.TestSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.testing.utilities.ApiValidation;
import org.testing.utilities.AuthContext;
import org.testing.utilities.RandomNumberGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class GetUsers {
	

	ApiValidation apiValidation = new ApiValidation();

	public void getAllUsers(APIRequestContext request, String URL, String startIndex, String count)
			throws IOException, InterruptedException {

		System.out.println("\n ******************** Get All Users ********************\n");

		APIResponse response = request.get(URL + "/api/scim/v2/users",
				RequestOptions.create().setQueryParam("startIndex", startIndex).setQueryParam("count", count)
						.setHeader("Authorization", "Bearer " + AuthContext.accessToken));

		System.out.println("Request URL: " + response.url());
		System.out.println("Response status: " + response.status());
		apiValidation.apiValidation(response);

	}

	public void getAllUsersWithExpiredAccessToken(APIRequestContext request, String URL, String startIndex,
			String count) throws IOException, InterruptedException {

		System.out.println("\n ******************** Get All Users with Invalid / Expired Access Token ********************\n");
		APIResponse response = request.get(URL + "/api/scim/v2/users",
				RequestOptions.create().setQueryParam("startIndex", startIndex).setQueryParam("count", count)
						.setHeader("Authorization", "Bearer " + AuthContext.expiredToken));
		System.out.println("Request URL: " + response.url());
		System.out.println("Response status: " + response.status());
		apiValidation.apiValidation(response);

	}

}