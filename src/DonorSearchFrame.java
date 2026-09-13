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
                        30,
                        80,
                        20,
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

        // Results

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

        resultArea.setText(
                "Search results will appear here."
        );

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

            if (!bloodGroup.equals("Any")) {
                sql.append(" AND blood_group = ?");
            }

            if (!location.isEmpty()) {
                sql.append(" AND location LIKE ?");
            }

            if (!availability.equals("Any")) {
                sql.append(" AND availability = ?");
            }

            if (!eligibility.equals("Any")) {
                sql.append(" AND eligibility = ?");
            }

            sql.append(" ORDER BY name");

            resultArea.setText("");

            try (
                    Connection con =
                            DBConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(sql.toString())
            ) {

                int parameter = 1;

                if (!bloodGroup.equals("Any")) {

                    ps.setString(
                            parameter++,
                            bloodGroup
                    );
                }

                if (!location.isEmpty()) {

                    ps.setString(
                            parameter++,
                            "%" + location + "%"
                    );
                }

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

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(searchButton);

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout()
                );

        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        centerPanel.add(
                new JScrollPane(resultArea),
                BorderLayout.SOUTH
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }

    // Compatibility method

    public Container getContentPane() {
        return this;
    }
}