package ca.bc.gov.gradtraxtestclient.exception;

public class TraxBatchServiceException extends Exception {

    public TraxBatchServiceException() {
        super();
    }

    public TraxBatchServiceException(String message) {
        super(message);
    }

    public TraxBatchServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TraxBatchServiceException(Throwable cause) {
        super(cause);
    }

    protected TraxBatchServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
