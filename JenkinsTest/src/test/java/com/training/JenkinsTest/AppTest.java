package com.training.JenkinsTest;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AppTest {
	private WebDriver driver;
	private MainPage mainPage;
	private SignUpPage signUpPage;
	private LoginPage loginPage;
	private SignUpSuccessPage signUpSuccessPage;
	private LoginErrorPage loginErrorPage;
	private SignUpPage failedSignUp;

	@BeforeClass
	public void classSetup() {
		driver = new FirefoxDriver();
		mainPage = new MainPage(driver, false);
		signUpPage = new SignUpPage(driver, false);
		loginPage = new LoginPage(driver, false);
		mainPage.get();
	}

	public String getUniqueId() {
		Random rand = new Random();
		return String.valueOf(rand.nextInt(1000));
	}

	@Test
	public void signup_test1() {
		String name = "testuser_" + getUniqueId();
		signUpPage = mainPage.goToSignUp();
		signUpSuccessPage = signUpPage.signUp(name, "1", "1", "", name
				+ "@gmail.com");
		Assert.assertEquals(signUpSuccessPage.getLoggedInUserName(), name);
	}

	@Test
	public void signup_test2() {
		signUpPage.get();
		failedSignUp = signUpPage.failedSignUp("", "", "", "", "");
		Assert.assertEquals(failedSignUp.getErrorMessageText(),
				"Invalid e-mail address");
	}

	@Test
	public void signup_test3() {
		signUpPage.get();
		failedSignUp = signUpPage.failedSignUp("", "", "", "", "test");
		Assert.assertEquals(failedSignUp.getErrorMessageText(),
				"Invalid e-mail address");
	}

	@Test
	public void signup_test4() {
		signUpPage.get();
		failedSignUp = signUpPage
				.failedSignUp("", "", "", "", "test@gmail.com");
		Assert.assertEquals(failedSignUp.getErrorMessageText(),
				"User name is required");

	}

	@Test
	public void signup_test5() {
		signUpPage.get();
		String name = "testuser_" + getUniqueId();
		failedSignUp = signUpPage.failedSignUp(name, "", "", "", name
				+ "@gmail.com");
		Assert.assertEquals(failedSignUp.getErrorMessageText(),
				"Password is required");

	}

	@Test
	public void signup_test6() {
		signUpPage.get();
		String name = "testuser_" + getUniqueId();
		failedSignUp = signUpPage.failedSignUp(name, "1", "", "", name
				+ "@gmail.com");
		Assert.assertEquals(failedSignUp.getErrorMessageText(),
				"Password didnt match");
	}

	@Test
	public void signup_test7() {
		signUpPage.get();
		String name = "testuser_" + getUniqueId();
		failedSignUp = signUpPage.failedSignUp(name, "1", "2", "", name
				+ "@gmail.com");
		Assert.assertEquals(failedSignUp.getErrorMessageText(),
				"Password didnt match");
	}

	@Test
	public void login_test1() {

		String name = "testuser_" + getUniqueId();
		String fullName = "testuser #" + getUniqueId();
		signUpPage.get();

		signUpSuccessPage = signUpPage.signUp(name, "1", "1", fullName, name
				+ "@gmail.com");
		loginPage = mainPage.goToLogin();
		mainPage = loginPage.login(name, "1");
		String loggedUserName = mainPage.getLoggedInUserName();

		Assert.assertEquals(loggedUserName, fullName);

	}

	@Test
	public void login_test2() {

		String name = "testuser_" + getUniqueId();
		signUpPage.get();
		signUpSuccessPage = signUpPage.signUp(name, "1", "1", "", name
				+ "@gmail.com");
		loginPage = mainPage.goToLogin();
		mainPage = loginPage.login(name, "1");
		String loggedUserName = mainPage.getLoggedInUserName();

		Assert.assertEquals(loggedUserName, name);

	}

	@Test
	public void login_test3() {
		loginPage.get();
		loginErrorPage = loginPage.loginError("", "");
		Assert.assertTrue(loginErrorPage.isErrorTextDisplayed());

	}

	@Test
	public void login_test4() {
		loginPage.get();
		String name = "testuser_" + getUniqueId();
		loginErrorPage = loginPage.loginError(name, "");
		Assert.assertTrue(loginErrorPage.isErrorTextDisplayed());

	}

	@Test
	public void login_test5() {
		signUpPage.get();
		String name = "testuser_" + getUniqueId();
		signUpPage.signUp(name, "1", "1", "", name + "@gmail.com");
		loginPage.get();
		loginErrorPage = loginPage.loginError(name, "");
		Assert.assertTrue(loginErrorPage.isErrorTextDisplayed());

	}

	@AfterClass
	public void classEnd() {
		driver.quit();
	}

}
