package org.testing.TestSteps;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testing.resources.BackendGatewayRequest;
import org.testing.utilities.ApiValidation;
import org.testing.utilities.LoadPropertiesFile;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jxl.read.biff.BiffException;

public class Leads {

	ApiValidation apiValidation = new ApiValidation();
	BackendGatewayRequest backendGatewayRequest = new BackendGatewayRequest();
	JSONArray savedFilterList;
	JSONArray getStateList;

	public void leadsModule(String URL, String environment) throws IOException, BiffException {
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		UserModule users = new UserModule(properties);
		users.loginBackendUser(URL, environment);
		String access_tokens = users.accessTokens;
		String deviceId = users.deviceId;
//		getSavedFilterList(URL, access_tokens, deviceId);
	//	getSavedFilterDetail(URL, access_tokens, deviceId);
		//getLMSData(URL, access_tokens, deviceId);
		//getLeadsCountryDialCodeList(URL, access_tokens, deviceId);
		//getStateList(URL, access_tokens, deviceId);
		//getCityList(URL, access_tokens, deviceId);
		getSortingFieldList(URL, access_tokens, deviceId);
		getAssignedCounsellor(URL, access_tokens, deviceId);

	}

	public void getSavedFilterList(String URL, String access_tokens, String deviceId) throws FileNotFoundException {

		System.out.println("******************** Get Saved Filter List ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when()
				.get(URL + "/leads/v2/getSavedFilterList/176");
		JSONObject jsonObject = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		JSONArray savedFilterList = jsonObject2.getJSONArray("list");
		if (savedFilterList.isEmpty()) {
			System.out.println("No Saved Filters found");
		} else {

			this.savedFilterList = savedFilterList;

		}
		apiValidation.apiValidation(response);

	}

	public void getSavedFilterDetail(String URL, String access_tokens, String deviceId) {
		System.out.println("******************** Get Saved Filter Detail ********************\n");
		for (int i = 0; i < savedFilterList.length(); i++) {
			JSONObject ob = savedFilterList.getJSONObject(i);
			String key = ob.get("id").toString();
			Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key")
					.and().header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest)
					.when().get(URL + "/leads/v2/getSavedFilterDetail/" + key);
			apiValidation.apiValidation(response);
			System.out.println("Executed for saved filter " + ob.get("filterName"));
		}
	}

	public void getLMSData(String URL, String access_tokens, String deviceId) {
		System.out.println("******************** Get LMS Data ********************\n");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setPage("0");
		backendGatewayRequest.setPer_page_record("15");
		backendGatewayRequest.setMarkAsDefault("false");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/leads/v2/getLMSData");
		apiValidation.apiValidation(response);
	}

	public void getSortingFieldList(String URL, String access_tokens, String deviceId) throws FileNotFoundException {

		System.out.println("******************** Get Sorting Field List ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when()
				.get(URL + "/leads/v1/getSortingFieldList/176");
		apiValidation.apiValidation(response);

	}
	
	public void getComparisonType(String URL, String access_tokens, String deviceId) throws FileNotFoundException {

		System.out.println("******************** Get Comparison Type ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when()
				.get(URL + "/leads/v1/getComparisonType");
		apiValidation.apiValidation(response);

	}

	public void getFilterList(String URL, String access_tokens, String deviceId) throws FileNotFoundException {

		System.out.println("******************** Get Filter List ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when()
				.get(URL + "/leads/v2/getFilterList/176");
		apiValidation.apiValidation(response);

	}

	public void getLeadsCountryDialCodeList(String URL, String access_tokens, String deviceId)
			throws FileNotFoundException {

		System.out.println("******************** Get Leads Country DialCode List ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when()
				.get(URL + "/leads/v1/getLeadsCountryDialCodeList/country_codes_12087");
		apiValidation.apiValidation(response);

	}

	public void getStateList(String URL, String access_tokens, String deviceId) throws FileNotFoundException {

		System.out.println("******************** Get State List ********************\n");
		backendGatewayRequest.setCollegeId("176");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/leads/v1/getStateList");
		JSONObject jsonObject = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		JSONArray getStateList = jsonObject2.getJSONArray("options");
		if (getStateList.isEmpty()) {
			System.out.println("No State Selected");
		} else {

			this.getStateList = getStateList;

		}
		apiValidation.apiValidation(response);

	}

	public void getCityList(String URL, String access_tokens, String deviceId) throws FileNotFoundException {

		System.out.println("******************** Get City List ********************\n");
		List<String> stateLists = new ArrayList<String>();
		for (int i = 0; i < getStateList.length(); i++) {
			JSONObject obj = getStateList.getJSONObject(i);
			stateLists.add(obj.getString("name"));
		}
		String[] listOfStates = stateLists.toArray(new String[stateLists.size()]);
		for(int i=0; i < listOfStates.length;i++)
		{
			System.out.println(listOfStates[i]);
		}
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setStateIds(listOfStates);
		System.out.println(backendGatewayRequest.getStateIds());
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/leads/v1/getCityList");
		apiValidation.apiValidation(response);

	}
	public void getAssignedCounsellor(String URL, String access_tokens, String deviceId) throws FileNotFoundException {

		System.out.println("******************** Get Assigned Counsellor ********************\n");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setAllowedOnly("false");
		backendGatewayRequest.setCompleteAllOverdueFollowups("false");
		backendGatewayRequest.setFollowUpToggle("false");
		backendGatewayRequest.setLeadId("13500818");
		backendGatewayRequest.setModuleName("lead");
		backendGatewayRequest.setVoiceNoteDuration("0");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/leads/v1/getAssignedCounsellor");
		JSONObject jsonObject = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		JSONArray getCounsellorId = jsonObject2.getJSONArray("options");
		if (getStateList.isEmpty()) {
			System.out.println("No State Selected");
		} else {

			this.getStateList = getStateList;

		}
		apiValidation.apiValidation(response);

	}
}
