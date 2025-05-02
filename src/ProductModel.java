import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Représente la fiche administrative du produit, provenant d'un certain lot.
 */

public class ProductModel {
    private Integer barcode;
    private String label;
    private Integer fidelityPointNb;
    private Integer requiredAge;
    private boolean keptWarm;
    private boolean keptCold;
    private LocalDate expirationDate;
    private Double weight;
    private Integer storageTemperature;
    private Lot provenance;
    private String ecoScore;
    private ArrayList<Product> products;

    public ProductModel(int barcode, String label, int fidelityPointNb, int requiredAge, boolean keptWarm, boolean keptCold, LocalDate expirationDate, double weight, int storageTemperature, Lot provenance, String ecoScore) {
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
        this.products = new ArrayList<>();
    }

}
