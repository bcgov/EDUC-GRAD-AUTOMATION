package ca.bc.gov.educ.gtts.exception;

public class JSONUtilitiesException extends Exception {

    public JSONUtilitiesException() {
        super();
    }

    public JSONUtilitiesException(String message) {
        super(message);
    }

    public JSONUtilitiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONUtilitiesException(Throwable cause) {
        super(cause);
    }

    protected JSONUtilitiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
