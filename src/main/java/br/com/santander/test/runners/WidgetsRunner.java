package br.com.santander.test.runners;

import br.com.santander.test.support.CustomTestNGCucumberTestsWeb;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;

@CucumberOptions(features = "classpath:features", //
		glue = "br.com.santander.test", //
		tags = {"@CT_15"}, //
		plugin = { "json:target/cucumber-report/cucumber.json" }, //
		monochrome = true, //
		snippets = SnippetType.CAMELCASE //
)

public class WidgetsRunner extends CustomTestNGCucumberTestsWeb {    

}
