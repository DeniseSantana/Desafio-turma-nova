package br.com.santander.test.support;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlTest;

import br.com.santander.core.utils.properties.ApplicationProperties;
import gherkin.pickles.PickleTag;
import io.cucumber.testng.TestNGCucumberRunner;
import lombok.extern.slf4j.Slf4j;

/**
 * Runner com hooks do TestNG
 *
 */
@Slf4j
public abstract class CustomTestNGCucumberTests {
	protected TestNGCucumberRunner testNGCucumberRunner;
	private static LocalDateTime timeInit;

	@BeforeSuite(alwaysRun = true)
	public abstract void beforeSuite(XmlTest xml);
	
	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		timeInit = LocalDateTime.now();	
		printHour("Inital Hour.......", timeInit);
		
		log.info("Before class...");
		try {
			testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		} catch (Exception e) {
			log.error("Error instantiating TestNGCucumberRunner: " + e.getMessage());
		}	}
	
	@BeforeMethod
	public abstract void beforeMethod(Object[] params);
	
	@AfterMethod
	public abstract void afterMethod();
	
	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		LocalDateTime timeFinal = LocalDateTime.now();
		printHour("Final Hour........", timeFinal);
		timeFinal = timeFinal.minusHours(timeInit.getHour()).minusMinutes(timeInit.getMinute()).minusSeconds(timeInit.getSecond());
		printTime("Time of execution.", timeFinal);

		log.info("After Class...");
		if (testNGCucumberRunner == null) {
			return;
		}
		testNGCucumberRunner.finish();	
	}

	@AfterSuite(alwaysRun = true)
	public abstract void afterSuite();
	
	@DataProvider(name = "mergedScenariosDevices", parallel = true)
	public Object[][] mergedScenariosDevices() {
		return combine(devices(), scenarios());
	}
	
	@DataProvider(name = "mergedScenariosDriverTypes", parallel = true)
	public Object[][] mergedScenariosDriverTypes() {
		return combine(driverTypes(), scenarios());
	}

	@DataProvider(name = "scenariosCucumber", parallel = true)
	public Object[][] scenarios() {
		if (testNGCucumberRunner == null) {
			return new Object[0][0];
		}
		return testNGCucumberRunner.provideScenarios();
	}

	@DataProvider(name = "devices")
	public Object[][] devices() {
		return new ApplicationProperties().getArrayObjectsExecutionMobileDevices();
	}
	
	@DataProvider(name = "driverTypes")
	public Object[][] driverTypes() {
		ApplicationProperties applicationProperties = new ApplicationProperties();
		if (applicationProperties.getExecutionWebSauceLabs())
			return applicationProperties.getArrayObjectsExecutionWebSauceLabsDriverTypes();
		else
			return applicationProperties.getArrayObjectsExecutionWebLocalDriverTypes();		
	}
	
	protected void finalizeDriverTest() {
		log.info("Tear down current manager if existent");
		Context.getDriverManagerFactory().tearDownCurrentManagerIfExistent();
		Context.tearDown();
	}
		
	private void printHour(String previous, LocalDateTime time) {
		log.info(previous + ": " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
	}

	private void printTime(String previous, LocalDateTime time) {
		log.info(previous + ": " + time.getHour() + " Hour(s) " + time.getMinute() + " minute(s) " + time.getSecond() + " second(s)");
	}
	
	protected String[] convertTagsToStrings(List<PickleTag> listPickerTag) {
		String tags[] = new String[listPickerTag.size()];
		for (int i = 0; i < listPickerTag.size(); i++) {
			tags[i] = listPickerTag.get(i).getName();
		}
		return tags;
	}
	
	/**
	 * método para combinar devices arrays em um só
	 * 
	 * @param a1
	 * @param a2
	 * @return array com a combinação dos dois arrays
	 */
	protected static Object[][] combine(Object[][] a1, Object[][] a2) {
		List<Object[]> objectCodesList = new LinkedList<>();
		for (Object[] o2 : a2) {	
			for (Object[] o1 : a1) {
				objectCodesList.add(concatAll(o1, o2));
			}
		}
		return objectCodesList.toArray(new Object[0][0]);
	}

	@SafeVarargs
	protected static <T> T[] concatAll(T[] first, T[]... rest) {
		// calculate the total length of the final object array after the concat
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		// copy the first array to result array and then copy each array completely to
		// result
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}

		return result;
	}
}
