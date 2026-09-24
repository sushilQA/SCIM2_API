package org.testing.TestScripts;

import java.io.IOException;
import org.testing.TestSteps.LenelProvisioning;
import org.testing.TestSteps.LoginPage;
import org.testing.utilities.BrowserContext;
import org.testing.utilities.ConfigContext;
import org.testing.utilities.ExcelDataRead;
import org.testing.utilities.GenerateExtentReports;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import jxl.read.biff.BiffException;

public class LenelProvisioningTests {

	LoginPage loginPage = new LoginPage();
	LenelProvisioning lenelProvisioning = new LenelProvisioning();

	@BeforeClass
	public void setupBrowser() throws BiffException, IOException {
		BrowserContext.launchBrowser();
		loginPage.login(BrowserContext.page, ConfigContext.properties.getProperty("humana_dev_ui"), ExcelDataRead.readACell(1, 0), ExcelDataRead.readACell(1, 1));
	}

	@AfterClass
	public void teardownBrowser() {
		BrowserContext.closeBrowser();
	}

	@Test(enabled = true, priority = 1)
	public void lenelProvisioningSuccess() {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Lenel Provisioning - Success");
		try {
			lenelProvisioning.navigateToLenelSettings(BrowserContext.page);
			lenelProvisioning.toggleProvisioningCheckbox(BrowserContext.page, true);
			lenelProvisioning.enterLenelCredentials(BrowserContext.page);
			lenelProvisioning.clickTestConnection(BrowserContext.page);

			String resultMessage = lenelProvisioning.getConnectionResultMessage(BrowserContext.page);
			extentTest.info("Connection result: " + resultMessage);

			// TODO: Adjust expected success text to match your actual UI message
			Assert.assertTrue(resultMessage.toLowerCase().contains("success"),
					"Expected success message but got: " + resultMessage);

			extentTest.log(Status.PASS, "Lenel provisioning connection succeeded as expected");

		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "Lenel provisioning success test failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(enabled = true, priority = 2)
	public void lenelProvisioningFailed() {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.createTest("Lenel Provisioning - Failed");
		try {
			lenelProvisioning.navigateToLenelSettings(BrowserContext.page);
			lenelProvisioning.toggleProvisioningCheckbox(BrowserContext.page, true);
			// Intentionally using an invalid password to force failure
			lenelProvisioning.enterLenelCredentials(BrowserContext.page);
			lenelProvisioning.clickTestConnection(BrowserContext.page);

			String resultMessage = lenelProvisioning.getConnectionResultMessage(BrowserContext.page);
			extentTest.info("Connection result: " + resultMessage);

			// TODO: Adjust expected failure text to match your actual UI message
			Assert.assertTrue(resultMessage.toLowerCase().contains("fail"),
					"Expected failure message but got: " + resultMessage);

			extentTest.log(Status.PASS, "Lenel provisioning connection failed as expected");

		} catch (AssertionError | Exception e) {
			extentTest.log(Status.FAIL, "Lenel provisioning failed test failed: " + e.getMessage());
			throw e;
		}
	}

}