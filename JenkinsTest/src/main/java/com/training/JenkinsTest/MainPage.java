package com.training.JenkinsTest;

import static com.training.JenkinsTest.Constants.MAIN_URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage<MainPage> {

	public MainPage(WebDriver wd) {
		super(wd, false);
	}
	
	protected MainPage(WebDriver driver, boolean verifyPageIsLoaded) {
		super(driver, verifyPageIsLoaded);
	}

	private final String loginLinkXpath = "//a[contains(@href,'/login')]";


	@FindBy(xpath = loginLinkXpath)
	private WebElement loginLink;
	@FindBy(xpath = "//a[@href ='/signup']")
	private WebElement signupLink;

	public SignUpPage goToSignUp() {
		logout();
		signupLink.click();
		return new SignUpPage(driver, true);
	}

	public LoginPage goToLogin() {
		logout();
		loginLink.click();
		return new LoginPage(driver, true);
	}

	@Override
	public String getPageUrl() {
		return MAIN_URL;
	}

}
