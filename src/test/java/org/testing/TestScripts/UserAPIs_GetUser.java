package org.testing.TestScripts;

import java.io.IOException;

import org.testing.TestSteps.GetUsers;
import org.testing.utilities.ConfigContext;
import org.testing.utilities.GenerateExtentReports;
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
		getUsers.getAllUsers(RequestContext.request, ConfigContext.properties.getProperty("humana_uat"), "10","10");
		extentTest.log(Status.PASS, "TC4 - Get All Users executed successfully");
	}

	@Test(enabled = true, priority = 5)
	public void getAllUsersWithExpiredToken() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("\nGet All Users");
		getUsers.getAllUsersWithExpiredAccessToken(RequestContext.request, ConfigContext.properties.getProperty("humana_uat"), "10","10");
		extentTest.log(Status.PASS, "TC5 - Get All Users with Expired Token Executed successfully");
	}
	
	

}