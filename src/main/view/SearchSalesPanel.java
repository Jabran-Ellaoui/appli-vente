package main.view;

import main.controller.SearchController;
import main.controller.CustomerController;
import main.exception.SearchException;
import main.exception.CustomerException;
import main.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchSalesPanel extends JPanel {

    private final JComboBox<String> customerComboBox;
    private final JSpinner startDateSpinner;
    private final JSpinner endDateSpinner;
    private final JTable resultTable;
    private final DefaultTableModel tableModel;

    // Mapping du label affiché → ID réel du client
    private final Map<String, Integer> customerMap = new LinkedHashMap<>();

    public SearchSalesPanel(SearchController searchController,
                            CustomerController customerController,
                            MainWindow mainWindow) {
        setLayout(new BorderLayout(10, 10));

        // ==== 1) Création du panneau de saisie ====
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        customerComboBox = new JComboBox<>();
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner   = new JSpinner(new SpinnerDateModel());

        // Formatage des JSpinner en "yyyy-MM-dd"
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startEditor);
        JSpinner.DateEditor endEditor   = new JSpinner.DateEditor(endDateSpinner,   "yyyy-MM-dd");
        endDateSpinner.setEditor(endEditor);

        inputPanel.add(new JLabel("Client (Nom Prénom ID) :"));
        inputPanel.add(customerComboBox);
        inputPanel.add(new JLabel("Date début :"));
        inputPanel.add(startDateSpinner);
        inputPanel.add(new JLabel("Date fin :"));
        inputPanel.add(endDateSpinner);

        // ==== 2) Bouton de recherche ====
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> performSearch(searchController));

        // on crée un panel dédié, centré et à taille naturelle
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(searchButton);

        // ==== 3) Tableau de résultats ====
        String[] columnNames = {"Prénom", "Nom", "Produits achetés", "Total dépensé"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);

        // ==== 4) Assemblage ====
        add(inputPanel,    BorderLayout.NORTH);
        add(buttonPanel,  BorderLayout.CENTER);
        add(new JScrollPane(resultTable), BorderLayout.SOUTH);

        // ==== 5) Chargement dynamique des clients ====
        loadCustomers(customerController);
    }

    private void loadCustomers(CustomerController customerController) {
        try {
            ArrayList<Customer> customers = customerController.getAllCustomers();
            customerMap.clear();
            customerComboBox.removeAllItems();

            for (Customer c : customers) {
                String label = String.format("%s %s (ID: %d)",
                        c.getFirstname(),
                        c.getLastname(),
                        c.getId());
                customerMap.put(label, c.getId());
                customerComboBox.addItem(label);
            }

        } catch (CustomerException ex) {
            JOptionPane.showMessageDialog(this,
                    "Impossible de charger la liste des clients :\n" + ex.getMessage(),
                    "Erreur de chargement", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performSearch(SearchController controller) {
        try {
            String selected = (String) customerComboBox.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez sélectionner un client.",
                        "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int clientId = customerMap.get(selected);

            // Conversion java.util.Date → java.sql.Date
            java.util.Date uStart = (java.util.Date) startDateSpinner.getValue();
            java.util.Date uEnd   = (java.util.Date) endDateSpinner.getValue();
            Date sqlStart = new Date(uStart.getTime());
            Date sqlEnd   = new Date(uEnd.getTime());

            if (sqlStart.after(sqlEnd)) {
                JOptionPane.showMessageDialog(this,
                        "La date de début doit être antérieure à la date de fin.",
                        "Erreur de dates", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Appel du SearchController
            ArrayList<Object[]> results = controller.getSalesSummary(clientId, sqlStart, sqlEnd);

            // Rafraîchir le tableau
            tableModel.setRowCount(0);
            for (Object[] row : results) {
                tableModel.addRow(row);
            }

        } catch (SearchException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la recherche :\n" + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
