package br.com.automacao.test.pages;

import br.com.automacao.core.webdriver.page.BasePage;
import br.com.automacao.core.webdriver.page.PageMaker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.Getter;

import javax.xml.xpath.XPath;

@Getter
public class LoginPage extends BasePage   {

	public LoginPage() {
		super();
	}
	public static final String salesforce = "https://wise-badger-3ks8kg-dev-ed.trailblaze.my.salesforce.com";


	// Localizadores dos elementos da página
	//CT=100
	private By usernameInput = By.id("username");
	private By passwordInput = By.id("password");

	private By lembrarButton = By.id("rememberUn");
	private By loginButton = By.id("Login");

	//CT=101
	private By contasButton = By.className("slds-context-bar__label-action dndItem");
	private By criarButton = By.className("forceActionLink");


	// Métodos para interagir com os elementos da página
	public void setUsername(String username) {
		getWebDriver().findElement(usernameInput).sendKeys(username);
	}

	public void setPassword(String password) {
		getWebDriver().findElement(passwordInput).sendKeys(password);
	}

	public void clickLoginButton() {
		getWebDriver().findElement(lembrarButton).click();
		getWebDriver().findElement(loginButton).click();
	}
	public void clickContasButton(){
		getWebDriver().findElement(contasButton).click();

	}
	public void clickCriarConta(){
		getWebDriver().findElement(criarButton).click();
	}
}





















