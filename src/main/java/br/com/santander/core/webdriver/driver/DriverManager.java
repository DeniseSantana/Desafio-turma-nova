package br.com.santander.core.webdriver.driver;

import org.openqa.selenium.WebDriver;

import br.com.santander.core.utils.properties.ApplicationProperties;
import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class DriverManager {
	protected WebDriver webDriver;

	public WebDriver getDriver() {
		return webDriver;
	}

	public abstract void createDriver();

	public void tearDown() {
		if (webDriver != null) {
			webDriver.quit();
			webDriver = null;
		}
	}
	
	protected void setupProxy() {
		ApplicationProperties appProperties = new ApplicationProperties();
		if (appProperties.getExecutionWebProxyActivate()) {
			WebDriverManager.globalConfig().setProxy(appProperties.getExecutionWebProxyHost())
					.setProxyUser(appProperties.getExecutionWebProxyUser());
		}
	}
}
