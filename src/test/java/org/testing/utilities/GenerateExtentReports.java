package org.testing.utilities;

import com.relevantcodes.extentreports.ExtentReports;

public class GenerateExtentReports {
	
		public static ExtentReports generateExtentReport()
		{
			ExtentReports extentReports = new ExtentReports("../SCIM2_API/test-output/SCIM2_API_Report.html", false);
			return extentReports;
		}

}
