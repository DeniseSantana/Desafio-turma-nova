package br.com.santander.core.webdriver.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import br.com.santander.core.exception.CoreException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class IEManager extends DriverManager {

	@Override
	public void createDriver() {
		setupProxy();

		WebDriverManager.iedriver().setup();
		try {
			webDriver = new InternetExplorerDriver(setupIEOptions());
			webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

	private static InternetExplorerOptions setupIEOptions() throws Exception {
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.setCapability("ignoreProtectedModeSettings", true);
		options.setCapability("requireWindowFocus", true);
		return options;
	}

	@Override
	public void tearDown() {
		super.tearDown();
		//in parallels crash test
//		try {
//			Runtime.getRuntime().exec("wmic process where name=\"iexplore.exe\" call terminate").waitFor();
//		} catch (IOException | InterruptedException e) {
//			throw new RuntimeException(e);
//		}
	}
}
