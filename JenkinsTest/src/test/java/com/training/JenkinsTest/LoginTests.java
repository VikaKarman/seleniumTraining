package com.training.JenkinsTest;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests {
	private WebDriver driver;
	private LoginPage loginPage;
	private SignUpPage signUpPage;
	private String user1 = "testuser_" + CommonMethods.getUniqueId();
	private String userfn1;
	private String user2 = "testuser_" + CommonMethods.getUniqueId();
	private String expectedErrorText = "Invalid login information. Please try again.\nTry again";
	private List<String> users;

	@BeforeClass
	public void classSetup() {
		driver = new FirefoxDriver();
		loginPage = new LoginPage(driver, false);
		signUpPage = new SignUpPage(driver, false);
		users = new ArrayList<String>();
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

		MainPage mainPage = new MainPage(driver, false).get();

		Assert.assertEquals(
				mainPage.goToLogin().login(user1, "1")
						.getLoggedInUserName(), userfn1);

	}

	@Test
	public void loginWithUserWithEmptyFullNameTest() {

		Assert.assertEquals(loginPage.login(user2, "1").getLoggedInUserName(),
				user2);

	}

	@Test
	public void loginWithEmptyIDAndPasswordTest() {
		Assert.assertEquals(loginPage.loginError("", "").getErrorText(),
				expectedErrorText);

	}

	@Test
	public void loginWithBlankPasswordTest(){
		Assert.assertEquals(loginPage.loginError(user1, "").getErrorText(),
				expectedErrorText);

	}

	@Test
	public void loginWithNonexistentUserTest() {
		Assert.assertEquals(
				loginPage.loginError("testuser_" + CommonMethods.getUniqueId(),
						"").getErrorText(), expectedErrorText);
	}

	@AfterClass
	public void classEnd(){
		loginPage.get().login("1", "1");
		for (String user: users){
			loginPage.searchForUser(user, user).deleteUser();
		}
		driver.quit();
	}

}
