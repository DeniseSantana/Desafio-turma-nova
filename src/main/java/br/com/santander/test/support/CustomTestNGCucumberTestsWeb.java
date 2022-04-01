package br.com.santander.test.support;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import br.com.santander.core.utils.Tag;
import br.com.santander.core.utils.properties.ApplicationProperties;
import br.com.santander.core.webdriver.driver.DriverType;
import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.PickleEventWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Runner com hooks do TestNG
 *
 */
@Slf4j
public abstract class CustomTestNGCucumberTestsWeb extends CustomTestNGCucumberTests {
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(XmlTest xml) {
		log.info("------ Starting Suite TestNG execution Web test ------");
		
		ApplicationProperties applicationProperties = new ApplicationProperties();
		int threads = applicationProperties.getExecutionWebDataProviderThreadCount();

		xml.getSuite().setDataProviderThreadCount(threads);
		log.info("Set data provider web thread count: " + threads);
		
		if (applicationProperties.getExecutionWebSauceLabs())
			log.info("Running web test in SauceLabs");
		else
			log.info("Running web test local");
	}
		
	@BeforeMethod
	public void beforeMethod(Object[] params) {
		//the order of the indices of the Object [] params are the same as the @Test parameters
		DriverType driverType = (DriverType) params[0]; //Pickle
		PickleEventWrapper pickle = (PickleEventWrapper) params[1]; //Pickle
		
		Tag tag = new Tag(convertTagsToStrings(pickle.getPickleEvent().pickle.getTags()));
		Context.setup(tag, driverType);
		log.info("Running Web test");
		log.info("Use data: " + Context.testData());
	}
	
	@Test(groups = "cucumber", description = "Cucumber Scenarios", dataProvider = "mergedScenariosDriverTypes")
	public void runScenario(DriverType driverType, PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());
	}
	
	@AfterMethod
	public void afterMethod() {
		finalizeDriverTest();
	}
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		log.info("------ Finishing Suite TestNG execution Web test ------");
	}
}
