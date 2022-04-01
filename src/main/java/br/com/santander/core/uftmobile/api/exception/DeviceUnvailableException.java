package br.com.santander.core.uftmobile.api.exception;

public class DeviceUnvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3570792068008112769L;

	public DeviceUnvailableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DeviceUnvailableException(String arg0) {
		super(arg0);
	}

	public DeviceUnvailableException(Throwable arg0) {
		super(arg0);
	}
}
