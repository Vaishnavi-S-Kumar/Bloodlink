import javax.swing.*;
import java.awt.*;

public class HospitalProfileFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public HospitalProfileFrame() {

        setTitle("BloodLink - Hospital Profile");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(800, 65));

        JLabel title = new JLabel("  BLOODLINK - HOSPITAL PROFILE");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        topPanel.add(title, BorderLayout.WEST);

        // Form
        JPanel formPanel = new JPanel(
                new GridLayout(6, 2, 10, 15)
        );

        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(40, 80, 30, 80)
        );

        JLabel nameLabel = new JLabel("Hospital / Blood Bank Name:");
        JTextField nameField = new JTextField();

        JLabel registrationLabel =
                new JLabel("Registration Number:");

        JTextField registrationField = new JTextField();

        JLabel locationLabel = new JLabel("Location:");
        JTextField locationField = new JTextField();

        JLabel phoneLabel = new JLabel("Contact Number:");
        JTextField phoneField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel typeLabel = new JLabel("Type:");

        JComboBox<String> typeBox =
                new JComboBox<>(new String[]{
                        "Hospital",
                        "Blood Bank"
                });

        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(registrationLabel);
        formPanel.add(registrationField);

        formPanel.add(locationLabel);
        formPanel.add(locationField);

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);

        formPanel.add(typeLabel);
        formPanel.add(typeBox);

        // Save button
        JButton saveButton =
                new JButton("SAVE PROFILE");

        saveButton.setBackground(darkRed);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        saveButton.addActionListener(e -> {

            if (nameField.getText().isEmpty()
                    || registrationField.getText().isEmpty()
                    || locationField.getText().isEmpty()
                    || phoneField.getText().isEmpty()
                    || emailField.getText().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields."
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Hospital profile saved successfully!"
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