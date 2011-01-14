package rchome.java.serial;

public class SerialNotFoundException extends SerialException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7346679106603298413L;

	public SerialNotFoundException() {
		super();
	}

	public SerialNotFoundException(String message) {
		super(message);
	}

	public SerialNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerialNotFoundException(Throwable cause) {
		super(cause);
	}
}
