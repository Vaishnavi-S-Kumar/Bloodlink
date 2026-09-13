import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HospitalTrackingFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    private int userId;

    public HospitalTrackingFrame(int userId) {

        this.userId = userId;

        setTitle("BloodLink - Request Tracking");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(850, 65));

        JLabel title =
                new JLabel("  BLOODLINK - REQUEST TRACKING");

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 21));

        topPanel.add(title, BorderLayout.WEST);

        // Heading

        JLabel heading =
                new JLabel(
                        "Blood Request Status",
                        SwingConstants.CENTER
                );

        heading.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        // Results

        JTextArea resultArea =
                new JTextArea();

        resultArea.setEditable(false);

        resultArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        resultArea.setMargin(
                new Insets(15, 15, 15, 15)
        );

        loadRequests(resultArea);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(
                heading,
                BorderLayout.NORTH
        );

        centerPanel.add(
                new JScrollPane(resultArea),
                BorderLayout.CENTER
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

    private void loadRequests(JTextArea resultArea) {

        String sql =
                "SELECT br.request_id, br.patient_name, " +
                "br.blood_group, br.units_required, " +
                "br.location, br.priority, " +
                "br.request_status, " +
                "dr.response, dr.responded_at " +
                "FROM blood_requests br " +
                "LEFT JOIN donor_responses dr " +
                "ON br.request_id = dr.request_id " +
                "WHERE br.hospital_id = " +
                "(SELECT hospital_id FROM hospitals WHERE user_id = ?) " +
                "ORDER BY br.created_at DESC";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                resultArea.append(
                        "Request ID: "
                        + rs.getInt("request_id")
                        + "\n"
                );

                resultArea.append(
                        "Patient: "
                        + rs.getString("patient_name")
                        + "\n"
                );

                resultArea.append(
                        "Blood Group: "
                        + rs.getString("blood_group")
                        + "\n"
                );

                resultArea.append(
                        "Units Required: "
                        + rs.getInt("units_required")
                        + "\n"
                );

                resultArea.append(
                        "Location: "
                        + rs.getString("location")
                        + "\n"
                );

                resultArea.append(
                        "Priority: "
                        + rs.getString("priority")
                        + "\n"
                );

                resultArea.append(
                        "Request Status: "
                        + rs.getString("request_status")
                        + "\n"
                );

                String response =
                        rs.getString("response");

                if (response == null) {

                    resultArea.append(
                            "Donor Response: No response yet\n"
                    );

                } else {

                    resultArea.append(
                            "Donor Response: "
                            + response
                            + "\n"
                    );

                    resultArea.append(
                            "Response Time: "
                            + rs.getString("responded_at")
                            + "\n"
                    );
                }

                resultArea.append(
                        "----------------------------------------\n"
                );
            }

            if (!found) {

                resultArea.setText(
                        "No blood requests available."
                );
            }

        } catch (Exception e) {

            resultArea.setText(
                    "Failed to load blood requests.\n\n"
                    + e.getMessage()
            );
        }
    }
}