package skillbridge.client.screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import skillbridge.client.MainFrame;
import skillbridge.client.model.AnalysisResult;
import skillbridge.client.util.Theme;

public class ResultPanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel careerLabel;
    private JLabel percentLabel;
    private JProgressBar matchBar;
    private JPanel haveList;
    private JPanel missingList;
    private JPanel suggestionList;

    public ResultPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("Your Results");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.NAVY);

        careerLabel = new JLabel(" ");
        careerLabel.setFont(Theme.SUBTITLE_FONT);
        careerLabel.setForeground(Theme.TEXT_GREY);

        haveList = makeListPanel();
        missingList = makeListPanel();
        suggestionList = makeListPanel();

        JButton againButton = new JButton("Analyze Again");
        Theme.styleButton(againButton);
        againButton.addActionListener(e -> {
            this.mainFrame.getUserData().clearSelection();
            this.mainFrame.showScreen(MainFrame.SKILLS);
        });

        JButton dashboardButton = new JButton("Back to Dashboard");
        Theme.styleSecondaryButton(dashboardButton);
        dashboardButton.addActionListener(e -> this.mainFrame.showScreen(MainFrame.DASHBOARD));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(againButton);
        buttonRow.add(Box.createRigidArea(new Dimension(12, 0)));
        buttonRow.add(dashboardButton);

        addToContent(content, title);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        addToContent(content, careerLabel);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        addToContent(content, buildMatchCard());
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        addToContent(content, buildListsCard());
        content.add(Box.createRigidArea(new Dimension(0, 20)));
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
                showResult();
            }
        });
    }

    private JPanel buildMatchCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));

        JLabel heading = new JLabel("Skill Match");
        heading.setFont(Theme.BOLD_FONT);
        heading.setForeground(Theme.NAVY);

        percentLabel = new JLabel("0%");
        percentLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        percentLabel.setForeground(Theme.GREEN);

        matchBar = new JProgressBar(0, 100);
        matchBar.setForeground(Theme.GREEN);
        matchBar.setBackground(Theme.BACKGROUND);
        matchBar.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        matchBar.setPreferredSize(new Dimension(400, 18));
        matchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 18));

        addToContent(card, heading);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        addToContent(card, percentLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        addToContent(card, matchBar);

        return card;
    }

    private JPanel buildListsCard() {
        JPanel card = new JPanel(new GridLayout(1, 3, 25, 0));
        card.setBackground(Theme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));

        card.add(makeColumn("Skills You Have", Theme.GREEN, haveList));
        card.add(makeColumn("Missing Skills", Theme.RED, missingList));
        card.add(makeColumn("Suggestions", Theme.NAVY, suggestionList));

        return card;
    }

    private JPanel makeColumn(String title, Color color, JPanel list) {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setOpaque(false);

        JLabel heading = new JLabel(title);
        heading.setFont(Theme.BOLD_FONT);
        heading.setForeground(color);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        addToContent(column, heading);
        addToContent(column, list);
        return column;
    }

    private JPanel makeListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        return panel;
    }

    private void fillList(JPanel panel, ArrayList<String> items, String emptyText) {
        panel.removeAll();
        if (items.isEmpty()) {
            addItem(panel, emptyText);
        }
        for (String item : items) {
            addItem(panel, "\u2022  " + item);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void addItem(JPanel panel, String text) {
        JLabel label = new JLabel("<html><body style='width:200px'>" + text + "</body></html>");
        label.setFont(Theme.BODY_FONT);
        label.setForeground(Theme.TEXT_DARK);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        addToContent(panel, label);
    }

    private void showResult() {
        AnalysisResult result = mainFrame.getResult();
        if (result == null) {
            return;
        }

        careerLabel.setText("Target career: " + mainFrame.getUserData().getTargetCareer());
        percentLabel.setText(result.getMatchPercentage() + "%");
        matchBar.setValue(result.getMatchPercentage());

        fillList(haveList, result.getSkills(), "None yet");
        fillList(missingList, result.getMissingSkills(), "None. You have every skill!");
        fillList(suggestionList, result.getSuggestions(), "No suggestions");
    }

    private void addToContent(JPanel panel, JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(component);
    }
}