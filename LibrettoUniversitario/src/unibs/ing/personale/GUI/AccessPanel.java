package unibs.ing.personale.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import it.unibs.fp.mylib.ServizioFile;
import unibs.ing.personale.Libretto;
import unibs.ing.personale.util.JSONManager;
import unibs.ing.personale.GUI.util.*;

import static unibs.ing.personale.GUI.util.UIConstants.*;

/**
 * Panel for creating or loading a student record book (libretto)
 */
public class AccessPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private MainFrame mainApp;

    public AccessPanel(MainFrame mainApp) {
        this.mainApp = mainApp;
        initializePanel();
        createAccessInterface();
    }
    
    /**
     * Initialize the panel with basic layout and styling
     */
    private void initializePanel() {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(600, 400));
    }
    
    /**
     * Create the main access interface with title and buttons
     */
    private void createAccessInterface() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(LARGE_INSET, LARGE_INSET, LARGE_INSET, LARGE_INSET);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        createTitle(gbc);
        
        // Buttons
        createActionButtons(gbc);
    }
    
    /**
     * Create and add the title label
     */
    private void createTitle(GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("Gestione Libretto");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(WHITE_TEXT);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);
    }
    
    /**
     * Create and add the action buttons
     */
    private void createActionButtons(GridBagConstraints gbc) {
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 1;

        // Create button
        JButton creaButton = ButtonFactory.createSmallButton("Crea libretto", DIRECTORY_ICONS + "create_icon.png");
        creaButton.addActionListener(e -> creaLibretto());
        add(creaButton, gbc);

        // Load button
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        JButton caricaButton = ButtonFactory.createSmallButton("Carica libretto", DIRECTORY_ICONS + "load_icon.png");
        caricaButton.addActionListener(e -> caricaLibretto());
        add(caricaButton, gbc);
    }

    /**
     * Handle the creation of a new libretto
     */
    private void creaLibretto() {
        String nome = DialogUtils.showCustomInputDialog(this, "Inserisci il nome: ");
        if (nome != null && !nome.trim().isEmpty()) {
            mainApp.setLibretto(new Libretto(nome));
            DialogUtils.showCustomMessageDialog(this, "Libretto creato con successo per " + nome);
            mainApp.showMenuPanel();
        }
    }

    /**
     * Handle the loading of an existing libretto
     */
    private void caricaLibretto() {
        Libretto libretto = caricaGestione();

        if (libretto != null) {
            // Se l'utente conferma, carica già dentro offerToLoadExistingLibretto
            if (offerToLoadExistingLibretto(libretto)) {
                return; // esci subito per non caricare di nuovo
            }
        }
        loadLibrettoByName();
    }
    
    /**
     * Offer to load an existing libretto found in the default location
     */
    private boolean offerToLoadExistingLibretto(Libretto libretto) {
        String message = "<html>🔄 Trovato libretto: " + libretto.getNome() + 
                        ".<br>Desideri caricarlo?</html>";
        
        if (DialogUtils.showCustomConfirmDialog(this, message)) {
            loadLibretto(libretto);
            return true;
        }
        return false;
    }
    
    /**
     * Load a libretto by asking the user for the name
     */
    private void loadLibrettoByName() {
        String nome = DialogUtils.showCustomInputDialog(this, "🔍 Inserisci il nome del libretto da caricare:");
        if (nome != null && !nome.trim().isEmpty()) {
            Libretto libretto = loadLibrettoFromFile(nome);
            
            if (libretto != null) {
                loadLibretto(libretto);
            } else {
                showLibrettoNotFoundError(nome);
            }
        }
    }
    
    /**
     * Load libretto from file (tries JSON first, then classic format)
     */
    private Libretto loadLibrettoFromFile(String nome) {
        // Try JSON format first (more recent)
        File fileJSON = new File(DIRECTORY_FILE, nome + ".json");
        Libretto libretto = JSONManager.caricaJSON(fileJSON, Libretto.class);
        
        // If JSON not found, try classic format
        if (libretto == null) {
            File fileTXT = new File(DIRECTORY_FILE, nome + ".txt");
            Object loaded = ServizioFile.caricaSingoloOggetto(fileTXT);
            if (loaded instanceof Libretto) {
                libretto = (Libretto) loaded;
            }
        }
        
        return libretto;
    }
    
    /**
     * Load the libretto into the application and show success message
     */
    private void loadLibretto(Libretto libretto) {
        mainApp.setLibretto(libretto);
        DialogUtils.showCustomMessageDialog(this, "✅ Libretto caricato con successo");
        mainApp.showMenuPanel();
    }
    
    /**
     * Show error message when libretto is not found
     */
    private void showLibrettoNotFoundError(String nome) {
        String errorMessage = "❌ Errore: libretto non trovato." + 
                             "\n\nVerifica che il nome sia corretto e che il file esista in:" +
                             "\n• " + nome + ".json" +
                             "\n• " + nome + ".txt";
        DialogUtils.showCustomMessageDialog(this, errorMessage);
    }
    
    /**
     * Try to load a libretto from the default management files
     */
    private Libretto caricaGestione() {
        // Try JSON format first (more recent)
        File dstJSON = new File(DIRECTORY_FILE, "gestione.json");
        Libretto libretto = JSONManager.caricaJSON(dstJSON, Libretto.class);
        
        // If JSON not found, try classic format
        if (libretto == null) {
            File dstTXT = new File(DIRECTORY_FILE, "gestione.txt");
            Object loaded = ServizioFile.caricaSingoloOggetto(dstTXT);
            if (loaded instanceof Libretto) {
                libretto = (Libretto) loaded;
            }
        }
        
        return libretto;
    }
}