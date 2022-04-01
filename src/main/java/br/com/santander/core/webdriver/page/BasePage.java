package br.com.santander.core.webdriver.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import br.com.santander.core.exception.PageException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class BasePage {
	public BasePage(WebDriver driver) {
		try {
			if (driver instanceof AppiumDriver) {
				PageFactory.initElements(new AppiumFieldDecorator(driver), this);
			} else {
				PageFactory.initElements(driver, this);
			}
		} catch (Exception e) {
			throw new PageException(e);
		}
	}
}