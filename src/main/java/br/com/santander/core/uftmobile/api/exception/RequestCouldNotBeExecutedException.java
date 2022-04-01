package br.com.santander.core.uftmobile.api.exception;

public class RequestCouldNotBeExecutedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4164290613592970207L;

	public RequestCouldNotBeExecutedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RequestCouldNotBeExecutedException(String arg0) {
		super(arg0);
	}

	public RequestCouldNotBeExecutedException(Throwable arg0) {
		super(arg0);
	}
}
