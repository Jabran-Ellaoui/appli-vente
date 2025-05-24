package exception;

import java.sql.SQLException;

public class CustomerException extends RuntimeException
{
    public CustomerException(String message, SQLException exception) {
        super(message);
    }
}
