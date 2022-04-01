package br.com.santander.test.logic;

import static br.com.santander.test.support.Context.rGen;
import static br.com.santander.test.support.Context.web;

import br.com.santander.core.webdriver.page.PageMaker;
import br.com.santander.test.pages.MudancaPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MudancaLogic {

	private MudancaPage mudancaPage;

	public MudancaLogic() {

		mudancaPage = PageMaker.getInstance(MudancaPage.class, web().getWebDriver());
	}

	public void navegacaosite() {
		web().navigateToUrl("https://automacaocombatista.herokuapp.com");
		web().getWebDriver().manage().window().maximize();
		rGen().registerStep(web().getScreenshot(), "estou na tela inicial");// pede pra registrar e fazer um print
		web().click(mudancaPage.getPaginainicial());
		web().click(mudancaPage.getIconemudanca());
	}

	public void clicandoalert() {
		web().click(mudancaPage.getBtnalert());
		rGen().registerStep(web().getScreenshot(), "clicando em alert");
		web().click(mudancaPage.getBtnjsalert());
		rGen().registerStep(web().getScreenshot(), "clicando em js alert");
		web().click(mudancaPage.getBtnconfirm());
		rGen().registerStep(web().getScreenshot(), "clicando em js confirm");
		web().click(mudancaPage.getBtnprompt());
		rGen().registerStep(web().getScreenshot(), "clicando em prompt");
	}

	public boolean validarpagina() {

		web().elementIsVisible(mudancaPage.getValidarpagina(), 5); // validando a tela
		rGen().registerStep(web().getScreenshot(), "Validando pagina");
		return web().elementIsVisible(mudancaPage.getValidarpagina()); // fechando o boolean
	}

	public void inserindousuarioesenha() {
		web().click(mudancaPage.getBtniframe());
		web().switchToFrame2(0);
		web().insertText(mudancaPage.getFirstname(), "denise");
		web().insertText(mudancaPage.getLastname(), "santana");
		web().insertText(mudancaPage.getPassword(), "denise1224@");
		rGen().registerStep(web().getScreenshot(), "insiro usuario e senha");
		web().insertText(mudancaPage.getEmail(), "felipe.moreno@teste.com");
		web().insertText(mudancaPage.getTxtarea(), "automação");
		//web().click(mudancaPage.getBtnvoltar());
		
			}
		
	public boolean validarpaginaiframe() {

		web().elementIsVisible(mudancaPage.getValidartela(), 5); // validando a tela
		rGen().registerStep(web().getScreenshot(), "Validando tela");
		return web().elementIsVisible(mudancaPage.getValidartela()); // fechando o boolean
	}

	public void clicandojanela() {
		web().click(mudancaPage.getBtnjanela());
		rGen().registerStep(web().getScreenshot(), "clicando em janela");
		//reconhecendo uma nova janela
		String originalWindow = web().getWebDriver().getWindowHandle();
		assert web().getWebDriver().getWindowHandles().size() == 1;
		web().click(mudancaPage.getCliqueaqui());
		for (String windowHandle : web().getWebDriver().getWindowHandles()) {
			if(!originalWindow.contentEquals(windowHandle)) {
			web().getWebDriver().switchTo().window(windowHandle);
			break;
			}
			
		//rGen().registerStep(web().getScreenshot(), "clicando em clique aqui");
		//web().sleep(5);
		}
	}

	public boolean validarpaginaJanela() {
		
		web().waitForElementToBeDisplayed(mudancaPage.getValidandopagina());
		//web().elementIsVisible(mudancaPage.getValidandopagina(), 5); // validando a tela
		rGen().registerStep(web().getScreenshot(), "Validando pagina janela");
		return web().elementIsVisible(mudancaPage.getValidandopagina()); // fechando o boolean
	}

	public void clicandomodal() {
		web().click(mudancaPage.getBtnmodal());
		rGen().registerStep(web().getScreenshot(), "clicando em modal");
		web().click(mudancaPage.getBtnabrir());
		web().sleep(5);
		rGen().registerStep(web().getScreenshot(), "clicando em abrir");
		
	}

	public boolean validandomodal() {
		web().elementIsVisible(mudancaPage.getValidarmodal(), 5); // validando a tela
		rGen().registerStep(web().getScreenshot(), "Validando pagina modal");
		return web().elementIsVisible(mudancaPage.getValidarmodal()); // fechando o boolean
	}
}