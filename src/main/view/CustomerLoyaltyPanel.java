package main.view;

import main.businessLogic.FidelityService;
import main.exception.ConnectionException;
import main.exception.CustomerException;
import main.exception.ProductModelException;
import main.exception.SalesDetailsException;
import main.model.Customer;
import main.model.SalesDetails;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Panel permettant d'afficher les achats récents d'un client et ses points de fidélité
 */
public class CustomerLoyaltyPanel extends JPanel {
    private JTextField customerIdField;
    private JButton searchButton;
    private JLabel customerNameLabel;
    private JLabel pointsLabel;
    private JLabel pointsValueLabel;
    private JTable purchasesTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private FidelityService loyaltyService;
    private MainWindow mainWindow;
    
    private static final int DEFAULT_PURCHASE_LIMIT = 10;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructeur du panel d'affichage des points de fidélité
     */
    public CustomerLoyaltyPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        try {
            loyaltyService = new FidelityService();
            initializeComponents();
            setupLayout();
            setupListeners();
        } catch (ConnectionException e) {
            JOptionPane.showMessageDialog(this, 
                    "Erreur de connexion à la base de données: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initialise les composants du panel
     */
    private void initializeComponents() {
        // Champ de recherche
        customerIdField = new JTextField(10);
        searchButton = new JButton("Rechercher");
        
        // Informations client
        customerNameLabel = new JLabel("Sélectionnez un client");
        customerNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pointsLabel = new JLabel("Points de fidélité: ");
        pointsValueLabel = new JLabel("0 (valeur: 0.00€)");
        
        // Tableau des achats
        String[] columnNames = {"Date", "Points utilisés", "Commentaire"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non éditables
            }
        };
        purchasesTable = new JTable(tableModel);
        purchasesTable.getTableHeader().setReorderingAllowed(false);
        purchasesTable.getTableHeader().setResizingAllowed(true);
        purchasesTable.setRowHeight(25);
        purchasesTable.setGridColor(Color.LIGHT_GRAY);
        
        scrollPane = new JScrollPane(purchasesTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
    }

    /**
     * Configure la disposition des composants
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Panel de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(new TitledBorder("Recherche client"));
        searchPanel.add(new JLabel("ID Client:"));
        searchPanel.add(customerIdField);
        searchPanel.add(searchButton);
        
        // Panel d'informations client
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(new TitledBorder("Informations client"));
        infoPanel.add(customerNameLabel);
        
        JPanel pointsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pointsPanel.add(pointsLabel);
        pointsPanel.add(pointsValueLabel);
        infoPanel.add(pointsPanel);
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(searchPanel, BorderLayout.NORTH);
        headerPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Panel des achats
        JPanel purchasesPanel = new JPanel(new BorderLayout());
        purchasesPanel.setBorder(new TitledBorder("Achats récents"));
        
        // Informations sur les points
        JPanel infoPointsPanel = new JPanel();
        infoPointsPanel.setLayout(new BoxLayout(infoPointsPanel, BoxLayout.Y_AXIS));
        
        purchasesPanel.add(infoPointsPanel, BorderLayout.NORTH);
        purchasesPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Ajout des composants au panel principal
        add(headerPanel, BorderLayout.NORTH);
        add(purchasesPanel, BorderLayout.CENTER);
    }

    /**
     * Configure les écouteurs d'événements
     */
    private void setupListeners() {
        searchButton.addActionListener(e -> searchCustomer());
        
        // Écouteur pour la touche Entrée dans le champ de recherche
        customerIdField.addActionListener(e -> searchCustomer());
    }

    /**
     * Recherche et affiche les informations du client et ses achats récents
     */
    private void searchCustomer() {
        try {
            String idText = customerIdField.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "Veuillez saisir un ID client valide",
                        "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int customerId;
            try {
                customerId = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                        "L'ID client doit être un nombre entier",
                        "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Récupérer les données du client et ses achats
            Map<String, Object> data = loyaltyService.getRecentPurchasesWithPoints(customerId, DEFAULT_PURCHASE_LIMIT);
            
            if (data == null) {
                JOptionPane.showMessageDialog(this, 
                        "Client introuvable pour l'ID " + customerId,
                        "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Mettre à jour les informations du client
            Customer client = (Customer) data.get("client");
            int totalPoints = (int) data.get("totalPoints");
            double pointsValue = (double) data.get("pointsValue");
            List<SalesDetails> recentPurchases = (List<SalesDetails>) data.get("recentPurchases");
            
            customerNameLabel.setText("Client: " + client.getFirstname() + " " + client.getLastname());
            pointsValueLabel.setText(totalPoints + " (valeur: " + String.format("%.2f", pointsValue) + "€)");
            
            // Mettre à jour le tableau des achats
            updatePurchasesTable(recentPurchases);
            
        } catch (CustomerException | SalesDetailsException | ProductModelException e) {
            JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la recherche: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Met à jour le tableau des achats récents
     * @param purchases Liste des achats à afficher
     */
    private void updatePurchasesTable(List<SalesDetails> purchases) {
        // Vider le tableau
        tableModel.setRowCount(0);
        
        // Ajouter les achats
        if (purchases.isEmpty()) {
            tableModel.addRow(new Object[]{"Aucun achat récent trouvé", "", ""});
        } else {
            for (SalesDetails purchase : purchases) {
                tableModel.addRow(new Object[]{
                    purchase.getDate().format(DATE_FORMATTER),
                    purchase.isFidelityPointsUsed() ? "Oui" : "Non",
                    purchase.getComment() != null ? purchase.getComment() : ""
                });
            }
        }
    }
}
