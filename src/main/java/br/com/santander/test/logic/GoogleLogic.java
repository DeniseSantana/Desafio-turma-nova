package br.com.santander.test.logic;

import static br.com.santander.test.support.Context.rGen;
import static br.com.santander.test.support.Context.web;
import static org.testng.Assert.assertTrue;

import br.com.santander.core.webdriver.page.PageMaker;
import br.com.santander.test.pages.GooglePage;
import br.com.santander.test.sheet.model.GoogleData;
import br.com.santander.test.support.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoogleLogic {

	private GooglePage googlePage;
	private GoogleData googleData;

	public GoogleLogic() {
		googlePage = PageMaker.getInstance(GooglePage.class, web().getWebDriver());
		googleData = (GoogleData) Context.testData();
	}

	public void navigateToGoogle() {
		log.info("I navigate to google");
		web().navigateToUrl(GooglePage.HTTP_GOOGLE_COM);
		web().getWebDriver().manage().window().maximize();
	}

	public void searchForString() {
		String step = "I search for the word: " + googleData.getQueryText();
		log.info(step);		
		web().insertText(googlePage.getQuery(), googleData.getQueryText());
		rGen().registerStep(web().getScreenshot(), step);
		web().elementSubmit(googlePage.getQuery());
	}

	public void validateResultElementToAppear() {
		String step = "I validate the outcome";
		log.info(step);		
		web().elementIsVisible(googlePage.getResult(), 5);
		rGen().registerStep(web().getScreenshot(), step);
		assertTrue(googlePage.getResult().isDisplayed());
	}
}
