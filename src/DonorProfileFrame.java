import javax.swing.*;
import java.awt.*;

public class DonorProfileFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public DonorProfileFrame() {

        setTitle("BloodLink - My Profile");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(800, 65));

        JLabel title = new JLabel("  BLOODLINK - MY PROFILE");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        topPanel.add(title, BorderLayout.WEST);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(40, 80, 40, 80)
        );

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        JComboBox<String> genderBox =
                new JComboBox<>(new String[]{"Male", "Female", "Other"});

        JLabel bloodLabel = new JLabel("Blood Group:");
        JComboBox<String> bloodBox =
                new JComboBox<>(new String[]{
                        "A+", "A-", "B+", "B-",
                        "AB+", "AB-", "O+", "O-"
                });

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        JLabel locationLabel = new JLabel("Location:");
        JTextField locationField = new JTextField();

        JLabel availabilityLabel = new JLabel("Availability:");
        JComboBox<String> availabilityBox =
                new JComboBox<>(new String[]{
                        "Available",
                        "Not Available"
                });

        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(ageLabel);
        formPanel.add(ageField);

        formPanel.add(genderLabel);
        formPanel.add(genderBox);

        formPanel.add(bloodLabel);
        formPanel.add(bloodBox);

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        formPanel.add(locationLabel);
        formPanel.add(locationField);

        formPanel.add(availabilityLabel);
        formPanel.add(availabilityBox);

        // Bottom button
        JButton saveButton = new JButton("SAVE PROFILE");
        saveButton.setBackground(darkRed);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        saveButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Profile saved successfully!"
            );

        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(saveButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}