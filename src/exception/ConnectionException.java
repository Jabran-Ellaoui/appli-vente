package exception;

import javax.swing.*;
import java.sql.SQLException;

public class ConnectionException extends SQLException
{

    public ConnectionException(String message, SQLException e)
    {
        super(message);
        JOptionPane.showMessageDialog (null, message,
                "Erreur de connection", JOptionPane.ERROR_MESSAGE) ;
    }
}