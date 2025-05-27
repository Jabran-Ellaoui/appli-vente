package view;

import controller.LotController;
import controller.ProductModelController;
import controller.SearchController;
import exception.ConnectionException;
import exception.ProductModelException;
import exception.SearchException;
import model.Lot;
import model.ProductModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class SearchProductPanel extends JPanel {
    private final JComboBox<String> modelList = new JComboBox<>();
    private final JComboBox<String> provenanceList  = new JComboBox<>();
    private final JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
    private final JTable table;

    private final SearchController searchController;
    private final LotController lotController;

    public SearchProductPanel(SearchController searchController, LotController lotController, MainWindow main) {
        this.searchController = searchController;
        this.lotController = lotController;
        setLayout(new BorderLayout(5,5));

        // Filtres
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        modelList.addItem("Tous");
        provenanceList .addItem("Tous");
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        JButton btn = new JButton("Rechercher");

        top.add(new JLabel("Modèle:"));   top.add(modelList);
        top.add(new JLabel("Provenance:")); top.add(provenanceList);
        top.add(new JLabel("Date min:")); top.add(dateSpinner);
        top.add(btn);
        add(top, BorderLayout.NORTH);

        String[] columns = {"Prix", "Promotion", "Modèle", "Provenance", "Date"};
        table = new JTable(new DefaultTableModel(columns, 0));
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadFilters();
        btn.addActionListener(e -> search());
    }

    private void loadFilters() // Fct qui sert à charger les filtres dans la zone de recherche
    {
        try {

            for (ProductModel models : new ProductModelController().getAllProductModels()) {
                modelList.addItem(models.getLabel());
            }

            // On get la provenance de chaque lot pour les ajouter dans le tableau que si elles n'existent pas déjà
            java.util.ArrayList<String> addedProvenances = new java.util.ArrayList<>();
            for (Lot lot : lotController.getAllLot()) {
                String provenance = lot.getProvenance();
                if (!addedProvenances.contains(provenance)) {
                    addedProvenances.add(provenance);
                    provenanceList.addItem(provenance);
                }
            }
        } catch (ConnectionException | ProductModelException ex) {
            showError(ex.getMessage());
        }
    }

    private void search() {
        String selectedItem = modelList.getSelectedItem().toString();
        String modelInstruction = "Tous".equals(selectedItem) ? "%" : selectedItem;
        String selectedProvenance   = provenanceList.getSelectedItem().toString();
        String provenanceInstruction = "Tous".equals(selectedProvenance) ? "%" : selectedProvenance;
        Date dateInstruction = new Date(((java.util.Date) dateSpinner.getValue()).getTime());

        try {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String lastProvenance = "";
            String lastDate = "";

            for (Object[] r : searchController.getProductsByModelProvenanceDate(modelInstruction, provenanceInstruction, dateInstruction)) {
                String currentProvenance = (String) r[3];
                String currentDate = formatter.format(r[4]);

                Vector<Object> row = new Vector<>();
                row.add(r[0]); // Prix
                row.add(r[1]); // Promotion
                row.add(r[2]); // Modèle

                if (!currentProvenance.equals(lastProvenance) || !currentDate.equals(lastDate)) {
                    row.add(currentProvenance);
                    row.add(currentDate);
                    lastProvenance = currentProvenance;
                    lastDate = currentDate;
                } else {
                    row.add("");
                    row.add("");
                }
                tableModel.addRow(row);
            }
        } catch (SearchException ex) {
            showError(ex.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}