package org.testing.utilities;

import org.json.JSONObject;

import io.restassured.response.Response;

public class ApiValidation {

	public void apiValidation(Response response) {

		if (response.statusCode() >= 200 & response.statusCode() < 300) {
			JSONObject jsonObject = new JSONObject(response.asPrettyString());
			System.out.println("\nAnd the response is given below :\n" + response.asPrettyString());
		} else if (response.statusCode() >= 300 & response.statusCode() < 400) {
			System.out.println("Response Code is =" + response.statusCode());
			JSONObject jsonObject = new JSONObject(response.asPrettyString());
			System.out.println(jsonObject.get("message").toString());
			System.out.println("\nAnd the response is given below :\n" + response.asPrettyString());
		} else if (response.statusCode() >= 400 & response.statusCode() < 500) {
			System.out.println("Response Code is =" + response.statusCode());
			JSONObject jsonObject = new JSONObject(response.asPrettyString());
			System.out.println(jsonObject.get("message").toString());
			System.out.println("\nAnd the response is given below :\n" + response.asPrettyString());
		} else if (response.statusCode() >= 500 & response.statusCode() < 512) {
			System.out.println("Response Code is =" + response.statusCode());
			JSONObject jsonObject = new JSONObject(response.asPrettyString());
			System.out.println(jsonObject.get("message").toString());
			System.out.println("\nAnd the response is given below :\n" + response.asPrettyString());
		} else {
			System.out.println("Response Code is =" + response.statusCode());
			JSONObject jsonObject = new JSONObject(response.asPrettyString());
			System.out.println(jsonObject.get("message").toString());
			System.out.println("\nAnd the response is given below :\n" + response.asPrettyString());
		}

	}

}
