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

    private final JLabel salesDetailsIDLabel = new JLabel("ID de la vente :");
    private final JLabel salesDetailsPaymentLabel = new JLabel("Moyen de paiement :");
    private final JLabel salesDetailsDateLabel = new JLabel("Date de la vente :");
    private final JLabel salesDetailsCommentLabel = new JLabel("Commentaire :");

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

        JPanel infoPanel = new JPanel(new GridLayout(6, 1)); // 2 lignes : client + employé
        infoPanel.add(clientInfoLabel);
        infoPanel.add(employeeInfoLabel);
        infoPanel.add(salesDetailsIDLabel);
        infoPanel.add(salesDetailsDateLabel);
        infoPanel.add(salesDetailsPaymentLabel);
        infoPanel.add(salesDetailsCommentLabel);

        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Action du bouton de recherche
        searchButton.addActionListener(e ->
        {
            try {
                int clientId = Integer.parseInt(clientField.getText());
                int employeeId = Integer.parseInt(employeeField.getText());
                Date date = Date.valueOf(dateField.getText());

                ArrayList<Object[]> results = searchController.getSoldProducts(clientId, employeeId, date);

                if (!results.isEmpty())
                {
                    Object[] firstRow = results.get(0);
                    clientInfoLabel.setText("Client : " + firstRow[10] + " " + firstRow[11] + " (ID : " + firstRow[12] + ")");
                    employeeInfoLabel.setText("Employé : " + firstRow[13] + " " + firstRow[14] + " (ID : " + firstRow[15] + ")");
                    salesDetailsIDLabel.setText("ID de la vente : " + firstRow[4]);
                    salesDetailsDateLabel.setText("Date de la vente :" + firstRow[9]);
                    salesDetailsPaymentLabel.setText("Moyen de payement : " + firstRow[7]);
                    salesDetailsCommentLabel.setText("Commentaire : " + firstRow[8]);
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
