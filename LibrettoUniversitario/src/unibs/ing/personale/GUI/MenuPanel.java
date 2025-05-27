package unibs.ing.personale.GUI;

import javax.swing.*;
import unibs.ing.personale.GUI.util.ButtonFactory;
import unibs.ing.personale.GUI.util.DialogUtils;
import unibs.ing.personale.GUI.util.UIConstants;
import unibs.ing.personale.Corso;
import unibs.ing.personale.util.JSONManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MenuPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private final MainFrame mainApp;

    public MenuPanel(MainFrame mainApp) {
        this.mainApp = mainApp;
        initializePanel();
        setupMainMenuButtons();
    }

    private void initializePanel() {
        setLayout(new GridBagLayout());
        setBackground(UIConstants.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(800, 400));
    }

    private void setupMainMenuButtons() {
        GridBagConstraints gbc = createDefaultConstraints();

        // First row
        gbc.gridy = 0;
        
        gbc.gridx = 0;
        JButton aggiungiButton = ButtonFactory.createStandardButton("Aggiungi", 
                UIConstants.DIRECTORY_ICONS + "add_icon.png");
        aggiungiButton.addActionListener(new MainCategoryActionListener("Aggiungi"));
        add(aggiungiButton, gbc);

        gbc.gridx = 1;
        JButton visualizzaButton = ButtonFactory.createStandardButton("Visualizza", 
                UIConstants.DIRECTORY_ICONS + "eye_icon.png");
        visualizzaButton.addActionListener(new MainCategoryActionListener("Visualizza"));
        add(visualizzaButton, gbc);

        // Second row
        gbc.gridy = 1;
        
        gbc.gridx = 0;
        JButton altroButton = ButtonFactory.createStandardButton("Altro", 
                UIConstants.DIRECTORY_ICONS + "altro_icon.png");
        altroButton.addActionListener(new MainCategoryActionListener("Altro"));
        add(altroButton, gbc);

        gbc.gridx = 1;
        JButton salvaEChiudiButton = ButtonFactory.createStandardButton("Salva e Chiudi", "");
        salvaEChiudiButton.addActionListener(e -> salvaEChiudi());
        add(salvaEChiudiButton, gbc);
    }

    private GridBagConstraints createDefaultConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UIConstants.STANDARD_INSET, UIConstants.STANDARD_INSET, 
                               UIConstants.STANDARD_INSET, UIConstants.STANDARD_INSET);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private class MainCategoryActionListener implements ActionListener {
        private final String category;

        public MainCategoryActionListener(String category) {
            this.category = category;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            removeAll();
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = createDefaultConstraints();

            switch (category) {
                case "Aggiungi":
                    setupAggiungiMenu(gbc);
                    break;
                case "Visualizza":
                    setupVisualizzaMenu(gbc);
                    break;
                case "Altro":
                    setupAltroMenu(gbc);
                    break;
            }
            revalidate();
            repaint();
        }

        private void setupAggiungiMenu(GridBagConstraints gbc) {
            createSubCategoryButton("Aggiungi corso", UIConstants.DIRECTORY_ICONS + "add_icon.png", gbc, 0, 0);
            createSubCategoryButton("Aggiungi voto", UIConstants.DIRECTORY_ICONS + "add_icon.png", gbc, 1, 0);
            createSubCategoryButton("Indietro", UIConstants.DIRECTORY_ICONS + "home_icon.png", gbc, 1, 1);
        }

        private void setupVisualizzaMenu(GridBagConstraints gbc) {
            createSubCategoryButton("Stampa media", UIConstants.DIRECTORY_ICONS + "eye_icon.png", gbc, 0, 0);
            createSubCategoryButton("Stampa corso", UIConstants.DIRECTORY_ICONS + "eye_icon.png", gbc, 0, 1);
            createSubCategoryButton("Stampa libretto", UIConstants.DIRECTORY_ICONS + "eye_icon.png", gbc, 1, 0);
            createSubCategoryButton("Indietro", UIConstants.DIRECTORY_ICONS + "home_icon.png", gbc, 1, 1);
        }

        private void setupAltroMenu(GridBagConstraints gbc) {
            createSubCategoryButton("Simulazione", UIConstants.DIRECTORY_ICONS + "bulb_icon.png", gbc, 0);
            createSubCategoryButton("Indietro", UIConstants.DIRECTORY_ICONS + "home_icon.png", gbc, 1);
        }

        private void createSubCategoryButton(String text, String iconPath, GridBagConstraints gbc, int gridy) {
            createSubCategoryButton(text, iconPath, gbc, 0, gridy);
        }
        
        private void createSubCategoryButton(String text, String iconPath, GridBagConstraints gbc, int gridx, int gridy) {
            JButton button = ButtonFactory.createStandardButton(text, iconPath);
            button.addActionListener(new SubCategoryActionListener(text));
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            add(button, gbc);
        }
    }

    private class SubCategoryActionListener implements ActionListener {
        private final String action;

        public SubCategoryActionListener(String action) {
            this.action = action;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action) {
                case "Aggiungi corso":
                    aggiungiCorso();
                    break;
                case "Aggiungi voto":
                    aggiungiVoto();
                    break;
                case "Stampa media":
                    stampaMedia();
                    stampaMediaPonderata();
                    stampaMedia110();
                    break;
                case "Stampa corso":
                    stampaCorso();
                    break;
                case "Stampa libretto":
                    stampaLibretto();
                    break;
                case "Simulazione":
                    simulazione();
                    break;
                case "Indietro":
                    mainApp.showMenuPanel();
                    break;
            }
        }
    }

    // Business logic methods
    
    public void aggiungiCorso() {
        String iD = DialogUtils.showCustomInputDialog(this, "Codice identificativo del corso:");
        if (iD == null) return;
        
        String nomeCorso = DialogUtils.showCustomInputDialog(this, "Nome del corso:");
        if (nomeCorso == null) return;
        
        String cfuInput = DialogUtils.showCustomInputDialog(this, "Crediti Formativi Universitari (CFU):");
        if (cfuInput == null) return;
        
        try {
            int CFU = Integer.parseInt(cfuInput);
            boolean domanda = DialogUtils.showCustomConfirmDialog(this, "Vuoi registrare il voto subito?");
            
            String annoInput = DialogUtils.showCustomInputDialog(this, "Anno accademico:");
            if (annoInput == null) return;
            
            int anno = Integer.parseInt(annoInput);
            
            if (domanda) {
                String votoInput = DialogUtils.showCustomInputDialog(this, "Voto conseguito:");
                if (votoInput == null) return;
                
                int voto = Integer.parseInt(votoInput);
                boolean lode = voto == 30 && DialogUtils.showCustomConfirmDialog(this, "Hai ottenuto la lode?");
                mainApp.getLibretto().aggiungiCorso(new Corso(nomeCorso, voto, lode, CFU, anno), iD);
            } else {
                mainApp.getLibretto().aggiungiCorso(new Corso(nomeCorso, CFU, anno), iD);
            }
        } catch (NumberFormatException e) {
            DialogUtils.showCustomMessageDialog(this, "Errore: inserire valori numerici validi.");
        }
    }

    public void aggiungiVoto() {
        String iD_daCercare = DialogUtils.showCustomInputDialog(this, "Codice identificativo del corso:");
        if (iD_daCercare == null) return;
        
        String votoInput = DialogUtils.showCustomInputDialog(this, "Voto conseguito:");
        if (votoInput == null) return;
        
        try {
            int voto_daAggiungere = Integer.parseInt(votoInput);
            mainApp.getLibretto().aggiungiVoto(voto_daAggiungere, iD_daCercare);
            
            if (voto_daAggiungere == 30) {
                boolean lode_daAggiungere = DialogUtils.showCustomConfirmDialog(this, "Hai ottenuto la lode?");
                mainApp.getLibretto().aggiungiLode(lode_daAggiungere, iD_daCercare);
            }
        } catch (NumberFormatException e) {
            DialogUtils.showCustomMessageDialog(this, "Errore: inserire un voto valido.");
        }
    }

    public void stampaMedia() {
        double media = mainApp.getLibretto().calcolaMedia();
        DialogUtils.showCustomMessageDialog(this, "La media aritmetica attuale è: " + String.format("%.2f", media));
    }
    
    public void stampaMediaPonderata() {
        double mediaPonderata = mainApp.getLibretto().calcolaMediaPonderata();
        DialogUtils.showCustomMessageDialog(this, "La media ponderata è: " + String.format("%.2f", mediaPonderata));
    }
    
    public void stampaMedia110() {
        double media110 = mainApp.getLibretto().calcolaMedia110();
        DialogUtils.showCustomMessageDialog(this, "La media rapportata a 110 è: " + String.format("%.2f", media110));
    }

    public void stampaCorso() {
        String iD_daCercare = DialogUtils.showCustomInputDialog(this, "Inserisci ID o nome del corso da cercare:");
        if (iD_daCercare == null) return;
        
        Corso corso = mainApp.getLibretto().getCorso(iD_daCercare);
        if (corso == null) {
            corso = findCorsoByName(iD_daCercare);
        }
        
        if (corso != null) {
            DialogUtils.showCustomMessageDialog(this, "Dettagli corso:\n\n" + corso.toString());
        } else {
            DialogUtils.showCustomMessageDialog(this, "Corso non trovato.");
        }
    }

    public void stampaLibretto() {
        DialogUtils.showCustomScrollableMessageDialog(this, mainApp.getLibretto().toString(), "Libretto completo");
    }

    public void simulazione() {
        try {
            String corsiInput = DialogUtils.showCustomInputDialog(this, "Numero di corsi da simulare:");
            if (corsiInput == null) return;
            
            int corsi_daSimulare = Integer.parseInt(corsiInput);
            ArrayList<Corso> arraylist = new ArrayList<>();
            
            for (int i = 0; i < corsi_daSimulare; i++) {
                String iD_daSimulare = DialogUtils.showCustomInputDialog(this, 
                        "Codice identificativo corso " + (i+1) + ":");
                if (iD_daSimulare == null) return;
                
                String votoInput = DialogUtils.showCustomInputDialog(this, 
                        "Voto ipotetico per il corso " + (i+1) + ":");
                if (votoInput == null) return;
                
                int voto_daSimulare = Integer.parseInt(votoInput);
                boolean lode_daSimulare = voto_daSimulare == 30 && 
                        DialogUtils.showCustomConfirmDialog(this, "Lode per questo corso?");
                
                Corso corso = findCorso(iD_daSimulare);
                if (corso != null) {
                    arraylist.add(new Corso(corso.getNomeCorso(), voto_daSimulare, 
                                          lode_daSimulare, corso.getCFU(), corso.getAnno()));
                }
            }
            
            double mediaSimulata = mainApp.getLibretto().calcolaMedia(arraylist);
            double mediaPonderataSimulata = mainApp.getLibretto().calcolaMediaPonderata(arraylist);
            double media110Simulata = mainApp.getLibretto().calcolaMedia110(arraylist);
            
            String risultati = "Risultati simulazione:\n\n" +
                    "Media aritmetica: " + String.format("%.2f", mediaSimulata) + "\n" +
                    "Media ponderata: " + String.format("%.2f", mediaPonderataSimulata) + "\n" +
                    "Media in 110: " + String.format("%.2f", media110Simulata);
            
            DialogUtils.showCustomMessageDialog(this, risultati);
            
        } catch (NumberFormatException e) {
            DialogUtils.showCustomMessageDialog(this, "Errore: inserire valori numerici validi.");
        } catch (Exception e) {
            DialogUtils.showCustomMessageDialog(this, "Errore durante la simulazione: " + e.getMessage());
        }
    }

    private void salvaEChiudi() {
        if (mainApp.getLibretto() != null) {
            String nome = mainApp.getLibretto().getNome();
            
            // Save in JSON format (main format)
            File dstJSON = new File(UIConstants.DIRECTORY_FILE, nome + ".json");
            File dst2JSON = new File(UIConstants.DIRECTORY_FILE, "gestione.json");
            
            boolean salvataggioOK = JSONManager.salvaJSON(mainApp.getLibretto(), dstJSON) &&
                                   JSONManager.salvaJSON(mainApp.getLibretto(), dst2JSON);

            String message = salvataggioOK ? 
                    "✅ Libretto salvato con successo.\n📁 Percorso: " + dstJSON.getName() + 
                    "\n\nGrazie per aver utilizzato il sistema!" :
                    "⚠️ Salvataggio parzialmente completato.\n\nIl backup in formato classico è stato creato.";
            
            DialogUtils.showCustomMessageDialog(this, message);
        }
        System.exit(0);
    }

    // Helper methods
    
    private Corso findCorso(String identifier) {
        Corso corso = mainApp.getLibretto().getCorso(identifier);
        return corso != null ? corso : findCorsoByName(identifier);
    }
    
    private Corso findCorsoByName(String name) {
        for (Corso corsoLibretto : mainApp.getLibretto().getLibretto().values()) {
            if (corsoLibretto.getNomeCorso().equals(name)) {
                return corsoLibretto;
            }
        }
        return null;
    }
}