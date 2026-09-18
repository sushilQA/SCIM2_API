package org.testing.TestScripts;

import java.io.IOException;

import org.testing.TestSteps.User_Login;
import org.testing.utilities.GenerateExtentReports;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

public class UserOAuth {

	@Test(enabled = true, priority = 1)
	public void users() throws IOException, InterruptedException {

		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("User OAuth");
		Playwright playwright = Playwright.create();
		APIRequestContext request = playwright.request().newContext();

		User_Login login = new User_Login();
		login.userLogin(request, "https://humana-uat.alerthsc.com");

		request.dispose();
		playwright.close();

		extentReports.flush();
	}

}