package unibs.ing.personale.GUI;

import javax.swing.*;
import unibs.ing.personale.Libretto;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel cards;
    private CardLayout cardLayout;
    private Libretto libretto;

    public MainFrame() {
        setTitle("Libretto Universitario - Sistema di Gestione");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBackground(new Color(60, 63, 65)); // Manteniamo il grigio originale

        // Pannello per creare o caricare il libretto
        AccessPanel initialPanel = new AccessPanel(this);
        cards.add(initialPanel, "InitialPanel");

        // Pannello per le operazioni sul libretto
        MenuPanel menuPanel = new MenuPanel(this);
        cards.add(menuPanel, "MenuPanel");

        add(cards, BorderLayout.CENTER);
        
        // Centra la finestra
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainApp = new MainFrame();
            mainApp.setVisible(true);
        });
    }

    public void showMenuPanel() {
        getContentPane().removeAll();
        getContentPane().add(new MenuPanel(this));
        revalidate();
        repaint();
    }

    public Libretto getLibretto() {
        return libretto;
    }

    public void setLibretto(Libretto libretto) {
        this.libretto = libretto;
    }
}