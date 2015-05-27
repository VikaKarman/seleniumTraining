package com.training.JenkinsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SignUpSuccessPage extends LoadableComponent<SignUpSuccessPage> {
	private WebDriver driver;
	

	public SignUpSuccessPage(WebDriver driver, boolean verifyPageIsLoaded) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		if (verifyPageIsLoaded)
			isLoaded();
	}
	
	@FindBy(xpath="//a[contains(@href,'/user/')]")
	private WebElement userNameLink;

	@Override
	protected void load() {
		driver.get("http://seltr-kbp1-1.synapse.com:8080/securityRealm/createAccount");

	}

	@Override
	protected void isLoaded() throws Error {
		try {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath("//div[@id='main-panel-content']//h1[text() = 'Success']")));
			System.out.println(">>>> User created");
		} catch (TimeoutException e) {
			Assert.fail("New user was not created");
		}
	}
	
	public String getLoggedInUserName() {
		return userNameLink.getText();
	}


}
