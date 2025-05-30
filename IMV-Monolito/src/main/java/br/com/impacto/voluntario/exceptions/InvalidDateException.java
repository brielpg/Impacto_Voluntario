package br.com.impacto.voluntario.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Date cannot be higher than the current one");
    }

    public InvalidDateException(String message) {
        super(message);
    }
}
