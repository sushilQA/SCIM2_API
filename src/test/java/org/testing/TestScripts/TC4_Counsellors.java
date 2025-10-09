package org.testing.TestScripts;

import java.io.IOException;
import java.util.Properties;
import org.testing.TestSteps.Analytics;
import org.testing.TestSteps.Counsellors;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.LoadPropertiesFile;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import jxl.read.biff.BiffException;

public class TC4_Counsellors {

	@Test
	public void counsellorModule() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = Counsellor Module");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		Counsellors counsellors = new Counsellors();
		counsellors.counsellorsModule(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}

}
