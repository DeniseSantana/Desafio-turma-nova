package br.com.santander.core.utils.json;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.santander.core.exception.CoreException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class JsonReader {

	private JsonObject jsonObject;

	protected JsonReader(String fileName) {
		JsonParser parser = new JsonParser();
		try (InputStream fileReader = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
			jsonObject = (JsonObject) parser.parse(new InputStreamReader(fileReader));
		} catch (Exception e) {
			log.error("Failed to load JSON file: " + fileName);			
			throw new CoreException(e);
		}
	}
	
	public JsonObject getJsonObject() {
		return jsonObject;
	}
}