import javax.swing.*;
import java.awt.*;

public class HospitalTrackingFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public HospitalTrackingFrame() {

        setTitle("BloodLink - Request Tracking");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(850, 65));

        JLabel title =
                new JLabel("  BLOODLINK - REQUEST TRACKING");

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 21));

        topPanel.add(title, BorderLayout.WEST);

        // Center
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(
                new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        JLabel heading =
                new JLabel("Blood Request Status");

        heading.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message =
                new JLabel(
                        "No active blood requests available."
                );

        message.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(
                Box.createVerticalStrut(80)
        );

        centerPanel.add(heading);

        centerPanel.add(
                Box.createVerticalStrut(20)
        );

        centerPanel.add(message);

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }
}