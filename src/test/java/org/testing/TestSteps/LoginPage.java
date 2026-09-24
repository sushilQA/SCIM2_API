package org.testing.TestSteps;

import org.testing.utilities.ConfigContext;

import com.microsoft.playwright.Page;

public class LoginPage {

	public void login(Page page, String url, String username, String password) {

		System.out.println("\n ******************** UI Login ********************\n");

		page.navigate(url);
		page.locator(ConfigContext.objectRepoProperties.getProperty("UserName")).fill(username);
		page.locator(ConfigContext.objectRepoProperties.getProperty("Password")).fill(password);
		page.locator(ConfigContext.objectRepoProperties.getProperty("LogIn")).click();
		// Wait for dashboard/home page to load after login
		page.waitForLoadState();
		System.out.println("UI Login completed, current URL: " + page.url());
	}

}