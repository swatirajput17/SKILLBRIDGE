package skillbridge.client.screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import skillbridge.client.MainFrame;
import skillbridge.client.util.Theme;

public class SkillsPanel extends JPanel {

    private MainFrame mainFrame;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    private JPanel otherPanel;
    private JTextField otherField;
    private JLabel errorLabel;

    private String[] programmingSkills = {"Java", "Python", "C", "C++", "C#"};
    private String[] webSkills = {"HTML", "CSS", "JavaScript", "PHP", "React"};
    private String[] databaseSkills = {"SQL", "MySQL", "MongoDB"};
    private String[] otherSkills = {"Git", "Linux", "Data Structures", "OOP", "Communication"};

    public SkillsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("Your Current Skills");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.NAVY);

        JLabel subtitle = new JLabel("Select all the skills you already have.");
        subtitle.setFont(Theme.SUBTITLE_FONT);
        subtitle.setForeground(Theme.TEXT_GREY);

        JLabel addLabel = new JLabel("Add a skill that is not in the list:");
        addLabel.setFont(Theme.BOLD_FONT);
        addLabel.setForeground(Theme.TEXT_DARK);

        otherField = new JTextField(18);
        Theme.styleTextField(otherField);
        otherField.addActionListener(e -> addOtherSkill());

        JButton addButton = new JButton("Add");
        Theme.styleSecondaryButton(addButton);
        addButton.addActionListener(e -> addOtherSkill());

        JPanel addRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        addRow.setOpaque(false);
        addRow.add(otherField);
        addRow.add(Box.createRigidArea(new Dimension(10, 0)));
        addRow.add(addButton);

        otherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        otherPanel.setOpaque(false);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(Theme.BODY_FONT);
        errorLabel.setForeground(Theme.RED);

        JButton backButton = new JButton("Back");
        Theme.styleSecondaryButton(backButton);
        backButton.addActionListener(e -> this.mainFrame.showScreen(MainFrame.DASHBOARD));

        JButton nextButton = new JButton("Next: Choose Career");
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
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        addToContent(content, buildSkillsCard());
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        addToContent(content, addLabel);
        content.add(Box.createRigidArea(new Dimension(0, 8)));
        addToContent(content, addRow);
        content.add(Box.createRigidArea(new Dimension(0, 8)));
        addToContent(content, otherPanel);
        content.add(Box.createRigidArea(new Dimension(0, 8)));
        addToContent(content, errorLabel);
        content.add(Box.createRigidArea(new Dimension(0, 8)));
        addToContent(content, buttonRow);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(content, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Theme.BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                refreshSelection();
            }
        });
    }

    private JPanel buildSkillsCard() {
        JPanel card = new JPanel(new GridLayout(1, 4, 25, 0));
        card.setBackground(Theme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));

        card.add(makeCategory("Programming", programmingSkills));
        card.add(makeCategory("Web", webSkills));
        card.add(makeCategory("Database", databaseSkills));
        card.add(makeCategory("Tools & Other", otherSkills));

        return card;
    }

    private JPanel makeCategory(String title, String[] skills) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = new JLabel(title);
        label.setFont(Theme.BOLD_FONT);
        label.setForeground(Theme.NAVY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        addToContent(panel, label);

        for (String skill : skills) {
            addToContent(panel, makeCheckBox(skill));
        }
        return panel;
    }

    private JCheckBox makeCheckBox(String text) {
        JCheckBox box = new JCheckBox(text);
        box.setFont(Theme.BODY_FONT);
        box.setForeground(Theme.TEXT_DARK);
        box.setOpaque(false);
        box.setFocusPainted(false);
        box.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 18));
        checkBoxes.add(box);
        return box;
    }

    private void addOtherSkill() {
        String text = otherField.getText().trim();
        if (text.isEmpty()) {
            return;
        }
        if (text.contains(",") || text.contains("|")) {
            errorLabel.setText("A skill name cannot contain a comma or the | symbol.");
            return;
        }

        for (JCheckBox box : checkBoxes) {
            if (box.getText().equalsIgnoreCase(text)) {
                box.setSelected(true);
                otherField.setText("");
                errorLabel.setText(" ");
                return;
            }
        }

        JCheckBox newBox = makeCheckBox(text);
        newBox.setSelected(true);
        otherPanel.add(newBox);
        otherPanel.revalidate();
        otherPanel.repaint();

        otherField.setText("");
        errorLabel.setText(" ");
    }

    private void goNext() {
        ArrayList<String> selected = new ArrayList<String>();
        for (JCheckBox box : checkBoxes) {
            if (box.isSelected()) {
                selected.add(box.getText());
            }
        }

        if (selected.isEmpty()) {
            errorLabel.setText("Please select at least one skill.");
            return;
        }

        mainFrame.getUserData().setSkills(selected);
        errorLabel.setText(" ");
        mainFrame.showScreen(MainFrame.CAREER);
    }

    private void refreshSelection() {
        ArrayList<String> saved = mainFrame.getUserData().getSkills();
        for (JCheckBox box : checkBoxes) {
            boolean found = false;
            for (String skill : saved) {
                if (skill.equalsIgnoreCase(box.getText())) {
                    found = true;
                }
            }
            box.setSelected(found);
        }
        errorLabel.setText(" ");
    }

    private void addToContent(JPanel panel, JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(component);
    }
}