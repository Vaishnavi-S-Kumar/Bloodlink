import javax.swing.*;
import java.awt.*;

public class HospitalFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public HospitalFrame() {

        setTitle("BloodLink - Hospital / Blood Bank");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(1000, 70));

        JLabel title = new JLabel("  BLOODLINK");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel welcome = new JLabel("Hospital / Blood Bank  ");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Arial", Font.PLAIN, 15));

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(welcome, BorderLayout.EAST);

        // Left menu
        JPanel menuPanel = new JPanel(
                new GridLayout(5, 1, 5, 5)
        );

        menuPanel.setBackground(new Color(245, 245, 245));
        menuPanel.setPreferredSize(new Dimension(230, 530));

        JButton profileButton =
                new JButton("Hospital Profile");

        JButton searchButton =
                new JButton("Search Donors");

        JButton requestButton =
                new JButton("Create Blood Request");

        JButton trackingButton =
                new JButton("Track Requests");

        JButton notificationButton =
                new JButton("Notifications");

        menuPanel.add(profileButton);
        menuPanel.add(searchButton);
        menuPanel.add(requestButton);
        menuPanel.add(trackingButton);
        menuPanel.add(notificationButton);

        // Center
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(
                new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        JLabel heading =
                new JLabel("Hospital / Blood Bank Dashboard");

        heading.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message =
                new JLabel(
                        "Manage blood requests and find suitable donors"
                );

        message.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(100));
        centerPanel.add(heading);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(message);

        // Search listener
        searchButton.addActionListener(e -> {

            DonorSearchFrame searchFrame =
                    new DonorSearchFrame();

            searchFrame.setVisible(true);
        });

        // Request listener
requestButton.addActionListener(e -> {

    CreateRequestFrame requestFrame =
            new CreateRequestFrame();

    requestFrame.setVisible(true);
});

        // Other buttons for now
profileButton.addActionListener(e -> {

    HospitalProfileFrame profileFrame =
            new HospitalProfileFrame();

    profileFrame.setVisible(true);
});

        trackingButton.addActionListener(e -> {

    HospitalTrackingFrame trackingFrame =
            new HospitalTrackingFrame();

    trackingFrame.setVisible(true);
});

        notificationButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Notifications interface will be created next."
            );
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}