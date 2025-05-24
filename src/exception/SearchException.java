package exception;

import java.sql.SQLException;

public class SearchException extends Exception {

    public SearchException(String message, SQLException sqlException) {
        super(message, sqlException);
    }
}