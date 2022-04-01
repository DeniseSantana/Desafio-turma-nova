package br.com.santander.test.logic;

import static br.com.santander.test.support.Context.rGen;
import static br.com.santander.test.support.Context.web;

import br.com.santander.core.webdriver.page.PageMaker;
import br.com.santander.test.pages.GooglePage;
import br.com.santander.test.pages.IteracoesPage;

public class IteracoesLogic {
	
	private IteracoesPage iteracoesPage;
	
	public IteracoesLogic() {
		
		iteracoesPage = PageMaker.getInstance(IteracoesPage.class, web().getWebDriver());
			
	}
	
	
	public void navegacaoautomacao() {
		
		web().navigateToUrl("https://automacaocombatista.herokuapp.com/");
		web().getWebDriver().manage().window().maximize();	
		rGen().registerStep(web().getScreenshot(),"acessando site");
		
	}
	
	public void acessandoiteracoes() {
		
		web().click(iteracoesPage.getBtnacessandoautomacao());
		web().click(iteracoesPage.getBtnIteracoes());
		rGen().registerStep(web().getScreenshot(),"acessando iteracoes");
		
	}
	
	public void interagindodraganddrop() {
		web().click(iteracoesPage.getBtndraganddrop());
		web().clickdragAndDrop(iteracoesPage.getBtnwinston(),iteracoesPage.getBtndropzone());	
		rGen().registerStep(web().getScreenshot()," interação realizada");
	}
	
	public boolean validandoiteracaodranganddrop() {
			
		web().elementIsVisible(iteracoesPage.getValidacaodrnagdrop(), 5); 
		rGen().registerStep(web().getScreenshot(), "validando a interação");
		return web().elementIsVisible(iteracoesPage.getValidacaodrnagdrop()); 
	
	}

	public void acessandomousehover() {
		
		web().click(iteracoesPage.getBtnmousehover());
		rGen().registerStep(web().getScreenshot(), "acessando mousehover");	
		web().moveToElement(iteracoesPage.getBtninteracaomouse());
		rGen().registerStep(web().getScreenshot(), "acessando mousehover");	
	}
	
	public boolean validandomousehover() {
		
		web().elementIsVisible(iteracoesPage.getValidandomousehover(), 5); 
		rGen().registerStep(web().getScreenshot(), "validando a interação");
		return web().elementIsVisible(iteracoesPage.getValidandomousehover());	
		
	}

}
