package app.main.todo.exceptions;

public class InvalidIntegerException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidIntegerException() {
        super("Invalid integer exception");
    }
}