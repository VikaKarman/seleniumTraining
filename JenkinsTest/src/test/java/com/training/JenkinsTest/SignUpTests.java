package com.training.JenkinsTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.JenkinsTest.utils.WebDriverManager;

public class SignUpTests extends BaseTest{
	private SignUpPage signUpPage;

	@BeforeClass
	public void classSetup() {
		System.setProperty("lang", "ua");
		System.setProperty("browser", "firefox");
		driver = WebDriverManager.getDriver();
		signUpPage = new SignUpPage(driver);
	}

	@BeforeMethod
	public void testSetup() {
		signUpPage.get();
	}

	@Test
	public void createUserTest() {
		logger.info("### Verify it is possible to create new user");
		String name = "testuser_" + CommonMethods.getUniqueId();
		MainPage mainPage = new MainPage(driver).get();
		String loggedUser = mainPage.goToSignUp()
				.signUp(name, "1", "1", "", name + "@gmail.com")
				.getLoggedInUserName();
		new UserPage(driver, name).verifyUserExists();
		users.add(name);
		Assert.assertEquals(loggedUser, name,
				"Newly created user is automatically logged in");
	}

	@Test
	public void signupWithBlankEmailTest() {
		logger.info("### Verify it is not possible to create user with all blank fields");
		Assert.assertEquals(signUpPage.failedSignUp("", "", "", "", "")
				.getErrorText(), "Invalid e-mail address");
	}

	@Test
	public void signupWithNotValidEmailTest() {
		logger.info("### Verify it is not possible to create user with not valid email address");
		Assert.assertEquals(signUpPage.failedSignUp("", "", "", "", "test")
				.getErrorText(), "Invalid e-mail address");
	}

	@Test
	public void signupWithBlankUserNameTest() {
		logger.info("### Verify it is not possible to create user with blank user name");
		Assert.assertEquals(
				signUpPage.failedSignUp("", "", "", "", "test@gmail.com")
						.getErrorText(), "User name is required");

	}

	@Test
	public void signupWithBlankPasswordTest() {
		logger.info("### Verify it is not possible to create user with blank password");
		String name = "testuser_" + CommonMethods.getUniqueId();
		Assert.assertEquals(
				signUpPage.failedSignUp(name, "", "", "", name + "@gmail.com")
						.getErrorText(), "Password is required");

	}

	@Test
	public void signupWithBlankConfirmationPasswordTest() {
		logger.info("### Verify it is not possible to create user with blank confirmation password");
		String name = "testuser_" + CommonMethods.getUniqueId();
		Assert.assertEquals(
				signUpPage.failedSignUp(name, "1", "", "", name + "@gmail.com")
						.getErrorText(), "Password didnt match");
	}

	@Test
	public void signupWithDifferentConfirmationPasswordTest() {
		logger.info("### Verify it is not possible to create user with not confirmed password");
		String name = "testuser_" + CommonMethods.getUniqueId();
		Assert.assertEquals(
				signUpPage
						.failedSignUp(name, "1", "2", "", name + "@gmail.com")
						.getErrorText(), "Password didnt match");
	}


}
