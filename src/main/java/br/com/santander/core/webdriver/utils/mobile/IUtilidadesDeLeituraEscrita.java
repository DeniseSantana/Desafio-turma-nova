package br.com.santander.core.webdriver.utils.mobile;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public interface IUtilidadesDeLeituraEscrita extends IUtilidadesDeEspera
{
	/**Pressiona a tecla <TAB> em um elemento.*/
	default void teclarTab(By by)
	{
		teclarTab( buscaElemento(by) );
	}
	
	/**Pressiona a tecla <TAB> em um elemento.*/
	default void teclarTab(WebElement element)
	{
		element.sendKeys(Keys.TAB);
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento,
	 * após esperar por sua visibilidade.*/
	default String le(By by)
	{
		return le( by, Tempo._60.qtde() );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento,
	 * após esperar por sua visibilidade.*/
	default String le(By by, int segundosAteTimeout)
	{
		WebElement element = esperaVisibilidadeDe( by, segundosAteTimeout );
		return leSimples( element );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento,
	 * após esperar por sua visibilidade.*/
	default String le(WebElement element)
	{
		return le( element, Tempo._60.qtde() );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento,
	 * após esperar por sua visibilidade.*/
	default String le(WebElement element, int segundosAteTimeout)
	{
		esperaVisibilidadeDe( element, segundosAteTimeout );
		return leSimples( element );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento.*/
	default String leSimples(By by)
	{
		return leSimples( buscaElemento(by) );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento.*/
	default String leSimples(WebElement element)
	{
		return element.getText();
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento.*/
	default void limpaSimples(By by)
	{
		limpaSimples( buscaElemento(by) );
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento.*/
	default void limpaSimples(WebElement element)
	{
		element.clear();
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento,
	 * após esperar que esteja clicável.*/
	default void limpa(By by)
	{
		limpa( by, Tempo._60.qtde() );
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento,
	 * após esperar que esteja clicável.*/
	default void limpa(By by, int segundosAteTimeout)
	{
		WebElement element = esperaAteEstarClicavel(by, segundosAteTimeout);
		limpaSimples(element);
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento,
	 * após esperar que esteja clicável.*/
	default void limpa(WebElement element)
	{
		limpa( element, Tempo._60.qtde() );
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento,
	 * após esperar que esteja clicável.*/
	default void limpa(WebElement element, int segundosAteTimeout)
	{
		esperaAteEstarClicavel(element, segundosAteTimeout);
		limpaSimples(element);
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento.*/
	default void escreveSimples(By by, String texto)
	{
		escreveSimples( buscaElemento(by), texto );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento.*/
	default void escreveSimples(WebElement element, String texto)
	{
		element.sendKeys(texto);
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * após esperar estar clicável.*/
	default void escreve(By by, String texto)
	{
		escreve( by, texto, Tempo._60.qtde() );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * após esperar estar clicável.*/
	default void escreve(By by, String texto, int segundosAteTimeout)
	{
		WebElement element = esperaAteEstarClicavel( by, segundosAteTimeout );
		limpaSimples(element);
		escreveSimples(element, texto);
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * após esperar estar clicável.*/
	default void escreve(WebElement element, String texto)
	{
		escreve( element, texto, Tempo._60.qtde() );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * após esperar estar clicável.*/
	default void escreve(WebElement element, String texto, int segundosAteTimeout)
	{
		esperaAteEstarClicavel(element, segundosAteTimeout);
		limpaSimples(element);
		escreveSimples(element, texto);
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * sem antes limpá-lo, após esperar estar clicável.*/
	default void escreveSemLimpar(By by, String texto)
	{
		escreveSemLimpar( by, texto, Tempo._60.qtde() );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * sem antes limpá-lo, após esperar estar clicável.*/
	default void escreveSemLimpar(By by, String texto, int segundosAteTimeout)
	{
		WebElement element = esperaAteEstarClicavel( by, segundosAteTimeout );
		escreveSimples( element, texto );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento, sem antes limpá-lo,
	 * após esperar estar clicável.*/
	default void escreveSemLimpar(WebElement element, String texto)
	{
		escreveSemLimpar( element, texto, Tempo._60.qtde() );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento, sem antes limpá-lo,
	 * após esperar estar clicável.*/
	default void escreveSemLimpar(WebElement element, String texto, int segundosAteTimeout)
	{
		esperaAteEstarClicavel( element, segundosAteTimeout );
		escreveSimples( element, texto );
	}
}
