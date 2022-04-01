package br.com.santander.core.webdriver.utils.mobile;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface IUtilidadesDeElemento extends IUtilidadesDeDriver
{
	/**Busca um elemento por localizador By.*/
	default WebElement buscaElemento( By by )
	{
		return getWebDriver().findElement( by );
	}
	
	/**Busca uma lista de elementos por localizador By.*/
	default List<WebElement> buscaElementos( By by )
	{
		return getWebDriver().findElements( by );
	}
	
	/**<p>element.getAttribute(atributo)</p>
	 * 
	 * Retorna o valor do atributo de um elemento, por exemplo,
	 * id, name, text, className, readOnly. (Atributos booleanos
	 * retornam "true" ou null)*/
	default String obtemAtributo(By by, String atributo)
	{
		return obtemAtributo( buscaElemento(by), atributo );
	}
	
	/**<p>element.getAttribute(atributo)</p>
	 * 
	 * Retorna o valor do atributo de um elemento, por exemplo,
	 * id, name, text, className, readOnly. (Atributos booleanos
	 * retornam "true" ou null)*/
	default String obtemAtributo(WebElement element, String atributo)
	{
		return element.getAttribute(atributo);
	}
}
