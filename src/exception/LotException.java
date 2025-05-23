package exception;

import java.sql.SQLException;

public class LotException extends RuntimeException
{
    public LotException(String message, SQLException exception) {
        super(message);
    }
}
