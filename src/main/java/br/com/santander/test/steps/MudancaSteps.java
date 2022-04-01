package br.com.santander.test.steps;

import static org.testng.Assert.assertTrue;

import br.com.santander.test.logic.GoogleLogic;
import br.com.santander.test.logic.MudancaLogic;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MudancaSteps {
	private MudancaLogic mudancaLogic;

	public MudancaSteps() {
		mudancaLogic = new MudancaLogic();
	}

	@Given("que estou na tela inicial")
	public void que_estou_na_tela_inicial() {
		mudancaLogic.navegacaosite();
	}

	@When("clico no icone alert")
	public void clico_no_icone_alert() {
		mudancaLogic.clicandoalert();
	}

	@Then("valido que estou na tela com sucesso")
	public void valido_que_estou_na_tela_com_sucesso() {
		assertTrue(mudancaLogic.validarpagina());
	}

	@When("clico no icone Iframe")
	public void clico_no_icone_Iframe() {
		mudancaLogic.inserindousuarioesenha();
	}

	@Then("valido que estou pagina com sucesso")
	public void valido_que_estou_pagina_com_sucesso() {
		assertTrue(mudancaLogic.validarpagina());
	}

	@When("clico no icone janela")
	public void clico_no_icone_janela() {
		mudancaLogic.clicandojanela();
	}

	@Then("valido que estou na tela")
	public void valido_que_estou_na_tela() {
		assertTrue(mudancaLogic.validarpaginaJanela());
	}
	@When("clico no icone modal")
	public void clico_no_icone_modal() {
		mudancaLogic.clicandomodal();
	}
	@Then("valido a pagina com sucesso")
	public void valido_a_pagina_com_sucesso() {
		assertTrue(mudancaLogic.validandomodal());
}
}