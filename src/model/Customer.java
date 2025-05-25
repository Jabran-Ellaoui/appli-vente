package model;

import java.util.ArrayList;

public class Customer
{
    private int id;
    private String lastname;
    private String firstname;
    private String address;
    private String email;
    private Integer phoneNumber;
    private Integer fidelityPointNb;
    private Locality locality;

    public Customer(int id, String lastname, String firstname, String address, String email, Integer phoneNumber, Integer fidelityPointNb, Locality locality)
    {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fidelityPointNb = fidelityPointNb;
        this.locality = locality;
    }

    public Customer(int id, String lastname, String firstname, String address, String email, Locality locality)
    {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
        this.email = email;
        this.phoneNumber = null;
        this.fidelityPointNb = null;
        this.locality = locality;
    }

    public Customer(int id)
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

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getFidelityPointNb() {
        return fidelityPointNb;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFidelityPointNb(Integer fidelityPointNb) {
        this.fidelityPointNb = fidelityPointNb;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public String toString() {
        return firstname + " " + lastname + " " + address;
    }
}