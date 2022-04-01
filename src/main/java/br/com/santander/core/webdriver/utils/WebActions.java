package br.com.santander.core.webdriver.utils;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.santander.core.exception.ActionsException;
import br.com.santander.core.report.utils.ReportProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebActions {

	private WebDriver driver;
	private boolean reportIsActive;

	private static final Integer MEDIUMSECONDS = 10;
	private static final Integer LONGSECONDS = 20;
	private static final Integer DEFAULTPOLLINGSECONDS = 1;
	private static final Integer DEFAULTTIMEOUTSECONDS = 10;
	private static final Duration D10SECONDS = Duration.ofSeconds(10);
	private static final Duration D500MILLIS = Duration.ofMillis(500);

	public WebActions(WebDriver driver) {
		this.driver = driver;
		try {
			this.reportIsActive = new ReportProperties().reportIsActive();
		} catch (Exception e) {
			throw new RuntimeException("Check if report.properties exists in the resources directory.");
		}
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	public WebDriverWait getWait(Integer seconds) {
		return new WebDriverWait(driver, seconds);
	}

	public void navigateToUrl(String url) {
		driver.get(url);
	}

	public String getHTML() {
		return driver.getPageSource();
	}

	public String getUrlFromPage() {
		return driver.getCurrentUrl();
	}

	public FluentWait<WebDriver> getDefaultFluentWait(Long timeoutInSeconds, Long pollingTimeInMillis) {
		return new FluentWait<>(driver) //
				.withTimeout(Duration.ofSeconds(timeoutInSeconds)) //
				.pollingEvery(Duration.ofMillis(pollingTimeInMillis)) //
				.ignoring(NoSuchElementException.class) //
				.ignoring(TimeoutException.class);
	}

	private WebElement fluentlyWaitUntilClickable(WebElement element, Integer timeoutInSeconds,
			Integer pollingInSeconds) {
		return (new FluentWait<WebDriver>(driver)) //
				.withTimeout(Duration.ofSeconds(timeoutInSeconds)) //
				.pollingEvery(Duration.ofSeconds(pollingInSeconds)) //
				.ignoring(StaleElementReferenceException.class) //
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	private WebElement explicitlyWaitForElementToBeClickable(WebElement element, Integer segundos) {
		return new WebDriverWait(driver, segundos) //
				.ignoring(NoSuchElementException.class) //
				.ignoring(StaleElementReferenceException.class) //
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	private WebElement fluentlyWaitUntilClickable(WebElement element) {
		return fluentlyWaitUntilClickable(element, DEFAULTTIMEOUTSECONDS, DEFAULTPOLLINGSECONDS);
	}

	public void click(WebElement webElement) {
		try {
			executeJS("arguments[0].click();", webElement);
		} catch (StaleElementReferenceException ex) {
			webElement.click();
		} catch (WebDriverException ex) {
			this.sleep(MEDIUMSECONDS);
			webElement.click();
			log.warn(ex.getMessage());
		}
	}

	public void clickSelenium(WebElement elemento) {
		elemento.click();
	}

	public void selectRadioFromList(List<WebElement> elements, Integer option) {
		if (option >= 0 && option <= elements.size()) {
			WebElement elemento = elements.get(option);
			this.click(elemento);
		}
	}

	public void selectElementByIndex(WebElement element, Integer index) {
		try {
			Select dropdown = new Select(fluentlyWaitUntilClickable(element));
			dropdown.selectByIndex(index);
		} catch (Exception ex) {
			log.error("Erro ao selecionar op��o por index.");
		}
	}

	public void selectElementByText(WebElement element, String value) {
		if (value.isEmpty() || value.equals(""))
			return;
		Select dropdown = new Select(this.fluentlyWaitUntilClickable(element));
		dropdown.selectByVisibleText(value);
	}

	public List<WebElement> getOptionsSelect(WebElement element) {
		Select dropdown = new Select(this.fluentlyWaitUntilClickable(element));
		return dropdown.getOptions();
	}

	public void selectElementByValue(WebElement element, String value) {
		try {

			Select dropdown = new Select(this.fluentlyWaitUntilClickable(element));
			dropdown.selectByValue(value);
		} catch (Exception ex) {
			log.error("Erro ao selecionar opcao por texto.");
		}
	}

	public void insertText(WebElement element, String value) {
		this.fluentlyWaitUntilClickable(element).sendKeys(value);
	}

	public void insertKey(WebElement element, Keys key) {
		this.fluentlyWaitUntilClickable(element).sendKeys(key);
	}

	public void sendEscToElement(WebElement element) {
		insertKey(element, Keys.ESCAPE);
	}

	public WebElement searchElementByText(String text) {
		this.sleep(LONGSECONDS);
		return driver.findElement(By.xpath(String.format("//*[contains(text(),'%1$s')]", text)));
	}

	public void clearElement(WebElement element) {
		fluentlyWaitUntilClickable(element).clear();
	}

	public Boolean elementIsVisible(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException ex) {
			log.error("Elemento nao visivel (NoSuchElementException).");
			return false;
		} catch (StaleElementReferenceException ex) {
			log.error("Elemento nao visivel (StaleElementReferenceException).");
			return false;
		} catch (Exception ex) {
			log.error("Elemento nao visivel " + ex.getMessage());
			return false;
		}
	}

	public Boolean elementIsVisible(WebElement webElement, Integer segundos) {
		try {
			return waitVisibilityOf(webElement, segundos) != null;
		} catch (NoSuchElementException ex) {
			log.error("Elemento nao visivel (NoSuchElementException).");
			return false;
		} catch (StaleElementReferenceException ex) {
			log.error("Elemento nao visivel (StaleElementReferenceException).");
			return false;
		} catch (Exception ex) {
			log.error("Elemento nao visivel " + ex.getMessage());
			return false;
		}
	}

	public Boolean elementIsClickable(WebElement element, Integer segundos) {
		try {
			return explicitlyWaitForElementToBeClickable(element, segundos) != null;
		} catch (NoSuchElementException ex) {
			log.error("Elemento nao visivel (NoSuchElementException).");
			return false;
		} catch (StaleElementReferenceException ex) {
			log.error("Elemento nao visivel (StaleElementReferenceException).");
			return false;
		} catch (Exception ex) {
			log.error(("Elemento nao visivel " + ex.getMessage()));
			return false;
		}
	}

	public Boolean elementIsClickable(WebElement element) {
		return elementIsClickable(element, 1);
	}

	public void elementSubmit(WebElement element) {
		element.submit();
	}

	public Boolean elementIsEnabled(WebElement element) {
		return element.isEnabled();
	}

	public Boolean waitForElementToBeEnabled(WebElement element) {
		return fluentlyWaitUntilClickable(element).isEnabled();
	}

	public Boolean waitForElementToBeDisplayed(WebElement element) {
		return fluentlyWaitUntilClickable(element).isDisplayed();
	}

	public void lowerTimeouts() {
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
	}

	public void raiseTimeouts() {
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public Boolean isDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isDisplayed(WebElement webelement, Integer segundosAteTimeout) {
		lowerTimeouts();
		Boolean isDisplayed = false;
		Integer contadorSegundos = 0;
		try {
			while (contadorSegundos < segundosAteTimeout && Boolean.FALSE.equals(isDisplayed)) {
				if (webelement.isDisplayed() && webelement.getSize().getWidth() > 0
						&& webelement.getSize().getHeight() > 0) {
					isDisplayed = true;
				}
				getWait(1);
				contadorSegundos++;
			}
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			isDisplayed = false;
		}
		raiseTimeouts();
		return isDisplayed;
	}

	public Boolean isTextEmpty(WebElement element) {
		return readFromElement(element).isEmpty();
	}

	public void clickListItem(List<WebElement> elements, String valor) {
		WebElement listItem = getListItem(elements, valor);
		if (listItem == null) {
			throw new ActionsException("Element not found");
		}
		listItem.click();
	}

	public WebElement getListItem(List<WebElement> elements, String valor) {
		for (WebElement element : elements) {
			if (element.isDisplayed() && element.getText().equals(valor)) {
				return element;
			}
		}
		return null;
	}

	public WebElement getListItemContainsIgnoreCase(List<WebElement> elements, String valor) {
		for (WebElement element : elements) {
			if (element.isDisplayed() && element.getText().toUpperCase().contains(valor.toUpperCase())) {
				return element;
			}
		}
		return null;
	}

	public void sendTab(WebElement element) {
		insertKey(element, Keys.TAB);
	}

	// ********************* Selecao ************************
	public void moveToElement(WebElement webElement) {
		new Actions(driver).moveToElement(webElement).perform();
	}

	public void moveToElementAndClick(WebElement webElement) {
		new Actions(driver).moveToElement(webElement).click().perform();
	}

	public Boolean isSelected(WebElement element) {
		return element.isSelected();
	}
	// ********************* Textos e Atributos ************************

	public String readFromElement(WebElement element) {
		return element.getText();
	}

	public List<String> readFromElements(List<WebElement> elements) {
		List<String> list = new ArrayList<>();
		for (WebElement webElement : elements) {
			list.add(webElement.getText());
		}
		return list;
	}

	public void clearAndwriteToElement(WebElement element, String texto) {
		this.fluentlyWaitUntilClickable(element);
		element.clear();
		element.sendKeys(texto);
	}

	public String getAttribute(WebElement element, String atributo) {
		return element.getAttribute(atributo);
	}

	// ********************* JS ************************

	public Object executeJS(String cmd, Object... param) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(cmd, param);
	}

	public void scroll(WebElement element) {
		executeJS("arguments[0].scrollIntoView(true);", element);
	}

	public void scroll(int scroll) {
		executeJS("window.scrollBy(0," + scroll + ")", "");
	}

	// ********************* Clipboard ************************

	public String getStringFromClipboard() {
		String text = null;
		try {
			text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			log.error(e.getMessage());
		}
		return text;
	}

	public void hoverOverElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();

	}

	public void contextClickElement(WebElement element) {
		Actions action = new Actions(driver);
		action.contextClick(element).build().perform();
	}

	public void switchToWindow(int indiceDaAba) {
		try {
			List<String> abas = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(abas.get(indiceDaAba));
		} catch (IndexOutOfBoundsException e) {
			log.error(e.getMessage());
		}
	}

	public int getQuantidadeDeAbas() {
		List<String> abas = new ArrayList<>(driver.getWindowHandles());
		return abas.size();
	}

	public void closeCurrentWindow() {
		driver.close();
	}

	// ********************* Regex ************************

	public String reduceMultipleSpacesToOne(String text) {
		String clean = text;
		Pattern pattern = Pattern.compile("(.*)(\\S)(\\s{2,})(\\S)(.*)");
		Matcher matcher = pattern.matcher(clean);
		while (matcher.matches()) {
			String inicio = matcher.group(1);
			String meio1 = matcher.group(2);
			String meio2 = matcher.group(4);
			String fim = matcher.group(5);
			clean = inicio + meio1 + " " + meio2 + fim;
			matcher = pattern.matcher(clean);
		}
		return clean;
	}

	// ********************* Espera ************************

	public void sleep(Integer seconds) {
		try {
			Thread.sleep(seconds * 1000l);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			Thread.currentThread().interrupt();
		}

	}

	public Boolean waitForElementToBeSelected(WebElement element) {
		getWait(30).until(ExpectedConditions.elementSelectionStateToBe(element, true));
		return isSelected(element);
	}

	public WebElement waitElementToBeClickable(WebElement element) {
		return getWait(30).withTimeout(D10SECONDS).pollingEvery(D500MILLIS)
				.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement waitElementToBeClickable(WebElement element, Integer segundosAteTimeout) {
		return getWait(30).withTimeout(Duration.ofSeconds(segundosAteTimeout)).pollingEvery(D500MILLIS)
				.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement waitVisibilityOf(WebElement element, Integer segundosAteTimeout) {
		return getWait(segundosAteTimeout).until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement waitVisibilityOf(WebElement element) {
		return waitVisibilityOf(element, 10);
	}

	public Boolean waitForElementToDissapear(WebElement element) {
		return waitForElementToDissapear(element, 5);
	}

	public Boolean waitForElementToDissapear(WebElement element, Integer secondsTimeout) {
		lowerTimeouts();
		Boolean dissapeared = false;
		Integer counter = 0;
		try {
			while (counter < secondsTimeout && Boolean.FALSE.equals(dissapeared)) {
				if (!element.isDisplayed()
						|| (element.getSize().getWidth() == 0 && element.getSize().getHeight() == 0)) {
					dissapeared = true;
				}
				sleep(1);
				counter++;
			}
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			dissapeared = true;
		}
		raiseTimeouts();
		return dissapeared;
	}

	public byte[] getScreenshot() {
		try {
			if (reportIsActive) {
				return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
			} else {
				return null;
			}
		} catch (Exception e) {
			log.info("Screenshot failed: " + e.getMessage());
			return null;
		}
	}

	public void selectAllAndInputText(WebElement element, String text) {
		element.sendKeys(Keys.CONTROL, Keys.chord("a"));
		element.sendKeys(text);
	}

	public void insertDate(WebElement element, String data) {
		try {
			String formato = "dd/MM/yyyy";
			if (!data.contains("/")) {
				formato = "ddMMyyyy";
			}
			Date date = new SimpleDateFormat(formato).parse(data);
			String elementoToString = element.toString();
			String campoFormatoData = (elementoToString.substring(0, elementoToString.length() - 1)).split("xpath: ")[1]
					.concat("/following-sibling::div");
			String textoData = driver.findElement(By.xpath(campoFormatoData)).getAttribute("innerText");
			if (textoData.toUpperCase().contains("MM/DD/YYYY")) {
				data = new SimpleDateFormat("MM/dd/yyyy").format(date);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		insertText(element, data + Keys.ENTER);
	}

	public void clickElementInFrame(WebElement frame, WebElement element) {
		WebDriver frameDriver = driver //
				.switchTo().frame(frame);
		WebElement frameElement = new WebDriverWait(frameDriver, 10) //
				.until(ExpectedConditions.elementToBeClickable(element));
		frameElement.click();
	}

	public void hoverElementInFrame(WebElement frame, WebElement element) {
		WebDriver frameDriver = driver //
				.switchTo().frame(frame);
		WebElement frameElement = new WebDriverWait(frameDriver, 10) //
				.until(ExpectedConditions.visibilityOf(element));
		Actions action = new Actions(frameDriver);
		action.moveToElement(frameElement).click().build().perform();
	}

	public void switchToFrame(String nomeFrame) {
		driver.switchTo().frame(nomeFrame);
	}

	public void defaultContent() {
		driver.switchTo().defaultContent();
	}

	public void clickdragAndDrop(WebElement sourceEle, WebElement targetEle) {

		Actions actionProvider = new Actions(driver);

		actionProvider.dragAndDrop(sourceEle, targetEle).build().perform();

	}

	public void moveToElement1(WebElement element) {

		Actions actionProvider = new Actions(driver);

		actionProvider.moveToElement(element).build().perform();

	}

	public void switchToFrame2(int numero) {
		driver.switchTo().frame(numero);
	}
}