package ca.bc.gov.restdemo.exceptions;

/**
 * Throw when there is serious trouble
 */
public class UnrecoverableException extends Exception {

    public UnrecoverableException() {
        super();
    }

    public UnrecoverableException(String message) {
        super(message);
    }

    public UnrecoverableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnrecoverableException(Throwable cause) {
        super(cause);
    }

    protected UnrecoverableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
