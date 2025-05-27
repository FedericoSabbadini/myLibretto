package unibs.ing.personale.GUI.util;

import java.awt.Color;
import java.awt.Font;

/**
 * Constants class containing all UI styling constants used throughout the application
 */
public final class UIConstants {
    
    // Private constructor to prevent instantiation
    private UIConstants() {}
    
    // Color constants
    public static final Color BACKGROUND_COLOR = new Color(60, 63, 65);
    public static final Color PANEL_BACKGROUND = new Color(250, 250, 250);
    public static final Color TEXT_COLOR = new Color(45, 45, 45);
    public static final Color WHITE_TEXT = Color.WHITE;
    
    // Button colors
    public static final Color BUTTON_BACKGROUND = new Color(60, 63, 65);
    public static final Color BUTTON_HOVER = new Color(85, 88, 90);
    public static final Color BUTTON_BORDER = new Color(120, 120, 120);
    public static final Color BUTTON_BORDER_HOVER = new Color(140, 140, 140);
    
    // Font constants
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 17);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font SMALL_BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font MONO_FONT = new Font("Monospaced", Font.PLAIN, 14);
    
    // Spacing constants
    public static final int STANDARD_INSET = 10;
    public static final int LARGE_INSET = 20;
    public static final int DIALOG_INSET = 15;
    public static final int PANEL_PADDING = 25;
    
    // Size constants
    public static final int ICON_SIZE = 20;
    public static final int BUTTON_HEIGHT = 60;
    public static final int STANDARD_BUTTON_WIDTH = 250;
    public static final int SMALL_BUTTON_WIDTH = 200;
    
    // File paths (these should be injected or configured externally in a real application)
    public static final String DIRECTORY_ICONS = "icons/";
    public static final String DIRECTORY_FILE = "libretto_dataInfo/";
   
}