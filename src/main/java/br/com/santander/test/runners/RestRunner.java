package br.com.santander.test.runners;

import br.com.santander.test.support.CustomTestNGCucumberTestsRest;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;

@CucumberOptions(features = "classpath:features", //
		glue = "br.com.santander.test", //
		tags = {"@rest", "@api"}, //
		plugin = { "json:target/cucumber-report/cucumber.json" }, //
		monochrome = true, //
		snippets = SnippetType.CAMELCASE //
)
public class RestRunner extends CustomTestNGCucumberTestsRest {

}
