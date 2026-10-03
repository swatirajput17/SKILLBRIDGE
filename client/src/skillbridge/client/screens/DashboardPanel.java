package skillbridge.client.screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import skillbridge.client.MainFrame;
import skillbridge.client.util.Theme;

public class DashboardPanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel welcomeLabel;

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(Theme.TITLE_FONT);
        welcomeLabel.setForeground(Theme.NAVY);

        JLabel tagline = new JLabel("Find out how close you are to your dream career.");
        tagline.setFont(Theme.SUBTITLE_FONT);
        tagline.setForeground(Theme.TEXT_GREY);

        JButton analyzeButton = new JButton("Analyze My Skills");
        Theme.styleButton(analyzeButton);
        analyzeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        analyzeButton.setBorder(BorderFactory.createEmptyBorder(14, 36, 14, 36));
        analyzeButton.addActionListener(e -> {
            this.mainFrame.getUserData().clearSelection();
            this.mainFrame.showScreen(MainFrame.SKILLS);
        });

        addToContent(content, welcomeLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        addToContent(content, tagline);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        addToContent(content, buildInfoCard());
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        addToContent(content, analyzeButton);

        add(content, BorderLayout.NORTH);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                welcomeLabel.setText("Welcome, " + DashboardPanel.this.mainFrame.getUserData().getUserName());
            }
        });
    }

    private JPanel buildInfoCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.WHITE);
        card.setBorder(Theme.cardBorder());
        card.setMaximumSize(new Dimension(720, 400));

        JLabel aboutTitle = new JLabel("About SkillBridge");
        aboutTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        aboutTitle.setForeground(Theme.TEXT_DARK);

        JLabel aboutText = new JLabel("<html><body style='width:500px'>"
                + "SkillBridge helps students understand which skills they need for their "
                + "desired career. Tell us what you already know, choose your target career, "
                + "and we will show your skill match and the skills you still need to learn."
                + "</body></html>");
        aboutText.setFont(Theme.BODY_FONT);
        aboutText.setForeground(Theme.TEXT_DARK);

        JLabel stepsTitle = new JLabel("How it works");
        stepsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        stepsTitle.setForeground(Theme.TEXT_DARK);

        JLabel step1 = makeStepLabel("1.  Select the skills you already have");
        JLabel step2 = makeStepLabel("2.  Choose your target career");
        JLabel step3 = makeStepLabel("3.  See your match percentage, missing skills and suggestions");

        addToContent(card, aboutTitle);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        addToContent(card, aboutText);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        addToContent(card, stepsTitle);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        addToContent(card, step1);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        addToContent(card, step2);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        addToContent(card, step3);

        return card;
    }

    private JLabel makeStepLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.BODY_FONT);
        label.setForeground(Theme.TEXT_DARK);
        return label;
    }

    private void addToContent(JPanel panel, JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(component);
    }
}