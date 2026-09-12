import javax.swing.*;
import java.awt.*;

public class DonorAvailabilityFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public DonorAvailabilityFrame() {

        setTitle("BloodLink - Availability");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(700, 65));

        JLabel title = new JLabel("  BLOODLINK - AVAILABILITY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        topPanel.add(title, BorderLayout.WEST);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Set Your Availability");
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message = new JLabel(
                "Let hospitals know whether you are available to donate."
        );
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> availabilityBox =
                new JComboBox<>(new String[]{
                        "Available",
                        "Not Available"
                });

        availabilityBox.setMaximumSize(new Dimension(250, 35));
        availabilityBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton saveButton = new JButton("SAVE");
        saveButton.setBackground(darkRed);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton.addActionListener(e -> {

            String status =
                    (String) availabilityBox.getSelectedItem();

            JOptionPane.showMessageDialog(
                    this,
                    "Availability updated to: " + status
            );
        });

        centerPanel.add(Box.createVerticalStrut(70));
        centerPanel.add(heading);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(message);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(availabilityBox);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(saveButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}