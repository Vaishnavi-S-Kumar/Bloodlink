import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HospitalProfileFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    private int userId;

    private JTextField nameField;
    private JTextField registrationField;
    private JTextField locationField;
    private JTextField phoneField;
    private JTextField emailField;
    private JComboBox<String> typeBox;

    public HospitalProfileFrame(int userId) {

        this.userId = userId;

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

        JLabel nameLabel =
                new JLabel("Hospital / Blood Bank Name:");

        nameField = new JTextField();

        JLabel registrationLabel =
                new JLabel("Registration Number:");

        registrationField = new JTextField();

        JLabel locationLabel =
                new JLabel("Location:");

        locationField = new JTextField();

        JLabel phoneLabel =
                new JLabel("Contact Number:");

        phoneField = new JTextField();

        JLabel emailLabel =
                new JLabel("Email:");

        emailField = new JTextField();

        JLabel typeLabel =
                new JLabel("Type:");

        typeBox =
                new JComboBox<>(
                        new String[]{
                                "Hospital",
                                "Blood Bank"
                        }
                );

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

        // Load existing profile
        loadProfile();

        // Save button
        JButton saveButton =
                new JButton("SAVE PROFILE");

        saveButton.setBackground(darkRed);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        saveButton.addActionListener(e -> saveProfile());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(saveButton);

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    // =========================================================
    // LOAD PROFILE
    // =========================================================

    private void loadProfile() {

        String sql =
                "SELECT hospital_name, registration_number, " +
                "location, contact_number, email, type " +
                "FROM hospitals " +
                "WHERE user_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                nameField.setText(
                        rs.getString("hospital_name")
                );

                registrationField.setText(
                        rs.getString("registration_number")
                );

                locationField.setText(
                        rs.getString("location")
                );

                phoneField.setText(
                        rs.getString("contact_number")
                );

                emailField.setText(
                        rs.getString("email")
                );

                String type =
                        rs.getString("type");

                if (type != null) {

                    if (type.equalsIgnoreCase("BLOOD BANK")) {
                        typeBox.setSelectedItem("Blood Bank");
                    } else {
                        typeBox.setSelectedItem("Hospital");
                    }
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load hospital profile.\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // SAVE PROFILE
    // =========================================================

    private void saveProfile() {

        if (nameField.getText().trim().isEmpty()
                || registrationField.getText().trim().isEmpty()
                || locationField.getText().trim().isEmpty()
                || phoneField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        String sql =
                "UPDATE hospitals " +
                "SET hospital_name = ?, " +
                "registration_number = ?, " +
                "location = ?, " +
                "contact_number = ?, " +
                "email = ?, " +
                "type = ? " +
                "WHERE user_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    nameField.getText().trim()
            );

            ps.setString(
                    2,
                    registrationField.getText().trim()
            );

            ps.setString(
                    3,
                    locationField.getText().trim()
            );

            ps.setString(
                    4,
                    phoneField.getText().trim()
            );

            ps.setString(
                    5,
                    emailField.getText().trim()
            );

            ps.setString(
                    6,
                    typeBox.getSelectedItem().toString().toUpperCase()
            );

            ps.setInt(
                    7,
                    userId
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Hospital profile saved successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Hospital profile not found."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save hospital profile.\n"
                            + e.getMessage()
            );
        }
    }
}