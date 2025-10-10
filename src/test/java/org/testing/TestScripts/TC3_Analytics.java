package org.testing.TestScripts;

import java.io.IOException;
import java.util.Properties;
import org.testing.TestSteps.Analytics;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.LoadPropertiesFile;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import jxl.read.biff.BiffException;

public class TC3_Analytics {

	@Test
	public void analyticsModule() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = Analytics Controler");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		Analytics analytics = new Analytics();
		analytics.analyticsModule(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}

}
