package com.training.JenkinsTest;

import static com.training.JenkinsTest.Constants.EMPTY_SEARCH_RES_URL;

import org.openqa.selenium.WebDriver;

public class EmptySearchResults extends BasePage<EmptySearchResults> {

	public EmptySearchResults(WebDriver driver, String searchCriteria) {
		super(driver, false);
	}

	public EmptySearchResults(WebDriver driver, boolean verifyPageIsLoaded) {
		super(driver, verifyPageIsLoaded);
	}

	@Override
	public String getPageUrl() {
		return EMPTY_SEARCH_RES_URL;
	}

}
