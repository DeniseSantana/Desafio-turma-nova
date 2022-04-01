package br.com.santander.core.utils.properties;

public class RestProperties extends PropertyReader {

	private static final String FILE_LOCATION = "rest.properties";

	public RestProperties() {
		super(FILE_LOCATION);
	}

	public boolean getRestProxyActivate() {
		return Boolean.parseBoolean(getProperty("rest.proxy.activate"));
	}
	
	public String getRestProxyHost() {
		return getProperty("rest.proxy.host");
	}
	
	public Integer getRestProxyPort() {
		return Integer.parseInt(getProperty("rest.proxy.port"));
	}
}
