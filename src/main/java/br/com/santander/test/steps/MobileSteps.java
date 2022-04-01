package br.com.santander.test.steps;

import br.com.santander.test.logic.MobileLogic;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class MobileSteps {

	private MobileLogic mobileLogic;

	public MobileSteps() {
		mobileLogic = new MobileLogic();
	}

	@Given("eu seleciono o ambiente")
	public void eu_seleciono_o_ambiente() {
		mobileLogic.selecionarAmbiente();
	}
	
	@And("preencho 'Agencia'")
	public void preencho_Agencia() {
		mobileLogic.preencherTxtAgencia();
	}
	
	@And("preencho 'Conta'")
	public void preencho_Conta() {
		mobileLogic.preencherTxtConta();
	}
	
	@And("preencho 'Usuario'")
	public void preencho_Usuario() {
		mobileLogic.preencherTxtUsuario();
	}
	
	@And("preencho 'Senha'")
	public void preencho_Senha() {
		mobileLogic.preencherTxtSenha();
	}
	
	@And("clico em 'ENTRAR'")
	public void clico_em_ENTRAR() {
		mobileLogic.clicarBtnENTRAR();
	}
	
	@Then("valido o login")
	public void valido_login() {
		mobileLogic.validarLogin();
	}
}
