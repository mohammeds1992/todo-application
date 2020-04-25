package app.main.todo.exceptions;

public class InvalidTodoIdException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidTodoIdException() {
        super("Invalid todoid exception. Please enter a todo Id which is present in the csv");
    }
}