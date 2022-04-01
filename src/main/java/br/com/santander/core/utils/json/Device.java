package br.com.santander.core.utils.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Device {

	private String id;
	private String deviceName;
	private String udid;
	private String platformName;
	private String automationName;
	private String observation;
}
