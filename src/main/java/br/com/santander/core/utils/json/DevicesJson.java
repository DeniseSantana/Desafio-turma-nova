package br.com.santander.core.utils.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class DevicesJson extends JsonReader {

	private static final String FILE_LOCATION = "devices.json";
	private static final String DEVICES = "devices";

	private List<Device> listDevices;

	public DevicesJson() {
		super(FILE_LOCATION);
		listDevices = new ArrayList<>();
	}

	public List<Device> getAllDevices() {
		Gson gson = new Gson();		
		
		JsonArray jsonArray = getJsonObject().getAsJsonArray(DEVICES);
		jsonArray.forEach(e -> listDevices.add(gson.fromJson(e, Device.class)));

		return listDevices;
	}
}