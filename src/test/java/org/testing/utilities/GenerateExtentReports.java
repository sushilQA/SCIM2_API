package org.testing.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class GenerateExtentReports {

	private static ExtentReports extentReports;

	public static ExtentReports generateExtentReport() {
		if (extentReports == null) {
			try {
				ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
						"../SCIM2_API/test-output/SCIM2_API_Report.html");
				extentReports = new ExtentReports();
				extentReports.attachReporter(sparkReporter);

			} catch (Exception e) {
				System.out.println("Failed to initialize ExtentReports: " + e.getMessage());
				throw new RuntimeException("Failed to initialize ExtentReports", e);
			}
		}
		return extentReports;
	}

}