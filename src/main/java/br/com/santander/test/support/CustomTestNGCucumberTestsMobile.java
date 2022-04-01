package br.com.santander.test.support;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import br.com.santander.core.uftmobile.client.UFTMobileClient;
import br.com.santander.core.utils.Tag;
import br.com.santander.core.utils.json.Device;
import br.com.santander.core.utils.properties.ApplicationProperties;
import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.PickleEventWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Runner com hooks do TestNG
 *
 */
@Slf4j
public abstract class CustomTestNGCucumberTestsMobile extends CustomTestNGCucumberTests {
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(XmlTest xml) {
		log.info("------ Starting Suite TestNG execution Mobile test ------");
		
		ApplicationProperties applicationProperties = new ApplicationProperties();
		
		int threads = applicationProperties.getExecutionMobileDataProviderThreadCount();
		int devices = applicationProperties.getExecutionMobileDevices().size();
				
		if(devices < threads) {
			log.info("Changing the number of threads (" + threads+ "), because its number of devices is less than the threads available");
			threads = devices;
		}
		
		xml.getSuite().setDataProviderThreadCount(threads);
		log.info("Set data provider mobile thread count: " + threads);
		
		
		if (applicationProperties.getExecutionMobileUftMobile())
			log.info("Running mobile test in UFT Mobile");
		else
			log.info("Running mobile test local");
		
	}
	
	@BeforeMethod
	public void beforeMethod(Object[] params) {
		//the order of the indices of the Object [] params are the same as the @Test parameters
		Device device = (Device) params[0]; //Device
		PickleEventWrapper pickle = (PickleEventWrapper) params[1]; //Pickle
		
		Tag tag = new Tag(convertTagsToStrings(pickle.getPickleEvent().pickle.getTags()));
		Context.setup(tag, device);
		log.info("Running Mobile test");
		log.info("Use device: '" + device.getDeviceName() + "'");
		log.info("Use data..: " + Context.testData());
	}
	
	@Test(groups = "cucumber", description = "Cucumber Scenarios", dataProvider = "mergedScenariosDevices")
	public void runScenario(Device device, PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());
	}	
	
	@AfterMethod
	public void afterMethod() {
		finalizeDriverTest();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		new UFTMobileClient().removeAllDevicesReservedByUser();
		log.info("------ Finishing Suite TestNG execution Mobile test ------");
	}
}
