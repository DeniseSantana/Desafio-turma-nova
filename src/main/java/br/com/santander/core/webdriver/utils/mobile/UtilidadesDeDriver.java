package br.com.santander.core.webdriver.utils.mobile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class UtilidadesDeDriver implements IUtilidadesDeDriver
{
	private WebDriver driver;
	private WebDriverWait wait;
	
	protected UtilidadesDeDriver( WebDriver driver, WebDriverWait wait )
	{
		this.driver = driver;
		this.wait = wait;
	}
	
	protected UtilidadesDeDriver()
	{
//		wait = new WebDriverWait( getWebDriver(), Tempo._60.qtde() ) ;
	}
	
	/**Retorna o WebDriver desta thread.*/
	public WebDriver getWebDriver()
	{
		return driver;
	}
	
	/**Retorna o WebDriverWait desta thread.*/
	public WebDriverWait getWait()
	{
		if(wait == null)
			wait = new WebDriverWait(getWebDriver(), Tempo._60.qtde());
		return wait;
	}
	
	public void setWebDriver(WebDriver driver) {
		this.driver = driver;
	}
}
