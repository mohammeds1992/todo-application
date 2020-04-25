package app.main.todo.exceptions;

public class MandatoryParameterException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryParameterException(String param) {
		super(param + " is a mandatory parameter");
	}
}