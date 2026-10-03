package skillbridge.client.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;

public class Theme {

    public static final Color NAVY       = new Color(0x1F3A5F);
    public static final Color GREEN      = new Color(0x2E7D5B);
    public static final Color BACKGROUND = new Color(0xF4F6F9);
    public static final Color BORDER     = new Color(0xD9DEE5);
    public static final Color TEXT_DARK  = new Color(0x222B36);
    public static final Color TEXT_GREY  = new Color(0x6B7785);
    public static final Color RED        = new Color(0xB3261E);
    public static final Color WHITE      = Color.WHITE;

    public static final Font TITLE_FONT    = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font BODY_FONT     = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BOLD_FONT     = new Font("Segoe UI", Font.BOLD, 14);

    public static void styleButton(JButton button) {
        button.setUI(new BasicButtonUI());
        button.setFont(BOLD_FONT);
        button.setBackground(NAVY);
        button.setForeground(WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void styleSecondaryButton(JButton button) {
        styleButton(button);
        button.setBackground(WHITE);
        button.setForeground(NAVY);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(9, 23, 9, 23)));
    }

    public static void styleTextField(JTextField field) {
        field.setFont(BODY_FONT);
        field.setForeground(TEXT_DARK);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(30, 40, 30, 40));
    }
}