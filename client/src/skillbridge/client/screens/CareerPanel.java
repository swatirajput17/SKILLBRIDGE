package skillbridge.client.screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import skillbridge.client.MainFrame;
import skillbridge.client.util.Theme;

public class CareerPanel extends JPanel {

    private MainFrame mainFrame;
    private JComboBox<String> careerBox;
    private JLabel descriptionLabel;
    private JLabel errorLabel;

    private String[] careers = {
        "-- Select a career --",
        "Java Developer",
        "Web Developer",
        "Python Developer",
        "Database Administrator",
        "Software Tester",
        "Data Analyst"
    };

    private String[] descriptions = {
        "Choose the career you want to work towards.",
        "Builds desktop, web and enterprise applications using Java.",
        "Creates websites and web applications using HTML, CSS, JavaScript and more.",
        "Writes software, automation and data tools using Python.",
        "Designs, manages and protects databases such as MySQL.",
        "Checks software for bugs and makes sure it works as expected.",
        "Collects and studies data to find useful patterns and answers."
    };

    public CareerPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("Your Target Career");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.NAVY);

        JLabel subtitle = new JLabel("Which career do you want to prepare for?");
        subtitle.setFont(Theme.SUBTITLE_FONT);
        subtitle.setForeground(Theme.TEXT_GREY);

        JLabel boxLabel = new JLabel("Career");
        boxLabel.setFont(Theme.BOLD_FONT);
        boxLabel.setForeground(Theme.TEXT_DARK);

        careerBox = new JComboBox<String>(careers);
        careerBox.setFont(Theme.BODY_FONT);
        careerBox.setBackground(Theme.WHITE);
        careerBox.setMaximumSize(new Dimension(400, 40));
        careerBox.setPreferredSize(new Dimension(400, 40));
        careerBox.addActionListener(e -> showDescription());

        descriptionLabel = new JLabel();
        descriptionLabel.setFont(Theme.BODY_FONT);
        descriptionLabel.setForeground(Theme.TEXT_DARK);

        JPanel descriptionCard = new JPanel(new BorderLayout());
        descriptionCard.setBackground(Theme.WHITE);
        descriptionCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)));
        descriptionCard.setMaximumSize(new Dimension(720, 90));
        descriptionCard.add(descriptionLabel, BorderLayout.CENTER);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(Theme.BODY_FONT);
        errorLabel.setForeground(Theme.RED);

        JButton backButton = new JButton("Back");
        Theme.styleSecondaryButton(backButton);
        backButton.addActionListener(e -> this.mainFrame.showScreen(MainFrame.SKILLS));

        JButton nextButton = new JButton("Next: Review");
        Theme.styleButton(nextButton);
        nextButton.addActionListener(e -> goNext());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(backButton);
        buttonRow.add(Box.createRigidArea(new Dimension(12, 0)));
        buttonRow.add(nextButton);

        addToContent(content, title);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        addToContent(content, subtitle);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        addToContent(content, boxLabel);
        content.add(Box.createRigidArea(new Dimension(0, 6)));
        addToContent(content, careerBox);
        content.add(Box.createRigidArea(new Dimension(0, 18)));
        addToContent(content, descriptionCard);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        addToContent(content, errorLabel);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        addToContent(content, buttonRow);

        add(content, BorderLayout.NORTH);

        showDescription();

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                String saved = CareerPanel.this.mainFrame.getUserData().getTargetCareer();
                if (saved.isEmpty()) {
                    careerBox.setSelectedIndex(0);
                } else {
                    careerBox.setSelectedItem(saved);
                }
                errorLabel.setText(" ");
            }
        });
    }

    private void showDescription() {
        int index = careerBox.getSelectedIndex();
        if (index < 0) {
            index = 0;
        }
        descriptionLabel.setText("<html><body style='width:520px'>" + descriptions[index] + "</body></html>");
    }

    private void goNext() {
        if (careerBox.getSelectedIndex() <= 0) {
            errorLabel.setText("Please select a career.");
            return;
        }

        mainFrame.getUserData().setTargetCareer((String) careerBox.getSelectedItem());
        errorLabel.setText(" ");
        mainFrame.showScreen(MainFrame.ANALYSIS);
    }

    private void addToContent(JPanel panel, JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(component);
    }
}