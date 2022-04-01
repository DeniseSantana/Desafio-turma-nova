package br.com.santander.core.uftmobile.api.exception;

public class InvalidCredentialsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9136266194225661169L;

	public InvalidCredentialsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidCredentialsException(String arg0) {
		super(arg0);
	}

	public InvalidCredentialsException(Throwable arg0) {
		super(arg0);
	}
}
