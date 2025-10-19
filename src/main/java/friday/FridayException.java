package friday;

public class FridayException extends RuntimeException {
    public FridayException(String message) {
        super(message);
    }
    public FridayException(String message, Throwable cause) {
        super(message, cause);
    }
}
