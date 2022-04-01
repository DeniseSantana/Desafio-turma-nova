package br.com.santander.test.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.santander.core.webdriver.page.PageMaker;
import lombok.Getter;

@Getter
public class GooglePage implements PageMaker {

	public static final String HTTP_GOOGLE_COM = "http://google.com";

	@FindBy(name = "q")
	private WebElement query;

	@FindBy(name = "btnK")
	private WebElement searchButton;

	@FindBy(id = "result-stats")
	private WebElement result;

}
