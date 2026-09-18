package org.testing.TestScripts;

import java.io.IOException;
import org.testing.TestSteps.Users;
import org.testing.utilities.ExcelDataRead;
import org.testing.utilities.GenerateExtentReports;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import jxl.read.biff.BiffException;

public class UserOAuth {

	@BeforeSuite(enabled = true)
	public void userOAuthWithValidCredentials() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("User OAuth With Valid Credentials");
		Playwright playwright = Playwright.create();
		APIRequestContext request = playwright.request().newContext();
		Users login = new Users();
		login.userLoginWithValidUserNameAndPassword(request, ExcelDataRead.readACell(1,0), "password", ExcelDataRead.readACell(1, 1), ExcelDataRead.readACell(1, 2));
		extentTest.log(Status.PASS, "TC1 - User login with valid credentials Successfully");
		request.dispose();
		playwright.close();
		extentReports.flush();
	}
	
	@Test(enabled = true, priority = 1)
	public void userOAuthWithInvalidUserName() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nUser OAuth With Invalid Username");
		Playwright playwright = Playwright.create();
		APIRequestContext request = playwright.request().newContext();
		Users login = new Users();
		login.userLoginWithInValidUserName(request, ExcelDataRead.readACell(1,0), "password", ExcelDataRead.readACell(2, 1), ExcelDataRead.readACell(1, 2));
		extentTest.log(Status.PASS, "TC2 - User unable to login due to invalid username");
		request.dispose();
		playwright.close();
		extentReports.flush();
	}
	
	@Test(enabled = true, priority = 2)
	public void userOAuthWithInvalidPassword() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nUser OAuth With Invalid Password");
		Playwright playwright = Playwright.create();
		APIRequestContext request = playwright.request().newContext();
		Users login = new Users();
		login.userLoginWithInValidPassword(request, ExcelDataRead.readACell(1,0), "password", ExcelDataRead.readACell(1, 1), ExcelDataRead.readACell(2, 2));
		extentTest.log(Status.PASS, "TC3 - User unable to login due to invalid password");
		request.dispose();
		playwright.close();
		extentReports.flush();
	}

}