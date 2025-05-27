package unibs.ing.personale.GUI.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Factory class for creating consistent styled buttons throughout the application
 */
public class ButtonFactory {
    
    private static final Color BUTTON_BACKGROUND = new Color(60, 63, 65);
    private static final Color BUTTON_HOVER = new Color(85, 88, 90);
    private static final Color BUTTON_BORDER = new Color(120, 120, 120);
    private static final Color BUTTON_BORDER_HOVER = new Color(140, 140, 140);
    private static final Color BUTTON_TEXT = Color.WHITE;
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    private static final Font SMALL_BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    
    /**
     * Creates a standard button with consistent styling
     */
    public static JButton createStandardButton(String text, String iconPath) {
        return createButton(text, iconPath, new Dimension(250, 60), BUTTON_FONT, 
                          new EmptyBorder(15, 25, 15, 25));
    }
    
    /**
     * Creates a small button with consistent styling
     */
    public static JButton createSmallButton(String text, String iconPath) {
        return createButton(text, iconPath, new Dimension(200, 60), SMALL_BUTTON_FONT, 
                          new EmptyBorder(10, 20, 10, 20));
    }
    
    /**
     * Creates a button with custom dimensions
     */
    public static JButton createCustomButton(String text, String iconPath, Dimension size) {
        return createButton(text, iconPath, size, BUTTON_FONT, 
                          new EmptyBorder(15, 25, 15, 25));
    }
    
    /**
     * Private method that handles the actual button creation and styling
     */
    private static JButton createButton(String text, String iconPath, Dimension size, 
                                      Font font, EmptyBorder padding) {
        JButton button = new JButton(text);
        
        // Set icon if provided
        if (iconPath != null && !iconPath.isEmpty()) {
            try {
                button.setIcon(resizeIcon(new ImageIcon(iconPath), 20, 20));
                button.setIconTextGap(15);
            } catch (Exception e) {
                // Continue without icon if not available
            }
        }
        
        // Apply styling
        button.setFont(font);
        button.setForeground(BUTTON_TEXT);
        button.setBackground(BUTTON_BACKGROUND);
        button.setFocusPainted(false);
        button.setPreferredSize(size);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BUTTON_BORDER, 2),
                padding
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setOpaque(true);

        // Add hover effects
        addHoverEffects(button, padding);
        
        return button;
    }
    
    /**
     * Adds hover effects to a button
     */
    private static void addHoverEffects(JButton button, EmptyBorder padding) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BUTTON_BORDER_HOVER, 2),
                        padding
                ));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_BACKGROUND);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BUTTON_BORDER, 2),
                        padding
                ));
            }
        });
    }
    
    /**
     * Utility method to resize icons
     */
    private static Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}