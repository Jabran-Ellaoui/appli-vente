package exception;

import javax.swing.*;
import java.sql.SQLException;

public class ConnectionException extends SQLException {
    public ConnectionException(String message, SQLException e) {
        super(message, e);
    }
}