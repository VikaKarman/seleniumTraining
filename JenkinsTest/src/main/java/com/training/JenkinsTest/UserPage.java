package com.training.JenkinsTest;

import static com.training.JenkinsTest.Constants.USER_URL;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class UserPage extends BasePage<UserPage> {

	private String userName;

	public UserPage(WebDriver driver, String userName) {
		super(driver, false);
		this.userName = userName;
	}

	public UserPage(WebDriver driver, boolean verifyPageIsLoaded,
			String userName) {
		super(driver, verifyPageIsLoaded);
		this.userName = userName;
		if (verifyPageIsLoaded)
			additionalVerification();
	}

	private WebElement delete;

	@FindBy(xpath = "//a[contains(@href, '/delete')][@class='task-link']")
	private WebElement deleteLink;

	public MainPage deleteUser() {
		deleteLink.click();
		delete.submit();
		return new MainPage(driver, true);
	}

	protected void additionalVerification() throws Error {
		try {
			new WebDriverWait(driver, 2).until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//h1[contains(text(),'"
							+ userName + "')]")));
		} catch (TimeoutException e) {
			Assert.assertTrue(false, "User Page was not opened for user "
					+ userName);
		}
	}

	@Override
	public String getPageUrl() {
		return USER_URL;
	}

}
