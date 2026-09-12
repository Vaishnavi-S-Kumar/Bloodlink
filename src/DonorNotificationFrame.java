import javax.swing.*;
import java.awt.*;

public class DonorNotificationFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public DonorNotificationFrame() {

        setTitle("BloodLink - Notifications");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(800, 65));

        JLabel title = new JLabel("  BLOODLINK - NOTIFICATIONS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 21));

        topPanel.add(title, BorderLayout.WEST);

        // Content
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(
                new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        JLabel heading = new JLabel("Notifications");
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message = new JLabel(
                "No new notifications."
        );
        message.setFont(new Font("Arial", Font.PLAIN, 16));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(80));
        centerPanel.add(heading);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(message);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}