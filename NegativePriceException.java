/**
 * Exceptie pentru cazul in care nu se poate atribui un discount pe produse,
 * intrucat cel putin unul dintre acestea va avea un pret negativ in urma
 * reducerii
 */

public class NegativePriceException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public NegativePriceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NegativePriceException(String message) {
        super(message);
    }
}
