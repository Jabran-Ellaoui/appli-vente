package main.view;

import main.controller.*;
import main.exception.ConnectionException;
import main.exception.SearchException;
import main.model.*;
import main.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainWindow extends JFrame

{
    Container frameContainer;
    private JMenuBar menuBar;
    private JMenu file, administration, productModelAdministration, salesDetailsAdministration, search, helpMenu, loyaltyMenu;
    private JMenuItem exit, createProductModel, deleteProductModel, updateProductModel, readOneProductModel, readAllProductModel, createSalesDetails, deleteSalesDetails, updateSalesDetails, readOneSalesDetails, readAllSalesDetails, searchProduct, searchSalesDetails, searchCustomer, animation, helpItem, customerLoyalty;

    public MainWindow() {
        super("Le Grand Bazar");
        setBounds(100,100,700,700);

        // partie container
        frameContainer = this.getContentPane();
        frameContainer.setLayout(new BorderLayout());

        // partie menu
        // création menubar
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // création menu
        file = new JMenu("Fichier");
        menuBar.add(file);

        administration = new JMenu("Administration");
        menuBar.add(administration);

        productModelAdministration = new JMenu("Type de produit");
        administration.add(productModelAdministration);

        salesDetailsAdministration = new JMenu("Vente");
        administration.add(salesDetailsAdministration);

        search = new JMenu("Recherche");
        menuBar.add(search);

        loyaltyMenu = new JMenu("Fidélité");
        menuBar.add(loyaltyMenu);

        helpMenu = new JMenu("Aide");
        menuBar.add(helpMenu);

        // création menuitem
        // Fichier
        exit = new JMenuItem("Quitter");
        exit.addActionListener(e -> {
            String[] options = {"Oui", "Non"};
            int confirm = JOptionPane.showOptionDialog(this, "Quitter l'application ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });
        file.add(exit);

        animation = new JMenuItem("Animation");
        animation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AnimationPanel animationPanel = new AnimationPanel(MainWindow.this);
                switchPanel(animationPanel);
            }
        });
        file.add(animation);

        // Administration
        // Produit
        createProductModel = new JMenuItem("Créer Produit");
        createProductModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ProductModelController productModelController = new ProductModelController();
                    LotController lotController = new LotController();
                    CreateProductModelPanel createProductModelPanel = new CreateProductModelPanel(productModelController, lotController, MainWindow.this);
                    switchPanel(createProductModelPanel);
                } catch (ConnectionException exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog (null, exception.toString(), "Erreur de connection", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        productModelAdministration.add(createProductModel);

        deleteProductModel = new JMenuItem("Supprimer Produit");
        deleteProductModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ProductModelController productModelController = new ProductModelController();
                    DeleteProductModelPanel deleteProductModelPanel = new DeleteProductModelPanel(productModelController, MainWindow.this);
                    switchPanel(deleteProductModelPanel);
                } catch (ConnectionException exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog (null, exception.toString(), "Erreur de connection", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        productModelAdministration.add(deleteProductModel);

        updateProductModel = new JMenuItem("Mettre à jour Produit");
        updateProductModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ProductModelController productModelController = new ProductModelController();
                    LotController lotController = new LotController();
                    UpdateProductModelPanel updateProductModelPanel = new UpdateProductModelPanel(productModelController, lotController, MainWindow.this);
                    switchPanel(updateProductModelPanel);
                } catch (ConnectionException exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog (null, exception.toString(), "Erreur de connection", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        productModelAdministration.add(updateProductModel);

        readOneProductModel = new JMenuItem("Voir un Produit");
        readOneProductModel.addActionListener(e -> {
            try {
                ProductModelController productModelController = new ProductModelController();
                ReadOneProductModelPanel readOneProductModelPanel = new ReadOneProductModelPanel(productModelController, MainWindow.this);
                switchPanel(readOneProductModelPanel);
            } catch (ConnectionException exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog (null, exception.toString(), "Erreur de connection", JOptionPane.ERROR_MESSAGE);
            }
        });
        productModelAdministration.add(readOneProductModel);

        readAllProductModel = new JMenuItem("Voir tous les Produits");
        readAllProductModel.addActionListener(e -> {
            try {
                ProductModelController productModelController = new ProductModelController();
                ReadAllProductModelPanel readAllProductModelPanel = new ReadAllProductModelPanel(productModelController, MainWindow.this);
                switchPanel(readAllProductModelPanel);
            } catch (ConnectionException exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog (null, exception.toString(), "Erreur de connection", JOptionPane.ERROR_MESSAGE);
            }
        });
        productModelAdministration.add(readAllProductModel);

        // Sales Details
        createSalesDetails = new JMenuItem("Créer Vente");
        createSalesDetails.addActionListener(e -> {
            try {
                SalesDetailsController salesDetailsController = new SalesDetailsController();
                EmployeeController employeeController = new EmployeeController();
                CustomerController customerController = new CustomerController();
                ProductController productController = new ProductController();
                CreateSalesDetailsPanel createSalesDetailsPanel = new CreateSalesDetailsPanel(salesDetailsController, employeeController, customerController, productController, MainWindow.this);
                switchPanel(createSalesDetailsPanel);
            } catch (ConnectionException exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog (null, exception.toString(), "Erreur de connection", JOptionPane.ERROR_MESSAGE);
            }

        });
        salesDetailsAdministration.add(createSalesDetails);

        deleteSalesDetails = new JMenuItem("Supprimer Vente");
        deleteSalesDetails.addActionListener(e -> {

        });
        salesDetailsAdministration.add(deleteSalesDetails);

        updateSalesDetails = new JMenuItem("Mettre à jour Vente");
        updateSalesDetails.addActionListener(e -> {

        });
        salesDetailsAdministration.add(updateSalesDetails);

        readOneSalesDetails = new JMenuItem("Voir une Vente");
        readOneSalesDetails.addActionListener(e -> {

        });
        salesDetailsAdministration.add(readOneSalesDetails);

        readAllSalesDetails = new JMenuItem("Voir toutes les Ventes");
        readAllSalesDetails.addActionListener(e-> {

        });
        salesDetailsAdministration.add(readAllSalesDetails);

        // Recherches
        searchCustomer = new JMenuItem("Recherche Client");
        searchCustomer.addActionListener(e -> {
            try
            {
                SearchController searchController = new SearchController();
                SearchCustomerPanel searchCustomerPanel = new SearchCustomerPanel(searchController, MainWindow.this);
                switchPanel(searchCustomerPanel);
            } catch (SearchException exception)
            {
                exception.printStackTrace();
                JOptionPane.showMessageDialog (null, exception.toString(), "Erreur de connection", JOptionPane.ERROR_MESSAGE);
            }

        });
        search.add(searchCustomer);

        searchProduct = new JMenuItem("Recherche Produit");
        searchProduct.addActionListener(e -> {
            try{
                SearchController searchController = new SearchController();
                LotController lotController = new LotController();
                SearchProductPanel searchProductPanel = new SearchProductPanel(searchController, lotController,MainWindow.this);
                switchPanel(searchProductPanel);
            } catch (SearchException exception)
            {
                exception.printStackTrace();
                JOptionPane.showMessageDialog (null, exception.toString(), "Erreur de connection", JOptionPane.ERROR_MESSAGE);
            } catch (ConnectionException ex) {
                throw new RuntimeException(ex);
            }

        });
        search.add(searchProduct);

        searchSalesDetails = new JMenuItem("Recherche Vente");
        searchSalesDetails.addActionListener(e -> {
            try {
                SearchController searchController = new SearchController();
                CustomerController customerController = new CustomerController();
                SearchSalesPanel searchSalesPanel = new SearchSalesPanel(searchController, customerController, MainWindow.this);
                switchPanel(searchSalesPanel);
            } catch (SearchException | ConnectionException ex) {
                throw new RuntimeException(ex);
            }

        });
        search.add(searchSalesDetails);

        // Menu Fidélité
        customerLoyalty = new JMenuItem("Points de fidélité client");
        customerLoyalty.addActionListener(e -> {
            CustomerLoyaltyPanel customerLoyaltyPanel = new CustomerLoyaltyPanel(MainWindow.this);
            switchPanel(customerLoyaltyPanel);
        });
        loyaltyMenu.add(customerLoyalty);

        // Aide
        helpItem = new JMenuItem("Documentation");
        helpItem.addActionListener(e -> {
            HelpPanel helpPanel = new HelpPanel(MainWindow.this);
            switchPanel(helpPanel);
        });
        helpMenu.add(helpItem);


        // partie panneau principal
        HomePanel homePanel = new HomePanel();
        frameContainer.add(homePanel,BorderLayout.NORTH);


        this.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e) {
                System.exit(0);
            }
        } );
        setVisible(true);
    }

    private class ExitListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }

    public void homePage() {
        HomePanel homePanel = new HomePanel();
        switchPanel(homePanel);
    }

    private void switchPanel(JPanel newPanel) {
        frameContainer.removeAll();
        frameContainer.add(newPanel, BorderLayout.CENTER);
        frameContainer.revalidate();
        frameContainer.repaint();
    }

}
