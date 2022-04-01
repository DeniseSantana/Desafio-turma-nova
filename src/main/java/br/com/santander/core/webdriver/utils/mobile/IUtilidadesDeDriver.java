package br.com.santander.core.webdriver.utils.mobile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface IUtilidadesDeDriver {
	
	WebDriver getWebDriver();

	WebDriverWait getWait();

}
