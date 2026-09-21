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
		ConfigContext.properties = LoadPropertiesFile.handlePropertyFile("../SCIM2_API/URI.properties");
		RequestContext.playwright = Playwright.create();
		RequestContext.request = RequestContext.playwright.request().newContext();
		UserOAuth login = new UserOAuth();
		login.userLogin(RequestContext.request, ConfigContext.properties.getProperty("humana_uat"), "password", ExcelDataRead.readACell(1, 1), ExcelDataRead.readACell(1, 2));
	}
	
	@AfterSuite(enabled = true)
	public void tearDown() {
		RequestContext.request.dispose();
		RequestContext.playwright.close();
		GenerateExtentReports.generateExtentReport().flush();
	}
}