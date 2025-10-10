package org.testing.TestScripts;

import java.io.IOException;
import java.util.Properties;

import org.testing.TestSteps.User_Login;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.LoadPropertiesFile;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import jxl.read.biff.BiffException;

public class UserOAuth {
	
	@Test(enabled=true , priority=1)
	public void users() throws IOException, BiffException, InterruptedException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("User OAuth");
		User_Login login = new User_Login();
		login.userLogin("https://humana-uat.alerthsc.com");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}
	
	
}
