import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DonorEligibilityFrame extends JPanel {

    Color darkRed = new Color(150, 30, 45);

    private int userId;

    private JTextField ageField;
    private JTextField lastDonationField;
    private JComboBox<String> healthBox;
    private JComboBox<String> availabilityBox;
    private JLabel resultLabel;

    public DonorEligibilityFrame(int userId) {

        this.userId = userId;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

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

        JPanel formPanel =
                new JPanel(new GridLayout(4, 2, 10, 15));

        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        50,
                        100,
                        30,
                        100
                )
        );

        JLabel ageLabel = new JLabel("Age:");

        ageField = new JTextField();

        JLabel lastDonationLabel =
                new JLabel("Last Donation Date:");

        lastDonationField =
                new JTextField();

        JLabel healthLabel =
                new JLabel("Basic Pre-screening:");

        healthBox =
                new JComboBox<>(
                        new String[]{
                                "Passed",
                                "Not Passed",
                                "Not Checked"
                        }
                );

        JLabel availabilityLabel =
                new JLabel("Availability:");

        availabilityBox =
                new JComboBox<>(
                        new String[]{
                                "Available",
                                "Not Available"
                        }
                );

        formPanel.add(ageLabel);
        formPanel.add(ageField);

        formPanel.add(lastDonationLabel);
        formPanel.add(lastDonationField);

        formPanel.add(healthLabel);
        formPanel.add(healthBox);

        formPanel.add(availabilityLabel);
        formPanel.add(availabilityBox);

        // Check button

        JButton checkButton =
                new JButton("CHECK ELIGIBILITY");

        checkButton.setBackground(darkRed);
        checkButton.setForeground(Color.WHITE);

        checkButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        // Result label must be created BEFORE loadDonorData()

        resultLabel =
                new JLabel(
                        "Eligibility status will appear here."
                );

        resultLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        checkButton.addActionListener(
                e -> checkEligibility()
        );

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setBackground(Color.WHITE);

        bottomPanel.setLayout(
                new BoxLayout(
                        bottomPanel,
                        BoxLayout.Y_AXIS
                )
        );

        checkButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        resultLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        bottomPanel.add(checkButton);

        bottomPanel.add(
                Box.createVerticalStrut(15)
        );

        bottomPanel.add(resultLabel);

        bottomPanel.add(
                Box.createVerticalStrut(20)
        );

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

        // Load donor information AFTER all components are created

        loadDonorData();
    }

    // Load donor data from database

    private void loadDonorData() {

        String sql =
                "SELECT age, availability, last_donation_date, eligibility " +
                "FROM donors " +
                "WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ageField.setText(
                        String.valueOf(
                                rs.getInt("age")
                        )
                );

                String availability =
                        rs.getString("availability");

                if ("AVAILABLE".equalsIgnoreCase(
                        availability
                )) {

                    availabilityBox.setSelectedItem(
                            "Available"
                    );

                } else {

                    availabilityBox.setSelectedItem(
                            "Not Available"
                    );
                }

                if (rs.getDate("last_donation_date") != null) {

                    lastDonationField.setText(
                            rs.getDate(
                                    "last_donation_date"
                            ).toString()
                    );
                }

                String eligibility =
                        rs.getString("eligibility");

                if (eligibility != null) {

                    resultLabel.setText(
                            "Current Status: "
                                    + eligibility
                    );
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading donor data:\n"
                            + e.getMessage()
            );
        }
    }

    // Check eligibility

    private void checkEligibility() {

        String ageText =
                ageField.getText().trim();

        if (ageText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your age."
            );

            return;
        }

        try {

            int age =
                    Integer.parseInt(ageText);

            if (age < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid age."
                );

                return;
            }

            // Validate last donation date if entered

            String lastDonation =
                    lastDonationField.getText().trim();

            if (!lastDonation.isEmpty()) {

                try {

                    LocalDate.parse(lastDonation);

                } catch (DateTimeParseException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter the date in YYYY-MM-DD format."
                    );

                    return;
                }
            }

            String status;

            if (age < 18) {

                status = "NOT ELIGIBLE";

            } else if (
                    availabilityBox
                            .getSelectedItem()
                            .equals("Not Available")
            ) {

                status = "NOT ELIGIBLE";

            } else if (
                    healthBox
                            .getSelectedItem()
                            .equals("Not Passed")
            ) {

                status = "NOT ELIGIBLE";

            } else if (
                    healthBox
                            .getSelectedItem()
                            .equals("Not Checked")
            ) {

                status = "PENDING";

            } else {

                status = "ELIGIBLE";
            }

            saveEligibility(status);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid age."
            );
        }
    }

    // Save eligibility to database

    private void saveEligibility(String status) {

        String sql =
                "UPDATE donors " +
                "SET age = ?, " +
                "last_donation_date = ?, " +
                "availability = ?, " +
                "eligibility = ? " +
                "WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            int age =
                    Integer.parseInt(
                            ageField.getText().trim()
                    );

            pst.setInt(1, age);

            String lastDonation =
                    lastDonationField.getText().trim();

            if (lastDonation.isEmpty()) {

                pst.setNull(
                        2,
                        java.sql.Types.DATE
                );

            } else {

                pst.setDate(
                        2,
                        java.sql.Date.valueOf(
                                lastDonation
                        )
                );
            }

            String selectedAvailability =
                    (String)
                            availabilityBox
                                    .getSelectedItem();

            String databaseAvailability;

            if ("Available".equals(
                    selectedAvailability
            )) {

                databaseAvailability =
                        "AVAILABLE";

            } else {

                databaseAvailability =
                        "NOT AVAILABLE";
            }

            pst.setString(
                    3,
                    databaseAvailability
            );

            pst.setString(
                    4,
                    status
            );

            pst.setInt(
                    5,
                    userId
            );

            int rowsUpdated =
                    pst.executeUpdate();

            if (rowsUpdated > 0) {

                resultLabel.setText(
                        "Status: " + status
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Eligibility status updated successfully."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Donor record not found."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating eligibility:\n"
                            + e.getMessage()
            );
        }
    }

    // Compatibility method for current DonorFrame navigation

    public Container getContentPane() {

        return this;
    }
}