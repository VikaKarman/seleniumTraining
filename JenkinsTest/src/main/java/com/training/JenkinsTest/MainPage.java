package com.training.JenkinsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MainPage extends LoadableComponent<MainPage> {
	private WebDriver driver;

	public MainPage(WebDriver driver, boolean verifyPageIsLoaded) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		if (verifyPageIsLoaded)
			isLoaded();
	}

	private By loginLinkBy = By.xpath("//a[contains(@href,'/login')]");

	@FindBy(xpath = "//a[contains(@href,'/user/')]")
	WebElement userNameLink;
	@FindBy(xpath = "//a[contains(@href,'/login')]")
	WebElement loginLink;
	@FindBy(xpath = "//a[@href ='/logout']")
	WebElement logoutLink;
	@FindBy(xpath = "//a[@href ='/signup']")
	WebElement signupLink;

	@Override
	protected void load() {
		driver.get("http://seltr-kbp1-1.synapse.com:8080");
	}

	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
		Assert.assertEquals(url, "http://seltr-kbp1-1.synapse.com:8080/",
				"Not on the main page: " + url);
	}

	public String getLoggedInUserName() {
		return userNameLink.getText();
	}

	public void logout() {
		try {
			logoutLink.click();
			new WebDriverWait(driver, 5).until(ExpectedConditions
					.presenceOfElementLocated(loginLinkBy));
		} catch (NotFoundException e) {

		}

	}

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

}
