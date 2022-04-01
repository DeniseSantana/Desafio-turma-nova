package br.com.santander.core.webdriver.utils.mobile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class UtilidadesDeAndroid extends UtilidadesDeMobile
{
	private static final Logger LOG = LogManager.getLogger(UtilidadesDeAndroid.class);
	
	/**Usa appium.AndroidKey para entrar uma sequência de dígitos (0 a 9).*/
	@SuppressWarnings("unchecked")
	public void escreveNumerosComRobo(String texto) {
		try {

			if (((AndroidDriver<AndroidElement>) getWebDriver()).isKeyboardShown()) {
				for (int i = 0; i < texto.length(); i++) {
					for (AndroidKey k : AndroidKey.values()) {
						char tecla = texto.toUpperCase().charAt(i);
						if (k.name().replace("NUMPAD_", "").equals(tecla+"")) {
							KeyEvent key = new KeyEvent();
							key.withKey(k);
							((AndroidDriver<AndroidElement>) getWebDriver()).pressKey(key);
							espera(99);
							System.out.println(">>> PRESSIONANDO: " + k.name());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**Usa appium.AndroidKey para entrar uma sequência de dígitos (0 a 9).*/
	@SuppressWarnings("rawtypes")
	public void escreveNumerosComAppium( String valor )
	{
		if ( valor.matches(".*\\D.*") )
		{
			throw new IllegalArgumentException( String.format(
					"A sequência '%s' deve conter somente dígitos de 0 a 9 ...", valor) );
		}
		char[] numero = valor.toCharArray();
		for (int i = 0; i < numero.length; i++) {
			switch (numero[i]) {
			case '0':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_0));
				break;
			case '1':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_1));
				break;
			case '2':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_2));
				break;
			case '3':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_3));
				break;
			case '4':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_4));
				break;
			case '5':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_5));
				break;
			case '6':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_6));
				break;
			case '7':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_7));
				break;
			case '8':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_8));
				break;
			case '9':
				((AndroidDriver) getWebDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_9));
				break;
			}
		}
	}
	
	/**appium.AndroidKey.ENTER*/
	@SuppressWarnings("rawtypes")
	public void teclarAndroidKey_ENTER()
	{
		( (AndroidDriver) getWebDriver() ).pressKey( new KeyEvent( AndroidKey.ENTER ) );
	}
	
	/**appium.AndroidKey.BACK*/
	@SuppressWarnings("rawtypes")
	public void teclarAndroidKey_BACK()
	{
		( (AndroidDriver) getWebDriver() ).pressKey( new KeyEvent( AndroidKey.BACK ) );
	}
	
	/**Verifica se o teclado está visível, então esconde
	 * o teclado usando AndroidKey.BACK.*/
	@Override
	@SuppressWarnings("unchecked")
	public void escondeTeclado()
	{
		try {
			int limite = 3;
			while ( limite > 0 )
			{
				espera( Tempo._300.qtde() );
				if ( ( (AndroidDriver<AndroidElement>) getWebDriver() ).isKeyboardShown() )
				{
					espera( Tempo._100.qtde() );
					KeyEvent key = new KeyEvent();
					key.withKey( AndroidKey.BACK );
					( (AndroidDriver<AndroidElement>) getWebDriver() ).pressKey( key );
					espera( Tempo._100.qtde() );
					break;
				}
				limite--;
			}
		}
		catch ( Exception e )
		{
			LOG.error( "Erro ao esconder o teclado ...", e );
		}
	}
	
	/**Usa findElementByAndroidUIAutomator() e UiScrollable() para
	 * encontrar um elemento e deslizar até ele. Equivalente a:
	 * <p><b>//*[@isScrollable='true']/*[contains(@text, 'texto')]</b></p>*/
	@SuppressWarnings("unchecked")
	public void deslizaAteElementoContendoComAndroidUIAutomator( String texto )
	{
		((AndroidDriver<AndroidElement>) getWebDriver())
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0))"
						+ ".scrollIntoView(new UiSelector().textContains(\"" + texto + "\").instance(1))");
	}
	
	/**Usa findElementByAndroidUIAutomator() e UiScrollable() para
	 * encontrar um elemento e deslizar até ele. Equivalente a:
	 * <p><b>//*[@isScrollable='true'][indiceDoUiSelectorPai]/*[contains(@text, 'texto')][indiceDoElementoFilho]</b></p>
	 * 
	 * @param texto o que está contido no elemento (@text).
	 * @param indiceDoUiSelectorPai índice do 'elemento scrollable' que é pai do 'elemento procurado'.
	 * @param indiceDoElementoFilho índice do 'elemento procurado' dentro do 'elemento scrollable'.
	 */
	@SuppressWarnings("unchecked")
	public void deslizaAteElementoContendoComAndroidUIAutomator(String texto, int indiceDoUiSelectorPai, int indiceDoElementoFilho)
	{
		((AndroidDriver<AndroidElement>) getWebDriver())
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(" + indiceDoUiSelectorPai
						+ ")).scrollIntoView(new UiSelector().textContains(\"" + texto + "\").instance(" + indiceDoElementoFilho + "))");
	}
	
	/**Usa o adb local para deslizar de (x1, y1) até (x2, y2).*/
	public void localAdbDesliza( int x1, int y1, int x2, int y2 )
	{
		localAdbComandar( String.format("adb shell input swipe %d %d %d %d", x1, y1, x2, y2) );
	}
	
	/**Envia um comando ao adb instalado no local indicado pela
	 * variável de ambiente 'ANDROID_HOME'.*/
	public void localAdbComandar( String comando )
	{
		String cdDiretorioADB = "cd " + System.getenv("ANDROID_HOME") + " && ";
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", cdDiretorioADB + comando);
		Process process = null;
		try {
			process = pb.start();
		}
		catch ( IOException e )
		{
			LOG.error(e);
		}
		showOutput(process.getInputStream());
		showOutput(process.getErrorStream());
	}
	
	/**Desliza usando RemoteWebDriver.executeScript("mobile: shell", { "input swipe", x1, y1, x2, y2 })
	 * no servidor MobileCenter.*/
	public void remoteAdbShellScroll( int x1, int y1, int x2, int y2 )
	{
		List<String> args = Arrays.asList(
				String.valueOf(x1),
				String.valueOf(y1),
				String.valueOf(x2),
				String.valueOf(y2)
				);
		remoteAdbShell( "input swipe", args );
	}
	
	/**Usa RemoteWebDriver.executeScript("mobile: shell", { command, args }) para enviar um comando ao
	 * adb do servidor MobileCenter.*/
	@SuppressWarnings("unchecked")
	public void remoteAdbShell( String command, List<String> args )
	{
		try {
			Map<String, Object> script = ImmutableMap.of(
					"command", command,
					"args", args
					);
			( (AndroidDriver<AndroidElement>) getWebDriver() ).executeScript( "mobile: shell", script );
		}
		catch ( WebDriverException e )
		{
			LOG.error( e );
			throw new WebDriverException( e );
		}
	}
	

	/**
	 * Use para ativar(true) ou desativar(false) o wifi
	 * @param boolean: enable
	 */
	public void enableWifi(boolean enable) {
		AndroidDriver<?> androidDriver = (AndroidDriver<?>) getWebDriver();		
		ConnectionState connectionStatus = androidDriver.getConnection();
		  
		if (enable && !connectionStatus.isWiFiEnabled()) {
			androidDriver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
		}else if (!enable && connectionStatus.isWiFiEnabled()) {
			androidDriver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());
		}
	}
	
	/**
	 * Use para ativar(true) ou desativar(false) o modo aviao
	 * @param boolean: enable
	 */
	public void enableAirPlaneMode(boolean enable) {
		AndroidDriver<?> androidDriver = (AndroidDriver<?>) getWebDriver();		
		ConnectionState connectionStatus = androidDriver.getConnection();
		  
		if (enable && !connectionStatus.isAirplaneModeEnabled()) {
			androidDriver.setConnection(new ConnectionStateBuilder().withAirplaneModeEnabled().build());
		}else if (!enable && connectionStatus.isAirplaneModeEnabled()) {
			androidDriver.setConnection(new ConnectionStateBuilder().withAirplaneModeDisabled().build());
		}
	}
	
//	public void voltarAoAppAndroid() {
//		abrirApp(ExecutionProperties.getInstance().getAppPackage(), ExecutionProperties.getInstance().getAppActivity());
//	}

	public void abrirApp(String appPackage, String appActivity) {
		JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
		HashMap<String, String> map = new HashMap<>();
		map.put("appPackage", appPackage);
		map.put("appActivity", appActivity);
		js.executeScript("mobile: launchApp", map);
	}
	
}
