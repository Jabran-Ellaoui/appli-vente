package main.model;

import java.time.LocalDate;
import java.util.Objects;

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

    public String getProvenance() {
        return provenance;
    }

    public LocalDate getReceptionDate() {
        return receptionDate;
    }

    public Employee getManager() {
        return manager;
    }

    @Override
    public String toString() {
        return "Lot n° " + number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lot lot = (Lot) o;
        return number == lot.number && Objects.equals(provenance, lot.provenance) && Objects.equals(receptionDate, lot.receptionDate) && Objects.equals(manager, lot.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, provenance, receptionDate, manager);
    }
}