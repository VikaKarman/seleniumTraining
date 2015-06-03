package com.training.JenkinsTest;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignUpTests {
	private WebDriver driver;
	private SignUpPage signUpPage;
	private List<String> users;

	@BeforeClass
	public void classSetup() {
		driver = new FirefoxDriver();
		signUpPage = new SignUpPage(driver).get();
		users = new ArrayList<String>();
	}

	@BeforeMethod
	public void testSetup() {
		signUpPage.get();
	}

	@Test
	public void createUserTest() {
		String name = "testuser_" + CommonMethods.getUniqueId();
		MainPage mainPage = new MainPage(driver, false);
		mainPage.get();
		Assert.assertEquals(
				mainPage.goToSignUp()
						.signUp(name, "1", "1", "", name + "@gmail.com")
						.getLoggedInUserName(), name);
		users.add(name);
	}

	@Test
	public void signupWithBlankEmailTest() {
		Assert.assertEquals(signUpPage.failedSignUp("", "", "", "", "")
				.getErrorText(), "Invalid e-mail address");
	}

	@Test
	public void signupWithNotValidEmailTest() {
		Assert.assertEquals(signUpPage.failedSignUp("", "", "", "", "test")
				.getErrorText(), "Invalid e-mail address");
	}

	@Test
	public void signupWithBlankUserNameTest() {
		Assert.assertEquals(
				signUpPage.failedSignUp("", "", "", "", "test@gmail.com")
						.getErrorText(), "User name is required");

	}

	@Test
	public void signupWithBlankPasswordTest() {
		String name = "testuser_" + CommonMethods.getUniqueId();
		Assert.assertEquals(
				signUpPage.failedSignUp(name, "", "", "", name + "@gmail.com")
						.getErrorText(), "Password is required");

	}

	@Test
	public void signupWithBlankConfirmationPasswordTest() {
		String name = "testuser_" + CommonMethods.getUniqueId();
		Assert.assertEquals(
				signUpPage.failedSignUp(name, "1", "", "", name + "@gmail.com")
						.getErrorText(), "Password didnt match");
	}

	@Test
	public void signupWithDifferentConfirmationPasswordTest() {
		String name = "testuser_" + CommonMethods.getUniqueId();
		Assert.assertEquals(
				signUpPage
						.failedSignUp(name, "1", "2", "", name + "@gmail.com")
						.getErrorText(), "Password didnt match");
	}

	@AfterClass
	public void classEnd() {
		for (String user : users) {
			signUpPage.searchForUser(user, user).deleteUser();
		}
		driver.quit();
	}

}
