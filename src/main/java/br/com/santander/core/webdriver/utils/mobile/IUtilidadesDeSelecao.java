package br.com.santander.core.webdriver.utils.mobile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface IUtilidadesDeSelecao extends IUtilidadesDeElemento
{
	/**<p>element.isSelected()</p>
	 * 
	 * Retorna true se o elemento estiver ticado/marcado.*/
	default boolean estaTicado( By by )
	{
		return estaTicado( buscaElemento(by) );
	}
	
	/**<p>element.isSelected()</p>
	 * 
	 * Retorna true se o elemento estiver ticado/marcado.*/
	default boolean estaTicado( WebElement element )
	{
		return element.isSelected();
	}
}
