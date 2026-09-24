package org.testing.TestScripts;

import java.io.IOException;

import org.testing.TestSteps.Create_User_Sync;
import org.testing.utilities.ConfigContext;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.RequestContext;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import jxl.read.biff.BiffException;

public class CreateUserSync {

	Create_User_Sync createUserSync = new Create_User_Sync();

	@Test(enabled = true, priority = 9)
	public void createUserSyncSuccess() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Create User Sync");
		try {
			createUserSync.createUserSyncSuccess(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), extentTest, 201);
			extentTest.log(Status.PASS, "TC9 - Create User Sync");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC9 - Create User Sync failed: " + e.getMessage());
			throw e;
		}
	}
	

	@Test(enabled = true, priority = 10)
	public void createUserSyncExpiredAccessToken() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Create User - Sync Failed - Invalid Or Expired Access Token");
		try {
			createUserSync.createUserSyncFailedDueToInvalidOrExpiredAccessToken(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), extentTest, 401, "Invalid OR Expired Access Token");
			extentTest.log(Status.PASS, "TC10 - Create User - Sync Failed - Invalid Or Expired Access Token");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC10 - Create User - Sync Failed - Invalid Or Expired Access Token failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 11)
	public void createUserSyncUserAlreadyExist() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Create User - Sync Failed - User Already Exist");
		try {
			createUserSync.createUserSyncFailedUserAlreadyExist(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), extentTest, 400, "Already Exist");
			extentTest.log(Status.PASS, "TC11 - Create User - Sync Failed - User Already Exist");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC11 - Create User - Sync Failed - User Already Exist failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 12)
	public void createUserSyncMissedId() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Create User - Sync Failed - Missed Id in Payload");
		try {
			createUserSync.createUserSyncFailedDueToMissedId(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), extentTest, 400, "id");
			extentTest.log(Status.PASS, "TC12 - Create User - Sync Failed - Missed Id in Payload");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC12 - Create User - Sync Failed - Missed Id in Payload failed: " + e.getMessage());
			throw e;
		}
	}
	
	@Test(enabled = true, priority = 13)
	public void createUserNoAuth() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Create User - Sync Failed - No Auth Defined");
		try {
			createUserSync.createUserSyncFailedDueNoAuth(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), extentTest, 400);
			extentTest.log(Status.PASS, "TC13 - Create User - Sync Failed - No Auth Defined");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC13 - Create User - Sync Failed - No Auth Defined: " + e.getMessage());
			throw e;
		}
	}
	
	@Test(enabled = true, priority = 14)
	public void createUserSyncSuccessWithGroup() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Create User - Sync Success with Group");
		try {
			createUserSync.createUserSyncSuccessWithGroup(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), extentTest, 201);
			extentTest.log(Status.PASS, "TC14 - Create User - Sync Success with Group");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC14 - Create User - Sync Success with Group: " + e.getMessage());
			throw e;
		}
	}

}