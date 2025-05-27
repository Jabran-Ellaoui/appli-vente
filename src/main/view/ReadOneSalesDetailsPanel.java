package main.view;

import main.controller.ProductController;
import main.controller.SalesDetailsController;
import main.exception.SalesDetailsException;
import main.model.Product;
import main.model.SalesDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReadOneSalesDetailsPanel extends JPanel {
    private final SalesDetailsController salesDetailsController;
    private final ProductController productController;
    private final MainWindow mainWindow;

    private final JTextField idField = new JTextField(9);
    private final JPanel salesDetailsResultsPanel = new JPanel(new GridLayout(8, 2, 5, 5));
    private final JPanel productsListPanel = new JPanel(new BorderLayout());

    public ReadOneSalesDetailsPanel(SalesDetailsController salesDetailsController, ProductController productController, MainWindow mainWindow) {
        this.salesDetailsController = salesDetailsController;
        this.productController = productController;
        this.mainWindow = mainWindow;

        setLayout(new BorderLayout());

        // ----- Search panel -----
        JPanel searchPanel = new JPanel(new GridLayout(2, 2));
        searchPanel.add(new JLabel("ID Vente :"));
        searchPanel.add(idField);
        searchPanel.add(new JLabel("Appuyez sur Entrée après la saisie"));
        idField.addActionListener(new IdSearchListener());
        add(searchPanel, BorderLayout.NORTH);

        // ----- Center panel -----
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(salesDetailsResultsPanel);
        centerPanel.add(productsListPanel);
        add(centerPanel, BorderLayout.CENTER);

        // ----- Buttons panel -----
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> mainWindow.homePage());
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void displaySalesDetails(SalesDetails sd, List<Product> products) {
        salesDetailsResultsPanel.removeAll();
        productsListPanel.removeAll();

        // Infos vente
        addLabel("ID :", String.valueOf(sd.getId()));
        addLabel("Quantité :", String.valueOf(sd.getQuantity()));
        addLabel("Points fidélité utilisés :", sd.isFidelityPointsUsed() ? "Oui" : "Non");
        addLabel("Méthode de paiement :", sd.getPaymentMethod() != null ? sd.getPaymentMethod() : "/");
        addLabel("Commentaire :", sd.getComment() != null ? sd.getComment() : "/");
        addLabel("Date :", sd.getDate().toString());
        addLabel("Vendeur :", sd.getSeller() != null ? String.valueOf(sd.getSeller().getId()) : "/");
        addLabel("Acheteur :", sd.getBuyer() != null ? String.valueOf(sd.getBuyer().getId()) : "/");

        // Produits liés
        DefaultListModel<Product> model = new DefaultListModel<>();
        for (Product p : products) model.addElement(p);
        JList<Product> productList = new JList<>(model);
        productsListPanel.add(new JLabel("Produits liés à la vente :"), BorderLayout.NORTH);
        productsListPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void addLabel(String label, String value) {
        JLabel key = new JLabel(label);
        key.setHorizontalAlignment(SwingConstants.RIGHT);
        salesDetailsResultsPanel.add(key);
        salesDetailsResultsPanel.add(new JLabel(value));
    }

    private class IdSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = idField.getText().trim();

            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(ReadOneSalesDetailsPanel.this,
                        "Veuillez entrer un ID.",
                        "Champ vide", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int saleId;
            try {
                saleId = Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ReadOneSalesDetailsPanel.this,
                        "L'ID doit être un entier valide.",
                        "Format invalide", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                SalesDetails sd = salesDetailsController.getSalesDetails(saleId);
                List<Product> products = productController.getAllProductsBySalesID(saleId);
                displaySalesDetails(sd, products);
            } catch (SalesDetailsException ex) {
                JOptionPane.showMessageDialog(ReadOneSalesDetailsPanel.this,
                        "Aucune vente trouvée pour l'ID " + saleId,
                        "Vente introuvable", JOptionPane.WARNING_MESSAGE);
                salesDetailsResultsPanel.removeAll();
                productsListPanel.removeAll();
                revalidate();
                repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ReadOneSalesDetailsPanel.this,
                        "Erreur inattendue : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
