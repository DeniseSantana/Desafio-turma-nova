package br.com.santander.core.webdriver.utils.mobile;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class UtilidadesDeMobile extends UtilidadesDePagina
{
	private static final Logger LOG = LogManager.getLogger(UtilidadesDeMobile.class);
	
	/**Configura implicitlyWait para 1 segundo.*/
	@Override
	public void baixaTimeouts()
	{
		getWebDriver().manage().timeouts().implicitlyWait( 1, TimeUnit.SECONDS );
	}

	/**Configura implicitlyWait para 10 segundos.*/
	@Override
	public void sobeTimeouts()
	{
		getWebDriver().manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
	}
	
	/**Esconde o teclado ao bater no canto superior esquerdo da tela.*/
	public void escondeTeclado()
	{
		touchActionBateSimples( 1, 1 );
	}
	
	/**<p>element.setValue(valor)</p>
	 * 
	 * Impõe um valor sobre um elemento.*/
	public void setValue( By by, String valor )
	{
		setValue( buscaElemento( by ), valor );
	}
	
	/**<p>element.setValue(valor)</p>
	 * 
	 * Impõe um valor sobre um elemento.*/
	public void setValue( WebElement element, String valor )
	{
		( ( MobileElement) element ).setValue( valor );
	}
	
	/**<p>io.appium.TouchAction.longPress()</p>
	 * 
	 * Clica e segura, isto é, aperta e não solta por
	 * um tempo, o centro de um elemento. O tempo
	 * que dura o aperto é igual ao tempo necessário para
	 * abrir o menu flutuante de contexto.*/
	public void touchActionCliqueLongo( By by )
	{
		touchActionCliqueLongo( by, Tempo._60.qtde() );
	}
	
	/**<p>io.appium.TouchAction.longPress()</p>
	 * 
	 * Clica e segura, isto é, aperta e não solta por
	 * um tempo, o centro de um elemento. O tempo
	 * que dura o aperto é igual ao tempo necessário para
	 * abrir o menu flutuante de contexto.*/
	public void touchActionCliqueLongo( By by, int segundosDeEspera )
	{
		touchActionCliqueLongo( buscaElemento( by ), segundosDeEspera );
	}
	
	/**<p>io.appium.TouchAction.longPress()</p>
	 * 
	 * Clica e segura, isto é, aperta e não solta por
	 * um tempo, o centro de um elemento. O tempo
	 * que dura o aperto é igual ao tempo necessário para
	 * abrir o menu flutuante de contexto.*/
	public void touchActionCliqueLongo( WebElement element )
	{
		touchActionCliqueLongo( element, Tempo._60.qtde() );
	}
	
	/**<p>io.appium.TouchAction.longPress()</p>
	 * 
	 * Clica e segura, isto é, aperta e não solta por
	 * um tempo, o centro de um elemento. O tempo
	 * que dura o aperto é igual ao tempo necessário para
	 * abrir o menu flutuante de contexto.*/
	public void touchActionCliqueLongo( WebElement element, int segundosDeEspera )
	{
		esperaAteEstarClicavel( element, segundosDeEspera );
		touchActionCliqueLongoSemEsperar( element );
	}
	
	/**<p>io.appium.TouchAction.longPress()</p>
	 * 
	 * Clica e segura, isto é, aperta e não solta por
	 * um tempo, o centro de um elemento, sem esperar
	 * que esteja clicável. O tempo que dura o aperto
	 * é igual ao tempo necessário para abrir o menu
	 * flutuante de contexto.*/
	public void touchActionCliqueLongoSemEsperar( By by )
	{
		touchActionCliqueLongoSemEsperar( buscaElemento( by ) );
	}
	
	/**<p>io.appium.TouchAction.longPress()</p>
	 * 
	 * Clica e segura, isto é, aperta e não solta por
	 * um tempo, o centro de um elemento, sem esperar
	 * que esteja clicável. O tempo que dura o aperto
	 * é igual ao tempo necessário para abrir o menu
	 * flutuante de contexto.*/
	public void touchActionCliqueLongoSemEsperar( WebElement element )
	{
		touchActionCliqueLongoSemEsperar( centroDoElemento( element ) );
	}
	
	/**<p>io.appium.TouchAction.longPress()</p>
	 * 
	 * Clica e segura, isto é, aperta e não solta por
	 * um tempo, uma posição absoluta na tela. O tempo
	 * que dura o aperto é igual ao tempo necessário para
	 * abrir o menu flutuante de contexto.*/
	public void touchActionCliqueLongoSemEsperar( Point point )
	{
		touchActionCliqueLongoSemEsperar( point.getX(), point.getY() );
	}
	
	/**<p>io.appium.TouchAction.longPress()</p>
	 * 
	 * Clica e segura, isto é, aperta e não solta por
	 * um tempo, uma posição absoluta na tela. O tempo
	 * que dura o aperto é igual ao tempo necessário para
	 * abrir o menu flutuante de contexto.*/
	@SuppressWarnings("rawtypes")
	public void touchActionCliqueLongoSemEsperar( int x, int y )
	{
		new TouchAction( (MobileDriver) getWebDriver() )
		.longPress( PointOption.point( x, y ) )
		.perform();
	}
	
	/**<p>io.appium.TouchAction.tap()</p>
	 * 
	 * Bate de leve no centro de um elemento.*/
	public void touchActionBate( By by )
	{
		touchActionBate( by, Tempo._60.qtde() );
	}
	
	/**<p>io.appium.TouchAction.tap()</p>
	 * 
	 * Bate de leve no centro de um elemento.*/
	public void touchActionBate( By by, int segundosAteTimeout )
	{
		touchActionBate( buscaElemento( by ), segundosAteTimeout );
	}
	
	/**<p>io.appium.TouchAction.tap()</p>
	 * 
	 * Bate de leve no centro de um elemento.*/
	public void touchActionBate( WebElement element )
	{
		touchActionBate( element, Tempo._60.qtde() );
	}
	
	/**<p>io.appium.TouchAction.tap()</p>
	 * 
	 * Bate de leve no centro de um elemento.*/
	public void touchActionBate( WebElement element, int segundosAteTimeout )
	{
		esperaAteEstarClicavel( element, segundosAteTimeout );
		touchActionBateSimples( centroDoElemento( element ) );
	}
	
	/**<p>io.appium.TouchAction.tap()</p>
	 * 
	 * Bate de leve no centro de um elemento,
	 * sem esperar estar clicável.*/
	public void touchActionBateSimples( By by )
	{
		touchActionBateSimples( buscaElemento( by ) );
	}
	
	/**<p>io.appium.TouchAction.tap()</p>
	 * 
	 * Bate de leve no centro de um elemento,
	 * sem esperar estar clicável.*/
	public void touchActionBateSimples( WebElement element )
	{
		touchActionBateSimples( centroDoElemento( element ) );
	}
	
	/**<p>io.appium.TouchAction.tap()</p>
	 * 
	 * Bate de leve em uma posição absoluta na tela.*/
	public void touchActionBateSimples( Point point )
	{
		touchActionBateSimples( point.getX(), point.getY() );
	}
	
	/**<p>io.appium.TouchAction.tap()</p>
	 * 
	 * Bate de leve em uma posição absoluta na tela.*/
	@SuppressWarnings("rawtypes")
	public void touchActionBateSimples( int x, int y )
	{
		new TouchAction( (MobileDriver) getWebDriver() )
			.tap( PointOption.point( x, y ) )
			.perform();

		espera( Tempo._300.qtde() );
	}
	
	/**<p>io.appium.TouchAction.press().release()</p>
	 * 
	 * Clique rápido em um ponto relativo da tela, usando o canto
	 * superior esquerdo do elemento como origem (0, 0), com um
	 * deslocamento de (x, y).*/
	public void touchActionClicaAPartirDoElemento( By by, int x, int y )
	{
		touchActionClicaAPartirDoElemento( buscaElemento( by ), x, y );
	}
	
	/**<p>io.appium.TouchAction.press().release()</p>
	 * 
	 * Clique rápido em um ponto relativo da tela, usando o canto
	 * superior esquerdo do elemento como origem (0, 0), com um
	 * deslocamento de (x, y).*/
	public void touchActionClicaAPartirDoElemento( WebElement element, int x, int y )
	{
		Point origem = element.getLocation();
		touchActionClica( origem.getX() + x, origem.getY() + y );
	}
	
	/**<p>io.appium.TouchAction.press().release()</p>
	 * 
	 * Clique rápido no centro de um elemento.*/
	public void touchActionClicaCentroDoElemento(By by)
	{
		touchActionClicaCentroDoElemento( buscaElemento( by ) );
	}
	
	/**<p>io.appium.TouchAction.press().release()</p>
	 * 
	 * Clique rápido no centro de um elemento.*/
	public void touchActionClicaCentroDoElemento( WebElement element )
	{
		Point centroDoElemento = centroDoElemento( element );
		touchActionClica( centroDoElemento.getX(), centroDoElemento.getY() );
	}
	
	/**<p>io.appium.TouchAction.press().release()</p>
	 * 
	 * Clique rápido em uma posição absoluta na tela.*/
	@SuppressWarnings("rawtypes")
	public void touchActionClica( int x, int y )
	{
		Dimension dimensao = tamanhoDaTela();
		if ( ( x > 0 && x < dimensao.getWidth() ) && ( y > 0 && y < dimensao.getHeight() ) )
		{
			TouchAction acao = new TouchAction( (MobileDriver) getWebDriver() );
			acao.press( PointOption.point(x, y) ).release().perform();
		}
		else
		{
			LOG.error(">>> Não foi possivel clicar, pois os valores de X e Y excedem a tela ...");
		}
	}
	
	/**<p>io.appium.TouchAction.press(x1, y1).moveTo(x2, y2).release()</p>
	 * 
	 * Desliza a tela de um ponto (x1, y1) até um outro ponto (x2, y2).*/
	public void touchActionDesliza( Point p1, Point p2 )
	{
		touchActionDesliza( p1.getX(), p1.getY(), p2.getX(), p2.getY() );
	}
	
	/**<p>io.appium.TouchAction.press(x1, y1).moveTo(x2, y2).release()</p>
	 * 
	 * Desliza a tela de um ponto (x1, y1) até um outro ponto (x2, y2).*/
	public void touchActionDesliza(int x1, int y1, int x2, int y2)
	{
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction( ( MobileDriver ) getWebDriver() );
		
		action.press( PointOption.point( x1, y1 ) )
		.waitAction( WaitOptions.waitOptions( Duration.ofMillis( 3_000 ) ) )
		.moveTo( PointOption.point( x2, y2 ) )
		.release()
		.perform();
	}
	
	/**<p>io.appium.TouchAction.press(x1, y1).moveTo(x2, y2).release()</p>
	 * 
	 * Desliza a tela de um ponto (x1, y1) até um outro ponto (x2, y2),
	 * de forma mais lenta.*/
	public void touchActionDeslizaDevagar( Point p1, Point p2 )
	{
		touchActionDeslizaDevagar( p1.getX(), p1.getY(), p2.getX(), p2.getY() );
	}
	
	/**<p>io.appium.TouchAction.press(x1, y1).moveTo(x2, y2).release()</p>
	 * 
	 * Desliza a tela de um ponto (x1, y1) até um outro ponto (x2, y2),
	 * de forma mais lenta.*/
	@SuppressWarnings("rawtypes")
	public void touchActionDeslizaDevagar(int x1, int y1, int x2, int y2)
	{
		TouchAction action = new TouchAction( ( MobileDriver ) getWebDriver() );

		action.longPress( PointOption.point( x1, y1 ) )
		.waitAction( WaitOptions.waitOptions( Duration.ofMillis( 3_000 ) ) )
		.moveTo( PointOption.point( x2, y2 ) )
		.waitAction( WaitOptions.waitOptions( Duration.ofMillis( 3_000 ) ) )
		.release()
		.perform();

		espera( Tempo._300.qtde() );
	}
	
	/**<p>JavascriptExecutor.executeScript("mobile: scroll", "direction", "down")</p>
	 * 
	 * Desliza a tela para baixo até que o elemento esteja visível.*/
	public void executaJsDeslizarParaBaixoProcurando( By by )
	{
		executaJsDeslizarParaBaixoProcurando( by, 5 );
	}
	
	/**<p>JavascriptExecutor.executeScript("mobile: scroll", "direction", "down")</p>
	 * 
	 * Desliza a tela para baixo até que o elemento esteja visível.*/
	public void executaJsDeslizarParaBaixoProcurando( By by, int tentativas )
	{
		int i = 0;
		while ( !estaVisivel( by, 3_000 ) && i < tentativas )
		{
			executaJsDeslizarParaBaixo();
			i++;
		}
	}
	
	/**<p>JavascriptExecutor.executeScript("mobile: scroll", "direction", "down")</p>
	 * 
	 * Desliza a tela para baixo.*/
	public void executaJsDeslizarParaBaixo()
	{
		executaJS( "mobile: scroll",
				new HashMap<String, String>().put( "direction", "down" ) );
	}
	
	/**<p>JavascriptExecutor.executeScript("mobile: scroll", "direction", "up")</p>
	 * 
	 * Desliza a tela para cima.*/
	public void executaJsDeslizarParaCima()
	{
		executaJS( "mobile: scroll",
				new HashMap<String, String>().put( "direction", "up" ) );
	}
	
	/**<p>JavascriptExecutor.executeScript("mobile: scroll", "direction", "right")</p>
	 * 
	 * Desliza a tela para a direita.*/
	public void executaJsDeslizarParaDireita()
	{
		executaJS( "mobile: scroll",
				new HashMap<String, String>().put( "direction", "right" ) );
	}
	
	/**<p>JavascriptExecutor.executeScript("mobile: scroll", "direction", "left")</p>
	 * 
	 * Desliza a tela para a esquerda.*/
	public void executaJsDeslizarParaEsquerda()
	{
		executaJS( "mobile: scroll",
				new HashMap<String, String>().put( "direction", "left" ) );
	}
	
	/**<p>JavascriptExecutor.executeScript("scrollIntoView", element)</p>
	 * 
	 * Desliza a tela até que um elemento esteja visível.*/
	public void jsScrollToElement( By by )
	{
		executaJS( "arguments[0].scrollIntoView(true);", buscaElemento( by ) );
	}
	
	/**<p>JavascriptExecutor.executeScript("scrollIntoView", element)</p>
	 * 
	 * Desliza a tela até que um elemento esteja visível.*/
	public void jsScrollToElement( WebElement element )
	{
		executaJS( "arguments[0].scrollIntoView(true);", element );
	}
	
	/**<p>selenium.JavascriptExecutor.executeScript(script, args)</p>
	 * 
	 * Executa um script com uma quantidade qualquer de argumentos.*/
	public Object executaJS( String script, Object... args )
	{
		return ( (JavascriptExecutor) getWebDriver() ).executeScript( script, args );
	}
	@Override
	public boolean esperaAteElementoSumir(By by)
	{
		return esperaAteElementoSumir( by, Tempo._60.qtde() );
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public boolean esperaAteElementoSumir( By by, int segundosAteTimeout )
	{
		MobileElement element = null;
		int contadorSegundos = 0;
		try {
			while ( contadorSegundos < segundosAteTimeout )
			{
				element = (MobileElement) ( (MobileDriver) getWebDriver() ).findElement( by );
				if ( element == null )
				{
					return true;
				}
				else
				{
					if ( !element.isDisplayed()
							|| ( element.getSize().getWidth() == 0
								&& element.getSize().getHeight() == 0 ) )
					{
						return true;
					}
				}
				this.espera( Tempo._1.vezesMil() );
				contadorSegundos++;
			}
		}
		catch ( WebDriverException e )
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean esperaAteElementoSumir( WebElement element )
	{
		return esperaAteElementoSumir( element, Tempo._60.qtde() );
	}
	
	@Override
	public boolean esperaAteElementoSumir( WebElement element, int segundosAteTimeout )
	{
		int contadorSegundos = 0;
		try {
			while ( contadorSegundos < segundosAteTimeout )
			{
				element = (MobileElement) element;
				if ( element == null )
				{
					return true;
				}
				else
				{
					if ( !element.isDisplayed()
							|| ( element.getSize().getWidth() == 0
								&& element.getSize().getHeight() == 0 ) )
					{
						return true;
					}
				}
				this.espera( Tempo._1.vezesMil() );
				contadorSegundos++;
			}
		}
		catch (WebDriverException e)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Finaliza o driver atual, remove a reserva e inicia um novo driver
	 */
	/*public void reiniciarDriver() {
		LOG.info("reiniciando o driver do appium");
		try {
			getDriver().quit();
		} catch (Exception e) {
			LOG.error("Erro ao finalizar driver");
		}
		try {
			LocalDriverManager.setDriver(null);
			new MobileCenterClient().removerTodosOsDispositivosReservadosPorUsuario();
			WebDriver driver = new DriverFactory().createDriver(LocalTestContextManager.getDispositivo());
			LocalDriverManager.setDriver(driver);
		} catch (MalformedURLException e) {
			LOG.error("Erro ao tentar criar um novo driver");
			e.printStackTrace();
		}
	}*/
}
