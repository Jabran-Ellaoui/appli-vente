package main.view;

import controller.ProductController;
import controller.SalesDetailsController;
import exception.SalesDetailsException;
import model.Product;
import model.SalesDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReadAllSalesDetailsPanel extends JPanel {
    private SalesDetailsController salesDetailsController;
    private ProductController productController;
    private MainWindow mainWindow;

    private JPanel salesDetailsPanel, productsListPanel, centerPanel, buttonsPanel;
    private JLabel idInfo, quantityInfo, fidelityInfo, paymentMethodInfo, commentInfo, dateInfo, sellerInfo, buyerInfo;
    private JButton backToWelcomePanel, nextButton;

    private ArrayList<SalesDetails> salesDetailsList;
    private int currentIndex;
    private int totalSales;

    public ReadAllSalesDetailsPanel(SalesDetailsController salesDetailsController, ProductController productController, MainWindow mainWindow) {
        this.salesDetailsController = salesDetailsController;
        this.productController = productController;
        this.mainWindow = mainWindow;
        this.setLayout(new BorderLayout());

        try {
            salesDetailsList = salesDetailsController.getAllSalesDetails();
            currentIndex = 0;
            totalSales = salesDetailsList.size();
        } catch (SalesDetailsException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des ventes : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        salesDetailsPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        centerPanel.add(salesDetailsPanel);

        productsListPanel = new JPanel(new BorderLayout());
        centerPanel.add(productsListPanel);

        this.add(centerPanel, BorderLayout.CENTER);

        buttonsPanel = new JPanel(new FlowLayout());
        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(e -> mainWindow.homePage());
        buttonsPanel.add(backToWelcomePanel);

        nextButton = new JButton("Suivant");
        nextButton.addActionListener(new NextButtonListener());
        buttonsPanel.add(nextButton);

        this.add(buttonsPanel, BorderLayout.SOUTH);

        showSalesDetails(salesDetailsList.get(currentIndex));
    }

    private void showSalesDetails(SalesDetails salesDetails) {
        salesDetailsPanel.removeAll();
        productsListPanel.removeAll();

        JLabel idLabel = new JLabel("ID :");
        idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        idInfo = new JLabel(String.valueOf(salesDetails.getId()));
        salesDetailsPanel.add(idLabel);
        salesDetailsPanel.add(idInfo);

        JLabel quantityLabel = new JLabel("Quantité :");
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityInfo = new JLabel(String.valueOf(salesDetails.getQuantity()));
        salesDetailsPanel.add(quantityLabel);
        salesDetailsPanel.add(quantityInfo);

        JLabel fidelityLabel = new JLabel("Points de fidélité utilisés :");
        fidelityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fidelityInfo = new JLabel(salesDetails.isFidelityPointsUsed() ? "Oui" : "Non");
        salesDetailsPanel.add(fidelityLabel);
        salesDetailsPanel.add(fidelityInfo);

        JLabel paymentMethodLabel = new JLabel("Méthode de paiement :");
        paymentMethodLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        paymentMethodInfo = new JLabel(salesDetails.getPaymentMethod() != null ? salesDetails.getPaymentMethod() : "/");
        salesDetailsPanel.add(paymentMethodLabel);
        salesDetailsPanel.add(paymentMethodInfo);

        JLabel commentLabel = new JLabel("Commentaire :");
        commentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        commentInfo = new JLabel(salesDetails.getComment() != null ? salesDetails.getComment() : "/");
        salesDetailsPanel.add(commentLabel);
        salesDetailsPanel.add(commentInfo);

        JLabel dateLabel = new JLabel("Date :");
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dateInfo = new JLabel(salesDetails.getDate().toString());
        salesDetailsPanel.add(dateLabel);
        salesDetailsPanel.add(dateInfo);

        JLabel sellerLabel = new JLabel("Vendeur :");
        sellerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sellerInfo = new JLabel(salesDetails.getSeller() != null ? salesDetails.getSeller().toString() : "/");
        salesDetailsPanel.add(sellerLabel);
        salesDetailsPanel.add(sellerInfo);

        JLabel buyerLabel = new JLabel("Acheteur :");
        buyerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        buyerInfo = new JLabel(salesDetails.getBuyer() != null ? salesDetails.getBuyer().toString() : "/");
        salesDetailsPanel.add(buyerLabel);
        salesDetailsPanel.add(buyerInfo);

        try {
            List<Product> productList = productController.getAllProductsBySalesID(salesDetails.getId());

            DefaultListModel<Product> listModel = new DefaultListModel<>();
            for (Product product : productList) {
                listModel.addElement(product);
            }

            JList<Product> productJList = new JList<>(listModel);
            productJList.setVisibleRowCount(5);
            productJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(productJList);

            productsListPanel.add(new JLabel("Produits associés :"), BorderLayout.NORTH);
            productsListPanel.add(scrollPane, BorderLayout.CENTER);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'affichage des produits : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        salesDetailsPanel.revalidate();
        salesDetailsPanel.repaint();
        productsListPanel.revalidate();
        productsListPanel.repaint();
    }

    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentIndex < totalSales - 1) {
                currentIndex++;
                showSalesDetails(salesDetailsList.get(currentIndex));
            } else {
                JOptionPane.showMessageDialog(ReadAllSalesDetailsPanel.this, "Vous avez atteint la dernière vente.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
