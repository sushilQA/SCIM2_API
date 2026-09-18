package org.testing.TestSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testing.utilities.ApiValidation;
import org.testing.utilities.AuthContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class Users {

	ApiValidation apiValidation = new ApiValidation();

	public void userLogin(APIRequestContext request, String URL, String grant_type, String username, String password ) throws IOException, InterruptedException {

		System.out.println("******************** User OAuth ********************\n");

		APIResponse response = request.post(URL + "/api/scim/oauth/token",
				RequestOptions.create()
				.setQueryParam("grant_type", grant_type)
				.setQueryParam("username", username)
				.setQueryParam("password", password)
				.setHeader("Content-Type", "application/json"));

		System.out.println("Request URL: " + response.url());
		System.out.println("Response status: " + response.status());
		JSONObject jsonResponse = new JSONObject(response.text());
		String accessToken = jsonResponse.getString("access_token");
		AuthContext.accessToken = accessToken;
		System.out.println("Access Token stored: " + accessToken);
		apiValidation.apiValidation(response);

	}

	public void getAllUsers(APIRequestContext request, String URL, String startIndex, String count) throws IOException, InterruptedException {

		System.out.println("******************** Get All Users ********************\n");

		APIResponse response = request.get(URL + "/api/scim/v2/users",
				RequestOptions.create()
				.setQueryParam("startIndex", startIndex)
				.setQueryParam("count", count)
				.setHeader("Authorization", "Bearer " + AuthContext.accessToken));

		System.out.println("Request URL: " + response.url());
		System.out.println("Response status: " + response.status());
		apiValidation.apiValidation(response);

	}

}