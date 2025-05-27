package main.view;

import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {
    private final MainWindow mainWindow;

    public HelpPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());

        // Titre
        JLabel title = new JLabel("Documentation de l'application", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        add(title, BorderLayout.NORTH);

        // Contenu dans un scroll
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Sections de la documentation
        content.add(section("1. Présentation générale",
                "Notre application permet de gérer un magasin de ventes et fourni les différents outils nécessaires à l'administration des produits."));
        content.add(section("2. Gestion des produits",
                "En cliquant sur Administration -> Type de produits, vous pouvez ajouter, modifier ou supprimer des produits et voir tous les produits à disposition."));
        content.add(section("3. Administration des ventes",
                "En cliquant sur Administration -> Vente, vous pouvez ajouter, modifier, supprimet une vente et voir toutes celles déjà réalisées."));
        content.add(section("4. Recherche ",
                "En cliquant sur Recherche, vous pouvez effectuer une recherche d'un client, d'un produit ou d'une vente."));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);

        // Bouton retour en bas
        JButton back = new JButton("Retour");
        back.addActionListener(e -> mainWindow.homePage());
        JPanel south = new JPanel();
        south.add(back);
        add(south, BorderLayout.SOUTH);
    }

    private JPanel section(String heading, String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel lbl = new JLabel(heading);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(lbl, BorderLayout.NORTH);
        JTextArea area = new JTextArea(text);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        panel.add(area, BorderLayout.CENTER);
        return panel;
    }
}
