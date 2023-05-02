package br.com.automacao.test.steps;

import static org.testng.Assert.assertTrue;
import br.com.automacao.test.logic.LoginActions;
import br.com.automacao.test.sheet.model.LoginData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

	private LoginActions loginActions;
	private LoginData loginData;

	public LoginSteps() {

		loginActions = new LoginActions();

	}

	@Given("que estou na tela principal do site") // CT01
	public void que_estou_na_tela_principal_do_site()  {
		loginActions.acessandoSiteSalesforce();

	}

	@When("insiro email e senha  válidos")
	public void insiro_email_e_senha_validos() {
		loginActions.inserindoEmailValido();
		loginActions.inserindoSenhaValida();
	}

	@Then("acesso o sistema com sucesso")
	public void acesso_o_sistema_com_sucesso() {
		loginActions.clickNologin();
	}
//	@And("acesso o sistema com sucesso")
//	public void acesso_o_sistema_com_sucesso_A() {
//		loginActions.clickNologin();
//	}


	@Then("clico em contas e depois clico em criar conta e preencho o formulario e crio minha conta com sucesso")
	public void preencho_o_formulario_e_crio_minha_conta_com_sucesso() {
		loginActions.clickNologin();
		loginActions.clickButtonCriarConta();
}
}





