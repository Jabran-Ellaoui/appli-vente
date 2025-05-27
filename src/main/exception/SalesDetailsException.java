package main.exception;

import java.sql.SQLException;

public class SalesDetailsException extends RuntimeException
{
    public SalesDetailsException(String message, SQLException exception) {
        super(message);
    }
}
