package br.com.santander.core.webdriver.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import br.com.santander.core.exception.CoreException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeManager extends DriverManager {

	@Override
	public void createDriver() {
		WebDriverManager.edgedriver().setup();
		try {
			webDriver = new EdgeDriver(new EdgeOptions());
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

}
