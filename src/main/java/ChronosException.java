/**
 * Represents an error caused by invalid Chronos user input.
 */
public class ChronosException extends Exception {

    /**
     * Creates an exception with a user-facing explanation of the error.
     *
     * @param message explanation of how the user can correct the input
     */
    public ChronosException(String message) {
        super(message);
    }
}
