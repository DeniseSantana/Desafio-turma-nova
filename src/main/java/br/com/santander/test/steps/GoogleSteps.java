package br.com.santander.test.steps;

import br.com.santander.test.logic.GoogleLogic;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoogleSteps {

	private GoogleLogic googleLogic;

	public GoogleSteps() {
		googleLogic = new GoogleLogic();
	}

	@Given("I navigate to google")
	public void given() {
		googleLogic.navigateToGoogle();
	}

	@When("I search for the word")
	public void when() {
		googleLogic.searchForString();
	}

	@Then("I validate the outcome")
	public void then() {
		googleLogic.validateResultElementToAppear();
	}
}