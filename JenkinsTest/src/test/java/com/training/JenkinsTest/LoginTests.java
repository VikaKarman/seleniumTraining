package com.training.JenkinsTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.JenkinsTest.utils.WebDriverManager;

public class LoginTests extends BaseTest {
	private LoginPage loginPage;
	private SignUpPage signUpPage;
	private String user1 = "testuser_" + CommonMethods.getUniqueId();
	private String userfn1;
	private String user2 = "testuser_" + CommonMethods.getUniqueId();
	private String expectedErrorText = "Invalid login information. Please try again.\nTry again";

	@BeforeClass
	public void classSetup() {
		System.setProperty("lang", "fr");
		driver = WebDriverManager.getDriver();
		loginPage = new LoginPage(driver, false);
		signUpPage = new SignUpPage(driver, false);
		signUpPage.get();
		userfn1 = user1.replace("_", " #");
		signUpPage.signUp(user1, "1", "1", userfn1, user1 + "@gmail.com");
		users.add(userfn1);
		signUpPage.get();
		signUpPage.signUp(user2, "1", "1", "", user2 + "@gmail.com");
		users.add(user2);
	}

	@BeforeMethod
	public void testSetup() {
		loginPage.get();
	}

	@Test
	public void loginWithUserWithFullNameTest() {
		logger.info("### Login using Login link. User has full name");
		MainPage mainPage = new MainPage(driver, false).get();

		Assert.assertEquals(mainPage.goToLogin().login(user1, "1")
				.getLoggedInUserName(), userfn1);

	}

	@Test
	public void loginWithUserWithEmptyFullNameTest() {
		logger.info("### Login with user with blank full name");
		Assert.assertEquals(loginPage.login(user2, "1").getLoggedInUserName(),
				user2);

	}

	@Test
	public void loginWithEmptyIDAndPasswordTest() {
		logger.info("### Login with blank credentials");
		Assert.assertEquals(loginPage.loginError("", "").getErrorText(),
				expectedErrorText);

	}

	@Test
	public void loginWithBlankPasswordTest() {
		logger.info("### Login with user with blank password");
		Assert.assertEquals(loginPage.loginError(user1, "").getErrorText(),
				expectedErrorText);

	}

	@Test
	public void loginWithNonexistentUserTest() {
		logger.info("### Login with nonexistent user");
		Assert.assertEquals(
				loginPage.loginError("testuser_" + CommonMethods.getUniqueId(),
						"").getErrorText(), expectedErrorText);
	}

}
