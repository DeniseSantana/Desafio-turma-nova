package br.com.santander.core.uftmobile.api.exception;

public class InvalidExpressionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4318498755760648651L;

	public InvalidExpressionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidExpressionException(String arg0) {
		super(arg0);
	}

	public InvalidExpressionException(Throwable arg0) {
		super(arg0);
	}
}
