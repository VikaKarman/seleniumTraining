package com.training.JenkinsTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteUserTests {
	private WebDriver driver;
	private LoginPage loginPage;

	@BeforeClass
	public void classSetup() {
		driver = new FirefoxDriver();
		loginPage = new LoginPage(driver, true);
		loginPage.get().login("1", "1");
	}

	@Test
	public void deleteUserTest() {
		String userName = "testuser #777";
		loginPage.searchForUser("#", userName).deleteUser();

		Assert.assertEquals(loginPage.searchForNonexistentUser(userName)
				.getErrorText(), "Nothing seems to match");
	}
}
