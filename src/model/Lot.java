package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lot {
    private int number;
    private String provenance;
    private LocalDate receptionDate;
    private Employee manager;

    public Lot(int number, String provenance, LocalDate receptionDate, Employee manager)
    {
        this.number = number;
        this.provenance = provenance;
        this.receptionDate = receptionDate;
        this.manager = manager;
    }

    public Lot(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Lot n° " + number;
    }

}