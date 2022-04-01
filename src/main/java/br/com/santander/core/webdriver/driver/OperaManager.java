package br.com.santander.core.webdriver.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import br.com.santander.core.exception.CoreException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class OperaManager extends DriverManager {

	@Override
	public void createDriver() {
		WebDriverManager.operadriver().setup();
		try {
			webDriver = new OperaDriver(new OperaOptions());
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

}
