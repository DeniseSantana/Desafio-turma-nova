package br.com.santander.core.webdriver.utils.mobile;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

public interface IUtilidadesDePosicaoEDimensao extends IUtilidadesDeElemento
{
	/**<p>getDriver().manage().window().getSize()</p>
	 * 
	 * Retorna o tamanho da tela.*/
	default Dimension tamanhoDaTela()
	{
		return getWebDriver().manage().window().getSize();
	}
	
	/**Retorna um ponto na extremidade direita do elemento,
	 * à meia altura.*/
	default Point direitaDoElemento( By by )
	{
		return direitaDoElemento( buscaElemento( by ) );
	}
	
	/**Retorna um ponto na extremidade direita do elemento,
	 * à meia altura.*/
	default Point direitaDoElemento( WebElement element )
	{
		Rectangle rect = element.getRect();
		int x = rect.getX() + rect.getWidth() - 1;
		int y = rect.getY() + ( rect.getHeight() / 2 );
		return new Point(x, y);
	}
	
	/**Retorna um ponto na extremidade esquerda do elemento,
	 * à meia altura.*/
	default Point esquerdaDoElemento( By by )
	{
		return esquerdaDoElemento( buscaElemento( by ) );
	}
	
	/**Retorna um ponto na extremidade esquerda do elemento,
	 * à meia altura.*/
	default Point esquerdaDoElemento( WebElement element )
	{
		Rectangle rect = element.getRect();
		int x = rect.getX() + 1;
		int y = rect.getY() + ( rect.getHeight() / 2 );
		return new Point(x, y);
	}
	
	/**Retorna o centro do elemento.*/
	default Point centroDoElemento( By by )
	{
		return centroDoElemento( buscaElemento( by ) );
	}
	
	/**Retorna o centro do elemento.*/
	default Point centroDoElemento( WebElement element )
	{
		Rectangle rect = element.getRect();
		int x = rect.getX() + ( rect.getWidth() / 2 );
		int y = rect.getY() + ( rect.getHeight() / 2 );
		return new Point( x, y );
	}
	
	/**Retorna a posição do elemento.*/
	default Point posicaoDoElemento( By by )
	{
		return posicaoDoElemento( buscaElemento( by ) );
	}
	
	/**Retorna a posição do elemento.*/
	default Point posicaoDoElemento( WebElement element )
	{
		return retanguloDoElemento( element ).getPoint();
	}
	
	/**Retorna o tamanho do elemento.*/
	default Dimension tamanhoDoElemento( By by )
	{
		return tamanhoDoElemento( buscaElemento( by ) );
	}
	
	/**Retorna o tamanho do elemento.*/
	default Dimension tamanhoDoElemento( WebElement element )
	{
		return retanguloDoElemento( element ).getDimension();
	}
	
	/**Retorna um retângulo representando a posição
	 * e o tamanho do elemento.*/
	default Rectangle retanguloDoElemento( By by )
	{
		return retanguloDoElemento( buscaElemento( by ) );
	}
	
	/**Retorna um retângulo representando a posição
	 * e o tamanho do elemento.*/
	default Rectangle retanguloDoElemento( WebElement element )
	{
		return element.getRect();
	}
}
