package org.testing.TestScripts;

import java.io.IOException;
import org.testing.TestSteps.Users;
import org.testing.utilities.ExcelDataRead;
import org.testing.utilities.GenerateExtentReports;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import jxl.read.biff.BiffException;

public class GetUser {

	@Test(enabled = true, priority = 2)
	public void getAllUsers() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nGet All Users");
		Playwright playwright = Playwright.create();
		APIRequestContext request = playwright.request().newContext();
		Users users = new Users();
		users.getAllUsers(request, ExcelDataRead.readACell(1, 0), "10","10");
		extentTest.log(Status.PASS, "TC4 - Get All Users executed successfully");
		request.dispose();
		playwright.close();

		extentReports.flush();
	}

}