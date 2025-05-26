package view;

import controller.SearchController;
import exception.SearchException;

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

    public SearchCustomerPanel(SearchController searchController, MainWindow mainWindow) {
        setLayout(new BorderLayout());

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
        add(new JScrollPane(table), BorderLayout.CENTER);

        searchButton.addActionListener(e ->
        {
            try {
                int clientId = Integer.parseInt(clientField.getText());
                int employeeId = Integer.parseInt(employeeField.getText());
                Date date = Date.valueOf(dateField.getText());

                ArrayList<Object[]> results = searchController.getSoldProducts(clientId, employeeId, date);

                DefaultTableModel model = new DefaultTableModel(new String[]{"Prix", "Quantité", "Produit", "Quantité", "Points de fidélité utilisé"}, 0);
                for (Object[] row : results)
                {
                    model.addRow(row);
                }
                table.setModel(model);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });
    }
}
