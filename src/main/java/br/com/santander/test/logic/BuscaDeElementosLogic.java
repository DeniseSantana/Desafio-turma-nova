package br.com.santander.test.logic;

import static br.com.santander.test.support.Context.rGen;
import static br.com.santander.test.support.Context.web;
import org.openqa.selenium.Keys;
import br.com.santander.core.webdriver.page.PageMaker;
import br.com.santander.test.pages.BuscaDeElementosPage;
import br.com.santander.test.sheet.model.BuscaData;
import br.com.santander.test.support.Context;


public class BuscaDeElementosLogic {

	private BuscaDeElementosPage buscaDeElementosPage;
	private BuscaData buscaData;
	
	public BuscaDeElementosLogic() {
		buscaDeElementosPage = PageMaker.getInstance(BuscaDeElementosPage.class, web().getWebDriver());
		buscaData = (BuscaData) Context.testData();
	}
	
	public void navegandotelainicialautomacao() { // CT03
		web().navigateToUrl("https://automacaocombatista.herokuapp.com");
		web().getWebDriver().manage().window().maximize();
		rGen().registerStep(web().getScreenshot(), "que estou na tela de inicio automação batista");
	}
	
	public void clicandoparabuscarelementos(){
		//web().sleep(2);
		web().click(buscaDeElementosPage.getComecarautomacaoweb());
		web().click(buscaDeElementosPage.getClicandobuscadelementos());
		rGen().registerStep(web().getScreenshot(), "Clicando em Começar Automação Web e Busca de Elementos");
	}
	
	public void clicandolinks(){
		//web().sleep(2);
		web().click(buscaDeElementosPage.getClicandoemlinks());
		web().click(buscaDeElementosPage.getClicandook200());
		web().click(buscaDeElementosPage.getVoltargeral());
		web().click(buscaDeElementosPage.getErro400());
		web().click(buscaDeElementosPage.getVoltargeral());
		web().click(buscaDeElementosPage.getErro400pagenotfound());
		web().click(buscaDeElementosPage.getVoltargeral());
		web().click(buscaDeElementosPage.getErro500());
		rGen().registerStep(web().getScreenshot(), "Clicando em Links e nos 4 links");
	}
	
	public boolean validandoclicks() {
		web().elementIsVisible(buscaDeElementosPage.getValidartelainternal(), 5);
		rGen().registerStep(web().getScreenshot(), "Validando que estou na tela Internal Server Error!!");
		return web().elementIsVisible(buscaDeElementosPage.getValidartelainternal()); // fechando o boolean
	}
	
	public void clicandoinputs(){ // CT04
		web().click(buscaDeElementosPage.getClicandoinputsetextfield());
		web().insertText(buscaDeElementosPage.getClicandofirstname(), buscaData.getFirstname());
		web().insertText(buscaDeElementosPage.getClicandolastname(), buscaData.getLastname());
		web().insertText(buscaDeElementosPage.getClicandopass(), buscaData.getPassword());
		web().insertText(buscaDeElementosPage.getClicandoemail(), buscaData.getEmail());
		web().insertText(buscaDeElementosPage.getClicandotextarea(), buscaData.getTextarea());
		rGen().registerStep(web().getScreenshot(), "Clicando em Inputs e TextFild e preenchendo os campos");
	}
	
	public boolean validandoinputs() {
		web().elementIsVisible(buscaDeElementosPage.getValidandoinputstext(), 5);
		rGen().registerStep(web().getScreenshot(), "Validando que preenchi todos os campos");
		return web().elementIsVisible(buscaDeElementosPage.getValidandoinputstext()); // fechando o boolean
	}
	
	public void clicandobotoes(){ // CT05
		//web().sleep(2);
		web().click(buscaDeElementosPage.getClicandobotoes());
		web().click(buscaDeElementosPage.getClicandoraised());
		web().click(buscaDeElementosPage.getClicandofloating());
		web().click(buscaDeElementosPage.getClicandoflat());
		web().click(buscaDeElementosPage.getClicandosubmit());
		rGen().registerStep(web().getScreenshot(), "Clicando nos botões");
	}
	
	public boolean validandotelabotoes() {
		web().elementIsVisible(buscaDeElementosPage.getValidandobotoes(), 5);
		rGen().registerStep(web().getScreenshot(), "Validando tela dos botões");
		return web().elementIsVisible(buscaDeElementosPage.getValidandobotoes()); // fechando o boolean
	}
	
	public void clicandoradioecheckbox(){ // CT06
		//web().sleep(2);
		web().click(buscaDeElementosPage.getClicandoemradioecheck());
		web().click(buscaDeElementosPage.getSelecionandoblue());
		web().click(buscaDeElementosPage.getSelecionandoyellow());
		web().click(buscaDeElementosPage.getSelecionandogrey());
		web().click(buscaDeElementosPage.getSelecionandoblack());
		rGen().registerStep(web().getScreenshot(), "Clicando nos botões e selecionando o tipo");
	}
	
	public boolean validandotelaradio() {
		web().elementIsVisible(buscaDeElementosPage.getValidandochecktela(), 5);
		rGen().registerStep(web().getScreenshot(), "Validando tela do radio e checkbox");
		return web().elementIsVisible(buscaDeElementosPage.getValidandochecktela()); // fechando o boolean
	}
	
	public void clicandodropdowneselect(){ // CT07
		//web().sleep(2);
		web().click(buscaDeElementosPage.getClicandoemdrop());
		web().click(buscaDeElementosPage.getDesenhofav());
		web().click(buscaDeElementosPage.getOptions());
		web().click(buscaDeElementosPage.getValidandodrop());
		//web().insertKey(buscaDeElementosPage.getOptions(), Keys.TAB);
		web().click(buscaDeElementosPage.getEscolhapais());
		web().click(buscaDeElementosPage.getEscolhagenero());
		web().click(buscaDeElementosPage.getEscolhasexo());
		web().click(buscaDeElementosPage.getEscolhabrowser());
		web().selectElementByValue(buscaDeElementosPage.getEscolhabrowser(), "Chrome");
		rGen().registerStep(web().getScreenshot(), "Clicando nas opções e escolhendo");
	}
	
	public boolean validandoteladropdown() {
		web().elementIsVisible(buscaDeElementosPage.getValidandodrop(), 5);
		rGen().registerStep(web().getScreenshot(), "Validando tela do radio e checkbox");
		return web().elementIsVisible(buscaDeElementosPage.getValidandodrop()); // fechando o boolean
	}
	
	public void clicandotexto(){ // CT08
		//web().sleep(2);
		web().click(buscaDeElementosPage.getClicandoemtextos());
		web().click(buscaDeElementosPage.getPrimeirotexto());
		web().navigateToUrl("https://medium.com/automa%C3%A7%C3%A3o-com-batista/trabalhando-com-verifica%C3%A7%C3%B5es-e-esperas-de-elementos-com-o-capybara-siteprism-e-rspec-parte-ii-6b12a6a39f77");
		//web().insertKey(buscaDeElementosPage.getPrimeirotexto(), Keys.LEFT_ALT);
		//web().click(buscaDeElementosPage.getPrimeirotexto());
		rGen().registerStep(web().getScreenshot(), "Clicando no primeiro texto");
	}
	
	public boolean validandotelatexto() {
		web().elementIsVisible(buscaDeElementosPage.getValidandoteladotextodois(), 5);
		rGen().registerStep(web().getScreenshot(), "Validando tela do aberta do texto");
		return web().elementIsVisible(buscaDeElementosPage.getValidandoteladotextodois()); // fechando o boolean
	}
	
	public void clicandotabela(){ // CT09
		//web().sleep(2);
		web().click(buscaDeElementosPage.getClicandoemtabela());
		web().click(buscaDeElementosPage.getVoltargeral());
		rGen().registerStep(web().getScreenshot(), "Clicando em tabela e em voltar");
	}
	
	public boolean validandoteladevolta() {
		web().elementIsVisible(buscaDeElementosPage.getValindandotelavolta(), 5);
		rGen().registerStep(web().getScreenshot(), "Validando tela do aberta do texto");
		return web().elementIsVisible(buscaDeElementosPage.getValindandotelavolta()); // fechando o boolean
	}
	
}
