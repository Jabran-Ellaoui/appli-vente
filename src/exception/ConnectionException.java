package exception;

import java.sql.SQLException;

public class ConnectionException extends SQLException
{

    public ConnectionException(String message, SQLException e)
    {
        super(message);
    }
}