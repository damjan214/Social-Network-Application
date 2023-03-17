package com.example.reteadesocializaregui.exceptions;

/**
 * Clasa de exceptie ce este utilizata cand dorim sa accesam o entitate ce nu exista.
 */
public class LackException extends RuntimeException {
    public LackException() {
    }

    public LackException(String message) {
        super(message);
    }

    public LackException(String message, Throwable cause) {
        super(message, cause);
    }

    public LackException(Throwable cause) {
        super(cause);
    }

    public LackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
