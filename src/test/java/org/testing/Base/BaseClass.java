package org.testing.Base;

import java.io.IOException;

import org.testing.TestSteps.UserOAuth;
import org.testing.utilities.ConfigContext;
import org.testing.utilities.ExcelDataRead;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.LoadPropertiesFile;
import org.testing.utilities.RequestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.microsoft.playwright.Playwright;
import jxl.read.biff.BiffException;

public class BaseClass {

	@BeforeSuite(enabled = true)
	public void generateAccessTokenWithValidCredentials() throws IOException, InterruptedException, BiffException {
		try {
			ConfigContext.properties = LoadPropertiesFile.handlePropertyFile("../SCIM2_API/URI.properties");
			RequestContext.playwright = Playwright.create();
			RequestContext.request = RequestContext.playwright.request().newContext();

			UserOAuth login = new UserOAuth();
			login.userLogin(RequestContext.request, ConfigContext.properties.getProperty("humana_dev"), "password",
					ExcelDataRead.readACell(1, 1), ExcelDataRead.readACell(1, 2));

		} catch (IOException | InterruptedException | BiffException e) {
			System.out.println("Suite setup failed: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("Unexpected error during suite setup: " + e.getMessage());
			throw e;
		}
	}

	@AfterSuite(enabled = true)
	public void tearDown() {
		try {
			if (RequestContext.request != null) {
				RequestContext.request.dispose();
			}
		} catch (Exception e) {
			System.out.println("Error disposing request context: " + e.getMessage());
		}

		try {
			if (RequestContext.playwright != null) {
				RequestContext.playwright.close();
			}
		} catch (Exception e) {
			System.out.println("Error closing playwright: " + e.getMessage());
		}

		try {
			GenerateExtentReports.generateExtentReport().flush();
		} catch (Exception e) {
			System.out.println("Error flushing extent report: " + e.getMessage());
		}
	}
}