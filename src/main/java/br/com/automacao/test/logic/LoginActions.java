package br.com.automacao.test.logic;

import br.com.automacao.test.pages.LoginPage;

import br.com.automacao.test.sheet.model.LoginData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginActions {

	private LoginPage loginPage;
	private LoginData loginData;

	public LoginActions() {
		loginPage =  new LoginPage();

	}

	public void acessandoSiteSalesforce() {
		loginPage.navigateToUrl(LoginPage.salesforce);

	}

	public void inserindoEmailValido() {
		loginPage.setUsername("denise.cristiane.silva@gmail.com.test");
	}
	public void inserindoSenhaValida() {
		loginPage.setPassword("Denise123");

	}

	public void clickNologin() {

		loginPage.clickLoginButton();
	}
	public void cliclNoButtonContas(){

		loginPage.clickContasButton();
	}
	public void clickButtonCriarConta(){
		loginPage.clickContasButton();
	}
}