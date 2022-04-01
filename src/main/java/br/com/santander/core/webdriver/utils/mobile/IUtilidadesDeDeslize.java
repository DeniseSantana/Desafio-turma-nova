package br.com.santander.core.webdriver.utils.mobile;

import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface IUtilidadesDeDeslize extends IUtilidadesDeEspera, IUtilidadesDePosicaoEDimensao
{
	/**Desliza para cima, traçando uma linha pelo centro da tela,
	 * por uma distância de 30% da altura da tela, por 5 vezes,
	 * até encontrar o elemento.*/
	default WebElement appiumDeslizaAcimaEProcura(By by)
	{
		return appiumDeslizaAcimaEProcura(by, 0.5, 5);
	}
	
	/**Desliza para cima, traçando uma linha a uma distância de XX%
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, por 5 vezes, até encontrar o elemento.*/
	default WebElement appiumDeslizaAcimaEProcura(By by, double porcentagemDaLarguraDaTela)
	{
		return appiumDeslizaAcimaEProcura( by, porcentagemDaLarguraDaTela, 5 );
	}
	
	/**Desliza para cima, traçando uma linha pelo centro da tela,
	 * por uma distância de 30% da altura da tela, um certo número
	 * de vezes, até encontrar o elemento.*/
	default WebElement appiumDeslizaAcimaEProcura(By by, int tentativas)
	{
		return appiumDeslizaAcimaEProcura( by, 0.5, tentativas );
	}

	/**Desliza para cima, traçando uma linha a uma distância de XX%
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, um certo número de vezes, até encontrar o elemento.*/
	default WebElement appiumDeslizaAcimaEProcura(
			By by, double porcentagemDaLarguraDaTela, int tentativas)
	{
		WebElement element = null;
		int contador = 0;
		Double screenWidthDistance = tamanhoDaTela().getWidth() * porcentagemDaLarguraDaTela;
		int widthDistance = screenWidthDistance.intValue();
		
		do {
			try {
				element = buscaElemento(by);
			}
			catch (WebDriverException e) {}

			if (element == null)
			{
				appiumDeslizaAcima( widthDistance, 1 );
			}
			contador++;
		}
		while (element == null && contador < tentativas);
		
		return element;
	}
	
	/**Desliza para cima, traçando uma linha pelo centro
	 * da tela, por uma distância de 30% da altura da tela.*/
	default void appiumDeslizaAcima()
	{
		Double screenWidthMiddle = tamanhoDaTela().getWidth() * 0.5;
		int widthMiddle = screenWidthMiddle.intValue();
		appiumDeslizaAcima( widthMiddle, 1 );
	}
	
	/**Desliza para cima, traçando uma linha à uma certa distância
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela.*/
	default void appiumDeslizaAcima( int distanciaDaBordaLateralEsquerda )
	{
		appiumDeslizaAcima( distanciaDaBordaLateralEsquerda, 1 );
	}
	
	/**Desliza para cima, traçando uma linha à uma certa distância
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, um certo número de vezes.*/
	default void appiumDeslizaAcima( int distanciaDaBordaLateralEsquerda, int qtasVezes )
	{
		Dimension dimension = tamanhoDaTela();
		Double screenHeightStart = dimension.getHeight() * 0.2;
		int scrollStart = screenHeightStart.intValue();
		Double screenHeightEnd = dimension.getHeight() * 0.5;
		int scrollEnd = screenHeightEnd.intValue();

		if ( qtasVezes <= 0 )
		{
			qtasVezes = 1;
		}
		
		for ( int i = 0; i < qtasVezes; i++ )
		{
			appiumDesliza(distanciaDaBordaLateralEsquerda, scrollStart,
					distanciaDaBordaLateralEsquerda, scrollEnd);
		}
	}
	
	/**Desliza para baixo, traçando uma linha pelo centro da tela,
	 * por uma distância de 30% da altura da tela, por 5 vezes,
	 * até encontrar o elemento, então espera até que ele
	 * esteja clicável.*/
	default WebElement appiumDeslizaAbaixoEProcuraEEsperaEstarClicavel( By by )
	{
		return appiumDeslizaAbaixoEProcuraEEsperaEstarClicavel( by, 0.5, 5, Tempo._60.qtde() );
	}
	
	/**Desliza para baixo, traçando uma linha pelo centro da tela,
	 * por uma distância de 30% da altura da tela, um certo número
	 * de vezes, até encontrar o elemento, então espera até que ele
	 * esteja clicável.*/
	default WebElement appiumDeslizaAbaixoEProcuraEEsperaEstarClicavel( By by, int tentativas )
	{
		return appiumDeslizaAbaixoEProcuraEEsperaEstarClicavel( by, 0.5, tentativas, Tempo._60.qtde() );
	}
	
	/**Desliza para baixo, traçando uma linha a uma distância de XX%
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, um certo número de vezes, até encontrar o elemento,
	 * então espera até que ele esteja clicável por um tempo em segundos.*/
	default WebElement appiumDeslizaAbaixoEProcuraEEsperaEstarClicavel(
			By by,
			double porcentagemDaLarguraDaTela,
			int tentativas,
			int segundosDeEspera )
	{
		WebElement element = appiumDeslizaAbaixoEProcura( by, porcentagemDaLarguraDaTela, tentativas );
		if ( element != null )
		{
			return esperaAteEstarClicavel( element, segundosDeEspera );
		}
		else
		{
			return element;
		}
	}
	
	/**Desliza para baixo, traçando uma linha pelo centro da tela,
	 * por uma distância de 30% da altura da tela, por 5 vezes,
	 * até encontrar o elemento.*/
	default WebElement appiumDeslizaAbaixoEProcura(By by)
	{
		return appiumDeslizaAbaixoEProcura(by, 0.5, 5);
	}
	
	/**Desliza para baixo, traçando uma linha a uma distância de XX%
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, por 5 vezes, até encontrar o elemento.*/
	default WebElement appiumDeslizaAbaixoEProcura(By by, double porcentagemDaLarguraDaTela)
	{
		return appiumDeslizaAbaixoEProcura( by, porcentagemDaLarguraDaTela, 5 );
	}
	
	/**Desliza para baixo, traçando uma linha pelo centro da tela,
	 * por uma distância de 30% da altura da tela, um certo número
	 * de vezes, até encontrar o elemento.*/
	default WebElement appiumDeslizaAbaixoEProcura(By by, int tentativas)
	{
		return appiumDeslizaAbaixoEProcura( by, 0.5, tentativas );
	}
	
	/**Desliza para baixo, traçando uma linha a uma distância de XX%
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, um certo número de vezes, até encontrar o elemento.*/
	default WebElement appiumDeslizaAbaixoEProcura(
			By by, double porcentagemDaLarguraDaTela, int tentativas)
	{
		WebElement element = null;
		int contador = 0;
		Double screenWidthDistance = tamanhoDaTela().getWidth() * porcentagemDaLarguraDaTela;
		int widthDistance = screenWidthDistance.intValue();
		
		do {
			try {
				element = buscaElemento(by);
			}
			catch (WebDriverException e) {}

			if (element == null)
			{
				appiumDeslizaAbaixo( widthDistance, 1 );
			}
			contador++;
		}
		while (element == null && contador < tentativas);
		
		return element;
	}
	
	/**Desliza para baixo, traçando uma linha a uma distância de XX%
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, um certo número de vezes, até encontrar o elemento.*/
	default WebElement appiumDeslizaEProcura( int x1, int y1, int x2, int y2, By by, int tentativas )
	{
		WebElement element = null;
		int contador = 0;
		
		do {
			try {
				element = buscaElemento(by);
			}
			catch (WebDriverException e) {}

			if (element == null)
			{
				appiumDesliza(x1, y1, x2, y2);
			}
			contador++;
		}
		while (element == null && contador < tentativas);
		
		return element;
	}
	
	/**Desliza para baixo pela parte inferior da tela, traçando uma linha a uma distância de XX%
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, um certo número de vezes, até encontrar o elemento.*/
	default WebElement appiumDeslizaAbaixoEProcuraInferior(
			By by, double porcentagemDaLarguraDaTela, int tentativas)
	{
		WebElement element = null;
		int contador = 0;
		Double screenWidthDistance = tamanhoDaTela().getWidth() * porcentagemDaLarguraDaTela;
		int widthDistance = screenWidthDistance.intValue();
		
		do {
			try {
				element = buscaElemento(by);
			}
			catch (WebDriverException e) {}

			if (element == null)
			{
				appiumDeslizaAbaixoInferior( widthDistance, 1 );
			}
			contador++;
		}
		while (element == null && contador < tentativas);
		
		return element;
	}
	
	/**Desliza para baixo pela parte inferior da tela, traçando uma linha a uma certa distância
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, um certo número de vezes.*/
	default void appiumDeslizaAbaixoInferior( int distanciaDaBordaLateralEsquerda, int qtasVezes )
	{
		Dimension dimension = tamanhoDaTela();
		Double screenHeightStart = dimension.getHeight() * 0.8;
		int scrollStart = screenHeightStart.intValue();
		Double screenHeightEnd = dimension.getHeight() * 0.5;
		int scrollEnd = screenHeightEnd.intValue();

		if ( qtasVezes <= 0 )
		{
			qtasVezes = 1;
		}
		
		for ( int i = 0; i < qtasVezes; i++ )
		{
			appiumDesliza(distanciaDaBordaLateralEsquerda, scrollStart,
					distanciaDaBordaLateralEsquerda, scrollEnd);
		}
	}
	
	/**Desliza para baixo, traçando uma linha pelo centro
	 * da tela, por uma distância de 30% da altura da tela.*/
	default void appiumDeslizaAbaixo()
	{
		Double screenWidthMiddle = tamanhoDaTela().getWidth() * 0.5;
		int widthMiddle = screenWidthMiddle.intValue();
		appiumDeslizaAbaixo( widthMiddle, 1 );
	}
	
	/**Desliza para baixo, traçando uma linha a uma certa distância
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela.*/
	default void appiumDeslizaAbaixo( int distanciaDaBordaLateralEsquerda )
	{
		appiumDeslizaAbaixo( distanciaDaBordaLateralEsquerda, 1 );
	}
	
	/**Desliza para baixo, traçando uma linha a uma certa distância
	 * da lateral esquerda da tela, por uma distância de 30% da altura
	 * da tela, um certo número de vezes.*/
	default void appiumDeslizaAbaixo( int distanciaDaBordaLateralEsquerda, int qtasVezes )
	{
		Dimension dimension = tamanhoDaTela();
		Double screenHeightStart = dimension.getHeight() * 0.5;
		int scrollStart = screenHeightStart.intValue();
		Double screenHeightEnd = dimension.getHeight() * 0.2;
		int scrollEnd = screenHeightEnd.intValue();

		if ( qtasVezes <= 0 )
		{
			qtasVezes = 1;
		}
		
		for ( int i = 0; i < qtasVezes; i++ )
		{
			appiumDesliza(distanciaDaBordaLateralEsquerda, scrollStart,
					distanciaDaBordaLateralEsquerda, scrollEnd);
		}
	}
	
	/**Desliza de um ponto absoluto da tela para um outro.*/
	default void appiumDesliza( Point p1, Point p2 )
	{
		appiumDesliza( p1.getX(), p1.getY(), p2.getX(), p2.getY() );
	}
	
	/**Desliza de um ponto absoluto da tela para um outro.*/
	default void appiumDesliza( int x1, int y1, int x2, int y2 )
	{
		pointerInputDesliza( x1, y1, x2, y2 );
	}
	
	/**<p>selenium.RemoteWebDriver.perform(PointerInput(Kind.TOUCH, "finger"))</p>
	 * 
	 * Usa a interface de ações de toque do W3C para deslizar a tela
	 * de um ponto ao outro.*/
	default void pointerInputDesliza( Point p1, Point p2 )
	{
		pointerInputDesliza( p1.getX(), p1.getY(), p2.getX(), p2.getY() );
	}
	
	/**<p>selenium.RemoteWebDriver.perform(PointerInput(Kind.TOUCH, "finger"))</p>
	 * 
	 * Usa a interface de ações de toque do W3C para deslizar a tela
	 * de um ponto ao outro.*/
	default void pointerInputDesliza( int x1, int y1, int x2, int y2 )
	{
		Origin VIEW = Origin.viewport();
		PointerInput finger = new PointerInput( Kind.TOUCH, "finger" );
		Sequence sequence = new Sequence( finger, 0 );
		sequence.addAction( finger.createPointerMove( Duration.ofMillis(133), VIEW, x1, y1 ) );
		sequence.addAction( finger.createPointerDown( MouseButton.LEFT.asArg() ) );
		sequence.addAction( finger.createPointerMove( Duration.ofMillis(133), VIEW, x2, y2 ) );
		sequence.addAction( finger.createPointerUp( MouseButton.LEFT.asArg() ) );
		( (RemoteWebDriver) getWebDriver() ).perform( Arrays.asList( sequence ) );
	}
}
