package org.testing.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class GenerateExtentReports {

	public static ExtentReports generateExtentReport() {
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
				"../SCIM2_API/test-output/SCIM2_API_Report.html");

		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter);

		return extentReports;
	}

}