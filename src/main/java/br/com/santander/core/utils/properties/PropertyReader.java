package br.com.santander.core.utils.properties;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public abstract class PropertyReader {
	private Properties prop;

	protected PropertyReader(String fileName) {
		prop = new Properties();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
		try {
			prop.load(in);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getProperty(String propKey) {
		return Optional.ofNullable((String) prop.get(propKey)).orElse("");
	}
}