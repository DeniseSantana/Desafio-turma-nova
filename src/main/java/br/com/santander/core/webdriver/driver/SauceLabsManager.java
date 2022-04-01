package br.com.santander.core.webdriver.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.santander.core.exception.CoreException;
import br.com.santander.core.utils.properties.SauceLabsProperties;

public class SauceLabsManager extends DriverManager {
	
	private static final String TUNNEL_IDENTIFIER = "tunel_01";
	private static final String PARENT_TUNNEL = "connectionuser";
	private static final Integer IDLE_TIMEOUT = 180;
	private static final String NAME = "Test SauceLabs";
	private static final String SCREEN_RESOLUTION = "1400x1050";
	
	private SauceLabsProperties sauceLabsProperties;
	private DriverType driverType;
	private DesiredCapabilities capabilities;
	
	public SauceLabsManager(DriverType driverType) {
		sauceLabsProperties = new SauceLabsProperties();
		this.driverType = driverType;
	}
	
	@Override
	public void createDriver() {
		try {
			createCapabilities();
			webDriver = new RemoteWebDriver(new URL(sauceLabsProperties.getUrl()), capabilities);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error in URL of SauceLabs");
		}
	}

	private void createCapabilities() {
		String browserVersion, os;
		switch (driverType) {
		case CHROME:
			browserVersion = sauceLabsProperties.getChromeBrowserVersion();
			os = sauceLabsProperties.getChromeOS();
			break;
		case FIREFOX:
			browserVersion = sauceLabsProperties.getFirefoxBrowserVersion();
			os = sauceLabsProperties.getFirefoxOS();
			break;
		case EDGE:
			browserVersion = sauceLabsProperties.getEdgeBrowserVersion();
			os = sauceLabsProperties.getEdgeOS();
			break;
		case SAFARI:
			browserVersion = sauceLabsProperties.getSafariBrowserVersion();
			os = sauceLabsProperties.getSafariBrowserVersion();
			break;
		default:
			throw new CoreException("SauceLabs Manager not found");
		}
		capabilities = new DesiredCapabilities(driverType.name(), browserVersion, Platform.valueOf(os));
		setCapabilitesDefault();		
	}
	
	private void setCapabilitesDefault() {
		capabilities.setCapability("tunnelIdentifier", TUNNEL_IDENTIFIER);
		capabilities.setCapability("parentTunnel", PARENT_TUNNEL);
		capabilities.setCapability("idleTimeout", IDLE_TIMEOUT);
		capabilities.setCapability("username", sauceLabsProperties.getAccessUserName());
		capabilities.setCapability("accessKey", sauceLabsProperties.getAccessKey());
		capabilities.setCapability("name", NAME);
		capabilities.setCapability("screenResolution", SCREEN_RESOLUTION);
		capabilities.setCapability("build", sauceLabsProperties.getTesterName());
	}
}
