package br.com.santander.core.uftmobile.api.model;

public class InstallationDetailsResponse {

	private Datum app;

	private Devices devices;

	private String status;

	public Datum getApp() {
		return app;
	}

	public void setApp(Datum app) {
		this.app = app;
	}

	public Devices getDevices() {
		return devices;
	}

	public void setDevices(Devices devices) {
		this.devices = devices;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}