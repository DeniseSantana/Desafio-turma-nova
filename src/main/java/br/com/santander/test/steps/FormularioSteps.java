 package br.com.santander.test.steps;

import static org.testng.Assert.assertTrue;

import br.com.santander.test.logic.FormularioLogic;
import br.com.santander.test.support.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FormularioSteps {
	private FormularioLogic formularioLogic;
	//private ElementsData elementsData;
		
	public FormularioSteps() {
		formularioLogic = new FormularioLogic();
		//elementsData = (ElementsData) Context.testData();
	}

	@Given("que estou na tela principal")
	public void que_estou_na_tela_principal() {
		formularioLogic.navegacaoAutomacaoBatista();
		formularioLogic.ListaFuncionalidades();
		
	}

	@When("seleciono o formulario e coloco os dados necessários e confirmo")
	public void seleciono_o_formulario_e_coloco_os_dados_necessários_e_confirmo() {
		
		formularioLogic.CadastrarUsuário();
	}

	@Then("valido a criação do usuário")
	public void valido_a_criação_do_usuário() {
		assertTrue(formularioLogic.ValidarCriacaoUsuario());
		assertTrue(formularioLogic.ValidarCriacaoUsuario1());
	}
	
	//CT=02
	
	@When("seleciono o usuário para excluir")
	public void seleciono_o_usuário_para_excluir() {
		
		formularioLogic.ExcluirUsuário();;
	}

	@Then("valido a exclussão do usuário")
	public void valido_a_exclussão_do_usuário() {
		assertTrue(formularioLogic.ValidarExclussãoUsuario());
		
	}

}
