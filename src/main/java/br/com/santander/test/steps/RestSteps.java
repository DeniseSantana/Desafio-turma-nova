package br.com.santander.test.steps;

import br.com.santander.test.logic.RestLogic;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class RestSteps {

	private RestLogic restLogic;

	public RestSteps() {
		restLogic = new RestLogic();
	}

	@Given("eu sou um usuario autorizado")
	public void eu_sou_um_usuario_autorizado() {
		restLogic.realizarAutorizacaoDoUsuario();
	}
	
	@And("valido a lista de livros disponivel")
	public void valido_a_lista_de_livros_disponivel() {
		restLogic.validarListaLivrosDisponiveis();
	}	
}