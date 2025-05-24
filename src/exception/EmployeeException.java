package exception;

import java.sql.SQLException;

public class EmployeeException extends RuntimeException
{
  public EmployeeException(String message, SQLException exception) {
    super(message);
  }
}
