package org.testing.TestScripts;

import java.io.IOException;
import java.security.Permission;
import java.util.Properties;

import org.testing.TestSteps.UserModule;
import org.testing.TestSteps.User_Permissions;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.LoadPropertiesFile;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import jxl.read.biff.BiffException;

public class TC2_UserPermissions {
	
	@Test
	public void usersPermissions() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = User Permissions");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		User_Permissions userPermissions = new User_Permissions();
		userPermissions.allBackendUserPermissions(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}

}
