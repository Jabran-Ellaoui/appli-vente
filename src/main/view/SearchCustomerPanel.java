package main.view;

import main.controller.SearchController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;

public class SearchCustomerPanel extends JPanel {

    private final JTextField clientField = new JTextField(5);
    private final JTextField employeeField = new JTextField(5);
    private final JTextField dateField = new JTextField(10);
    private final JTable table = new JTable();

    private final JLabel clientInfoLabel = new JLabel("Client : ");
    private final JLabel employeeInfoLabel = new JLabel("Employé : ");

    private final JLabel salesDetailsIDLabel = new JLabel("ID de la vente :");
    private final JLabel salesDetailsPaymentLabel = new JLabel("Moyen de paiement :");
    private final JLabel salesDetailsDateLabel = new JLabel("Date de la vente :");
    private final JLabel salesDetailsCommentLabel = new JLabel("Commentaire :");
    private final JLabel salesDetailsFidelityLabel = new JLabel("Points de fidélités utilisés : ");

    public SearchCustomerPanel(SearchController searchController, MainWindow mainWindow) {
        setLayout(new BorderLayout());

        // Formulaire de recherche
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Client ID:"));
        topPanel.add(clientField);
        topPanel.add(new JLabel("Employé ID:"));
        topPanel.add(employeeField);
        topPanel.add(new JLabel("Date (yyyy-mm-dd):"));
        topPanel.add(dateField);

        JButton searchButton = new JButton("Rechercher");
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Informations + tableau
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(7, 1));
        infoPanel.add(clientInfoLabel);
        infoPanel.add(employeeInfoLabel);
        infoPanel.add(salesDetailsIDLabel);
        infoPanel.add(salesDetailsDateLabel);
        infoPanel.add(salesDetailsPaymentLabel);
        infoPanel.add(salesDetailsCommentLabel);
        infoPanel.add(salesDetailsFidelityLabel);

        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Action de recherche
        searchButton.addActionListener(e -> {
            try {
                int clientId = Integer.parseInt(clientField.getText());
                int employeeId = Integer.parseInt(employeeField.getText());
                Date date = Date.valueOf(dateField.getText());

                ArrayList<Object[]> results = searchController.getSoldProducts(clientId, employeeId, date);

                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Aucun résultat trouvé pour cette recherche.",
                            "Recherche vide",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Réinitialiser les champs info
                    clientInfoLabel.setText("Client : ");
                    employeeInfoLabel.setText("Employé :");
                    salesDetailsIDLabel.setText("ID de la vente : ");
                    salesDetailsDateLabel.setText("Date de la vente : ");
                    salesDetailsPaymentLabel.setText("Moyen de paiement : ");
                    salesDetailsCommentLabel.setText("Commentaire : ");
                    salesDetailsFidelityLabel.setText("Points de fidélités utilisés : ");

                    // Vider la table
                    table.setModel(new DefaultTableModel(
                            new String[]{"Produit", "Prix", "Promotion appliquée (en %)"}, 0
                    ));
                    return;
                }

                Object[] firstRow = results.get(0);
                clientInfoLabel.setText("Client : " + firstRow[10] + " " + firstRow[11] + " (ID : " + firstRow[12] + ")");
                employeeInfoLabel.setText("Employé : " + firstRow[13] + " " + firstRow[14] + " (ID : " + firstRow[15] + ")");
                salesDetailsIDLabel.setText("ID de la vente : " + firstRow[4]);
                salesDetailsDateLabel.setText("Date de la vente : " + firstRow[9]);
                salesDetailsPaymentLabel.setText("Moyen de paiement : " + firstRow[7]);
                salesDetailsCommentLabel.setText("Commentaire : " + firstRow[8]);
                salesDetailsFidelityLabel.setText("Points de fidélités utilisés : " + firstRow[6]);

                // Remplir la table
                DefaultTableModel model = new DefaultTableModel(
                        new String[]{"Produit", "Prix", "Promotion appliquée (en %)"}, 0);
                for (Object[] row : results) {
                    model.addRow(new Object[]{row[3], row[1], row[2]});
                }
                table.setModel(model);

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Erreur : " + exception.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
