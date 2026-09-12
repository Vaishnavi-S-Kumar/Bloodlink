import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public AdminFrame() {

        setTitle("BloodLink - Admin");
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

        JLabel welcome = new JLabel("Authorized Admin  ");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Arial", Font.PLAIN, 15));

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(welcome, BorderLayout.EAST);

        // Left menu
        JPanel menuPanel = new JPanel(
                new GridLayout(4, 1, 5, 5)
        );

        menuPanel.setBackground(new Color(245, 245, 245));
        menuPanel.setPreferredSize(new Dimension(230, 530));

        JButton verificationButton =
                new JButton("User Verification");

        JButton requestsButton =
                new JButton("Manage Requests");

        JButton usersButton =
                new JButton("Manage Users");

        JButton reportsButton =
                new JButton("Reports");

        menuPanel.add(verificationButton);
        menuPanel.add(requestsButton);
        menuPanel.add(usersButton);
        menuPanel.add(reportsButton);

        // Center
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(
                new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        JLabel heading =
                new JLabel("Admin Dashboard");

        heading.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message =
                new JLabel(
                        "Verify users and manage the BloodLink system"
                );

        message.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(100));
        centerPanel.add(heading);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(message);

        // Button actions

        verificationButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "User Verification will be connected to the database."
            );
        });

        requestsButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Request Management will be connected to the database."
            );
        });

        usersButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "User Management will be connected to the database."
            );
        });

        reportsButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Reports will be connected to the database."
            );
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}