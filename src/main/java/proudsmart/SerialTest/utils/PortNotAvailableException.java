package proudsmart.SerialTest.utils;

public class PortNotAvailableException extends Exception {
	private static final long serialVersionUID = 1L;

	public PortNotAvailableException() {
		super();
	}

	public PortNotAvailableException(String s) {
		super(s);
	}
}
