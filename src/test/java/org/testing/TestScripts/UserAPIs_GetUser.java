package org.testing.TestScripts;

import java.io.IOException;

import org.testing.TestSteps.GetUsers;
import org.testing.utilities.ConfigContext;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.RandomNumberGenerator;
import org.testing.utilities.RequestContext;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import jxl.read.biff.BiffException;

public class UserAPIs_GetUser {

	GetUsers getUsers = new GetUsers();

	@Test(enabled = true, priority = 4)
	public void getAllUsers() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nGet All Users");
		try {
			getUsers.getAllUsers(RequestContext.request, ConfigContext.properties.getProperty("humana_dev"), "10", "10");
			extentTest.log(Status.PASS, "TC4 - Get All Users executed successfully");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC4 - Get All Users failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 5)
	public void getAllUsers_ExpiredORInvalidAccessToken() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nGet All Users with Expired/Invalid Access Token");
		try {
			getUsers.getAllUsersWithExpiredORInvalidAccessToken(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), "10", "10");
			extentTest.log(Status.PASS, "TC5 - Get All Users with Expired/Invalid Access Token Executed successfully");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC5 - Get All Users with Expired/Invalid Access Token failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 6)
	public void getSingleUser() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nGet Single User");
		try {
			getUsers.getSingleUser(RequestContext.request, ConfigContext.properties.getProperty("humana_dev"), "HMQALenel24");
			extentTest.log(Status.PASS, "TC6 - Get Single Users executed successfully");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC6 - Get Single User failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 7)
	public void getSingleUser_ExpiredOrInvalidAccessToken() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nGet Single User with Expired OR Invalid Access Token");
		try {
			getUsers.getSingleUserWithExpiredOrInvalidAccessToken(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), "HMQALenel24");
			extentTest.log(Status.PASS, "TC7 - Get Single User with Expired/Invalid Access Token Executed Successfully");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC7 - Get Single User with Expired/Invalid Access Token failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 8)
	public void getSingleUserNotExist() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nGet Single User - User Not Exist");
		try {
			getUsers.userNotExist(RequestContext.request, ConfigContext.properties.getProperty("humana_dev"),
					RandomNumberGenerator.randomNumber());
			extentTest.log(Status.PASS, "TC8 - Get Single User - User Not Exist");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC8 - Get Single User - User Not Exist failed: " + e.getMessage());
			throw e;
		}
	}

}