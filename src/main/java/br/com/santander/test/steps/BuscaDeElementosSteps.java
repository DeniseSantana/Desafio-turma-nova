package br.com.santander.test.steps;

import static org.testng.Assert.assertTrue;

import br.com.santander.test.logic.BuscaDeElementosLogic;
import br.com.santander.test.sheet.model.BuscaData;
import br.com.santander.test.support.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BuscaDeElementosSteps {
	
	private BuscaDeElementosLogic buscaDeElementosLogic;
	private BuscaData buscaData;
	
	public BuscaDeElementosSteps() {

		buscaDeElementosLogic = new BuscaDeElementosLogic();
		buscaData = (BuscaData) Context.testData();
	}
	
	@Given("que estou na tela inicial e clico em comecar automacao web e busca de elementos") // CT03
	public void que_estou_na_tela_inicial_e_clico_em_comecar_automacao_web_e_busca_de_elementos() {
		buscaDeElementosLogic.navegandotelainicialautomacao();
		buscaDeElementosLogic.clicandoparabuscarelementos();
	}
	
	@When("clico em links e clico nos quatro links")
	public void clico_em_links_e_clico_nos_quatro_links() {
		buscaDeElementosLogic.clicandolinks();
	}
	
	@Then("valido que algum link foi clicado")
	public void valido_que_algum_link_foi_clicado() {
		assertTrue(buscaDeElementosLogic.validandoclicks());
	}
	
	@When("clico em inputs e textfield e preencho as informacoes") // CT04
	public void clico_em_inputs_e_textfield_e_preencho_as_informacoes() {
		buscaDeElementosLogic.clicandoinputs();
	}
	
	@Then("valido que as informacoes do inputs e textfield foram preenchidas")
	public void valido_que_as_informacoes_do_inputs_e_textfield_foram_preenchidas() {
		assertTrue(buscaDeElementosLogic.validandoinputs());
	}
	
	@When("clico em botoes e clico em raised e floating e flat e submit") // CT05
	public void clico_em_botoes_e_clico_em_raised_e_floating_e_flat_e_submit() {
		buscaDeElementosLogic.clicandobotoes();
	}
	
	@Then("valido mensagem ao clicar em algum desses botoes")
	public void valido_mensagem_ao_clicar_em_algum_desses_botoes() {
		assertTrue(buscaDeElementosLogic.validandotelabotoes());
	}
	
	@When("clico em radio e checkbox e seleciono cada tipo") // CT06
	public void clico_em_radio_e_checkbox_e_seleciono_cada_tipo() {
		buscaDeElementosLogic.clicandoradioecheckbox();
	}
	
	@Then("valido que os tipos foram selecionados")
	public void valido_que_os_tipos_foram_selecionados() {
		assertTrue(buscaDeElementosLogic.validandotelaradio());
	}
	
	@When("clico em dropdown e seleciono as opcoes escolhidas") // CT07
	public void clico_em_dropdown_e_seleciono_as_opcoes_escolhidas() {
		buscaDeElementosLogic.clicandodropdowneselect();
	}
	
	@Then("valido que a tela com as opcoes escolhidas")
	public void valido_que_a_tela_com_as_opcoes_escolhidas() {
		assertTrue(buscaDeElementosLogic.validandoteladropdown());
	}
	
	@When("clico em textos e clico em um texto") // CT08
	public void clico_em_textos_e_clico_em_um_texto() {
		buscaDeElementosLogic.clicandotexto();
	}
	
	@Then("valido que abriu e estou na tela do texto")
	public void valido_que_abriu_e_estou_na_tela_do_texto() {
		assertTrue(buscaDeElementosLogic.validandotelatexto());
	}
	
	@When("clico em tabela e clico em voltar") // CT09
	public void clico_em_tabela_e_clico_em_voltar() {
		buscaDeElementosLogic.clicandotabela();
	}
	
	@Then("valido que voltei corretamente")
	public void valido_que_voltei_corretamente() {
		assertTrue(buscaDeElementosLogic.validandoteladevolta());
	}
}
