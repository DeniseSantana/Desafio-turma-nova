package br.com.santander.core.utils.properties;

public class SauceLabsProperties extends PropertyReader {

	private static final String FILE_LOCATION = "saucelabs.properties";
	private static final String DEFAULT_BROWSER_VERSION = "latest";

	public SauceLabsProperties() {
		super(FILE_LOCATION);
	}

	public String getAccessUserName() {
		return getProperty("access.username");
	}
	
	public String getAccessKey() {
		return getProperty("access.key");
	}
	
	private String getBrowserVersion(String browserVersion) {
		if (browserVersion.isEmpty()) {
			return DEFAULT_BROWSER_VERSION;
		}
		return browserVersion;
	}
	
	public String getChromeBrowserVersion() {
		return getBrowserVersion(getProperty("chrome.browser.version"));
	}
	
	public String getChromeOS() {
		return getProperty("chrome.os");
	}
	
	public String getFirefoxBrowserVersion() {
		return getBrowserVersion(getProperty("firefox.browser.version"));
	}
	
	public String getFirefoxOS() {
		return getProperty("firefox.os");
	}
	
	public String getEdgeBrowserVersion() {
		return getBrowserVersion(getProperty("edge.browser.version"));
	}
	
	public String getEdgeOS() {
		return getProperty("edge.os");
	}
	
	public String getSafariBrowserVersion() {
		return getBrowserVersion(getProperty("safari.browser.version"));
	}
	
	public String getSafariOS() {
		return getProperty("safari.os");
	}

	public String getTesterName() {
		return getProperty("tester.name");
	}

	public String getUrl() {
		return getProperty("url");
	}
}
