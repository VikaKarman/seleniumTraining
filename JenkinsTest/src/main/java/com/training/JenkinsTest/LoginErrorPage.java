package com.training.JenkinsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginErrorPage extends LoadableComponent<LoginErrorPage> {
	private WebDriver driver;
	
	public LoginErrorPage(WebDriver driver, boolean verifyPageIsLoaded){
		this.driver = driver;
		if (verifyPageIsLoaded)
			isLoaded();
	}
	
	@Override
	protected void load() {
		driver.get("http://seltr-kbp1-1.synapse.com:8080/loginError");
	}

	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.endsWith("/loginError"),
				"Not on the login error page: " + url);
	}
	
	public boolean isErrorTextDisplayed(){
		try {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath("//*[contains(text(),'Invalid login information. Please try again.')]")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

}
