package com.training.JenkinsTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

public class LoginPage extends LoadableComponent<LoginPage> {
	private WebDriver driver;

	private WebElement j_username;
	private WebElement j_password;
	private WebElement login;
	
	public LoginPage(WebDriver driver, boolean verifyPageIsLoaded) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		if (verifyPageIsLoaded)
			isLoaded();
	}

	@Override
	protected void load() {
		driver.get("http://seltr-kbp1-1.synapse.com:8080/login?from=%2F");

	}

	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.endsWith("/login?from=%2F"),
				"Not on the login page: " + url);
	}

	private void clearAndType(WebElement field, String text) {
		field.clear();
		field.sendKeys(text);
	}
	
	private void submit(){
		login.submit();
	}
	
	public MainPage login(String userName, String pwd){
		clearAndType(j_username, userName);
		clearAndType(j_password, pwd);
		submit();
		return new MainPage(driver, true);
	}
	
	public LoginErrorPage loginError(String userName, String pwd){
		clearAndType(j_username, userName);
		clearAndType(j_password, pwd);
		submit();
		return new LoginErrorPage(driver, true);
	}


}
