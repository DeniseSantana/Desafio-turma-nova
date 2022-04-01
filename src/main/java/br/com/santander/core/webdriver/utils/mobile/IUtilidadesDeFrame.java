package br.com.santander.core.webdriver.utils.mobile;

import org.openqa.selenium.WebDriver.TargetLocator;

public interface IUtilidadesDeFrame extends IUtilidadesDeElemento
{
	/**<p>getDriver().switchTo().defaultContent()</p>
	 * 
	 * Volta ou para o primeiro frame da página, ou para a página
	 * principal quando houver iframes.*/
	default void switchToDefaultContent()
	{
		TargetLocator locator = getWebDriver().switchTo();
		locator.defaultContent();
	}
}
