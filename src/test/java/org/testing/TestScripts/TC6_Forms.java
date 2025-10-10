package org.testing.TestScripts;

import java.io.IOException;
import java.util.Properties;
import org.testing.TestSteps.Analytics;
import org.testing.TestSteps.Counsellors;
import org.testing.TestSteps.Forms;
import org.testing.TestSteps.Leads;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.LoadPropertiesFile;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import jxl.read.biff.BiffException;

public class TC6_Forms {

	@Test
	public void msForms() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = Forms");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		Forms forms = new Forms();
		forms.getCollegeConfigOptions(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}

}
