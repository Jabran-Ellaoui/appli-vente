package main.businessLogic;

import main.dataAccess.CustomerDAO;
import main.dataAccess.ProductModelDAO;
import main.dataAccess.SalesDetailsDAO;
import main.exception.ConnectionException;
import main.exception.CustomerException;
import main.exception.ProductModelException;
import main.exception.SalesDetailsException;
import main.model.Customer;
import main.model.SalesDetails;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FidelityService {
    private final CustomerDAO customerDAO;
    private final SalesDetailsDAO salesDAO;
    private final ProductModelDAO productDAO;

    private static final double POINTS_PER_EURO = 0.1;
    private static final double EURO_PER_POINT = 0.05;

    private static final double DEFAULT_PURCHASE_AMOUNT = 50.0;
    
    public FidelityService() throws ConnectionException
    {
        this.customerDAO = new CustomerDAO();
        this.salesDAO = new SalesDetailsDAO();
        this.productDAO = new ProductModelDAO();
    }

    public int calculateLoyaltyPoints(double amount) {
        return (int) Math.floor(amount * POINTS_PER_EURO);
    }

    public double convertPointsToEuros(int points) {
        return points * EURO_PER_POINT;
    }

    public int updateCustomerPoints(int customerId, double purchaseAmount, boolean usePoints) throws CustomerException {
        try {
            Customer customer = getCustomerById(customerId);
            
            if (customer == null) {
                return -1;
            }

            Integer currentPoints = customer.getFidelityPointNb();
            if (currentPoints == null) {
                currentPoints = 0;
            }
            

            if (usePoints && currentPoints > 0) {

                currentPoints = 0;
            } else {
                int newPoints = calculateLoyaltyPoints(purchaseAmount);
                currentPoints += newPoints;
            }

            customer.setFidelityPointNb(currentPoints);

            return currentPoints;
        } catch (Exception e) {
            return -1;
        }
    }

    private Customer getCustomerById(int customerId) throws CustomerException {
        for (Customer c : customerDAO.readAll()) {
            if (c.getId() == customerId) {
                return c;
            }
        }
        return null;
    }

    public Map<String, Object> getRecentPurchasesWithPoints(int customerId, int limit) throws CustomerException, SalesDetailsException, ProductModelException {
        Map<String, Object> result = new HashMap<>();

        Customer client = getCustomerById(customerId);
        
        if (client == null) {
            return null;
        }

        result.put("client", client);

        int totalPoints = client.getFidelityPointNb() != null ? client.getFidelityPointNb() : 0;
        result.put("totalPoints", totalPoints);
        result.put("pointsValue", convertPointsToEuros(totalPoints));

        List<SalesDetails> ventes = new ArrayList<>();
        for (SalesDetails sd : salesDAO.readAll()) {
            if (sd.getBuyer() != null && sd.getBuyer().getId() == customerId) {
                ventes.add(sd);
            }
        }
        ventes.sort(Comparator.comparing(SalesDetails::getDate).reversed());

        List<SalesDetails> recent = ventes.size() > limit
                ? ventes.subList(0, limit)
                : ventes;

        result.put("recentPurchases", recent);
        
        return result;
    }

    public boolean displayRecentPurchasesWithPoints(int customerId, int limit) throws CustomerException, SalesDetailsException, ProductModelException {
        Map<String, Object> data = getRecentPurchasesWithPoints(customerId, limit);
        
        if (data == null) {
            System.out.println("Client introuvable pour l'ID " + customerId);
            return false;
        }
        
        Customer client = (Customer) data.get("client");
        int totalPoints = (int) data.get("totalPoints");
        double pointsValue = (double) data.get("pointsValue");
        List<SalesDetails> recentPurchases = (List<SalesDetails>) data.get("recentPurchases");
        
        System.out.println("=== Achats récents et points de fidélité ===");
        System.out.println("Client: " + client.getFirstname() + " " + client.getLastname());
        System.out.println("Points de fidélité: " + totalPoints + " (valeur: " + pointsValue + "€)");
        System.out.println("\nAchats récents:");
        
        if (recentPurchases.isEmpty()) {
            System.out.println("Aucun achat récent trouvé.");
        } else {
            for (SalesDetails purchase : recentPurchases) {
                System.out.println("- Date: " + purchase.getDate() + 
                                  ", Points utilisés: " + (purchase.isFidelityPointsUsed() ? "Oui" : "Non") +
                                  ", Commentaire: " + (purchase.getComment() != null ? purchase.getComment() : ""));
            }
        }
        
        return true;
    }
}
