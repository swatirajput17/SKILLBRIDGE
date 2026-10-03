package skillbridge.client.screens;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import skillbridge.client.MainFrame;
import skillbridge.client.util.Theme;

public class LoginPanel extends JPanel {

    private MainFrame mainFrame;
    private JTextField nameField;
    private JTextField emailField;
    private JLabel errorLabel;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());
        setBackground(Theme.BACKGROUND);

        add(buildCard());
    }

    private JPanel buildCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.WHITE);
        card.setBorder(Theme.cardBorder());
        card.setPreferredSize(new Dimension(440, 480));

        JLabel title = new JLabel("SkillBridge");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.NAVY);

        JLabel subtitle = new JLabel("Smart Career & Skill Gap Analysis System");
        subtitle.setFont(Theme.BODY_FONT);
        subtitle.setForeground(Theme.TEXT_GREY);

        JLabel intro = new JLabel("Enter your details to get started.");
        intro.setFont(Theme.SUBTITLE_FONT);
        intro.setForeground(Theme.TEXT_DARK);

        nameField = new JTextField();
        Theme.styleTextField(nameField);

        emailField = new JTextField();
        Theme.styleTextField(emailField);
        emailField.addActionListener(e -> doLogin());

        errorLabel = new JLabel(" ");
        errorLabel.setFont(Theme.BODY_FONT);
        errorLabel.setForeground(Theme.RED);

        JButton continueButton = new JButton("Continue");
        Theme.styleButton(continueButton);
        continueButton.addActionListener(e -> doLogin());

        addToCard(card, title);
        addToCard(card, subtitle);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        addToCard(card, intro);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        addToCard(card, makeFieldLabel("Full Name"));
        addToCard(card, nameField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        addToCard(card, makeFieldLabel("Email"));
        addToCard(card, emailField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        addToCard(card, errorLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        addToCard(card, continueButton);

        return card;
    }

    private JLabel makeFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.BOLD_FONT);
        label.setForeground(Theme.TEXT_DARK);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return label;
    }

    private void addToCard(JPanel card, JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (component instanceof JTextField) {
            component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }
        card.add(component);
    }

    private void doLogin() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            errorLabel.setText("Please enter both your name and email.");
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            errorLabel.setText("Please enter a valid email address.");
            return;
        }

        mainFrame.getUserData().setUserName(name);
        mainFrame.getUserData().setEmail(email);
        errorLabel.setText(" ");

        mainFrame.showScreen(MainFrame.DASHBOARD);
    }
}