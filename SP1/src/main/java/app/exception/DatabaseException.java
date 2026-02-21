package app.exception;

public class DatabaseException extends Exception {

    // Attributes

    // __________________________________________________________

    public DatabaseException(String userMessage, Throwable cause) {
        super(userMessage, cause);
        System.out.println("userMessage: " + userMessage);
        System.out.println("cause: " + cause);
    }

    // __________________________________________________________

    public DatabaseException(String userMessage) {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
    }

    // __________________________________________________________

    public DatabaseException(String userMessage, String systemMessage) {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
        System.out.println("errorMessage: " + systemMessage);
    }

} // DatabaseException End