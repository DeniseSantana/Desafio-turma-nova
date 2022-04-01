package br.com.santander.core.webdriver.utils.mobile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public interface IUtilidadesDeClique extends IUtilidadesDeEspera
{
	/**<p>new Actions( getDriver() ).moveToElement( element )</p>
	 * 
	 * Move o cursor até o centro de um elemento.*/
	default void moveAoElemento( By by )
	{
		moveAoElemento( buscaElemento( by ) );
	}
	
	/**<p>new Actions( getDriver() ).moveToElement( element )</p>
	 * 
	 * Move o cursor até o centro de um elemento.*/
	default void moveAoElemento( WebElement element )
	{
		new Actions( getWebDriver() ).moveToElement( element )
			.click().build().perform();
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento, sem esperar que esteja clicável.*/
	default void clicaSimples(By by)
	{
		clicaSimples( buscaElemento(by) );
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento, sem esperar que esteja clicável.*/
	default void clicaSimples(WebElement element)
	{
		element.click();
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento.*/
	default void clica(By by)
	{
		clica( by, Tempo._60.qtde() );
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento.*/
	default void clica(By by, int segundosAteTimeout)
	{
		WebElement element = esperaAteEstarClicavel( by, segundosAteTimeout );
		clicaSimples( element );
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento.*/
	default void clica(WebElement element)
	{
		clica( element, Tempo._60.qtde() );
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento.*/
	default void clica(WebElement element, int segundosAteTimeout)
	{
		esperaAteEstarClicavel(element, segundosAteTimeout);
		clicaSimples(element);
	}
	
}








