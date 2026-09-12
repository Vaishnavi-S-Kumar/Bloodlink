import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateRequestFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public CreateRequestFrame() {

        setTitle("BloodLink - Create Blood Request");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(800, 65));

        JLabel title = new JLabel("  BLOODLINK - CREATE BLOOD REQUEST");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 21));

        topPanel.add(title, BorderLayout.WEST);

        // Form
        JPanel formPanel = new JPanel(
                new GridLayout(6, 2, 10, 15)
        );

        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(40, 80, 30, 80)
        );

        JLabel patientLabel = new JLabel("Patient / Request ID:");
        JTextField patientField = new JTextField();

        JLabel bloodLabel = new JLabel("Blood Group:");

        JComboBox<String> bloodBox =
                new JComboBox<>(new String[]{
                        "A+", "A-", "B+", "B-",
                        "AB+", "AB-", "O+", "O-"
                });

        JLabel unitsLabel = new JLabel("Units Required:");
        JTextField unitsField = new JTextField();

        JLabel locationLabel = new JLabel("Location:");
        JTextField locationField = new JTextField();

        JLabel priorityLabel = new JLabel("Priority:");

        JComboBox<String> priorityBox =
                new JComboBox<>(new String[]{
                        "Normal",
                        "Urgent",
                        "Emergency"
                });

        JLabel contactLabel = new JLabel("Contact Number:");
        JTextField contactField = new JTextField();

        formPanel.add(patientLabel);
        formPanel.add(patientField);

        formPanel.add(bloodLabel);
        formPanel.add(bloodBox);

        formPanel.add(unitsLabel);
        formPanel.add(unitsField);

        formPanel.add(locationLabel);
        formPanel.add(locationField);

        formPanel.add(priorityLabel);
        formPanel.add(priorityBox);

        formPanel.add(contactLabel);
        formPanel.add(contactField);

        // Create button
        JButton createButton =
                new JButton("CREATE REQUEST");

        createButton.setBackground(darkRed);
        createButton.setForeground(Color.WHITE);
        createButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        createButton.addActionListener(e -> {

            if (patientField.getText().trim().isEmpty()
                    || unitsField.getText().trim().isEmpty()
                    || locationField.getText().trim().isEmpty()
                    || contactField.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields."
                );

                return;
            }

            int units;

            try {
                units = Integer.parseInt(unitsField.getText().trim());

                if (units <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Units required must be greater than 0."
                    );
                    return;
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid number of units."
                );

                return;
            }

            String patientName = patientField.getText().trim();
            String bloodGroup = bloodBox.getSelectedItem().toString();
            String location = locationField.getText().trim();
            String priority = priorityBox.getSelectedItem().toString().toUpperCase();
            String contact = contactField.getText().trim();

            // Test hospital ID
            int hospitalId = 1;

            String sql = "INSERT INTO blood_requests "
                    + "(hospital_id, patient_name, blood_group, units_required, "
                    + "location, priority, contact_number) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (
                    Connection con = DBConnection.getConnection();
                    PreparedStatement pst = con.prepareStatement(sql)
            ) {

                pst.setInt(1, hospitalId);
                pst.setString(2, patientName);
                pst.setString(3, bloodGroup);
                pst.setInt(4, units);
                pst.setString(5, location);
                pst.setString(6, priority);
                pst.setString(7, contact);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Blood request created successfully!"
                );

                patientField.setText("");
                unitsField.setText("");
                locationField.setText("");
                contactField.setText("");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to create blood request.\n"
                        + ex.getMessage()
                );
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(createButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}