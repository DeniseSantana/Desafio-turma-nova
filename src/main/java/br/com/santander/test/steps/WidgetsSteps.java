package br.com.santander.test.steps;

import static org.testng.Assert.assertTrue;

import br.com.santander.test.logic.WidgetsLogic;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WidgetsSteps {
	private WidgetsLogic widgetsLogic;
	
	public  WidgetsSteps() {
		widgetsLogic = new  WidgetsLogic(); 
		
	}
	
	@Given("que acesso a tela automation batista")
	public void que_acesso_a_tela_automation_batista() {
		widgetsLogic.navegacaoautomationbatista();
	}

	@When("clico no icone comecar automacao e seleciono widgets")
	public void clico_no_icone_comecar_automacao_e_seleciono_widgets() {
		widgetsLogic.Btncomecarautomacao();
		widgetsLogic.selecionarwidgets();
		widgetsLogic.selecionaraccordion();
		widgetsLogic.selecionarfirst();
		widgetsLogic.selecionarsecond();
		widgetsLogic.selecinarthird();
	}
	@Then("valido o accordion")
	public void valido_o_accordion() {
		widgetsLogic.validacaoaccordion();
		assertTrue(widgetsLogic.validacaoaccordion()); 
	}

	@When("clico no icone autocomplete")
	public void clico_no_icone_autocomplete() {
		widgetsLogic.selecinarautocomplete(); 
		widgetsLogic.selecinarestado();
	}
    @Then("valido o autocomplete")
	public void valido_o_autocomplete() {
		widgetsLogic.validarautocomplete();
		assertTrue(widgetsLogic.validacaoaccordion());
	
    }	
    
    @When("clico no icone datapicker")
	public void clico_no_icone_datapicker() {
		widgetsLogic.selecinardatapicker();
	}
    
    @Then("valido o datapicker")
	public void valido_o_datapicker() {
		widgetsLogic.validardatapicker();
		assertTrue(widgetsLogic.validardatapicker());
	
	
	}	

}