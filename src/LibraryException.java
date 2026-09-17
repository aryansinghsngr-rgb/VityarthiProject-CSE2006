/**
 * Custom checked exception used to signal library-specific error conditions,
 * such as attempting to borrow a book that is already issued, or looking up
 * a book / member ID that does not exist.
 */
public class LibraryException extends Exception {
    public LibraryException(String message) {
        super(message);
    }
}
