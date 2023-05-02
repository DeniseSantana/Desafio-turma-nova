package br.com.automacao.test.runners;

import br.com.automacao.test.support.CustomTestNGCucumberTestsWeb;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;

@CucumberOptions(features = "classpath:features", //
		glue = "br.com.automacao.test", //
		tags =  {"@CT=105"} , //
		plugin = { "json:target/cucumber-report/cucumber.json" }, //
		monochrome = true, //
		snippets = SnippetType.CAMELCASE //
)
public class AutomacaoSalesforceRunner extends CustomTestNGCucumberTestsWeb {

}
