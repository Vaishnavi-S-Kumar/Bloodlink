import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    JTextField usernameField;
    JPasswordField passwordField;

    JButton donorButton;
    JButton hospitalButton;
    JButton adminButton;
    JButton loginButton;

    String selectedRole = "";

    public LoginFrame() {

        setTitle("BloodLink - Login");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MAIN PANEL

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));

        // LEFT SIDE

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(darkRed);
        leftPanel.setLayout(
                new BoxLayout(leftPanel, BoxLayout.Y_AXIS)
        );

        JLabel logo = new JLabel("BLOODLINK");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle =
                new JLabel("Blood Donor Management System");

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message =
                new JLabel(
                        "<html><center>Connecting donors with hospitals<br>" +
                        "when every drop matters.</center></html>"
                );

        message.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        message.setForeground(Color.WHITE);

        JPanel messagePanel = new JPanel();
        messagePanel.setOpaque(false);
        messagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 0));
        messagePanel.add(message);

        leftPanel.add(Box.createVerticalGlue());

        leftPanel.add(logo);

        leftPanel.add(
                Box.createVerticalStrut(10)
        );

        leftPanel.add(subtitle);

        leftPanel.add(
                Box.createVerticalStrut(40)
        );

        leftPanel.add(messagePanel);

        leftPanel.add(Box.createVerticalGlue());

        // RIGHT SIDE

        JPanel rightPanel = new JPanel();

        rightPanel.setBackground(Color.WHITE);

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel welcome =
                new JLabel("Welcome Back!");

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        welcome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel roleLabel =
                new JLabel("Select your role");

        roleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        roleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                Box.createVerticalStrut(35)
        );

        rightPanel.add(welcome);

        rightPanel.add(
                Box.createVerticalStrut(5)
        );

        rightPanel.add(roleLabel);

        rightPanel.add(
                Box.createVerticalStrut(20)
        );

        // ROLE BUTTONS

        donorButton =
                new JButton("DONOR");

        hospitalButton =
                new JButton("HOSPITAL / BLOOD BANK");

        adminButton =
                new JButton("AUTHORIZED ADMIN");

        styleRoleButton(donorButton);
        styleRoleButton(hospitalButton);
        styleRoleButton(adminButton);

        rightPanel.add(donorButton);

        rightPanel.add(
                Box.createVerticalStrut(8)
        );

        rightPanel.add(hospitalButton);

        rightPanel.add(
                Box.createVerticalStrut(8)
        );

        rightPanel.add(adminButton);

        rightPanel.add(
                Box.createVerticalStrut(20)
        );

        // EMAIL / USERNAME

        JLabel usernameLabel =
                new JLabel("Email / Username");

        usernameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        usernameField =
                new JTextField();

        usernameField.setMaximumSize(
                new Dimension(330, 35)
        );

        rightPanel.add(usernameLabel);

        rightPanel.add(
                Box.createVerticalStrut(5)
        );

        rightPanel.add(usernameField);

        rightPanel.add(
                Box.createVerticalStrut(12)
        );

        // PASSWORD

        JLabel passwordLabel =
                new JLabel("Password");

        passwordLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        passwordField =
                new JPasswordField();

        passwordField.setMaximumSize(
                new Dimension(330, 35)
        );

        rightPanel.add(passwordLabel);

        rightPanel.add(
                Box.createVerticalStrut(5)
        );

        rightPanel.add(passwordField);

        rightPanel.add(
                Box.createVerticalStrut(20)
        );

        // LOGIN BUTTON

        loginButton =
                new JButton("LOGIN");

        loginButton.setMaximumSize(
                new Dimension(330, 40)
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.setBackground(darkRed);
        loginButton.setForeground(Color.WHITE);

        loginButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        loginButton.setFocusPainted(false);

        rightPanel.add(loginButton);

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        // REGISTER BUTTONS

        JButton donorRegisterButton =
                new JButton("New donor? Register here");

        JButton hospitalRegisterButton =
                new JButton("Hospital / Blood Bank? Register here");

        styleRegisterButton(donorRegisterButton);
        styleRegisterButton(hospitalRegisterButton);

        rightPanel.add(donorRegisterButton);

        rightPanel.add(
                Box.createVerticalStrut(5)
        );

        rightPanel.add(hospitalRegisterButton);

        rightPanel.add(
                Box.createVerticalGlue()
        );

        // ADD PANELS

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);

        // BUTTON ACTIONS

        donorButton.addActionListener(e -> {

            selectedRole = "DONOR";

            roleLabel.setText(
                    "Selected: DONOR"
            );
        });

        hospitalButton.addActionListener(e -> {

            selectedRole = "HOSPITAL";

            roleLabel.setText(
                    "Selected: HOSPITAL / BLOOD BANK"
            );
        });

        adminButton.addActionListener(e -> {

            selectedRole = "ADMIN";

            roleLabel.setText(
                    "Selected: AUTHORIZED ADMIN"
            );
        });

        loginButton.addActionListener(e -> login());

        donorRegisterButton.addActionListener(e ->
                registerDonor()
        );

        hospitalRegisterButton.addActionListener(e ->
                registerHospital()
        );
    }

    // ROLE BUTTON STYLE

    void styleRoleButton(JButton button) {

        button.setMaximumSize(
                new Dimension(330, 40)
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setBackground(Color.WHITE);
        button.setForeground(darkRed);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);
    }

    // REGISTER BUTTON STYLE

    void styleRegisterButton(JButton button) {

        button.setMaximumSize(
                new Dimension(330, 30)
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setBackground(Color.WHITE);
        button.setForeground(darkRed);

        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );
    }

    // LOGIN METHOD

    void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        // CHECK ROLE

        if (selectedRole.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select your role."
            );

            return;
        }

        // CHECK EMAIL / USERNAME AND PASSWORD

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email / username and password."
            );

            return;
        }

        String sql =
                "SELECT user_id, username, email, role, status " +
                "FROM users " +
                "WHERE (username = ? OR email = ?) " +
                "AND password = ? " +
                "AND role = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    username
            );

            ps.setString(
                    2,
                    username
            );

            ps.setString(
                    3,
                    password
            );

            ps.setString(
                    4,
                    selectedRole
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                int userId =
                        rs.getInt("user_id");

                String role =
                        rs.getString("role");

                String status =
                        rs.getString("status");

                // CHECK ACCOUNT STATUS

                if (!status.equals("APPROVED")) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Your account is not approved yet."
                    );

                    rs.close();
                    ps.close();
                    con.close();

                    return;
                }

                // LOGIN SUCCESSFUL

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!"
                );

                // DONOR

                if (role.equals("DONOR")) {

                    DonorFrame donorFrame =
                            new DonorFrame(userId);

                    donorFrame.setVisible(true);

                    dispose();
                }

                // HOSPITAL

                else if (role.equals("HOSPITAL")) {

                    HospitalFrame hospitalFrame =
                            new HospitalFrame(userId);

                    hospitalFrame.setVisible(true);

                    dispose();
                }

                // ADMIN

                else if (role.equals("ADMIN")) {

                    AdminFrame adminFrame =
                            new AdminFrame();

                    adminFrame.setVisible(true);

                    dispose();
                }
            }

            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email / username, password, or role."
                );
            }

            rs.close();
            ps.close();
            con.close();

        }

        catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: "
                            + e.getMessage()
            );
        }
    }

    // DONOR REGISTRATION

    void registerDonor() {

        JTextField usernameField =
                new JTextField();

        JTextField emailField =
                new JTextField();

        JPasswordField passwordField =
                new JPasswordField();

        JPasswordField confirmPasswordField =
                new JPasswordField();

        JTextField nameField =
                new JTextField();

        JTextField ageField =
                new JTextField();

        JComboBox<String> bloodGroupBox =
                new JComboBox<>(
                        new String[]{
                                "A+",
                                "A-",
                                "B+",
                                "B-",
                                "AB+",
                                "AB-",
                                "O+",
                                "O-"
                        }
                );

        JPanel panel =
                new JPanel(
                        new GridLayout(0, 2, 8, 8)
                );

        panel.add(
                new JLabel("Username:")
        );
        panel.add(usernameField);

        panel.add(
                new JLabel("Email:")
        );
        panel.add(emailField);

        panel.add(
                new JLabel("Password:")
        );
        panel.add(passwordField);

        panel.add(
                new JLabel("Confirm Password:")
        );
        panel.add(confirmPasswordField);

        panel.add(
                new JLabel("Name:")
        );
        panel.add(nameField);

        panel.add(
                new JLabel("Age:")
        );
        panel.add(ageField);

        panel.add(
                new JLabel("Blood Group:")
        );
        panel.add(bloodGroupBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Donor Registration",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String username =
                usernameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        String name =
                nameField.getText().trim();

        String ageText =
                ageField.getText().trim();

        String bloodGroup =
                bloodGroupBox.getSelectedItem().toString();

        if (username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()
                || name.isEmpty()
                || ageText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match."
            );

            return;
        }

        int age;

        try {

            age =
                    Integer.parseInt(ageText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number."
            );

            return;
        }

        if (age < 1 || age > 100) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid age."
            );

            return;
        }

        Connection con = null;

        try {

            con =
                    DBConnection.getConnection();

            con.setAutoCommit(false);

            String userSql =
                    "INSERT INTO users " +
                    "(username, password, email, role, status) " +
                    "VALUES (?, ?, ?, 'DONOR', 'PENDING')";

            PreparedStatement userPs =
                    con.prepareStatement(
                            userSql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );

            userPs.setString(
                    1,
                    username
            );

            userPs.setString(
                    2,
                    password
            );

            userPs.setString(
                    3,
                    email
            );

            userPs.executeUpdate();

            ResultSet keys =
                    userPs.getGeneratedKeys();

            if (!keys.next()) {

                con.rollback();

                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed."
                );

                return;
            }

            int userId =
                    keys.getInt(1);

            String donorSql =
                    "INSERT INTO donors " +
                    "(user_id, name, age, blood_group) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement donorPs =
                    con.prepareStatement(
                            donorSql
                    );

            donorPs.setInt(
                    1,
                    userId
            );

            donorPs.setString(
                    2,
                    name
            );

            donorPs.setInt(
                    3,
                    age
            );

            donorPs.setString(
                    4,
                    bloodGroup
            );

            donorPs.executeUpdate();

            con.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Donor registration successful!\n" +
                    "Your account is pending admin approval."
            );

            keys.close();
            userPs.close();
            donorPs.close();
            con.close();

        }

        catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ignored) {
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed.\n"
                            + e.getMessage()
            );

            try {

                if (con != null) {
                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }

    // HOSPITAL REGISTRATION

    void registerHospital() {

        JTextField usernameField =
                new JTextField();

        JTextField emailField =
                new JTextField();

        JPasswordField passwordField =
                new JPasswordField();

        JPasswordField confirmPasswordField =
                new JPasswordField();

        JTextField hospitalNameField =
                new JTextField();

        JTextField registrationField =
                new JTextField();

        JTextField locationField =
                new JTextField();

        JTextField phoneField =
                new JTextField();

        JComboBox<String> typeBox =
                new JComboBox<>(
                        new String[]{
                                "Hospital",
                                "Blood Bank"
                        }
                );

        JPanel panel =
                new JPanel(
                        new GridLayout(0, 2, 8, 8)
                );

        panel.add(
                new JLabel("Username:")
        );
        panel.add(usernameField);

        panel.add(
                new JLabel("Email:")
        );
        panel.add(emailField);

        panel.add(
                new JLabel("Password:")
        );
        panel.add(passwordField);

        panel.add(
                new JLabel("Confirm Password:")
        );
        panel.add(confirmPasswordField);

        panel.add(
                new JLabel("Hospital / Blood Bank Name:")
        );
        panel.add(hospitalNameField);

        panel.add(
                new JLabel("Registration Number:")
        );
        panel.add(registrationField);

        panel.add(
                new JLabel("Location:")
        );
        panel.add(locationField);

        panel.add(
                new JLabel("Contact Number:")
        );
        panel.add(phoneField);

        panel.add(
                new JLabel("Type:")
        );
        panel.add(typeBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Hospital / Blood Bank Registration",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String username =
                usernameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        String hospitalName =
                hospitalNameField.getText().trim();

        String registrationNumber =
                registrationField.getText().trim();

        String location =
                locationField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String type =
                typeBox.getSelectedItem().toString().toUpperCase();

        if (username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()
                || hospitalName.isEmpty()
                || registrationNumber.isEmpty()
                || location.isEmpty()
                || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match."
            );

            return;
        }

        Connection con = null;

        try {

            con =
                    DBConnection.getConnection();

            con.setAutoCommit(false);

            String userSql =
                    "INSERT INTO users " +
                    "(username, password, email, role, status) " +
                    "VALUES (?, ?, ?, 'HOSPITAL', 'PENDING')";

            PreparedStatement userPs =
                    con.prepareStatement(
                            userSql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );

            userPs.setString(
                    1,
                    username
            );

            userPs.setString(
                    2,
                    password
            );

            userPs.setString(
                    3,
                    email
            );

            userPs.executeUpdate();

            ResultSet keys =
                    userPs.getGeneratedKeys();

            if (!keys.next()) {

                con.rollback();

                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed."
                );

                return;
            }

            int userId =
                    keys.getInt(1);

            String hospitalSql =
                    "INSERT INTO hospitals " +
                    "(user_id, hospital_name, registration_number, " +
                    "location, contact_number, email, type) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement hospitalPs =
                    con.prepareStatement(
                            hospitalSql
                    );

            hospitalPs.setInt(
                    1,
                    userId
            );

            hospitalPs.setString(
                    2,
                    hospitalName
            );

            hospitalPs.setString(
                    3,
                    registrationNumber
            );

            hospitalPs.setString(
                    4,
                    location
            );

            hospitalPs.setString(
                    5,
                    phone
            );

            hospitalPs.setString(
                    6,
                    email
            );

            hospitalPs.setString(
                    7,
                    type
            );

            hospitalPs.executeUpdate();

            con.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Hospital / Blood Bank registration successful!\n" +
                    "Your account is pending admin approval."
            );

            keys.close();
            userPs.close();
            hospitalPs.close();
            con.close();

        }

        catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ignored) {
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed.\n"
                            + e.getMessage()
            );

            try {

                if (con != null) {
                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }

    // MAIN METHOD

    public static void main(String[] args) {

        LoginFrame frame =
                new LoginFrame();

        frame.setVisible(true);
    }
}