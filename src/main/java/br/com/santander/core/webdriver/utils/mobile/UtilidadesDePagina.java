package br.com.santander.core.webdriver.utils.mobile;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.clipboard.HasClipboard;
import io.appium.java_client.touch.offset.PointOption;

/** Contém diversos métodos para interagir com elementos do Selenium. */
public class UtilidadesDePagina extends UtilidadesDeDriver
		implements IUtilidadesDeEspera, IUtilidadesDeTituloDePagina, IUtilidadesDePosicaoEDimensao,
		IUtilidadesDeSelecao, IUtilidadesDeLeituraEscrita, IUtilidadesDeClique, IUtilidadesDeDeslize {
	private static final Logger LOG = LogManager.getLogger(UtilidadesDePagina.class);
	/**
	 * Configura pageLoadTimeout para 10 segundos, e implicitlyWait para 1 segundo.
	 */
	public void baixaTimeouts() {
		getWebDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	/**
	 * Configura pageLoadTimeout para 60 segundos, e implicitlyWait para 10
	 * segundos.
	 */
	public void sobeTimeouts() {
		getWebDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * Devolve na saída padrão do sistema o que entra por uma InputStream qualquer.
	 */
	protected void showOutput(InputStream is) {
		String line = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			while ((line = br.readLine()) != null)
				System.out.println(line);
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	/**
	 * <p>
	 * getDriver().getPageSource()
	 * </p>
	 * 
	 * Retorna a fonte da página atual.
	 */
	public String obtemCodigoFonteDaPagina() {
		return getWebDriver().getPageSource();
	}

	/**
	 * <p>
	 * getDriver().getPageSource()
	 * </p>
	 * 
	 * Tenta retornar a fonte da página atual um número de vezes.
	 */
	public String obtemCodigoFonteDaPagina(int tentativas) {

		int limite = tentativas;
		while (limite > 0) {
			try {
				System.out.println(">>> Tentativa Nº " + ((tentativas - limite) + 1) + " de obter o codigo fonte.");
				return getWebDriver().getPageSource();
			} catch (WebDriverException e) {
				LOG.info(">>> Houve um erro na comunicacao, tentando novamente...");
				espera(1_000);
			}
			limite--;
		}
		return null;
	}

	/**
	 * <p>
	 * getDriver().getCurrentUrl()
	 * </p>
	 * 
	 * Retorna a URL da página atual.
	 */
	public String leAtualUrl() {
		return getWebDriver().getCurrentUrl();
	}

	// ********************* Clipboard ************************

	/**
	 * <p>
	 * java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
	 * </p>
	 * 
	 * Retorna o conteúdo da área de transferência na forma de texto.
	 */
	public String obtemTextoDoClipboard() {
		String texto = null;
		try {
			texto = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			LOG.error(e);
		}
		return texto;
	}

	public void setAreaDeTransferencia(String text) {
		try {
			((HasClipboard) getWebDriver()).setClipboardText(text);
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	public String getAreaDeTransferencia() {
		try {
			return ((HasClipboard) getWebDriver()).getClipboardText();
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}
	
	// INTEGRAGINDO ATRAVEZ DA PAGE SOURCE
	public class LimitesElementoPageSource {
		private int x1;
		private int x2;
		private int y1;
		private int y2;
		
		public int getX1() {
			return x1;
		}
		public void setX1(int x1) {
			this.x1 = x1;
		}
		public int getX2() {
			return x2;
		}
		public void setX2(int x2) {
			this.x2 = x2;
		}
		public int getY1() {
			return y1;
		}
		public void setY1(int y1) {
			this.y1 = y1;
		}
		public int getY2() {
			return y2;
		}
		public void setY2(int y2) {
			this.y2 = y2;
		}			
	}
	
	/**
	 * Retorna uma lista de elementos (NodeList) com base na expressao xpath usada para buscar, conforme o XML da page source
	 * @param expressaoXpath
	 * @return NodeList
	 */
	public NodeList retornarListaElementosBuscaXpathPageSource(String expressaoXpath) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(obtemCodigoFonteDaPagina())));

			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(expressaoXpath);

			NodeList listaElementos = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			return listaElementos;
		} catch (Exception e) {
			LOG.error("Nao foi possivel encontrar o elemento com expressao: " + expressaoXpath);
			return null;
		}
	}
	
	/** Retorna o primeiro elemento com base na expressao xpath usada para buscar, conforme o XML da page source
	 * @param expressaoXpath
	 * @return Element
	 */
	public Element retornarElementoBuscaXpathPageSource(String expressaoXpath) {
		return (Element) retornarListaElementosBuscaXpathPageSource(expressaoXpath).item(0);
	}
	
	/**Retorna boolean se o elemento for encontrado com base na expressao xpath usada para buscar, conforme o XML da page source
	 * @param expressaoXpath
	 * @return boolean
	 */
	public boolean elementoPresenteNoPageSource(String expressaoXpath) {
		return retornarListaElementosBuscaXpathPageSource(expressaoXpath).getLength() > 0;
	}
	
	/**Retorna boolean se o elemento for encontrado com base no By em xpath usado para buscar, conforme o XML da page source
	 * @param elementoByEmXpath
	 * @return boolean
	 */
	public boolean elementoPresenteNoPageSource(By elementoByEmXpath) {
		return retornarListaElementosBuscaXpathPageSource(converterXpathByEmString(elementoByEmXpath)).getLength() > 0;
	}
	
	/** Desliza a pagina ate uma quantidade especifica de tentativas para baixo ate encontrar o elemento, conforme o XML da page source
	 * @param elementoByEmXpath
	 * @param tentativas
	 * @return boolean
	 */
	public boolean appiumDeslizaAbaixoEProcuraNaPageSource(By elementoByEmXpath,int tentativas) {		
		while(tentativas > 0) {
			if(elementoPresenteNoPageSource(converterXpathByEmString(elementoByEmXpath))) {
				return true;
			}else {
				appiumDeslizaAbaixo();
			}
			tentativas--;
		}
		return false;
	}
	
	/** Desliza a pagina ate uma quantidade especifica de 5 tentativas para baixo ate encontrar o elemento, conforme o XML da page source
	 * @param elementoByEmXpath
	 * @param tentativas
	 * @return boolean
	 */
	public boolean appiumDeslizaAbaixoEProcuraNaPageSource(By elementoByEmXpath) {		
		return appiumDeslizaAbaixoEProcuraNaPageSource(elementoByEmXpath, 5);
	}
	
	/** Desliza a pagina ate uma quantidade especifica para cima de tantativas ate encontrar o elemento, conforme o XML da page source
	 * @param elementoByEmXpath
	 * @param tentativas
	 * @return boolean
	 */
	public boolean appiumDeslizaAcimaEProcuraNaPageSource(By elementoByEmXpath,int tentativas) {		
		while(tentativas > 0) {
			if(elementoPresenteNoPageSource(converterXpathByEmString(elementoByEmXpath))) {
				return true;
			}else {
				appiumDeslizaAcima();
			}
			tentativas--;
		}
		return false;
	}
	
	/** Desliza a pagina ate uma quantidade especifica de 5 tentativas para cima ate encontrar o elemento, conforme o XML da page source
	 * @param elementoByEmXpath
	 * @param tentativas
	 * @return boolean
	 */
	public boolean appiumDeslizaAcimaEProcuraNaPageSource(By elementoByEmXpath) {		
		return appiumDeslizaAbaixoEProcuraNaPageSource(elementoByEmXpath, 5);
	}
	
	/**Retorna boolean se o elemento for encontrado no intervalo de tempo espcifico com base na expressao xpath usada para buscar, conforme o XML da page source
	 * @param expressaoXpath
	 * @param tempo segundos
	 * @return boolean
	 */
	public boolean esperarElementoPresenteNoPageSource(String expressaoXpath, int tempo) {
		tempo = tempo / 1000;
		while(tempo > 0) {
			if(elementoPresenteNoPageSource(expressaoXpath)) {
				return true;
			}else {
				espera(500); // considerando o tempo de 500 segundos para retorno da page-source
			}
			tempo--;
		}
		return false;
	}
	
	/**Retorna boolean se o elemento for encontrado no intervalo de tempo de aprox. 60 segundos com base na expressao xpath usada para buscar, conforme o XML da page source
	 * @param expressaoXpath
	 * @param tempo
	 * @return boolean
	 */
	public boolean esperarElementoPresenteNoPageSource(String expressaoXpath) {
		return esperarElementoPresenteNoPageSource( expressaoXpath, Tempo._60.vezesMil());
	}
	
	/**Retorna boolean se o elemento for encontrado no intervalo de tempo de aprox. 60 segundos com base no By em xpath usado para buscar, conforme o XML da page source
	 * @param expressaoXpath
	 * @param tempo
	 * @return boolean
	 */
	public boolean esperarElementoPresenteNoPageSource(By elementoByEmXpath) {
		return esperarElementoPresenteNoPageSource(converterXpathByEmString(elementoByEmXpath), Tempo._60.vezesMil());
	}
	
	/**Retorna boolean se o elemento for encontrado no intervalo de tempo espcifico com base no By em xpath usado para buscar, conforme o XML da page source
	 * @param expressaoXpath
	 * @param tempo
	 * @return boolean
	 */
	public boolean esperarElementoPresenteNoPageSource(By elementoByEmXpath, int tempo) {
		return esperarElementoPresenteNoPageSource(converterXpathByEmString(elementoByEmXpath), tempo);
	}
		
	/** Clica no elemento por coordenadas com base na expressao xpath usada para buscar, conforme o XML da page source 
	 * @param elementoByEmXpath
	 */
	@SuppressWarnings("rawtypes")
	public void clicarXYPageSource(By elementoByEmXpath) {
		String expressaoXpath = converterXpathByEmString(elementoByEmXpath);
		Point p = retornarElementoEmXYPageSource(expressaoXpath);
		new TouchAction( (MobileDriver) getWebDriver() )
		.tap( PointOption.point( p.getX(), p.getY() ) )
		.perform();
	}
	
	/**Converter .toString() de um By.xpath em uma expressa xpath somente
	 * @param elementoByEmXpath
	 * @return
	 */
	public String converterXpathByEmString(By elementoByEmXpath) {
		return elementoByEmXpath.toString().replace("By.xpath: ", "");
	}

	/**Retorna coordenadas iniciais somadas a 1 pixel com base na expressao xpath usada para buscar, conforme o XML da page source 
	 * @param expressaoXpath
	 * @return
	 */
	public Point retornarElementoEmXYPageSource(String expressaoXpath) {
		esperarElementoPresenteNoPageSource(expressaoXpath);
		LimitesElementoPageSource limites = retornarLimitesDoElementoPageSource(expressaoXpath);
		
		int x = limites.getX1() + 1;
		int y = limites.getY1() + 1;
		
		return new Point(x, y);
	}
	
	/**Retorna todos os valores do limite do elemento (x1, x2, y1 e y2), conforme o XML da page source
	 * @param expressaoXpath
	 * @return LimitesElementoPageSource
	 */
	/**
	 * @param expressaoXpath
	 * @return
	 */
	public LimitesElementoPageSource retornarLimitesDoElementoPageSource(String expressaoXpath) {

		final String ATRIBUTO_BOUNDS_ANDROID = "bounds";
		final String ATRIBUTO_X_IOS = "x";
		final String ATRIBUTO_Y_IOS = "y";
		final String ATRIBUTO_WIDTH_IOS = "width";
		final String ATRIBUTO_HEIGHT_IOS = "height";

		Element e = retornarElementoBuscaXpathPageSource(expressaoXpath);
		
		if(e == null) {
			throw new RuntimeException("Erro ao retornar limites do elemento: " + expressaoXpath);
		}
		
		LimitesElementoPageSource limites = new LimitesElementoPageSource();

		// ANDROID
		if (!e.getAttribute(ATRIBUTO_BOUNDS_ANDROID).isEmpty()) {
			String bounds = e.getAttribute(ATRIBUTO_BOUNDS_ANDROID);
			bounds = bounds.replace("][", ",").replace("[", "").replace("]", "");
			String boundsXYs[] = bounds.split(",");

			limites.setX1(Integer.parseInt(boundsXYs[0]));
			limites.setY1(Integer.parseInt(boundsXYs[1]));
			limites.setX2(Integer.parseInt(boundsXYs[2]));
			limites.setY2(Integer.parseInt(boundsXYs[3]));
		}
		// IOS
		else if (!e.getAttribute(ATRIBUTO_X_IOS).isEmpty() && !e.getAttribute(ATRIBUTO_Y_IOS).isEmpty()
				&& !e.getAttribute(ATRIBUTO_WIDTH_IOS).isEmpty() && !e.getAttribute(ATRIBUTO_HEIGHT_IOS).isEmpty()) {

			int x1 = Integer.parseInt(e.getAttribute(ATRIBUTO_X_IOS));
			int x2 = x1 + Integer.parseInt(e.getAttribute(ATRIBUTO_WIDTH_IOS));
			int y1 = Integer.parseInt(e.getAttribute(ATRIBUTO_Y_IOS));
			int y2 = y1 + Integer.parseInt(e.getAttribute(ATRIBUTO_HEIGHT_IOS));

			limites.setX1(x1);
			limites.setX2(x2);
			limites.setY1(y1);
			limites.setY2(y2);						
		}
		//LOG.info(">>> Coordenadas do elemento(" + expressaoXpath + ") => X1: " + limites.getX1() + " X2: " + limites.getX2() + " Y1: " + limites.getY1() + " Y2: " + limites.getY2());
		return limites;
	}	
}
