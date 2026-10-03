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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import skillbridge.client.MainFrame;
import skillbridge.client.model.AnalysisResult;
import skillbridge.client.network.ServerConnection;
import skillbridge.client.util.Theme;

public class AnalysisPanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel careerValue;
    private JLabel skillsValue;
    private JLabel statusLabel;
    private JButton backButton;
    private JButton analyzeButton;

    public AnalysisPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("Review and Analyze");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.NAVY);

        JLabel subtitle = new JLabel("Check your details, then click Analyze Skills.");
        subtitle.setFont(Theme.SUBTITLE_FONT);
        subtitle.setForeground(Theme.TEXT_GREY);

        careerValue = new JLabel();
        careerValue.setFont(Theme.BODY_FONT);
        careerValue.setForeground(Theme.TEXT_DARK);

        skillsValue = new JLabel();
        skillsValue.setFont(Theme.BODY_FONT);
        skillsValue.setForeground(Theme.TEXT_DARK);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(Theme.BODY_FONT);

        backButton = new JButton("Back");
        Theme.styleSecondaryButton(backButton);
        backButton.addActionListener(e -> this.mainFrame.showScreen(MainFrame.CAREER));

        analyzeButton = new JButton("Analyze Skills");
        Theme.styleButton(analyzeButton);
        analyzeButton.addActionListener(e -> doAnalyze());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(backButton);
        buttonRow.add(Box.createRigidArea(new Dimension(12, 0)));
        buttonRow.add(analyzeButton);

        addToContent(content, title);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        addToContent(content, subtitle);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        addToContent(content, buildReviewCard());
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        addToContent(content, statusLabel);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        addToContent(content, buttonRow);

        add(content, BorderLayout.NORTH);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                refreshDetails();
            }
        });
    }

    private JPanel buildReviewCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.WHITE);
        card.setBorder(Theme.cardBorder());
        card.setMaximumSize(new Dimension(720, 400));

        addToContent(card, makeHeading("Target Career"));
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        addToContent(card, careerValue);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        addToContent(card, makeHeading("Your Skills"));
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        addToContent(card, skillsValue);

        return card;
    }

    private JLabel makeHeading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.BOLD_FONT);
        label.setForeground(Theme.NAVY);
        return label;
    }

    private void refreshDetails() {
        careerValue.setText(mainFrame.getUserData().getTargetCareer());
        String skillText = String.join(", ", mainFrame.getUserData().getSkills());
        skillsValue.setText("<html><body style='width:560px'>" + skillText + "</body></html>");
        statusLabel.setText(" ");
        analyzeButton.setEnabled(true);
        backButton.setEnabled(true);
    }

    private void doAnalyze() {
        statusLabel.setForeground(Theme.TEXT_GREY);
        statusLabel.setText("Analyzing... please wait.");
        analyzeButton.setEnabled(false);
        backButton.setEnabled(false);

        SwingWorker<AnalysisResult, Void> worker = new SwingWorker<AnalysisResult, Void>() {
            protected AnalysisResult doInBackground() throws Exception {
                ServerConnection connection = new ServerConnection();
                return connection.analyze(mainFrame.getUserData());
            }

            protected void done() {
                try {
                    AnalysisResult result = get();
                    mainFrame.setResult(result);
                    statusLabel.setText(" ");
                    mainFrame.showScreen(MainFrame.RESULT);
                } catch (Exception e) {
                    Throwable cause = e.getCause();
                    String message = (cause != null) ? cause.getMessage() : e.getMessage();
                    if (message == null) {
                        message = "Something went wrong. Please try again.";
                    }
                    statusLabel.setForeground(Theme.RED);
                    statusLabel.setText(message);
                }
                analyzeButton.setEnabled(true);
                backButton.setEnabled(true);
            }
        };
        worker.execute();
    }

    private void addToContent(JPanel panel, JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(component);
    }
}