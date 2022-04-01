package br.com.santander.core.uftmobile.api.model;

public class InstallationDetails {

	private Datum app;
	
	private DeviceContent deviceCapabilities;

	public InstallationDetails() {}
	
	/**
	 * @param app
	 * @param deviceCapabilities
	 */
	public InstallationDetails(Datum app, DeviceContent deviceCapabilities) {
		this.app = app;
		this.deviceCapabilities = deviceCapabilities;
	}

	public Datum getApp() {
		return app;
	}

	public void setApp(Datum app) {
		this.app = app;
	}

	public DeviceContent getDeviceCapabilities() {
		return deviceCapabilities;
	}

	public void setDeviceCapabilities(DeviceContent deviceCapabilities) {
		this.deviceCapabilities = deviceCapabilities;
	}
}
