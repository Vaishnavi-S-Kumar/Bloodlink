import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DonorProfileFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    private int userId;

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderBox;
    private JComboBox<String> bloodBox;
    private JTextField phoneField;
    private JTextField locationField;
    private JComboBox<String> availabilityBox;

    public DonorProfileFrame(int userId) {

        this.userId = userId;

        setTitle("BloodLink - My Profile");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(Color.WHITE);


        // TOP BAR

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.setBackground(darkRed);

        topPanel.setPreferredSize(
                new Dimension(800, 65)
        );

        JLabel title =
                new JLabel(
                        "  BLOODLINK - MY PROFILE"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        topPanel.add(
                title,
                BorderLayout.WEST
        );


        // FORM PANEL

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                7,
                                2,
                                10,
                                15
                        )
                );

        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        80,
                        40,
                        80
                )
        );


        JLabel nameLabel =
                new JLabel("Name:");

        nameField =
                new JTextField();


        JLabel ageLabel =
                new JLabel("Age:");

        ageField =
                new JTextField();


        JLabel genderLabel =
                new JLabel("Gender:");

        genderBox =
                new JComboBox<>(
                        new String[]{
                                "Male",
                                "Female",
                                "Other"
                        }
                );


        JLabel bloodLabel =
                new JLabel("Blood Group:");

        bloodBox =
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


        JLabel phoneLabel =
                new JLabel("Phone:");

        phoneField =
                new JTextField();


        JLabel locationLabel =
                new JLabel("Location:");

        locationField =
                new JTextField();


        JLabel availabilityLabel =
                new JLabel("Availability:");

        availabilityBox =
                new JComboBox<>(
                        new String[]{
                                "Available",
                                "Not Available"
                        }
                );


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


        // SAVE BUTTON

        JButton saveButton =
                new JButton("SAVE PROFILE");

        saveButton.setBackground(darkRed);

        saveButton.setForeground(Color.WHITE);

        saveButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        saveButton.addActionListener(e -> {

            saveProfile();

        });


        JPanel bottomPanel =
                new JPanel();

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


        // LOAD EXISTING PROFILE

        loadProfile();
    }


    private void loadProfile() {

        String sql =
                "SELECT name, age, gender, blood_group, "
                + "phone, location, availability "
                + "FROM donors "
                + "WHERE user_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);


            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                if (rs.next()) {

                    nameField.setText(
                            rs.getString("name")
                    );

                    ageField.setText(
                            String.valueOf(
                                    rs.getInt("age")
                            )
                    );

                    genderBox.setSelectedItem(
                            rs.getString("gender")
                    );

                    bloodBox.setSelectedItem(
                            rs.getString("blood_group")
                    );

                    phoneField.setText(
                            rs.getString("phone")
                    );

                    locationField.setText(
                            rs.getString("location")
                    );


                    String availability =
                            rs.getString(
                                    "availability"
                            );


                    if (availability != null) {

                        if (availability.equals(
                                "AVAILABLE"
                        )) {

                            availabilityBox
                                    .setSelectedItem(
                                            "Available"
                                    );

                        } else {

                            availabilityBox
                                    .setSelectedItem(
                                            "Not Available"
                                    );
                        }
                    }
                }
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load profile.\n"
                    + ex.getMessage()
            );
        }
    }


    private void saveProfile() {

        String name =
                nameField.getText().trim();

        String ageText =
                ageField.getText().trim();

        String gender =
                (String) genderBox.getSelectedItem();

        String bloodGroup =
                (String) bloodBox.getSelectedItem();

        String phone =
                phoneField.getText().trim();

        String location =
                locationField.getText().trim();

        String availability =
                (String) availabilityBox
                        .getSelectedItem();


        // VALIDATION

        if (name.isEmpty()
                || ageText.isEmpty()
                || phone.isEmpty()
                || location.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );

            return;
        }


        int age;


        try {

            age = Integer.parseInt(ageText);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number."
            );

            return;
        }


        if (age < 18 || age > 65) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid donor age."
            );

            return;
        }


        if (!phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain 10 digits."
            );

            return;
        }


        String availabilityValue;


        if (availability.equals("Available")) {

            availabilityValue =
                    "AVAILABLE";

        } else {

            availabilityValue =
                    "NOT AVAILABLE";
        }


        String checkSql =
                "SELECT donor_id "
                + "FROM donors "
                + "WHERE user_id = ?";


        String updateSql =
                "UPDATE donors "
                + "SET name = ?, "
                + "age = ?, "
                + "gender = ?, "
                + "blood_group = ?, "
                + "phone = ?, "
                + "location = ?, "
                + "availability = ? "
                + "WHERE user_id = ?";


        String insertSql =
                "INSERT INTO donors "
                + "(user_id, name, age, gender, "
                + "blood_group, phone, location, availability) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement checkPst =
                        con.prepareStatement(checkSql)
        ) {

            checkPst.setInt(1, userId);


            try (
                    ResultSet rs =
                            checkPst.executeQuery()
            ) {

                if (rs.next()) {

                    // EXISTING PROFILE

                    try (
                            PreparedStatement updatePst =
                                    con.prepareStatement(
                                            updateSql
                                    )
                    ) {

                        updatePst.setString(
                                1,
                                name
                        );

                        updatePst.setInt(
                                2,
                                age
                        );

                        updatePst.setString(
                                3,
                                gender
                        );

                        updatePst.setString(
                                4,
                                bloodGroup
                        );

                        updatePst.setString(
                                5,
                                phone
                        );

                        updatePst.setString(
                                6,
                                location
                        );

                        updatePst.setString(
                                7,
                                availabilityValue
                        );

                        updatePst.setInt(
                                8,
                                userId
                        );

                        updatePst.executeUpdate();
                    }


                } else {

                    // NEW PROFILE

                    try (
                            PreparedStatement insertPst =
                                    con.prepareStatement(
                                            insertSql
                                    )
                    ) {

                        insertPst.setInt(
                                1,
                                userId
                        );

                        insertPst.setString(
                                2,
                                name
                        );

                        insertPst.setInt(
                                3,
                                age
                        );

                        insertPst.setString(
                                4,
                                gender
                        );

                        insertPst.setString(
                                5,
                                bloodGroup
                        );

                        insertPst.setString(
                                6,
                                phone
                        );

                        insertPst.setString(
                                7,
                                location
                        );

                        insertPst.setString(
                                8,
                                availabilityValue
                        );

                        insertPst.executeUpdate();
                    }
                }
            }


            JOptionPane.showMessageDialog(
                    this,
                    "Profile saved successfully!"
            );


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save profile.\n"
                    + ex.getMessage()
            );
        }
    }
}