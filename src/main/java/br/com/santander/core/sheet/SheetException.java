package br.com.santander.core.sheet;

public class SheetException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SheetException(String message, Throwable e) {
		super(message, e);
	}

	public SheetException(String message) {
		super(message);
	}
}
