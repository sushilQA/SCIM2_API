package org.testing.TestScripts;

import java.io.IOException;

import org.testing.TestSteps.Create_User_Sync;
import org.testing.TestSteps.Update_User_Sync;
import org.testing.utilities.ConfigContext;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.RequestContext;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import jxl.read.biff.BiffException;

public class UpdateUserSync {

	Update_User_Sync updateUserSync = new Update_User_Sync();

	@Test(enabled = true, priority = 15)
	public void updateUserSyncModify() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Update User Sync - Modify");
		try {
			updateUserSync.updateUserSync_modify(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), extentTest, 200, "HMQALenel21");
			extentTest.log(Status.PASS, "TC15 - Update User Sync - Modify");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC15 - Update User Sync - Modify: " + e.getMessage());
			throw e;
		}
	}
	
	@Test(enabled = true, priority = 16)
	public void updateUserSyncUserNotExist() throws IOException, InterruptedException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Update User - Negative - User Not Exist");
		try {
			updateUserSync.updateUserSync_userNotExist(RequestContext.request,
					ConfigContext.properties.getProperty("humana_dev"), extentTest, 404, "User With This ID Not Found" , "HMQALenel22");
			extentTest.log(Status.PASS, "TC16 - Update User - Negative - User Not Exist");
		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "TC16 - Update User - Negative - User Not Exist: " + e.getMessage());
			throw e;
		}
	}
	


}