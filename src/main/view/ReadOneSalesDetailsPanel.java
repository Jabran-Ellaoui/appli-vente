package main.view;

import main.controller.ProductController;
import main.controller.SalesDetailsController;
import main.model.Product;
import main.model.SalesDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReadOneSalesDetailsPanel extends JPanel {
    SalesDetailsController salesDetailsController;
    ProductController productController;
    MainWindow mainWindow;

    JPanel searchPanel, salesDetailsResultsPanel, productsListPanel, buttonsPanel, centerPanel;
    JLabel idLabel, info;
    JTextField idField;
    JButton backToWelcomePanel;

    // SalesDetails info labels
    JLabel idInfoLabel, quantityLabel, fidelityLabel, paymentMethodLabel, commentLabel, dateLabel, sellerLabel, buyerLabel;
    JLabel idInfo, quantityInfo, fidelityInfo, paymentMethodInfo, commentInfo, dateInfo, sellerInfo, buyerInfo;

    public ReadOneSalesDetailsPanel(SalesDetailsController salesDetailsController, ProductController productController, MainWindow mainWindow) {
        this.salesDetailsController = salesDetailsController;
        this.productController = productController;
        this.mainWindow = mainWindow;
        this.setLayout(new BorderLayout());

        // Search Panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2));

        idLabel = new JLabel("ID Vente :");
        idField = new JTextField(9);
        IdSearchListener idSearchListener = new IdSearchListener();
        idField.addActionListener(idSearchListener);
        info = new JLabel("Appuyez sur Entrée après la saisie");

        searchPanel.add(idLabel);
        searchPanel.add(idField);
        searchPanel.add(info);
        this.add(searchPanel, BorderLayout.NORTH);

        // Center Panel = SalesDetails info + Product list
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2, 10, 10));

        salesDetailsResultsPanel = new JPanel();
        salesDetailsResultsPanel.setLayout(new GridLayout(8, 2, 5, 5));
        centerPanel.add(salesDetailsResultsPanel);

        productsListPanel = new JPanel();
        productsListPanel.setLayout(new BorderLayout());
        centerPanel.add(productsListPanel);

        this.add(centerPanel, BorderLayout.CENTER);

        // Buttons panel
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainWindow.homePage();
            }
        });
        buttonsPanel.add(backToWelcomePanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void createSalesDetailsResultsPanel(SalesDetails salesDetails, List<Product> productList) {
        salesDetailsResultsPanel.removeAll();
        productsListPanel.removeAll();

        // SalesDetails display
        idInfoLabel = new JLabel("ID :");
        idInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        idInfo = new JLabel(String.valueOf(salesDetails.getId()));
        salesDetailsResultsPanel.add(idInfoLabel);
        salesDetailsResultsPanel.add(idInfo);

        quantityLabel = new JLabel("Quantité :");
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityInfo = new JLabel(String.valueOf(salesDetails.getQuantity()));
        salesDetailsResultsPanel.add(quantityLabel);
        salesDetailsResultsPanel.add(quantityInfo);

        fidelityLabel = new JLabel("Points de fidélité utilisés :");
        fidelityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fidelityInfo = new JLabel(salesDetails.isFidelityPointsUsed() ? "Oui" : "Non");
        salesDetailsResultsPanel.add(fidelityLabel);
        salesDetailsResultsPanel.add(fidelityInfo);

        paymentMethodLabel = new JLabel("Méthode de paiement :");
        paymentMethodLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        paymentMethodInfo = new JLabel(salesDetails.getPaymentMethod() != null ? salesDetails.getPaymentMethod() : "/");
        salesDetailsResultsPanel.add(paymentMethodLabel);
        salesDetailsResultsPanel.add(paymentMethodInfo);

        commentLabel = new JLabel("Commentaire :");
        commentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        commentInfo = new JLabel(salesDetails.getComment() != null ? salesDetails.getComment() : "/");
        salesDetailsResultsPanel.add(commentLabel);
        salesDetailsResultsPanel.add(commentInfo);

        dateLabel = new JLabel("Date :");
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dateInfo = new JLabel(salesDetails.getDate().toString());
        salesDetailsResultsPanel.add(dateLabel);
        salesDetailsResultsPanel.add(dateInfo);

        sellerLabel = new JLabel("Vendeur :");
        sellerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sellerInfo = new JLabel(salesDetails.getSeller() != null ? salesDetails.getSeller().toString() : "/");
        salesDetailsResultsPanel.add(sellerLabel);
        salesDetailsResultsPanel.add(sellerInfo);

        buyerLabel = new JLabel("Acheteur :");
        buyerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        buyerInfo = new JLabel(salesDetails.getBuyer() != null ? salesDetails.getBuyer().toString() : "/");
        salesDetailsResultsPanel.add(buyerLabel);
        salesDetailsResultsPanel.add(buyerInfo);

        // Product List display
        DefaultListModel<Product> productListModel = new DefaultListModel<>();
        for (Product product : productList) {
            productListModel.addElement(product);
        }

        JList<Product> productJList = new JList<>(productListModel);
        productJList.setVisibleRowCount(5);
        productJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productJList);
        productsListPanel.add(new JLabel("Produits liés à la vente :"), BorderLayout.NORTH);
        productsListPanel.add(scrollPane, BorderLayout.CENTER);

        salesDetailsResultsPanel.revalidate();
        salesDetailsResultsPanel.repaint();
        productsListPanel.revalidate();
        productsListPanel.repaint();
    }

    private class IdSearchListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                int saleId = Integer.parseInt(idField.getText());
                SalesDetails salesDetails = salesDetailsController.getSalesDetails(saleId);
                List<Product> productList = productController.getAllProductsBySalesID(saleId);
                createSalesDetailsResultsPanel(salesDetails, productList);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(ReadOneSalesDetailsPanel.this, "L'ID doit être un entier.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(ReadOneSalesDetailsPanel.this, "Erreur lors de la recherche : " + e.getMessage());
            }
        }
    }
}