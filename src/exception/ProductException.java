package exception;

import java.sql.SQLException;

public class ProductException extends RuntimeException {
    public ProductException(String message, SQLException exception) {
        super(message);
    }
}
