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

public class SignUpPage extends LoadableComponent<SignUpPage> {
	private WebDriver driver;

	WebElement username;
	WebElement password1;
	WebElement password2;
	WebElement fullname;
	WebElement email;
	
	public SignUpPage(WebDriver driver, boolean verifyPageIsLoaded){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		if (verifyPageIsLoaded)
			isLoaded();
	}

	@FindBy(xpath = "//form[@action='/securityRealm/createAccount']")
	WebElement signUpForm;

	@Override
	protected void load() {
		driver.get("http://seltr-kbp1-1.synapse.com:8080/signup");
	}

	@Override
	protected void isLoaded() throws Error {
		try {
			new WebDriverWait(driver, 5).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath("//button[contains(text(), 'Sign up')]")));
		} catch (TimeoutException e) {
			Assert.fail("Sign up page is not opened");
		}
	}

	private void clearAndType(WebElement field, String text) {
		field.clear();
		field.sendKeys(text);
	}

	private void submit() {
		signUpForm.submit();
	}

	public SignUpSuccessPage signUp(String userName, String pwd1, String pwd2,
			String fullName, String emailAddr) {
		clearAndType(username, userName);
		clearAndType(password1, pwd1);
		clearAndType(password2, pwd2);
		clearAndType(fullname, fullName);
		clearAndType(email, emailAddr);
		submit();
		return new SignUpSuccessPage(driver, true);
	}

	public SignUpPage failedSignUp(String userName, String pwd1, String pwd2,
			String fullName, String emailAddr) {
		clearAndType(username, userName);
		clearAndType(password1, pwd1);
		clearAndType(password2, pwd2);
		clearAndType(fullname, fullName);
		clearAndType(email, emailAddr);
		submit();
		return new SignUpPage(driver, true);
	}

	public String getErrorMessageText() {
		try {
			new WebDriverWait(driver, 5).until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//*[@class='error']")));
		} catch (TimeoutException e) {
			return null;
		}

		return driver.findElement(By.xpath("//*[@class='error']")).getText();
	}

}
