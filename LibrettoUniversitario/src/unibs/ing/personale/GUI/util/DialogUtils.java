package unibs.ing.personale.GUI.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * Utility class for creating custom styled dialog boxes
 */
public class DialogUtils {
    
    private static final Color PANEL_BACKGROUND = new Color(250, 250, 250);
    private static final Color TEXT_COLOR = new Color(45, 45, 45);
    private static final Color BORDER_COLOR = new Color(180, 180, 180);
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 17);
    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font MONO_FONT = new Font("Monospaced", Font.PLAIN, 14);
    
    /**
     * Shows a custom input dialog with styled components
     */
    public static String showCustomInputDialog(Component parent, String message) {
        JPanel panel = createBasePanel();
        GridBagConstraints gbc = createDefaultConstraints();

        JLabel label = createStyledLabel(message);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        JTextField textField = createStyledTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(textField, gbc);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Input Richiesto", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        return result == JOptionPane.OK_OPTION ? textField.getText() : null;
    }

    /**
     * Shows a custom message dialog with styled components
     */
    public static void showCustomMessageDialog(Component parent, String message) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(PANEL_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel label = new JLabel("<html><div style='text-align: center;'>" + 
                                 message.replace("\n", "<br>") + "</div></html>");
        label.setForeground(TEXT_COLOR);
        label.setFont(DEFAULT_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(parent, panel, "Informazione", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Shows a custom scrollable message dialog for large text content
     */
    public static void showCustomScrollableMessageDialog(Component parent, String message, String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PANEL_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        GridBagConstraints gbc = createDefaultConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JTextPane textPane = createStyledTextPane(message);
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        panel.add(scrollPane, gbc);

        JOptionPane.showMessageDialog(parent, panel, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Shows a custom confirmation dialog
     */
    public static boolean showCustomConfirmDialog(Component parent, String message) {
        JPanel panel = createBasePanel();
        GridBagConstraints gbc = createDefaultConstraints();

        JLabel label = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        label.setForeground(TEXT_COLOR);
        label.setFont(DEFAULT_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Conferma", 
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        return result == JOptionPane.YES_OPTION;
    }

    // Private helper methods for creating styled components
    
    private static JPanel createBasePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PANEL_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        return panel;
    }
    
    private static GridBagConstraints createDefaultConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }
    
    private static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(DEFAULT_FONT);
        return label;
    }
    
    private static JTextField createStyledTextField() {
        JTextField textField = new JTextField(18);
        textField.setFont(INPUT_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return textField;
    }
    
    private static JTextPane createStyledTextPane(String message) {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(MONO_FONT);
        textPane.setBackground(new Color(245, 245, 245));
        textPane.setForeground(TEXT_COLOR);

        // Set right alignment for the text
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet rightAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(0, doc.getLength(), rightAlign, false);

        try {
            doc.insertString(doc.getLength(), message, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        return textPane;
    }
}