package com.training.JenkinsTest;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {

	protected WebDriver driver;

	@FindBy(xpath = "//a[@href ='/logout']")
	private WebElement logoutLink;
	@FindBy(xpath = "//a[contains(@href,'/user/')]")
	private WebElement userNameLink;

	private String popupMenuItemXpath = "//div[@id='search-box-completion']//ul//li[contains(text(),'{0}')]";

	private WebElement q;
	
	@FindBy(css = ".error")
	private WebElement error;

	public BasePage(WebDriver wd) {
		this(wd, false);
	}
	
	protected BasePage(WebDriver driver, boolean verifyPageIsLoaded) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		if (verifyPageIsLoaded)
			isLoaded();

	}

	public abstract String getPageUrl();
	
	@Override
	protected void load() {
		driver.get(getPageUrl());
	}

	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.startsWith(getPageUrl()), "Expected page: "
				+ getPageUrl() + ". Current page: " + url);
	}

	public String getErrorText() {
		return error.getText();
	}

	protected void clearAndType(WebElement field, String text) {
		field.clear();
		field.sendKeys(text);
	}

	public MainPage logout() {
		try {
			logoutLink.click();
		} catch (NotFoundException e) {
			// does nothing
		}

		return new MainPage(driver, true);

	}

	public String getLoggedInUserName() {
		return userNameLink.getText();
	}

	public interface SearchRequest {
		
		public String search(List<String> results);
		
	}
	
//	public UserPage searchForUser(String criteria, SearchRequest request) {
//		
//	}
	
	public UserPage searchForUser(String searchCriteria, String userName) {
		q.clear();
		q.sendKeys(searchCriteria);
		WebElement popupMenuItem;
		try {
			popupMenuItem = new WebDriverWait(driver, 2)
					.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath(popupMenuItemXpath.replace("{0}", userName))));
		} catch (TimeoutException e) {
			throw new RuntimeException("User with name '" + userName
					+ "' was not found");
		}
		Actions act = new Actions(driver);
		act.moveToElement(popupMenuItem).click(popupMenuItem)
				.sendKeys(Keys.ENTER).build().perform();
		return new UserPage(driver, true, userName);

	}

	public EmptySearchResults searchForNonexistentUser(String userName) {
		q.clear();
		Actions act = new Actions(driver);
		act.sendKeys(q, userName).sendKeys(Keys.ENTER).build().perform();
		return new EmptySearchResults(driver, true);

	}
}
