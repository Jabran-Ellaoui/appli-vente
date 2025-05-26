package view;

import controller.SearchController;

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

    public SearchCustomerPanel(SearchController searchController, MainWindow mainWindow)
    {
        setLayout(new BorderLayout());

        // formulaire recherche
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

        // infos client/employé + tableau
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(2, 1)); // 2 lignes : client + employé
        infoPanel.add(clientInfoLabel);
        infoPanel.add(employeeInfoLabel);

        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Action du bouton de recherche
        searchButton.addActionListener(e -> {
            try {
                int clientId = Integer.parseInt(clientField.getText());
                int employeeId = Integer.parseInt(employeeField.getText());
                Date date = Date.valueOf(dateField.getText());

                ArrayList<Object[]> results = searchController.getSoldProducts(clientId, employeeId, date);

                if (!results.isEmpty())
                {
                    Object[] firstRow = results.get(0);
                    clientInfoLabel.setText("Client : " + firstRow[5] + " " + firstRow[6] + " (ID : " + firstRow[7] + ")");
                    employeeInfoLabel.setText("Employé : " + firstRow[8] + " " + firstRow[9] + " (ID : " + firstRow[10] + ")");
                }
                else
                {
                    clientInfoLabel.setText("Client : Aucun résultat");
                    employeeInfoLabel.setText("Employé : Aucun résultat");
                }

                DefaultTableModel model = new DefaultTableModel(new String[]{"Produit", "Prix", "Promotion appliquée", "Points de fidélité utilisés" }, 0);
                for (Object[] row : results)
                {
                    model.addRow(new Object[]{row[2], row[0], row[1], row[4]});
                }
                table.setModel(model);

            } catch (Exception exception)
            {
                JOptionPane.showMessageDialog(this, "Erreur : " + exception.getMessage());
            }
        });
    }
}
