package com.training.JenkinsTest;

import static com.training.JenkinsTest.Constants.SIGNUP_URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends BasePage<SignUpPage> {

	public SignUpPage(WebDriver driver) {
		super(driver, false);
	}

	protected SignUpPage(WebDriver driver, boolean verifyPageIsLoaded) {
		super(driver, verifyPageIsLoaded);
	}
	
	private WebElement username;
	private WebElement password1;
	private WebElement password2;
	private WebElement fullname;
	private WebElement email;

	@FindBy(xpath = "//form[@action='/securityRealm/createAccount']")
	private WebElement signUpForm;

	private void submit() {
		signUpForm.submit();
	}
	
	private void populateFieldsAndSubmit(String userName, String pwd1,
			String pwd2, String fullName, String emailAddr) {
		clearAndType(username, userName);
		clearAndType(password1, pwd1);
		clearAndType(password2, pwd2);
		clearAndType(fullname, fullName);
		clearAndType(email, emailAddr);
		submit();
	}

	public SignUpSuccessPage signUp(String userName, String pwd1, String pwd2,
			String fullName, String emailAddr) {
		log.info(">> Try to create user with valid credentials");
		populateFieldsAndSubmit(userName, pwd1, pwd2, fullName, emailAddr);
		return new SignUpSuccessPage(driver, true);
	}

	public SignUpErrorPage failedSignUp(String userName, String pwd1,
			String pwd2, String fullName, String emailAddr) {
		log.info(">> Try to create user with invalid credentials");
		populateFieldsAndSubmit(userName, pwd1, pwd2, fullName, emailAddr);
		return new SignUpErrorPage(driver, true);
	}

	@Override
	public String getPageUrl() {
		return SIGNUP_URL;
	}

}
