package ca.bc.gov.gradtraxtestclient.exception;

public class IOUtilsException extends Exception {

    public IOUtilsException() {
        super();
    }

    public IOUtilsException(String message) {
        super(message);
    }

    public IOUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IOUtilsException(Throwable cause) {
        super(cause);
    }

    protected IOUtilsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
