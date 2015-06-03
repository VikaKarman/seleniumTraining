package com.training.JenkinsTest;

import static com.training.JenkinsTest.Constants.LOGIN_URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage extends BasePage<LoginPage> {

	public LoginPage(WebDriver driver) {
		super(driver, false);
	}

	protected LoginPage(WebDriver driver, boolean verifyPageIsLoaded) {
		super(driver, verifyPageIsLoaded);
	}

	private WebElement j_username;
	private WebElement j_password;
	private WebElement login;

	@Override
	protected void isLoaded() throws Error {

		String url = driver.getCurrentUrl();
		String loginErrorPageUrl = new LoginErrorPage(driver).getPageUrl();
		Assert.assertTrue(
				url.equals(getPageUrl())
						|| (!url.equals(loginErrorPageUrl) && url
								.startsWith(getPageUrl())), "Expected page: "
						+ getPageUrl() + ". Current page: " + url);
	}

	private void submit() {
		login.submit();
	}

	private void populateFieldsAndSubmit(String userName, String pwd) {
		clearAndType(j_username, userName);
		clearAndType(j_password, pwd);
		submit();

	}

	public MainPage login(String userName, String pwd) {
		log.info(">> Try to login with valid credentials");
		populateFieldsAndSubmit(userName, pwd);
		return new MainPage(driver, true);
	}

	public LoginErrorPage loginError(String userName, String pwd) {
		log.info(">> Try to login with invalid credentials");
		populateFieldsAndSubmit(userName, pwd);
		return new LoginErrorPage(driver, true);
	}

	@Override
	public String getPageUrl() {
		return LOGIN_URL;
	}

}
