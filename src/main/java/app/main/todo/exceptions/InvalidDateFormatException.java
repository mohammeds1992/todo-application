package app.main.todo.exceptions;

import app.main.todo.generic.utils.GenericUtils;

public class InvalidDateFormatException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidDateFormatException() {
        super("Invalid due date format. Please enter in this format: " + GenericUtils.DUE_DATE_FORMAT);
    }
}