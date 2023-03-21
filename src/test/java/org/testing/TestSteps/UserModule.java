package org.testing.TestSteps;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testing.resources.BackendGatewayRequest;
import org.testing.resources.Field_Force_Request_Body;
import org.testing.resources.LoadUaerModuleData;
import org.testing.resources.User_App_Settings;
import org.testing.utilities.ApiValidation;
import org.testing.utilities.BeforeRequest;
import org.testing.utilities.ExcelDataRead;
import io.restassured.response.*;
import jxl.read.biff.BiffException;
import io.restassured.http.ContentType;

public class UserModule {

	Properties properties;
	String accessTokens;
	String deviceId;
	String otp;
	User_App_Settings appSettings = new User_App_Settings();
	BackendGatewayRequest backendGatewayRequest = new BackendGatewayRequest();
	ApiValidation apiValidation = new ApiValidation();
	LoadUaerModuleData loadUaerModuleData = new LoadUaerModuleData();

	public UserModule(Properties properties) {
		this.properties = properties;
	}

	public void usersModule(String uriKey, String environment) throws IOException, BiffException {

		otpEmail(uriKey, environment);
		loginBackendUserByEmailOTP(uriKey, environment);
		otpMobile(uriKey, environment);
		loginBackendUserByMobileOTP(uriKey, environment); 
		getCollegeList(uriKey,accessTokens, deviceId); 
		getCollegeDetails(uriKey, accessTokens, deviceId);
		getHeadCounsellorList(uriKey, accessTokens, deviceId);
		updateUserDeviceAndAppInfo(uriKey, accessTokens, deviceId);
		formList(uriKey,accessTokens, deviceId);
		updateAppPreferences(uriKey, accessTokens,deviceId);
		logoutBackendUser(uriKey);
		forgotPasswordBackendUser(uriKey,environment);
		resetPasswordBackendUser(uriKey, environment);
	}

	public void fieldForce(String URL, String environment) throws BiffException, IOException {

		loginBackendUser(URL, environment);
		markCheckinCheckout(URL, accessTokens, deviceId);
		getCurrentAttendanceDetails(URL, accessTokens, deviceId);
		getCheckinCheckoutDetails(URL, accessTokens, deviceId);
		getAttendanceBreakdown(URL, accessTokens, deviceId);
		getLocationHistory(URL, accessTokens, deviceId);
		updateLocationByLatLong(URL, accessTokens, deviceId);
		trackLocation(URL, accessTokens, deviceId);

	}

	public void emailAuth(String uriKey, String environment) throws BiffException, IOException {
		System.out.println("******************** Email Auth ********************\n");
		backendGatewayRequest.setEmail(ExcelDataRead.readACell(environment, 2, 0));
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(uriKey + "/users/v1/emailAuth");
		apiValidation.apiValidation(response);
	}

	public void loginBackendUser(String URL, String environment) throws BiffException, IOException {
		System.out.println("******************** Backend User Login ********************\n");
		backendGatewayRequest.setEmail(ExcelDataRead.readACell(environment, 2, 0));
		backendGatewayRequest.setPassword(ExcelDataRead.readACell(environment, 2, 1));
		backendGatewayRequest.setDeviceId("Oppo A1");
		backendGatewayRequest.setLoginWith("credentails");
		backendGatewayRequest.setDeviceOs("android");
		backendGatewayRequest.setFcmToken("dPxK-km7Qb6J-jjWPj8F26:APA91bFbYWAG1wjuPzUGEM6LUZUUGnH3Ir"
				+ "CpAB77fyeh0BNEwO2SCJAZ9Ru942XJt7i92xX6XP6ETKG2lfzO4LwAZKLm"
				+ "5cT2Imk7L7xM6vVf3cVuQQesacaeeRPuTpTCgDBBXBQYKpnl");
		backendGatewayRequest.setRememberMe("");
		backendGatewayRequest.setAppType("backend");
		backendGatewayRequest.setMode("android");
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v3/login");
		JSONObject jsonObject = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		if (jsonObject.get("message").toString().contains("Incorrect password")) {
			System.out.println(jsonObject.get("message").toString());
		} else {
			this.accessTokens = jsonObject2.get("accessToken").toString();
			this.deviceId = jsonObject2.get("deviceId").toString();
		}
		apiValidation.apiValidation(response);

	}

	public void loginBackendUserByEmailOTP(String URL, String environment) throws BiffException, IOException {
		System.out.println("******************** Backend User Login By Email OTP ********************\n");
		backendGatewayRequest.setEmail(ExcelDataRead.readACell(environment, 2, 0));
		backendGatewayRequest.setPassword(otp);
		backendGatewayRequest.setDeviceId("Oppo A1");
		backendGatewayRequest.setLoginWith("otp");
		backendGatewayRequest.setDeviceOs("android");
		backendGatewayRequest.setFcmToken("dPxK-km7Qb6J-jjWPj8F26:APA91bFbYWAG1wjuPzUGEM6LUZUUGnH3Ir"
				+ "CpAB77fyeh0BNEwO2SCJAZ9Ru942XJt7i92xX6XP6ETKG2lfzO4LwAZKLm"
				+ "5cT2Imk7L7xM6vVf3cVuQQesacaeeRPuTpTCgDBBXBQYKpnl");
		backendGatewayRequest.setRememberMe("");
		backendGatewayRequest.setAppType("backend");
		backendGatewayRequest.setMode("android");
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v3/login");
		JSONObject jsonObject = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		if (jsonObject.get("message").toString().contains("Incorrect password")) {
			System.out.println(jsonObject.get("message").toString());
		} else {
			this.accessTokens = jsonObject2.get("accessToken").toString();
			this.deviceId = jsonObject2.get("deviceId").toString();
		}
		apiValidation.apiValidation(response);
	}

	public void loginBackendUserByMobileOTP(String URL, String environment) throws BiffException, IOException {
		System.out.println("******************** Backend User Login By Mobile OTP ********************\n");
		backendGatewayRequest.setEmail(ExcelDataRead.readACell(environment, 2, 2));
		backendGatewayRequest.setPassword(otp);
		backendGatewayRequest.setDeviceId("Oppo A1");
		backendGatewayRequest.setLoginWith("otp");
		backendGatewayRequest.setDeviceOs("android");
		backendGatewayRequest.setFcmToken("dPxK-km7Qb6J-jjWPj8F26:APA91bFbYWAG1wjuPzUGEM6LUZUUGnH3Ir"
				+ "CpAB77fyeh0BNEwO2SCJAZ9Ru942XJt7i92xX6XP6ETKG2lfzO4LwAZKLm"
				+ "5cT2Imk7L7xM6vVf3cVuQQesacaeeRPuTpTCgDBBXBQYKpnl");
		backendGatewayRequest.setRememberMe("");
		backendGatewayRequest.setAppType("backend");
		backendGatewayRequest.setMode("android");
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v3/login");
		JSONObject jsonObject = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		if (jsonObject.get("message").toString().contains("Incorrect password")) {
			System.out.println(jsonObject.get("message").toString());
		} else {
			this.accessTokens = jsonObject2.get("accessToken").toString();
			this.deviceId = jsonObject2.get("deviceId").toString();
		}
		apiValidation.apiValidation(response);
	}

	public void logoutBackendUser(String URL) {
		System.out.println("******************** Backend User Logout  ********************\n");
		backendGatewayRequest.setDeviceId("Oppo A1");
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v2/logout");
		apiValidation.apiValidation(response);

	}

	public void otpEmail(String URL, String environment) throws BiffException, IOException {
		System.out.println("******************** Otp Login Email  ********************\n");
		backendGatewayRequest.setEmailOrMobile(ExcelDataRead.readACell(environment, 2, 0));
		backendGatewayRequest.setIsEmail("true");
		backendGatewayRequest.setLoginWith("otp");
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/otpLogin");
		JSONObject jsonObject = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		if (jsonObject2.get("otp_value").toString().isEmpty()) {
			System.out.println("Otp is not trigger");
		} else {
			this.otp = jsonObject2.get("otp_value").toString();
		}
		apiValidation.apiValidation(response);

	}
	
	public void otpMobile(String URL, String environment) throws BiffException, IOException {
		System.out.println("******************** Otp Login Email  ********************\n");
		File file = new File("../Npf_Backend_Gateway/src/test/java/org/testing/resources/otpMobile.json");
		FileReader fileReader = new FileReader(file);
		JSONTokener jsonTokener = new JSONTokener(fileReader);
		JSONObject jsonObject = new JSONObject(jsonTokener);
		Response response = given().contentType(ContentType.JSON).body(jsonObject.toString())
				.post(URL + "/users/v1/otpLogin");
		JSONObject jsonObject1 = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject1.get("data");
		if (jsonObject2.get("otp_value").toString().isEmpty()) {
			System.out.println("Otp is not trigger");
		} else {
			this.otp = jsonObject2.get("otp_value").toString();
		}
		apiValidation.apiValidation(response);

	}

	public void otpLoginMobile(String URL, String environment) throws BiffException, IOException {
		System.out.println("******************* Otp Login Mobile  ********************\n");
		backendGatewayRequest.setEmailOrMobile(ExcelDataRead.readACell(environment, 2, 2));
		backendGatewayRequest.setIsEmail("false");
		backendGatewayRequest.setLoginWith("otp");
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/otpLogin");
		BeforeRequest.printRequest(backendGatewayRequest);
		JSONObject jsonObject = new JSONObject(response.asPrettyString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		if (jsonObject2.get("otp_value").toString().isEmpty()) {
			System.out.println("Otp is not trigger");
		} else {
			this.otp = jsonObject2.get("otp_value").toString();
		}
		apiValidation.apiValidation(response);
	}

	public void forgotPasswordBackendUser(String URL, String environment) throws BiffException, IOException {
		System.out.println("******************** Forgot Password ********************\n");
		backendGatewayRequest.setEmail(ExcelDataRead.readACell(environment, 1, 0));
		backendGatewayRequest.setAppType("backend");
		backendGatewayRequest.setMode("android");
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/forgot");
		apiValidation.apiValidation(response);

	}

	public void resetPasswordBackendUser(String URL, String environment) throws BiffException, IOException {
		System.out.println("******************** Reset Password ********************\n");
		backendGatewayRequest.setEmail(ExcelDataRead.readACell(environment, 1, 0));
		backendGatewayRequest.setPassword(ExcelDataRead.readACell(environment, 1, 1));
		backendGatewayRequest.setVerifyPassword("Test@1234");
		backendGatewayRequest.setUniqueId("abcdef-1234-xyz");
		backendGatewayRequest.setAppType("backend");
		backendGatewayRequest.setMode("android");
		Response response = given().contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/resetPassword");
		apiValidation.apiValidation(response);

	}

	public void checkPasswordExpired(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Check Password Expired ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when()
				.post(URL + "/users/v2/checkPasswordExpired");
		apiValidation.apiValidation(response);

	}

	public void getCollegeList(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Get College List ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when().get(URL + "/colleges/v1/list");
		apiValidation.apiValidation(response);

	}

	public void getCollegeDetails(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Get College Details ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when()
				.get(URL + "/users/v1/getCollegeDetails/176");
		apiValidation.apiValidation(response);

	}

	public void getHeadCounsellorList(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Get HeadCounsellor List ********************\n");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).when()
				.get(URL + "/users/v1/getHeadCounsellors/176");
		apiValidation.apiValidation(response);

	}

	public void updateUserDeviceAndAppInfo(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Update User Device And App Info ********************\n");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setAppUpdate("true");
		backendGatewayRequest.setDeviceBrand("Realme");
		backendGatewayRequest.setDeviceId("Oppo A1");
		backendGatewayRequest.setDeviceModel("RMX3430");
		backendGatewayRequest.setDeviceOs("Android");
		backendGatewayRequest.setHasTrueCaller("True");
		backendGatewayRequest.setInstituteName("Testing Institute");
		backendGatewayRequest.setNpfAppVersion("3.3.0");
		backendGatewayRequest.setNpfTrackerAppVersion("1.13");
		backendGatewayRequest.setOsVersion("Android 11 (30)");
		backendGatewayRequest.setUserName("SUSHIL");
		backendGatewayRequest.setUserRole("Counsellor");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/updateUserDeviceAndAppInfo/176");
		apiValidation.apiValidation(response);

	}

	public void updateAppPreferences(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Update App Preferences ********************\n");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setUpdated("1643204126");
		backendGatewayRequest.setPartialUpdate("true");
		appSettings.setIsTrackerAbsent("false");
		appSettings.setMainOverlaySwitch("false");
		appSettings.setMainPermissionOverLay("true");
		appSettings.setMainPermissionStorage("true");
		appSettings.setMainRecordingSwitch("false");
		appSettings.setMainTrackingSwitch("false");
		appSettings.setTrackerPermissionCallLog("true");
		appSettings.setTrackerPermissionPhoneCall("true");
		appSettings.setTrackerPermissionPhoneState("true");
		appSettings.setTrackerPermissionReadContact("true");
		appSettings.setTrackerPermissionRecording("true");
		appSettings.setTrackerPermissionStorage("true");
		appSettings.setTrackerPermissionTracking("true");
		appSettings.setTrackingPermissionRecording("true");
		backendGatewayRequest.setAppSettings(appSettings);
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/updateAppPreferences/176");
		System.out.println(response.asPrettyString());
		// apiValidation.apiValidation(response);

	}

	public void formList(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Form List ********************\n");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setShowAllForm("true");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/formList");
		apiValidation.apiValidation(response);

	}

	public void markCheckinCheckout(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Mark Checkin Checkout ********************\n");
		backendGatewayRequest.setBattery_percentage("95");
		backendGatewayRequest.setLocation_accuracy("20.0");
		backendGatewayRequest.setAddress("9, Old Delhi Rd, Sector 12,"
				+ " Dharam Colony, Palam Vihar Extension, Gurugram, Haryana 122017, India,");
		backendGatewayRequest.setDevice_type("Android Mobile App");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setLat("28.5003695");
		backendGatewayRequest.setLong("77.0331542");
		backendGatewayRequest.setStatus("checkin");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/markCheckinCheckout/176");
		apiValidation.apiValidation(response);

	}

	public void getCurrentAttendanceDetails(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Get Current Attendance Details ********************\n");
		backendGatewayRequest.setBattery_percentage("95");
		backendGatewayRequest.setLocation_accuracy("20.0");
		backendGatewayRequest.setDevice_type("Android Mobile App");
		backendGatewayRequest.setCollegeId("176");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/getCurrentAttendanceDetails/176");
		apiValidation.apiValidation(response);

	}

	public void getCheckinCheckoutDetails(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Get Checkin Checkout Details ********************\n");
		backendGatewayRequest.setBattery_percentage("95");
		backendGatewayRequest.setLocation_accuracy("20.0");
		backendGatewayRequest.setDevice_type("Android Mobile App");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setStart_date("01/01/2022");
		backendGatewayRequest.setEnd_date("31/01/2022");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/getCheckinCheckoutDetails/176");
		apiValidation.apiValidation(response);

	}

	public void getAttendanceBreakdown(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Get Attendance Breakdown ********************\n");
		backendGatewayRequest.setBattery_percentage("95");
		backendGatewayRequest.setLocation_accuracy("20.0");
		backendGatewayRequest.setDevice_type("Android Mobile App");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setDate("22/01/2022");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/getAttendanceBreakdown/176");
		apiValidation.apiValidation(response);

	}

	public void getLocationHistory(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Get Location History ********************\n");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setDate("22/01/2022");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/getLocationHistory");
		apiValidation.apiValidation(response);

	}

	public void updateLocationByLatLong(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Update Location By Lat Long ********************\n");
		backendGatewayRequest.setCollegeId("176");
		backendGatewayRequest.setDate("22/01/2022");
		backendGatewayRequest.setLat("28.5003695");
		backendGatewayRequest.setLong("77.0331542");
		backendGatewayRequest.setLocation("9, Old Delhi Rd, Sector 12,"
				+ " Dharam Colony, Palam Vihar Extension, Gurugram, Haryana 122017, India,");
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/updateLocationByLatLong");
		apiValidation.apiValidation(response);

	}

	public void trackLocation(String URL, String access_tokens, String deviceId) {

		System.out.println("******************** Track Location ********************\n");
		backendGatewayRequest.setCollegeId("176");
		Field_Force_Request_Body[] ff_data = new Field_Force_Request_Body[5];
		ff_data[0] = new Field_Force_Request_Body();
		System.out.println(ff_data[0]);
		ff_data[0].setAddress("Faridabad, ABC test sector 56 Faridabad");
		ff_data[0].setBattery_percentage("95");
		ff_data[0].setDate("22/01/2022");
		ff_data[0].setHas_manual_time("false");
		ff_data[0].setLat("28.4089");
		ff_data[0].setLong("77.3178");
		ff_data[0].setMode("tracking");
		ff_data[0].setStatus("checkin");
		ff_data[0].setTimestamp("1624973110");
		backendGatewayRequest.setFf_data(ff_data);
		Response response = given().header("access-token", access_tokens).and().header("access-key", "access-key").and()
				.header("deviceId", deviceId).contentType(ContentType.JSON).body(backendGatewayRequest).when()
				.post(URL + "/users/v1/trackLocation");
		apiValidation.apiValidation(response);
	}
}
