package main.model;

import java.util.ArrayList;
import java.util.Objects;

public class Employee
{
    private int id;
    private String lastname;
    private String firstname;
    private int phoneNumber;

    public Employee(int id, String lastname, String firstname, int phoneNumber)
    {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;

    }

    public Employee(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return firstname + " " + lastname;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && phoneNumber == employee.phoneNumber && Objects.equals(lastname, employee.lastname) && Objects.equals(firstname, employee.firstname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastname, firstname, phoneNumber);
    }
}