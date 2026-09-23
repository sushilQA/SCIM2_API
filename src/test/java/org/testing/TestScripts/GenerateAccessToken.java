package org.testing.TestScripts;

import java.io.IOException;

import org.testing.TestSteps.UserOAuth;
import org.testing.utilities.ConfigContext;
import org.testing.utilities.ExcelDataRead;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.RequestContext;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import jxl.read.biff.BiffException;

public class GenerateAccessToken {

	UserOAuth login = new UserOAuth();

	@Test(enabled = true, priority = 1)
	public void generateAccessTokenWithValidCredentials() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("User OAuth With Valid Credentials");
		try {
			login.userLoginWithValidUserNameAndPassword(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), "password", ExcelDataRead.readACell(1, 0),
					ExcelDataRead.readACell(1, 1), extentTest, 200);
			extentTest.log(Status.PASS, "TC1 - Access Token Generated Successfully");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC1 - Access Token Generation failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 2)
	public void generateAccessTokenWithInvalidUserName() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("User OAuth With Invalid Username");
		try {
			login.userLoginWithInValidUserName(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), "password", ExcelDataRead.readACell(2, 0),
					ExcelDataRead.readACell(1, 1), extentTest, 400, "Invalid username or password");
			extentTest.log(Status.PASS, "TC2 - Unable To Generate Access Token Due To Invalid Username");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC2 - Invalid Username test failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 3)
	public void generateAccessTokenWithInvalidPassword() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("User OAuth With Invalid Password");
		try {
			login.userLoginWithInValidPassword(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), "password", ExcelDataRead.readACell(1, 0),
					ExcelDataRead.readACell(2, 1), extentTest, 400, "Invalid username or password");
			extentTest.log(Status.PASS, "TC3 - Unable To Generate Access Token Due To Invalid Password");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC3 - Invalid Password test failed: " + e.getMessage());
			throw e;
		}
	}

}