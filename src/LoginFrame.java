import javax.swing.*;
import java.awt.*;

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

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));


        // LEFT SIDE

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(darkRed);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("BLOODLINK");
        logo.setFont(new Font("Arial", Font.BOLD, 32));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Blood Donor Management System");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message = new JLabel(
                "<html><center>Connecting donors with hospitals<br>" +
                "when every drop matters.</center></html>"
        );

        message.setFont(new Font("Arial", Font.PLAIN, 15));
        message.setForeground(Color.WHITE);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logo);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(subtitle);
        leftPanel.add(Box.createVerticalStrut(40));
        leftPanel.add(message);
        leftPanel.add(Box.createVerticalGlue());


        // RIGHT SIDE

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome Back!");
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel("Select your role");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(Box.createVerticalStrut(35));
        rightPanel.add(welcome);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(roleLabel);
        rightPanel.add(Box.createVerticalStrut(20));


        // ROLE BUTTONS

        donorButton = new JButton("DONOR");
        hospitalButton = new JButton("HOSPITAL / BLOOD BANK");
        adminButton = new JButton("AUTHORIZED ADMIN");

        styleRoleButton(donorButton);
        styleRoleButton(hospitalButton);
        styleRoleButton(adminButton);

        rightPanel.add(donorButton);
        rightPanel.add(Box.createVerticalStrut(8));

        rightPanel.add(hospitalButton);
        rightPanel.add(Box.createVerticalStrut(8));

        rightPanel.add(adminButton);

        rightPanel.add(Box.createVerticalStrut(20));


        // USERNAME

        JLabel usernameLabel = new JLabel("Email / Username");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(330, 35));

        rightPanel.add(usernameLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(usernameField);

        rightPanel.add(Box.createVerticalStrut(12));


        // PASSWORD

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(330, 35));

        rightPanel.add(passwordLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(passwordField);

        rightPanel.add(Box.createVerticalStrut(20));


        // LOGIN BUTTON

        loginButton = new JButton("LOGIN");

        loginButton.setMaximumSize(new Dimension(330, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(darkRed);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setFocusPainted(false);

        rightPanel.add(loginButton);

        rightPanel.add(Box.createVerticalStrut(15));


        // REGISTER TEXT

        JLabel registerLabel = new JLabel(
                "<html><center>New donor? Register here<br>" +
                "Hospital / Blood Bank? Register here</center></html>"
        );

        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(registerLabel);

        rightPanel.add(Box.createVerticalGlue());


        // ADD PANELS

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);


        // BUTTON ACTIONS

        donorButton.addActionListener(e -> {

            selectedRole = "DONOR";
            roleLabel.setText("Selected: DONOR");

        });

        hospitalButton.addActionListener(e -> {

            selectedRole = "HOSPITAL";
            roleLabel.setText("Selected: HOSPITAL / BLOOD BANK");

        });

        adminButton.addActionListener(e -> {

            selectedRole = "ADMIN";
            roleLabel.setText("Selected: AUTHORIZED ADMIN");

        });

        loginButton.addActionListener(e -> login());
    }


    // ROLE BUTTON STYLE

    void styleRoleButton(JButton button) {

        button.setMaximumSize(new Dimension(330, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setForeground(darkRed);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
    }


    // LOGIN METHOD

    void login() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());


        if (selectedRole.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select your role."
            );

            return;
        }


        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password."
            );

            return;
        }


        // OPEN DONOR INTERFACE

        if (selectedRole.equals("DONOR")) {

            DonorFrame donorFrame = new DonorFrame();

            donorFrame.setVisible(true);

            dispose();

        }

         else if (selectedRole.equals("HOSPITAL")) {

    HospitalFrame hospitalFrame =
            new HospitalFrame();

    hospitalFrame.setVisible(true);

    dispose();
}

else if (selectedRole.equals("ADMIN")) {

    AdminFrame adminFrame =
            new AdminFrame();

    adminFrame.setVisible(true);

    dispose();
}
    }


    // MAIN METHOD

    public static void main(String[] args) {

        LoginFrame frame = new LoginFrame();

        frame.setVisible(true);
    }
}