package br.com.santander.test.logic;

import static br.com.santander.test.support.Context.rGen;
import static br.com.santander.test.support.Context.web;
import br.com.santander.core.webdriver.page.PageMaker;
import br.com.santander.test.pages.WidgetsPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WidgetsLogic {
	private WidgetsPage widgetsPage;
	
	public WidgetsLogic() {
		widgetsPage = PageMaker.getInstance(WidgetsPage.class, web().getWebDriver());
	}

	public void navegacaoautomationbatista() {
		log.info("que estou na tela inicial");
		web().navigateToUrl("https://automacaocombatista.herokuapp.com");
		web().getWebDriver().manage().window().maximize();
		rGen().registerStep(web().getScreenshot(), "Tela inicial");
	}

	public void Btncomecarautomacao() {
		web().click(widgetsPage.getBtncomecar());
		rGen().registerStep(web().getScreenshot(), "clicando em comecar automacao");
	}

	public void selecionarwidgets() {
		web().click(widgetsPage.getBtnwidgets());
		rGen().registerStep(web().getScreenshot(), "clicando em widgets");
	}
	
	public void selecionaraccordion() {
	web().click(widgetsPage.getBtnaccordion());
	rGen().registerStep(web().getScreenshot(), "clicando no accordion");
	
	}
	public void selecionarfirst() {
		web().click(widgetsPage.getBtnfirst());
		rGen().registerStep(web().getScreenshot(), "clicando no first");
		
}
	public void selecionarsecond() {
		web().click(widgetsPage.getBtnsecond());
		rGen().registerStep(web().getScreenshot(), "clicando no second");
		
	}
	
	public void selecinarthird() {
		web().click(widgetsPage.getBtnthird());
		rGen().registerStep(web().getScreenshot(), "clicando no third");
	
	
}
	public boolean validacaoaccordion() {
		web().elementIsVisible(widgetsPage.getValidaraccordion(), 3);
		rGen().registerStep(web().getScreenshot(), "accordion validado");
		return web().elementIsVisible(widgetsPage.getValidaraccordion()); 
	
	}
	public void selecinarautocomplete() {
		web().click(widgetsPage.getBtncomecar());
		web().click(widgetsPage.getBtnwidgets());
		web().click(widgetsPage.getClicautocomplete());
		web().insertText(widgetsPage.getSelecionaracre(), "Acre");
		rGen().registerStep(web().getScreenshot(), "precher Acre"); 
	}
	
	public void selecinarestado() {
		web().click(widgetsPage.getSelecionaracre());
		rGen().registerStep(web().getScreenshot(), "estado selecionado");
	
	
	}	
	public boolean validarautocomplete() {
		web().elementIsVisible(widgetsPage.getValaidarautocomplete(), 3);
		rGen().registerStep(web().getScreenshot(), "accordion validado");
		return web().elementIsVisible(widgetsPage.getValaidarautocomplete()); 
	
}
	public void selecinardatapicker() {
		web().click(widgetsPage.getBtncomecar());
		web().click(widgetsPage.getBtnwidgets());
		web().click(widgetsPage.getBtndatapicker());
		web().click(widgetsPage.getBtndata());
		web().click(widgetsPage.getBtnmarch());
		web().click(widgetsPage.getMarch());
		web().click(widgetsPage.getBtnano());
		web().click(widgetsPage.getBtn2022());
		web().click(widgetsPage.getDia());
		web().click(widgetsPage.getBtnok());
		rGen().registerStep(web().getScreenshot(), "ver data");
	
	}	
	public boolean validardatapicker() {
		web().elementIsVisible(widgetsPage.getValidardatapicker(), 3);
		rGen().registerStep(web().getScreenshot(), "validar licker");
		return web().elementIsVisible(widgetsPage.getValidardatapicker());
}
}