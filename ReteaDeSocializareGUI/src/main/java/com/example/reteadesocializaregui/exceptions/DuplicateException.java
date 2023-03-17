package com.example.reteadesocializaregui.exceptions;

/**
 * Clasa de exceptie ce este utilizata cand 2 entitati au acelasi ID.
 */
public class DuplicateException extends RuntimeException {
    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
