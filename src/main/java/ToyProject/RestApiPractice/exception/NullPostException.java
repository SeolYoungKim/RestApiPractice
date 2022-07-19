package ToyProject.RestApiPractice.exception;

public class NullPostException extends Exception {
    public NullPostException() {
        super();
    }

    public NullPostException(String message) {
        super(message);
    }

    public NullPostException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullPostException(Throwable cause) {
        super(cause);
    }

    protected NullPostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
