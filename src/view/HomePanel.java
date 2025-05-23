package view;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    JLabel welcomeLabel;
    public HomePanel() {
        this.setLayout(new BorderLayout());
        welcomeLabel = new JLabel("Bienvenue sur l'application de gestion du Grand Bazar");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(welcomeLabel, BorderLayout.CENTER);
    }
}
