package skillbridge.client.screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import skillbridge.client.model.UserData;
import skillbridge.client.util.Theme;

public class ProfilePanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel nameValue;
    private JLabel emailValue;
    private JLabel careerValue;
    private JLabel skillCountValue;

    public ProfilePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("My Profile");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.NAVY);

        JLabel subtitle = new JLabel("Your basic details.");
        subtitle.setFont(Theme.SUBTITLE_FONT);
        subtitle.setForeground(Theme.TEXT_GREY);

        nameValue = makeValueLabel();
        emailValue = makeValueLabel();
        careerValue = makeValueLabel();
        skillCountValue = makeValueLabel();

        JButton backButton = new JButton("Back to Dashboard");
        Theme.styleSecondaryButton(backButton);
        backButton.addActionListener(e -> this.mainFrame.showScreen(MainFrame.DASHBOARD));

        addToContent(content, title);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        addToContent(content, subtitle);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        addToContent(content, buildDetailsCard());
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        addToContent(content, backButton);

        add(content, BorderLayout.NORTH);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                refreshDetails();
            }
        });
    }

    private JPanel buildDetailsCard() {
        JPanel card = new JPanel(new GridLayout(4, 2, 10, 16));
        card.setBackground(Theme.WHITE);
        card.setBorder(Theme.cardBorder());
        card.setMaximumSize(new Dimension(720, 260));

        card.add(makeKeyLabel("Name"));
        card.add(nameValue);
        card.add(makeKeyLabel("Email"));
        card.add(emailValue);
        card.add(makeKeyLabel("Selected Career"));
        card.add(careerValue);
        card.add(makeKeyLabel("Skills Selected"));
        card.add(skillCountValue);

        return card;
    }

    private JLabel makeKeyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.BOLD_FONT);
        label.setForeground(Theme.NAVY);
        return label;
    }

    private JLabel makeValueLabel() {
        JLabel label = new JLabel(" ");
        label.setFont(Theme.BODY_FONT);
        label.setForeground(Theme.TEXT_DARK);
        return label;
    }

    private void refreshDetails() {
        UserData data = mainFrame.getUserData();

        nameValue.setText(data.getUserName());
        emailValue.setText(data.getEmail());

        String career = data.getTargetCareer();
        if (career.isEmpty()) {
            careerValue.setText("Not selected yet");
        } else {
            careerValue.setText(career);
        }

        skillCountValue.setText(String.valueOf(data.getSkills().size()));
    }

    private void addToContent(JPanel panel, JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(component);
    }
}