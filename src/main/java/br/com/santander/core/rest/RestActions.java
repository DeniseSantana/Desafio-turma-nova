package br.com.santander.core.rest;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import br.com.santander.core.exception.CoreException;
import br.com.santander.core.utils.properties.RestProperties;
import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Slf4j
public class RestActions {

	private RestProperties restProperties;
	private RequestSpecification request;
	private Response response;

	public RestActions() {
		restProperties = new RestProperties();
	}

	public void setBaseURI(String BASE_URL) {
		RestAssured.baseURI = BASE_URL;
	}

	public void newRequest() {
		request = RestAssured.given();
		if (restProperties.getRestProxyActivate()) {
			request.proxy(restProperties.getRestProxyHost(), restProperties.getRestProxyPort());
		}
	}

	public RequestSpecification getRequest() {
		return request;
	}
	
	public void setResponse(Response response) {
		this.response = response;
	}
	
	public Response getResponse() {
		return response;
	}

	public RequestSpecification header(Header header) {
		return getRequest().header(header);
	}

	public RequestSpecification header(String headerName, Object obj, Object... addObj) {
		return getRequest().header(headerName, obj, addObj);
	}

	public RequestSpecification body(String body) {
		return getRequest().body(body);
	}

	public RequestSpecification body(String property, String value) {
		return getRequest().body(getJson(property, value, new String[0]).toString());
	}

	public RequestSpecification body(String property, String value, String... add) {
		return getRequest().body(getJson(property, value, add).toString());
	}

	public JsonObject getJson(String property, String value) {
		return getJson(property, value, new String[0]);
	}

	public JsonObject getJson(String property, String value, String... add) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(property, value);
		try {
			for (int i = 0; i < add.length; i += 2) {
				jsonObject.addProperty(add[i], add[i + 1]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new CoreException("Invalid number arguments to 'property' and 'value'" + e);
		}
		return jsonObject;
	}
	
	public String getResponseValue(String parameter) {
		return JsonPath.from(response.asString()).get(parameter);
	}

	public List<Map<String, String>> getResponseValues(String parameter) {
		return JsonPath.from(response.asString()).get(parameter);
	}
	
	public String getResponseValue(Response response, String parameter) {
		return JsonPath.from(response.asString()).get(parameter);
	}

	public List<Map<String, String>> getResponseValues(Response response, String parameter) {
		return JsonPath.from(response.asString()).get(parameter);
	}
}
