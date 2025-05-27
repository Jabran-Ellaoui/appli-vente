package main.view;

import main.controller.*;
import main.exception.ProductModelException;
import main.model.Customer;
import main.model.Employee;
import main.model.Product;
import main.model.SalesDetails;
import main.model.ProductModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.*;

public class ReceiptPanel extends JPanel {
    SalesDetailsController salesDetailsController;
    ProductController productController;
    ProductModelController productModelController;
    CustomerController customerController;
    EmployeeController employeeController;
    MainWindow mainWindow;

    JPanel searchPanel, salesDetailsResultsPanel, productsListPanel, buttonsPanel, centerPanel;
    JLabel idLabel, info;
    JTextField idField;
    JButton backToWelcomePanel;

    // SalesDetails info labels
    JLabel idInfoLabel, quantityLabel, fidelityLabel, paymentMethodLabel, commentLabel, dateLabel, sellerLabel, buyerLabel;
    JLabel idInfo, quantityInfo, fidelityInfo, paymentMethodInfo, commentInfo, dateInfo, sellerInfo, buyerInfo;

    public ReceiptPanel(SalesDetailsController salesDetailsController, ProductController productController, ProductModelController productModelController, EmployeeController employeeController, CustomerController customerController, MainWindow mainWindow) {
        this.salesDetailsController = salesDetailsController;
        this.productController = productController;
        this.productModelController = productModelController;
        this.customerController = customerController;
        this.employeeController = employeeController;
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
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(e -> {
            mainWindow.homePage();
        });
        buttonsPanel.add(backToWelcomePanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private class IdSearchListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                int saleId = Integer.parseInt(idField.getText());
                SalesDetails salesDetails = salesDetailsController.getSalesDetails(saleId);
                ArrayList<Product> productList = productController.getAllProductsBySalesID(saleId);
                Customer customer = customerController.getCustomerById(salesDetails.getBuyer().getId());
                Employee employee = employeeController.getEmployeeById(salesDetails.getSeller().getId());
                String receipt = generateReceipt(salesDetails, productList, customer, employee);
                JOptionPane.showMessageDialog(null, receipt,"Ticket de caisse", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "L'ID doit être un entier.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la recherche : " + e.getMessage());
            }
        }
    }

    private String generateReceipt(SalesDetails salesDetails, ArrayList<Product> productList, Customer customer, Employee employee) {
        String receipt = "";
        receipt += "Le Grand Bazar\n";
        receipt += "Numéro de vente : " + salesDetails.getId() + "\n";
        receipt += "Date : " + salesDetails.getDate() + "\n";
        receipt += "Quantité : " + salesDetails.getQuantity() + "\n";
        receipt += "Points fidélité utilisés : " + (salesDetails.isFidelityPointsUsed() ? "Oui" : "Non") + "\n";
        receipt += "Méthode de paiement : " + (salesDetails.getPaymentMethod() != null ? salesDetails.getPaymentMethod() : "Non spécifiée") + "\n";
        receipt += "Commentaire : " + (salesDetails.getComment() != null ? salesDetails.getComment() : "Aucun") + "\n";
        receipt += "Acheteur : " + customer.getFirstname() + " " + customer.getLastname() + "\n";
        receipt += "Vendeur : " + employee.getFirstname() + " " + employee.getLastname() + "\n";
        receipt += generateProductList(productList);
        receipt += "Bonne journée";
        return receipt;
    }

    private String generateProductList(ArrayList<Product> productList) {
        String productsListReceipt = "";
        double prixTotal = 0;
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            try {
                int productModelId = product.getModel().getBarcode();
                String label = productModelController.getProductModelById(productModelId).getLabel();
                productsListReceipt += (i + 1) + ". " + product.getId() + " - " + label + " " + product.getUnitPrice() + "€\n";
                prixTotal += product.getUnitPrice();
            } catch (ProductModelException exception) {
                productsListReceipt += (i + 1) + ". Produit " + product.getId() + " : modèle introuvable\n";
            }
        }
        productsListReceipt += "Prix Total : " + Math.round(prixTotal) + "€\n";
        return productsListReceipt;
    }
}
