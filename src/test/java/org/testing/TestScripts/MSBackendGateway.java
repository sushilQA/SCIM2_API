package org.testing.TestScripts;

import java.io.IOException;
import java.util.Properties;
import org.testing.TestSteps.Analytics;
import org.testing.TestSteps.Counsellors;
import org.testing.TestSteps.Forms;
import org.testing.TestSteps.Leads;
import org.testing.TestSteps.UserModule;
import org.testing.TestSteps.User_Permissions;
import org.testing.utilities.GenerateExtentReports;
import org.testing.utilities.LoadPropertiesFile;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import jxl.read.biff.BiffException;

public class MSBackendGateway {
	
	@Test(enabled=true , priority=1)
	public void users() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = User Module");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		UserModule userModule = new UserModule(properties);
		userModule.usersModule(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}
	
	@Test(enabled=false , priority = 2)
	public void fieldForce() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = Field Force");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		UserModule userModule = new UserModule(properties);
		userModule.fieldForce(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}
	
	@Test(enabled=false , priority = 3)
	public void usersPermissions() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = User Permissions");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		User_Permissions userPermissions = new User_Permissions();
		userPermissions.allBackendUserPermissions(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}
	
	@Test(enabled=false , priority = 4)
	public void analytics() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = Analytics Controler");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		Analytics analytics = new Analytics();
		analytics.analyticsModule(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}
	
	@Test(enabled=false , priority = 5)
	public void counsellor() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = Counsellor Module");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		Counsellors counsellors = new Counsellors();
		counsellors.counsellorsModule(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}
	
	@Test(enabled=false , priority = 6)
	public void leads() throws IOException, BiffException {
		ExtentReports extentReports = GenerateExtentReports.generateExtentReport();
		ExtentTest extentTest = extentReports.startTest("Test Name = Leads");
		Properties properties = LoadPropertiesFile.handlePropertyFile("../Npf_Backend_Gateway/URI.properties");
		Leads leads = new Leads();
		leads.leadsModule(properties.getProperty("In1"),"Live");
		extentReports.endTest(extentTest);
		extentReports.flush();
	}
	
	@Test(enabled=false , priority = 7)
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
