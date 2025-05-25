package model;

import java.util.ArrayList;

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
}