package br.com.santander.core.webdriver.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import br.com.santander.core.report.utils.ReportProperties;
import br.com.santander.core.webdriver.utils.mobile.UtilidadesDeMobile;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobileActions extends UtilidadesDeMobile {

	private boolean reportIsActive;

	public MobileActions(WebDriver driver) {
		setWebDriver(driver);
		try {
			this.reportIsActive = new ReportProperties().reportIsActive();
		} catch (Exception e) {
			throw new RuntimeException("Check if report.properties exists in the resources directory.");
		}
	}
	
	public byte[] getScreenshot() {
		try {
			if (reportIsActive) {
				return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
			} else {
				return null;
			}
		} catch (Exception e) {
			log.info("Screenshot failed: " + e.getMessage());
			return null;
		}
	}
	
	public boolean isAndroidDriver() {
		return getWebDriver() instanceof AndroidDriver;
	}
	
	public boolean isIOSDriver() {
		return getWebDriver() instanceof IOSDriver;
	}
}
