package dataAccess;

import exception.ProductModelException;
import model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAOInterface
{
    ArrayList<Employee> readall() throws SQLException;
}

