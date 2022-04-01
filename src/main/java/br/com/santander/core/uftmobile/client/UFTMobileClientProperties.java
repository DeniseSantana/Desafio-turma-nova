package br.com.santander.core.uftmobile.client;

import br.com.santander.core.utils.properties.PropertyReader;

public class UFTMobileClientProperties extends PropertyReader {

	private static final String FILE_LOCATION = "uftmobile.properties";

	public UFTMobileClientProperties() {
		super(FILE_LOCATION);		
	}

	public String getServerAddress() {
		return getProperty("server.address");
	}
	
	public String getServerPort() {
		return getProperty("server.port");
	}
	
	public String getUserName() {
		return getProperty("user.name");
	}
	
	public String getUserPassword() {
		return getProperty("user.password");
	}
	
	public boolean getServerProxyEnable() {
		return Boolean.parseBoolean(getProperty("server.proxy.enable"));
	}
	
	public String getServerProxyURL() {
		return getProperty("server.proxy.url");
	}
	
	public int getServerProxyPort() {
		return Integer.parseInt(getProperty("server.proxy.port"));
	}	
}
