import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DonorSearchFrame extends JPanel {

    Color darkRed = new Color(150, 30, 45);

    public DonorSearchFrame() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(
                new Dimension(850, 65)
        );

        JLabel title =
                new JLabel("  BLOODLINK - DONOR SEARCH");

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

        // Search panel
        JPanel searchPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                10
                        )
                );

        searchPanel.setBackground(Color.WHITE);

        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        80,
                        15,
                        80
                )
        );

        JLabel bloodLabel =
                new JLabel("Blood Group:");

        JComboBox<String> bloodBox =
                new JComboBox<>(
                        new String[]{
                                "Any",
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

        JLabel locationLabel =
                new JLabel("Location:");

        JTextField locationField =
                new JTextField();

        JLabel availabilityLabel =
                new JLabel("Availability:");

        JComboBox<String> availabilityBox =
                new JComboBox<>(
                        new String[]{
                                "Any",
                                "Available",
                                "Not Available"
                        }
                );

        JLabel eligibilityLabel =
                new JLabel("Eligibility:");

        JComboBox<String> eligibilityBox =
                new JComboBox<>(
                        new String[]{
                                "Any",
                                "Eligible",
                                "Not Eligible"
                        }
                );

        searchPanel.add(bloodLabel);
        searchPanel.add(bloodBox);

        searchPanel.add(locationLabel);
        searchPanel.add(locationField);

        searchPanel.add(availabilityLabel);
        searchPanel.add(availabilityBox);

        searchPanel.add(eligibilityLabel);
        searchPanel.add(eligibilityBox);

        // Search button
        JButton searchButton =
                new JButton("SEARCH DONORS");

        searchButton.setBackground(darkRed);

        searchButton.setForeground(Color.WHITE);

        searchButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        // Button panel
        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(searchButton);

        // Results area
        JTextArea resultArea =
                new JTextArea();

        resultArea.setEditable(false);

        resultArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        resultArea.setLineWrap(true);

        resultArea.setWrapStyleWord(true);

        resultArea.setText(
                "Search results will appear here."
        );

        JScrollPane scrollPane =
                new JScrollPane(resultArea);

        // Controls panel
        JPanel controlsPanel =
                new JPanel(
                        new BorderLayout()
                );

        controlsPanel.setBackground(Color.WHITE);

        controlsPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        controlsPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        // Results panel
        JPanel resultsPanel =
                new JPanel(
                        new BorderLayout()
                );

        resultsPanel.setBackground(Color.WHITE);

        resultsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        30,
                        30,
                        30
                )
        );

        resultsPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // Search functionality
        searchButton.addActionListener(e -> {

            String bloodGroup =
                    (String) bloodBox.getSelectedItem();

            String location =
                    locationField.getText().trim();

            String availability =
                    (String) availabilityBox.getSelectedItem();

            String eligibility =
                    (String) eligibilityBox.getSelectedItem();

            StringBuilder sql =
                    new StringBuilder(
                            "SELECT donor_id, name, age, gender, " +
                            "blood_group, phone, location, " +
                            "availability, eligibility " +
                            "FROM donors WHERE 1=1"
                    );

            // Blood group filter
            if (!bloodGroup.equals("Any")) {

                sql.append(
                        " AND blood_group = ?"
                );
            }

            // Location filter
            if (!location.isEmpty()) {

                sql.append(
                        " AND location LIKE ?"
                );
            }

            // Availability filter
            if (!availability.equals("Any")) {

                sql.append(
                        " AND availability = ?"
                );
            }

            // Eligibility filter
            if (!eligibility.equals("Any")) {

                sql.append(
                        " AND eligibility = ?"
                );
            }

            sql.append(
                    " ORDER BY name"
            );

            resultArea.setText("");

            try (
                    Connection con =
                            DBConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(
                                    sql.toString()
                            )
            ) {

                int parameter = 1;

                // Blood group parameter
                if (!bloodGroup.equals("Any")) {

                    ps.setString(
                            parameter++,
                            bloodGroup
                    );
                }

                // Location parameter
                if (!location.isEmpty()) {

                    ps.setString(
                            parameter++,
                            "%" + location + "%"
                    );
                }

                // Availability parameter
                if (!availability.equals("Any")) {

                    String dbAvailability =
                            availability.equals("Available")
                                    ? "AVAILABLE"
                                    : "NOT AVAILABLE";

                    ps.setString(
                            parameter++,
                            dbAvailability
                    );
                }

                // Eligibility parameter
                if (!eligibility.equals("Any")) {

                    String dbEligibility =
                            eligibility.equals("Eligible")
                                    ? "ELIGIBLE"
                                    : "NOT ELIGIBLE";

                    ps.setString(
                            parameter++,
                            dbEligibility
                    );
                }

                ResultSet rs =
                        ps.executeQuery();

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    resultArea.append(
                            "Donor ID: "
                                    + rs.getInt("donor_id")
                                    + "\n"
                    );

                    resultArea.append(
                            "Name: "
                                    + rs.getString("name")
                                    + "\n"
                    );

                    resultArea.append(
                            "Age: "
                                    + rs.getInt("age")
                                    + "\n"
                    );

                    resultArea.append(
                            "Gender: "
                                    + rs.getString("gender")
                                    + "\n"
                    );

                    resultArea.append(
                            "Blood Group: "
                                    + rs.getString("blood_group")
                                    + "\n"
                    );

                    resultArea.append(
                            "Phone: "
                                    + rs.getString("phone")
                                    + "\n"
                    );

                    resultArea.append(
                            "Location: "
                                    + rs.getString("location")
                                    + "\n"
                    );

                    resultArea.append(
                            "Availability: "
                                    + rs.getString("availability")
                                    + "\n"
                    );

                    resultArea.append(
                            "Eligibility: "
                                    + rs.getString("eligibility")
                                    + "\n"
                    );

                    resultArea.append(
                            "-----------------------------------\n"
                    );
                }

                if (!found) {

                    resultArea.setText(
                            "No suitable donors found."
                    );
                }

            } catch (Exception ex) {

                resultArea.setText(
                        "Failed to search donors.\n\n"
                                + ex.getMessage()
                );
            }
        });

        // Add top bar
        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // Add search controls
        mainPanel.add(
                controlsPanel,
                BorderLayout.CENTER
        );

        // Main layout with controls and results
        JPanel contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(
                controlsPanel,
                BorderLayout.NORTH
        );

        contentPanel.add(
                resultsPanel,
                BorderLayout.CENTER
        );

        // Replace duplicate center component
        mainPanel.remove(controlsPanel);

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }

    // Compatibility method
    public Container getContentPane() {
        return this;
    }
}