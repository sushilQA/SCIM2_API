package org.testing.TestSteps;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;
import org.testing.utilities.ApiValidation;
import org.testing.utilities.LoadPropertiesFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jxl.read.biff.BiffException;

public class User_Login {

	ApiValidation apiValidation = new ApiValidation();

	public void userLogin(String URL) throws IOException, InterruptedException {

		System.out.println("******************** User OAuth ********************\n");

		ObjectMapper objectMapper = new ObjectMapper();
		/*
		 * Map<String, Object> payload = new HashMap<>(); 
		 * payload.put("client_id","sushiladmin");
		 * payload.put("client_secret", "sushilAgr12@");
		 * payload.put("scope", "scim");
		 * String jsonPayload = objectMapper.writeValueAsString(payload); 
		 * Response response = given().log().all().contentType(ContentType.JSON).body(jsonPayload)
		 * .when()
		 * .post(URL + "/api/oauth/token?grant_type=client_credentials&username=sushiladmin&password=sushilAgr12@");
		 */
		Map<String, String> payload = new HashMap<>();
		payload.put("client_id", "sushiladmin");
		payload.put("client_secret", "sushilAgr12@");
		payload.put("scope", "scim");
		String jsonPayload = objectMapper.writeValueAsString(payload);

		Response response = given()
		        .log().all()
		        .contentType(ContentType.JSON)
		        .queryParam("grant_type", "password")
		        .queryParam("username", "sushiladmin")
		        .queryParam("password", "sushilAgr12@")
		        .body(jsonPayload)
		    .when()
		        .post(URL + "/api/scim/oauth/token")
		    .then()
		       // .log().all()
		        .extract().response();
		System.out.println("RESPONSE AFTER API SUCCESS");
		apiValidation.apiValidation(response);

	}

}
