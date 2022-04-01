package br.com.santander.test.steps;

import static org.testng.Assert.assertTrue;

import br.com.santander.test.logic.IteracoesLogic;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IteracoesSteps {
	
	private IteracoesLogic iteracoesLogic;
	
	public IteracoesSteps() {
		
		iteracoesLogic = new IteracoesLogic();
		
	}

	
	@Given("que estou na tela de automacao com batista")
	public void que_estou_na_tela_de_automacao_com_batista() {
		iteracoesLogic.navegacaoautomacao();
	}

	@When("seleciono no icone iteracoes e acesso o  Drag and Drop")
	public void seleciono_no_icone_iteracoes_e_acesso_o_Drag_and_Drop() {
	     iteracoesLogic.acessandoiteracoes();
	     iteracoesLogic.interagindodraganddrop();
		
	}

	@Then("arrasto o elemento selecionado para seu destinno com sucesso")
	public void arrasto_o_elemento_selecionado_para_seu_destinno_com_sucesso() {
		assertTrue(iteracoesLogic.validandoiteracaodranganddrop());
	}
	

	@When("seleciono no icone iteracoes e acesso o  Mousehover")
	public void seleciono_no_icone_iteracoes_e_acesso_o_Mousehover() {
	     iteracoesLogic.acessandoiteracoes();
	     iteracoesLogic.acessandomousehover();
		
	}

	@Then("direciono o mause para dentro da caixa que exibira uma menssagem validando a interacao")
	public void direciono_o_mause_para_dentro_da_caixa_que_exibira_uma_menssagem_validando_a_interacao() {
		assertTrue(iteracoesLogic.validandomousehover());
	}

}
