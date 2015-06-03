package com.training.JenkinsTest;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.log4testng.Logger;

public class BaseTest {
	protected WebDriver driver;
	protected Logger logger = Logger.getLogger(getClass());
	protected List<String> users;

	@BeforeClass
	public void baseClassSetup() {
		users = new ArrayList<String>();

	}

	@AfterClass
	public void classEnd() {
		if (users.size() == 0)
			return;
		MainPage mainPage = new LoginPage(driver).get().login("1", "1");
		for (String user : users) {
			mainPage.searchForUser(user, user).deleteUser();
		}
		driver.quit();
	}

}
