package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductModel {
    private int barcode;
    private String label;
    private int fidelityPointNb;
    private Integer requiredAge;
    private boolean keptWarm;
    private boolean keptCold;
    private LocalDate expirationDate;
    private double weight;
    private Integer storageTemperature;
    private Lot provenance;
    private String ecoScore;

    public ProductModel(int barcode, String label, int fidelityPointNb, Integer requiredAge, boolean keptWarm, boolean keptCold, LocalDate expirationDate, double weight, Integer storageTemperature, Lot provenance, String ecoScore) {
        this.barcode = barcode;
        this.label = label;
        this.fidelityPointNb = fidelityPointNb;
        this.requiredAge = requiredAge;
        this.keptWarm = keptWarm;
        this.keptCold = keptCold;
        this.expirationDate = expirationDate;
        this.weight = weight;
        this.storageTemperature = storageTemperature;
        this.provenance = provenance;
        this.ecoScore = ecoScore;
    }

    public ProductModel(int barcode, String label, int fidelityPointNb, boolean keptWarm, boolean keptCold, LocalDate expirationDate, double weight, Lot provenance)
    {
        this.barcode = barcode;
        this.label = label;
        this.fidelityPointNb = fidelityPointNb;
        this.requiredAge = null;
        this.keptWarm = keptWarm;
        this.keptCold = keptCold;
        this.expirationDate = expirationDate;
        this.weight = weight;
        this.storageTemperature = null;
        this.provenance = provenance;
        this.ecoScore = null;
    }

    public ProductModel(int barcode)
    {
        this.barcode = barcode;
    }

    public int getBarcode() {
        return barcode;
    }

    public String getLabel() {
        return label;
    }

    public int getFidelityPointNb() {
        return fidelityPointNb;
    }

    public Integer getRequiredAge() {
        return requiredAge;
    }

    public boolean isKeptWarm() {
        return keptWarm;
    }

    public boolean isKeptCold() {
        return keptCold;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public double getWeight() {
        return weight;
    }

    public Integer getStorageTemperature() {
        return storageTemperature;
    }

    public Lot getProvenance() {
        return provenance;
    }

    public String getEcoScore() {
        return ecoScore;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setFidelityPointNb(int fidelityPointNb) {
        this.fidelityPointNb = fidelityPointNb;
    }

    public void setRequiredAge(Integer requiredAge) {
        this.requiredAge = requiredAge;
    }

    public void setKeptWarm(boolean keptWarm) {
        this.keptWarm = keptWarm;
    }

    public void setKeptCold(boolean keptCold) {
        this.keptCold = keptCold;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setStorageTemperature(Integer storageTemperature) {
        this.storageTemperature = storageTemperature;
    }

    public void setProvenance(Lot provenance) {
        this.provenance = provenance;
    }

    public void setEcoScore(String ecoScore) {
        this.ecoScore = ecoScore;
    }

    public String toString() {
        return "Code-barres : " + barcode + "\n" +
                "Libellé : " + label + "\n" +
                "Points fidélité : " + fidelityPointNb + "\n" +
                "Âge requis : " + (requiredAge != null ? requiredAge + " ans" : "Aucun") + "\n" +
                "Conservé au chaud : " + (keptWarm ? "Oui" : "Non") + "\n" +
                "Conservé au froid : " + (keptCold ? "Oui" : "Non") + "\n" +
                "Date d'expiration : " + (expirationDate != null ? expirationDate.toString() : "Non spécifiée") + "\n" +
                "Poids : " + weight + " kg\n" +
                "Température de stockage : " + (storageTemperature != null ? storageTemperature + "°C" : "Non spécifiée") + "\n" +
                "Provenance : " + (provenance != null ? provenance.toString() : "Inconnue") + "\n" +
                "Éco-score : " + (ecoScore != null ? ecoScore : "Non disponible");
    }
}