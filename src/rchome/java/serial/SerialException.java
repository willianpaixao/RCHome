package rchome.java.serial;

public class SerialException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4840105039574014447L;

	public SerialException() {
		super();
	}

	public SerialException(String message) {
		super(message);
	}

	public SerialException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerialException(Throwable cause) {
		super(cause);
	}
}
