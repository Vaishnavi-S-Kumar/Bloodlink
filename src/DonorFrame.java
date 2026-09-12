import javax.swing.*;
import java.awt.*;

public class DonorFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public DonorFrame() {

        setTitle("BloodLink - Donor");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // MAIN PANEL

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());


        // TOP BAR

        JPanel topPanel = new JPanel();

        topPanel.setLayout(new BorderLayout());

        topPanel.setBackground(darkRed);

        topPanel.setPreferredSize(
                new Dimension(1000, 70)
        );


        JLabel title = new JLabel("  BLOODLINK");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );


        JLabel welcome = new JLabel(
                "Welcome, Donor  "
        );

        welcome.setForeground(Color.WHITE);

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );


        topPanel.add(
                title,
                BorderLayout.WEST
        );

        topPanel.add(
                welcome,
                BorderLayout.EAST
        );


        // LEFT MENU

        JPanel menuPanel = new JPanel();

        menuPanel.setLayout(
                new GridLayout(
                        6,
                        1,
                        5,
                        5
                )
        );

        menuPanel.setBackground(
                new Color(245, 245, 245)
        );

        menuPanel.setPreferredSize(
                new Dimension(220, 530)
        );


        JButton profileButton =
                new JButton("My Profile");

        JButton availabilityButton =
                new JButton("Availability");

        JButton eligibilityButton =
                new JButton("Eligibility");

        JButton requestsButton =
                new JButton("Blood Requests");

        JButton historyButton =
                new JButton("Donation History");

        JButton notificationButton =
                new JButton("Notifications");


        menuPanel.add(profileButton);
        menuPanel.add(availabilityButton);
        menuPanel.add(eligibilityButton);
        menuPanel.add(requestsButton);
        menuPanel.add(historyButton);
        menuPanel.add(notificationButton);
profileButton.addActionListener(e -> {

    DonorProfileFrame profileFrame =
            new DonorProfileFrame();

    profileFrame.setVisible(true);
});


availabilityButton.addActionListener(e -> {

    DonorAvailabilityFrame availabilityFrame =
            new DonorAvailabilityFrame();

    availabilityFrame.setVisible(true);
});
eligibilityButton.addActionListener(e -> {

    DonorEligibilityFrame eligibilityFrame =
            new DonorEligibilityFrame();

    eligibilityFrame.setVisible(true);
});
requestsButton.addActionListener(e -> {

    BloodRequestFrame requestFrame =
            new BloodRequestFrame();

    requestFrame.setVisible(true);
});
historyButton.addActionListener(e -> {

    DonorHistoryFrame historyFrame =
            new DonorHistoryFrame();

    historyFrame.setVisible(true);
});
notificationButton.addActionListener(e -> {

    DonorNotificationFrame notificationFrame =
            new DonorNotificationFrame();

    notificationFrame.setVisible(true);
});
        // CENTER

        JPanel centerPanel = new JPanel();

        centerPanel.setBackground(Color.WHITE);

        centerPanel.setLayout(
                new BoxLayout(
                        centerPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel heading =
                new JLabel("Donor Dashboard");

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel message =
                new JLabel(
                        "Manage your blood donation information"
                );

        message.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        message.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        centerPanel.add(
                Box.createVerticalStrut(80)
        );

        centerPanel.add(heading);

        centerPanel.add(
                Box.createVerticalStrut(10)
        );

        centerPanel.add(message);


        // ADD PANELS

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                menuPanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        add(mainPanel);
    }


    // MAIN METHOD

    public static void main(String[] args) {

        DonorFrame frame =
                new DonorFrame();

        frame.setVisible(true);
    }
}