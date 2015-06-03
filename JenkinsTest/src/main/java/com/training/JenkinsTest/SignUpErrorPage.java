package com.training.JenkinsTest;

import static com.training.JenkinsTest.Constants.SIGNUP_ERROR_URL;

import org.openqa.selenium.WebDriver;

public class SignUpErrorPage extends BasePage<SignUpErrorPage> {

	public SignUpErrorPage(WebDriver driver) {
		super(driver, false);
	}
	
	protected SignUpErrorPage(WebDriver driver, boolean verifyPageIsLoaded) {
		super(driver, verifyPageIsLoaded);
	}

	@Override
	public String getPageUrl() {
		return SIGNUP_ERROR_URL;
	}

}
