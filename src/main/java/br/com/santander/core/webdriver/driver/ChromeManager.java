package br.com.santander.core.webdriver.driver;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import br.com.santander.core.exception.CoreException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeManager extends DriverManager {

	@Override
	public void createDriver() {
		setupProxy();

		WebDriverManager.chromedriver().setup();
		try {
			webDriver = new ChromeDriver(setupChromeOptions());
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

	private static ChromeOptions setupChromeOptions() throws Exception {
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.LOGGING_PREFS, createLogsSetup());
		options.setCapability("applicationCacheEnabled", false);

		Map<String, Object> preferences = new Hashtable<String, Object>();

		// configuracao para abrir janela de download do PDF
		preferences.put("plugins.plugins_disabled", new String[] { "Chrome PDF Viewer" });
		preferences.put("download.prompt_for_download", true);
		preferences.put("download.directory_upgrade", true);
		preferences.put("plugins.always_open_pdf_externally", true);
		preferences.put("profile.default_content_settings.popups", 0);
		// ---------------------------------------

		preferences.put("useAutomationExtension", false);
		options.setExperimentalOption("prefs", preferences);
		options.addArguments("--test-name", "--test-type", "--no-check-default-driver", "--reduce-security-for-testing",
				"--allow-running-insecure-content", // Aceita certificados SSL self-signed
				"--disable-web-security", // Permite chamadas Ajax CORS
				"--no-sandbox", // Permite executar chrome dentro de um container docker
				"--no-first-run", // Evita que o chrome abra popups na inicialização
				"--disable-popup-blocking", "--disable-infobars", "--testing-fixed-https-port", "--start-maximized",
				"--disable-extensions");
		return options;
	}

	private static LoggingPreferences createLogsSetup() {
		LoggingPreferences loggingPreferences = new LoggingPreferences();
		loggingPreferences.enable(LogType.DRIVER, Level.SEVERE);
		return loggingPreferences;
	}

	@Override
	public void tearDown() {
		super.tearDown();
		//in parallels crash test
//		try {
//			Runtime.getRuntime().exec("wmic process where name=\"chromedriver.exe\" call terminate").waitFor();
//		} catch (IOException | InterruptedException e) {
//			throw new RuntimeException(e);
//		}
	}
}
