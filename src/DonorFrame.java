import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class DonorFrame extends JFrame {

    private int userId;

    private Color darkRed = new Color(150, 30, 45);
    private Color lightRed = new Color(245, 235, 237);

    private JPanel mainContent;
    private CardLayout cardLayout;

    private Stack<String> pageHistory = new Stack<>();

    public DonorFrame(int userId) {

        this.userId = userId;

        setTitle("BloodLink - Donor");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // =====================================================
        // TOP BAR
        // =====================================================

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(1000, 60));

        JLabel titleLabel = new JLabel("BLOODLINK");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(0, 25, 0, 0)
        );

        JLabel welcomeLabel = new JLabel("Welcome, Donor");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        welcomeLabel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 25)
        );

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(welcomeLabel, BorderLayout.EAST);

        // =====================================================
        // LEFT MENU
        // =====================================================

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setPreferredSize(new Dimension(220, 540));

        menuPanel.setLayout(
                new BoxLayout(menuPanel, BoxLayout.Y_AXIS)
        );

        JLabel menuTitle = new JLabel("DONOR MENU");
        menuTitle.setFont(
                new Font("Arial", Font.BOLD, 16)
        );
        menuTitle.setForeground(darkRed);
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(
                Box.createVerticalStrut(25)
        );

        menuPanel.add(menuTitle);

        menuPanel.add(
                Box.createVerticalStrut(25)
        );

        JButton profileButton =
                createMenuButton("My Profile");

        JButton availabilityButton =
                createMenuButton("Availability");

        JButton eligibilityButton =
                createMenuButton("Eligibility");

        JButton requestButton =
                createMenuButton("Blood Requests");

        JButton historyButton =
                createMenuButton("Donation History");

        JButton notificationButton =
                createMenuButton("Notifications");

        menuPanel.add(profileButton);
        menuPanel.add(Box.createVerticalStrut(10));

        menuPanel.add(availabilityButton);
        menuPanel.add(Box.createVerticalStrut(10));

        menuPanel.add(eligibilityButton);
        menuPanel.add(Box.createVerticalStrut(10));

        menuPanel.add(requestButton);
        menuPanel.add(Box.createVerticalStrut(10));

        menuPanel.add(historyButton);
        menuPanel.add(Box.createVerticalStrut(10));

        menuPanel.add(notificationButton);

        menuPanel.add(Box.createVerticalGlue());

        JButton logoutButton =
                createMenuButton("Logout");

        menuPanel.add(logoutButton);

        menuPanel.add(
                Box.createVerticalStrut(20)
        );

        // =====================================================
        // CENTER CONTENT
        // =====================================================

        cardLayout = new CardLayout();

        mainContent = new JPanel(cardLayout);

        JPanel dashboardPanel =
                createDashboardPanel();

        mainContent.add(
                dashboardPanel,
                "DASHBOARD"
        );

        // =====================================================
        // ADD EVERYTHING
        // =====================================================

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                menuPanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                mainContent,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        profileButton.addActionListener(e -> {
            showPage(
                    "PROFILE",
                    createProfilePage()
            );
        });

        availabilityButton.addActionListener(e -> {
            showPage(
                    "AVAILABILITY",
                    createAvailabilityPage()
            );
        });

        eligibilityButton.addActionListener(e -> {
            showPage(
                    "ELIGIBILITY",
                    createEligibilityPage()
            );
        });

        requestButton.addActionListener(e -> {
            showPage(
                    "REQUESTS",
                    createRequestPage()
            );
        });

        historyButton.addActionListener(e -> {
            showPage(
                    "HISTORY",
                    createHistoryPage()
            );
        });

        notificationButton.addActionListener(e -> {
            showPage(
                    "NOTIFICATIONS",
                    createNotificationPage()
            );
        });

        logoutButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                dispose();

                LoginFrame loginFrame =
                        new LoginFrame();

                loginFrame.setVisible(true);
            }
        });
    }

    // =========================================================
    // MENU BUTTON STYLE
    // =========================================================

    private JButton createMenuButton(String text) {

        JButton button =
                new JButton(text);

        button.setMaximumSize(
                new Dimension(190, 42)
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setBackground(Color.WHITE);
        button.setForeground(darkRed);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        return button;
    }

    // =========================================================
    // DASHBOARD
    // =========================================================

    private JPanel createDashboardPanel() {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(
                new Color(250, 250, 250)
        );

        JLabel heading =
                new JLabel(
                        "Donor Dashboard",
                        SwingConstants.CENTER
                );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        heading.setForeground(darkRed);

        JLabel description =
                new JLabel(
                        "Manage your blood donation information",
                        SwingConstants.CENTER
                );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        JPanel center =
                new JPanel();

        center.setBackground(
                new Color(250, 250, 250)
        );

        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );

        center.add(
                Box.createVerticalGlue()
        );

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        description.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        center.add(heading);

        center.add(
                Box.createVerticalStrut(10)
        );

        center.add(description);

        center.add(
                Box.createVerticalGlue()
        );

        panel.add(
                center,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // PAGE NAVIGATION
    // =========================================================

    private void showPage(
            String pageName,
            Container page
    ) {

        if (!pageHistory.empty()) {

            String currentPage =
                    pageHistory.peek();

            if (!currentPage.equals(pageName)) {
                pageHistory.push(pageName);
            }

        } else {

            pageHistory.push(pageName);
        }

        mainContent.removeAll();

        mainContent.add(
                page,
                pageName
        );

        cardLayout.show(
                mainContent,
                pageName
        );

        mainContent.revalidate();
        mainContent.repaint();
    }

    // =========================================================
    // BACK BUTTON
    // =========================================================

    private JButton createBackButton() {

        JButton backButton =
                new JButton("← Back");

        backButton.setBackground(darkRed);
        backButton.setForeground(Color.WHITE);

        backButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> goBack());

        return backButton;
    }

    private void goBack() {

        if (pageHistory.size() <= 1) {

            cardLayout.show(
                    mainContent,
                    "DASHBOARD"
            );

            return;
        }

        pageHistory.pop();

        String previousPage =
                pageHistory.peek();

        mainContent.removeAll();

        if (previousPage.equals("DASHBOARD")) {

            mainContent.add(
                    createDashboardPanel(),
                    "DASHBOARD"
            );

        } else if (previousPage.equals("PROFILE")) {

            mainContent.add(
                    createProfilePage(),
                    "PROFILE"
            );

        } else if (previousPage.equals("AVAILABILITY")) {

            mainContent.add(
                    createAvailabilityPage(),
                    "AVAILABILITY"
            );

        } else if (previousPage.equals("ELIGIBILITY")) {

            mainContent.add(
                    createEligibilityPage(),
                    "ELIGIBILITY"
            );

        } else if (previousPage.equals("REQUESTS")) {

            mainContent.add(
                    createRequestPage(),
                    "REQUESTS"
            );

        } else if (previousPage.equals("HISTORY")) {

            mainContent.add(
                    createHistoryPage(),
                    "HISTORY"
            );

        } else if (previousPage.equals("NOTIFICATIONS")) {

            mainContent.add(
                    createNotificationPage(),
                    "NOTIFICATIONS"
            );
        }

        cardLayout.show(
                mainContent,
                previousPage
        );

        mainContent.revalidate();
        mainContent.repaint();
    }

    // =========================================================
    // PROFILE PAGE
    // =========================================================

    private JPanel createProfilePage() {

        DonorProfileFrame profileFrame =
                new DonorProfileFrame(userId);

        JPanel wrapper =
                new JPanel(new BorderLayout());

        wrapper.add(
                createBackButton(),
                BorderLayout.NORTH
        );

        wrapper.add(
                profileFrame.getContentPane(),
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // =========================================================
    // AVAILABILITY PAGE
    // =========================================================

    private JPanel createAvailabilityPage() {

        DonorAvailabilityFrame frame =
                new DonorAvailabilityFrame(userId);

        JPanel wrapper =
                new JPanel(new BorderLayout());

        wrapper.add(
                createBackButton(),
                BorderLayout.NORTH
        );

        wrapper.add(
                frame.getContentPane(),
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // =========================================================
    // ELIGIBILITY PAGE
    // =========================================================

    private JPanel createEligibilityPage() {

        DonorEligibilityFrame frame =
                new DonorEligibilityFrame(userId);

        JPanel wrapper =
                new JPanel(new BorderLayout());

        wrapper.add(
                createBackButton(),
                BorderLayout.NORTH
        );

        wrapper.add(
                frame.getContentPane(),
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // =========================================================
    // BLOOD REQUEST PAGE
    // =========================================================

    private JPanel createRequestPage() {

        BloodRequestFrame frame =
                new BloodRequestFrame(userId);

        JPanel wrapper =
                new JPanel(new BorderLayout());

        wrapper.add(
                createBackButton(),
                BorderLayout.NORTH
        );

        wrapper.add(
                frame.getContentPane(),
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // =========================================================
    // HISTORY PAGE
    // =========================================================

    private JPanel createHistoryPage() {

        DonorHistoryFrame frame =
                new DonorHistoryFrame(userId);

        JPanel wrapper =
                new JPanel(new BorderLayout());

        wrapper.add(
                createBackButton(),
                BorderLayout.NORTH
        );

        wrapper.add(
                frame.getContentPane(),
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // =========================================================
    // NOTIFICATION PAGE
    // =========================================================

    private JPanel createNotificationPage() {

        DonorNotificationFrame frame =
                new DonorNotificationFrame(userId);

        JPanel wrapper =
                new JPanel(new BorderLayout());

        wrapper.add(
                createBackButton(),
                BorderLayout.NORTH
        );

        wrapper.add(
                frame.getContentPane(),
                BorderLayout.CENTER
        );

        return wrapper;
    }
}