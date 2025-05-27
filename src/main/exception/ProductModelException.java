package main.exception;

public class ProductModelException extends Exception {

    public ProductModelException(String message) {
        super(message);
    }

    public ProductModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductModelException(Throwable cause)
    {
        super(cause);
    }
}
