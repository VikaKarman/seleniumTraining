package com.training.JenkinsTest;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class App {
	private WebDriver driver;
	private Date date;

	@BeforeClass
	public void classSetup() {
		driver = new FirefoxDriver();
		driver.get("http://seltr-kbp1-1.synapse.com:8080/");
	}

	@BeforeMethod
	public void testSetup() {
		try {
			driver.findElement(By.xpath("//a[@href='/logout']")).click();
			new WebDriverWait(driver, 5).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath("//a[contains(@href,'/login')]")));
		} catch (NotFoundException e) {
			// do nothing
		}
	}

	public void populateSignUpFormFields(String name, String pwd,
			String confirmPwd, String fullName, String email) {

		WebElement field;
		field = driver.findElement(By.name("username"));
		field.clear();
		field.sendKeys(name);
		field = driver.findElement(By.name("password1"));
		field.clear();
		field.sendKeys(pwd);
		field = driver.findElement(By.name("password2"));
		field.clear();
		field.sendKeys(confirmPwd);
		field = driver.findElement(By.name("fullname"));
		field.clear();
		field.sendKeys(fullName);
		field = driver.findElement(By.name("email"));
		field.clear();
		field.sendKeys(email);

	}

	public void submitButtonClick() {
		driver.findElement(By.xpath("//span[@name = 'Submit']//button"))
				.click();
	}

	public void populateLoginFormFields(String name, String pwd) {

		WebElement field;
		field = driver.findElement(By.name("j_username"));
		field.clear();
		field.sendKeys(name);
		field = driver.findElement(By.name("j_password"));
		field.clear();
		field.sendKeys(pwd);

	}

	public void goToSignUp() {
		driver.findElement(By.xpath("//a[@href='/signup']")).click();
		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By
						.xpath("//form[contains(@action,'createAccount')]")));
	}

	public void goToLoginPage() {
		driver.findElement(By.xpath("//a[contains(@href,'/login')]")).click();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//form[@name='login']")));
	}

	@Test
	public void signup_test1() {

		goToSignUp();
		date = new Date();
		String id = String.valueOf(date.getTime());
		String name = "testuser_" + id;
		String fullName = "testuser #" + id;

		populateSignUpFormFields(name, "1", "1", fullName, name + "@gmail.com");

		submitButtonClick();

		try {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath("//div[@id='main-panel-content']//h1[text() = 'Success']")));
			System.out.println("New user was created");
		} catch (TimeoutException e) {
			Assert.fail("New user was not created");
		}
		try {
			driver.findElement(By.xpath("//a[@href='/user/" + name + "']"));
			System.out
					.println("Newly creaded user was automatically logged in");
		} catch (NotFoundException e) {
			Assert.fail("Newly creaded user was not automatically logged in");
		}

	}

	@Test
	public void signup_test2() {
		goToSignUp();
		populateSignUpFormFields("", "", "", "", "");

		submitButtonClick();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@class='error']")));

		WebElement error = driver.findElement(By.xpath("//*[@class='error']"));

		Assert.assertEquals(error.getText(), "Invalid e-mail address");

		try {
			driver.findElement(By
					.xpath("//form[contains(@action,'createAccount')]"));
			System.out.println("Sign up form is still opened");
		} catch (NotFoundException e) {
			Assert.fail("Sign up form is not opened");
		}
	}

	@Test
	public void signup_test3() {
		goToSignUp();

		populateSignUpFormFields("", "", "", "", "test");

		submitButtonClick();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@class='error']")));

		WebElement error = driver.findElement(By.xpath("//*[@class='error']"));
		Assert.assertEquals(error.getText(), "Invalid e-mail address");

	}

	@Test
	public void signup_test4() {
		goToSignUp();
		populateSignUpFormFields("", "", "", "", "test@gmail.com");

		submitButtonClick();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@class='error']")));

		WebElement error = driver.findElement(By.xpath("//*[@class='error']"));

		Assert.assertEquals(error.getText(), "User name is required");

	}

	@Test
	public void signup_test5() {
		goToSignUp();
		date = new Date();
		String id = String.valueOf(date.getTime());
		String name = "testuser_" + id;
		populateSignUpFormFields(name, "", "", "", name + "@gmail.com");

		submitButtonClick();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@class='error']")));

		WebElement error = driver.findElement(By.xpath("//*[@class='error']"));

		Assert.assertEquals(error.getText(), "Password is required");

	}

	@Test
	public void signup_test6() {
		goToSignUp();
		date = new Date();
		String id = String.valueOf(date.getTime());
		String name = "testuser_" + id;

		populateSignUpFormFields(name, "1", "", "", name + "@gmail.com");

		submitButtonClick();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@class='error']")));

		WebElement error = driver.findElement(By.xpath("//*[@class='error']"));

		Assert.assertEquals(error.getText(), "Password didnt match");

	}

	@Test
	public void signup_test7() {
		goToSignUp();
		date = new Date();
		String id = String.valueOf(date.getTime());
		String name = "testuser_" + id;

		populateSignUpFormFields(name, "1", "2", "", name + "@gmail.com");

		submitButtonClick();
		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@class='error']")));

		WebElement error = driver.findElement(By.xpath("//*[@class='error']"));

		Assert.assertEquals(error.getText(), "Password didnt match");

	}

	@Test
	public void login_test1() {
		goToSignUp();
		date = new Date();
		String id = String.valueOf(date.getTime());
		String name = "testuser_" + id;
		String fullName = "testuser #" + id;

		populateSignUpFormFields(name, "1", "1", fullName, name + "@gmail.com");

		submitButtonClick();

		new WebDriverWait(driver, 5)
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//div[@id='main-panel-content']//h1[text() = 'Success']")));
		System.out.println("New user was created");

		driver.findElement(By.xpath("//a[@href='/logout']")).click();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By
						.xpath("//a[contains(@href,'/login')]")));

		goToLoginPage();

		populateLoginFormFields(name, "1");
		submitButtonClick();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//a[@href='/user/" + name
						+ "']")));
		WebElement userName = driver.findElement(By.xpath("//a[@href='/user/"
				+ name + "']"));
		System.out.println("Newly creaded user was logged in.");

		Assert.assertEquals(userName.getText(), fullName);

	}

	@Test
	public void login_test2() {
		goToSignUp();
		date = new Date();
		String id = String.valueOf(date.getTime());
		String name = "testuser_" + id;

		populateSignUpFormFields(name, "1", "1", "", name + "@gmail.com");

		submitButtonClick();

		new WebDriverWait(driver, 5)
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//div[@id='main-panel-content']//h1[text() = 'Success']")));
		System.out.println("New user was created");

		driver.findElement(By.xpath("//a[@href='/logout']")).click();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By
						.xpath("//a[contains(@href,'/login')]")));

		goToLoginPage();

		populateLoginFormFields(name, "1");
		submitButtonClick();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//a[@href='/user/" + name
						+ "']")));
		WebElement userName = driver.findElement(By.xpath("//a[@href='/user/"
				+ name + "']"));
		System.out.println("Newly creaded user was logged in.");

		Assert.assertEquals(userName.getText(), name);
	}

	@Test
	public void login_test3() {

		goToLoginPage();

		populateLoginFormFields("", "");
		submitButtonClick();

		try {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath("//*[contains(text(),'Invalid login information. Please try again.')]")));
			System.out
					.println("'Invalid login information' error didn't appeared");
		} catch (TimeoutException e) {
			Assert.fail("'Invalid login information' error didn't appear");
		}

	}

	@Test
	public void login_test4() {

		goToLoginPage();

		date = new Date();
		String id = String.valueOf(date.getTime());
		String name = "testuser_" + id;

		populateLoginFormFields(name, "1");
		submitButtonClick();

		try {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath("//*[contains(text(),'Invalid login information. Please try again.')]")));
			System.out
					.println("'Invalid login information' error didn't appeared");
		} catch (TimeoutException e) {
			Assert.fail("'Invalid login information' error didn't appear");
		}

	}

	@Test
	public void login_test5() {
		goToSignUp();
		date = new Date();
		String id = String.valueOf(date.getTime());
		String name = "testuser_" + id;

		populateSignUpFormFields(name, "1", "1", "", name + "@gmail.com");

		submitButtonClick();

		new WebDriverWait(driver, 5)
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//div[@id='main-panel-content']//h1[text() = 'Success']")));
		System.out.println("New user was created");

		driver.findElement(By.xpath("//a[@href='/logout']")).click();

		new WebDriverWait(driver, 5).until(ExpectedConditions
				.presenceOfElementLocated(By
						.xpath("//a[contains(@href,'/login')]")));

		goToLoginPage();
		populateLoginFormFields(name, "");
		submitButtonClick();

		try {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath("//*[contains(text(),'Invalid login information. Please try again.')]")));
			System.out
					.println("'Invalid login information' error didn't appeared");
		} catch (TimeoutException e) {
			Assert.fail("'Invalid login information' error didn't appear");
		}
	}

	@AfterClass
	public void classEnd() {
		driver.quit();
	}

}
