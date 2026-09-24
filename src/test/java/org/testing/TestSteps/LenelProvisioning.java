package org.testing.TestSteps;

import com.microsoft.playwright.Page;

import org.testing.utilities.ConfigContext;

import com.microsoft.playwright.Locator;

public class LenelProvisioning {

	// TODO: Replace with actual navigation steps to reach the Lenel settings page
	// (e.g. clicking through a menu, or navigating directly to a settings URL)
	public void navigateToLenelSettings(Page page) {
		System.out.println("\n ******************** Navigate to Lenel Settings ********************\n");

		// Example - adjust to your actual navigation flow:
		page.locator("//a[text()='Settings']").click();
		page.locator("//a[text()='Lenel Integration']").click();
		page.waitForLoadState();
	}

	public void toggleProvisioningCheckbox(Page page, boolean enable) {
		System.out.println("Setting provisioning checkbox to: " + enable);
		Locator checkbox = page.locator(ConfigContext.objectRepoProperties.getProperty("ProvisioningCheckBox"));
		boolean isCurrentlyChecked = checkbox.isChecked();
		if (isCurrentlyChecked != enable) {
			checkbox.click();
		}
	}

	public void enterLenelCredentials(Page page) {
		System.out.println("Entering some string in Lenel UTL :"+ "test");
		page.locator(ConfigContext.objectRepoProperties.getProperty("SystemParameter")).click();
		page.locator(ConfigContext.objectRepoProperties.getProperty("LenelURLInput")).fill("httpxs://test-loaap.humana.com");
	}

	public void clickTestConnection(Page page) {
		System.out.println("Clicking Test Connection button");

		// TODO: Replace with actual button locator
		page.locator("//button[@id='testConnectionBtn']").click();

		// Wait for the result message to appear
		page.waitForTimeout(2000);
	}

	public String getConnectionResultMessage(Page page) {

		// TODO: Replace with actual locator for the success/failure message element
		Locator resultMessage = page.locator("//div[@class='connection-result-message']");

		String message = resultMessage.textContent();
		System.out.println("Connection result message: " + message);
		return message;
	}

}