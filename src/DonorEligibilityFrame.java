import javax.swing.*;
import java.awt.*;

public class DonorEligibilityFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public DonorEligibilityFrame() {

        setTitle("BloodLink - Eligibility");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(750, 65));

        JLabel title = new JLabel("  BLOODLINK - ELIGIBILITY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        topPanel.add(title, BorderLayout.WEST);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(50, 100, 30, 100)
        );

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel lastDonationLabel =
                new JLabel("Last Donation Date:");

        JTextField lastDonationField = new JTextField();

        JLabel healthLabel =
                new JLabel("Basic Pre-screening:");

        JComboBox<String> healthBox =
                new JComboBox<>(new String[]{
                        "Passed",
                        "Not Passed",
                        "Not Checked"
                });

        JLabel availabilityLabel =
                new JLabel("Availability:");

        JComboBox<String> availabilityBox =
                new JComboBox<>(new String[]{
                        "Available",
                        "Not Available"
                });

        formPanel.add(ageLabel);
        formPanel.add(ageField);

        formPanel.add(lastDonationLabel);
        formPanel.add(lastDonationField);

        formPanel.add(healthLabel);
        formPanel.add(healthBox);

        formPanel.add(availabilityLabel);
        formPanel.add(availabilityBox);

        // Check button
        JButton checkButton = new JButton("CHECK ELIGIBILITY");
        checkButton.setBackground(darkRed);
        checkButton.setForeground(Color.WHITE);
        checkButton.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel resultLabel =
                new JLabel("Eligibility status will appear here.");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        checkButton.addActionListener(e -> {

            String ageText = ageField.getText();

            if (ageText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your age."
                );

                return;
            }

            try {

                int age = Integer.parseInt(ageText);

                if (age < 18) {

                    resultLabel.setText("Status: Not Eligible");

                } else if (
                        availabilityBox.getSelectedItem()
                                .equals("Not Available")) {

                    resultLabel.setText("Status: Not Eligible");

                } else if (
                        healthBox.getSelectedItem()
                                .equals("Not Passed")) {

                    resultLabel.setText("Status: Not Eligible");

                } else {

                    resultLabel.setText("Status: Eligible");

                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid age."
                );
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        bottomPanel.setLayout(
                new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)
        );

        checkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(checkButton);
        bottomPanel.add(Box.createVerticalStrut(15));
        bottomPanel.add(resultLabel);
        bottomPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}