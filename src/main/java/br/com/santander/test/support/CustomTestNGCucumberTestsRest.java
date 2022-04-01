package br.com.santander.test.support;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import br.com.santander.core.utils.Tag;
import br.com.santander.core.utils.properties.ApplicationProperties;
import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.PickleEventWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Runner com hooks do TestNG
 *
 */
@Slf4j
public abstract class CustomTestNGCucumberTestsRest extends CustomTestNGCucumberTests {
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(XmlTest xml) {
		log.info("------ Starting Suite TestNG execution Rest test ------");
		
		int threads = new ApplicationProperties().getExecutionRestDataProviderThreadCount();

		xml.getSuite().setDataProviderThreadCount(threads);
		log.info("Set data provider web thread count: " + threads);
	}
		
	@BeforeMethod
	public void beforeMethod(Object[] params) {
		//the order of the indices of the Object [] params are the same as the @Test parameters
		PickleEventWrapper pickle = (PickleEventWrapper) params[0]; //Pickle
		
		Tag tag = new Tag(convertTagsToStrings(pickle.getPickleEvent().pickle.getTags()));
		Context.setupRest(tag);
		log.info("Running Rest test");
		log.info("Use data: " + Context.testData());
	}
	
	@Test(groups = "cucumber", description = "Cucumber Scenarios", dataProvider = "scenariosCucumber")
	public void runScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());
	}
	
	@AfterMethod
	public void afterMethod() {
		Context.tearDown();
	}
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		log.info("------ Finishing Suite TestNG execution Rest test ------");
	}
}
