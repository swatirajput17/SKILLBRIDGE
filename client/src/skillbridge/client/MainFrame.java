package skillbridge.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import skillbridge.client.model.AnalysisResult;
import skillbridge.client.model.UserData;
import skillbridge.client.screens.AnalysisPanel;
import skillbridge.client.screens.CareerPanel;
import skillbridge.client.screens.DashboardPanel;
import skillbridge.client.screens.LoginPanel;
import skillbridge.client.screens.ProfilePanel;
import skillbridge.client.screens.ResultPanel;
import skillbridge.client.screens.SkillsPanel;
import skillbridge.client.util.Theme;

public class MainFrame extends JFrame {

    public static final String LOGIN     = "LOGIN";
    public static final String DASHBOARD = "DASHBOARD";
    public static final String SKILLS    = "SKILLS";
    public static final String CAREER    = "CAREER";
    public static final String ANALYSIS  = "ANALYSIS";
    public static final String RESULT    = "RESULT";
    public static final String PROFILE   = "PROFILE";

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private JPanel headerBar;

    private UserData userData = new UserData();
    private AnalysisResult result;

    public MainFrame() {
        setTitle("SkillBridge - Smart Career & Skill Gap Analysis System");
        setSize(1000, 650);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        buildHeaderBar();
        add(headerBar, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        cardPanel.add(new LoginPanel(this), LOGIN);
        cardPanel.add(new DashboardPanel(this), DASHBOARD);
        cardPanel.add(new SkillsPanel(this), SKILLS);
        cardPanel.add(new CareerPanel(this), CAREER);
        cardPanel.add(new AnalysisPanel(this), ANALYSIS);
        cardPanel.add(new ResultPanel(this), RESULT);
        cardPanel.add(new ProfilePanel(this), PROFILE);

        showScreen(LOGIN);
    }

    private void buildHeaderBar() {
        headerBar = new JPanel(new BorderLayout());
        headerBar.setBackground(Theme.NAVY);
        headerBar.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        JLabel logo = new JLabel("SkillBridge");
        logo.setFont(Theme.TITLE_FONT);
        logo.setForeground(Theme.WHITE);
        headerBar.add(logo, BorderLayout.WEST);

        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);

        JButton dashboardButton = makeNavButton("Dashboard");
        JButton profileButton = makeNavButton("Profile");
        JButton logoutButton = makeNavButton("Logout");

        dashboardButton.addActionListener(e -> showScreen(DASHBOARD));
        profileButton.addActionListener(e -> showScreen(PROFILE));
        logoutButton.addActionListener(e -> {
            userData.clearAll();
            result = null;
            showScreen(LOGIN);
        });

        navPanel.add(dashboardButton);
        navPanel.add(profileButton);
        navPanel.add(logoutButton);
        headerBar.add(navPanel, BorderLayout.EAST);
    }

    private JButton makeNavButton(String text) {
        JButton button = new JButton(text);
        Theme.styleButton(button);
        button.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        return button;
    }

    public void showScreen(String screenName) {
        headerBar.setVisible(!screenName.equals(LOGIN));
        cardLayout.show(cardPanel, screenName);
    }

    public UserData getUserData() {
        return userData;
    }

    public AnalysisResult getResult() {
        return result;
    }

    public void setResult(AnalysisResult result) {
        this.result = result;
    }
}