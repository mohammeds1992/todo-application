package app.main.todo.exceptions;

public class InvalidPriorityException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidPriorityException() {
        super("Invalid priority exception. Priority should be between 1 and 3");
    }
}