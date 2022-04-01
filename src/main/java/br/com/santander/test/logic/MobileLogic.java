package br.com.santander.test.logic;

import static br.com.santander.test.support.Context.mobile;
import static br.com.santander.test.support.Context.rGen;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;

import br.com.santander.core.webdriver.page.PageMaker;
import br.com.santander.core.webdriver.utils.mobile.Tempo;
import br.com.santander.test.pages.MobilePage;
import br.com.santander.test.sheet.model.MobileData;
import br.com.santander.test.support.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobileLogic {

	private MobilePage mobilePage;
	private MobileData mobileData;

	public MobileLogic() {
		mobilePage = PageMaker.getInstance(MobilePage.class, mobile().getWebDriver());
		mobileData = (MobileData) Context.testData();
	}

	public void selecionarAmbiente() {
		String step = "eu seleciono o ambiente";
		log.info(step);
		verificarPopAcesseMinhaConta(mobilePage.getRdbHomologacao(), Tempo._60.qtde());
		rGen().registerStep(mobile().getScreenshot(), step);
		mobile().clica(mobilePage.getRdbHomologacao());
		
		if(mobile().isAndroidDriver()) {
			clicarBtnContinuar();
		}		
	}

	private void clicarBtnContinuar() {
		String step = "clico em 'CONTINUAR'";
		log.info(step);
		rGen().registerStep(mobile().getScreenshot(), step);
		mobile().clica(mobilePage.getBtnContinuar());
	}
	
	public void preencherTxtAgencia() {
		verificarPopAcesseMinhaConta(mobilePage.getTxtAgencia(), Tempo._60.qtde());

		log.info("limpo 'Agencia'");
		mobile().limpa(mobilePage.getTxtAgencia());

		String step = "preencho 'Agencia': " + mobileData.getAgencia();
		log.info(step);		
		rGen().registerStep(mobile().getScreenshot(), step);	
		mobile().escreve(mobilePage.getTxtAgencia(), mobileData.getAgencia());
	}

	public void preencherTxtConta() {
		log.info("limpo 'Conta'");
		mobile().limpa(mobilePage.getTxtConta());

		String step = "preencho 'Conta': " + mobileData.getConta();
		log.info(step);		
		rGen().registerStep(mobile().getScreenshot(), step);
		mobile().escreve(mobilePage.getTxtConta(), mobileData.getConta());
	}

	public void preencherTxtUsuario() {
		log.info("limpo 'Usuario'");
		mobile().limpa(mobilePage.getTxtUsuario());

		String step = "preencho 'Usuario': " + mobileData.getUsuario();
		log.info(step);		
		rGen().registerStep(mobile().getScreenshot(), step);
		mobile().escreve(mobilePage.getTxtUsuario(), mobileData.getUsuario());
	}

	public void preencherTxtSenha() {
		log.info("limpo 'Senha'");
		mobile().limpa(mobilePage.getTxtSenha());

		String step = "preencho 'Senha': " + mobileData.getSenha();
		log.info(step);		
		rGen().registerStep(mobile().getScreenshot(), step);
		mobile().escreve(mobilePage.getTxtSenha(), mobileData.getSenha());
	}

	public void clicarBtnENTRAR() {
		String step = "clico em 'ENTRAR'";
		log.info(step);		
		rGen().registerStep(mobile().getScreenshot(), step);
		mobile().clica(mobilePage.getBtnEntrar());
	}
	
	public void validarLogin() {
		String step = "valido o login";
		log.info(step);	
		Boolean visivel = mobile().estaVisivel(mobilePage.getBtnMenu());
		rGen().registerStep(mobile().getScreenshot(), step);
		assertTrue(visivel);
	}	
	
	private void verificarPopAcesseMinhaConta(WebElement elementoEsperado, int segundosEsperaLimite) {
		log.info("verificando popup 'Acesse minha conta'");
		int tempo = segundosEsperaLimite / 2;
		while (tempo > 0) {
			if (mobile().estaVisivel(mobilePage.getLblAcesseMinhaConta(), 1_000)) {
				String step = "fecho o popup 'Acesse minha conta'";
				log.info(step);
				rGen().registerStep(mobile().getScreenshot(), step);
				mobile().clica(mobilePage.getLblAcesseMinhaConta());
			}
			if (mobile().estaVisivel(elementoEsperado, 1_000)) {
				log.info("elemento esperado encontrado");
				break;
			}
			tempo--;
		}
	}	
}