package ca.bc.gov.gradtraxcomparisontest.exception;

public class GenericHTTPRequestServiceException extends Exception {

    public GenericHTTPRequestServiceException() {
        super();
    }

    public GenericHTTPRequestServiceException(String message) {
        super(message);
    }

    public GenericHTTPRequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericHTTPRequestServiceException(Throwable cause) {
        super(cause);
    }

    protected GenericHTTPRequestServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
