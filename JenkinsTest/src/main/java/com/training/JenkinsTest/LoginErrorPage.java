package com.training.JenkinsTest;

import static com.training.JenkinsTest.Constants.LOGIN_ERROR_URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginErrorPage extends BasePage<LoginErrorPage> {

	public LoginErrorPage(WebDriver driver) {
		super(driver, false);
	}
	
	protected LoginErrorPage(WebDriver driver, boolean verifyPageIsLoaded) {
		super(driver, verifyPageIsLoaded);
	}

	@FindBy(xpath = "//div[@id='main-panel-content']//div[contains(@style,'color:red')]")
	private WebElement error;

	public String getErrorText() {
		return error.getText();
	}

	@Override
	public String getPageUrl() {
		return LOGIN_ERROR_URL;
	}

}
