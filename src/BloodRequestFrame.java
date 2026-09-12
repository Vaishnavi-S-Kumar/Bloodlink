import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BloodRequestFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public BloodRequestFrame() {

        setTitle("BloodLink - Blood Requests");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(850, 65));

        JLabel title = new JLabel("  BLOODLINK - BLOOD REQUESTS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        topPanel.add(title, BorderLayout.WEST);

        // Request list
        JPanel requestPanel = new JPanel();
        requestPanel.setBackground(Color.WHITE);
        requestPanel.setLayout(
                new BoxLayout(requestPanel, BoxLayout.Y_AXIS)
        );

        JLabel heading = new JLabel("Active Blood Requests");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        requestPanel.add(Box.createVerticalStrut(30));
        requestPanel.add(heading);
        requestPanel.add(Box.createVerticalStrut(25));

        loadRequests(requestPanel);

        JScrollPane scrollPane =
                new JScrollPane(requestPanel);

        scrollPane.setBorder(null);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadRequests(JPanel requestPanel) {

        String sql =
                "SELECT br.request_id, h.hospital_name, "
                + "br.blood_group, br.units_required, "
                + "br.priority, br.location "
                + "FROM blood_requests br "
                + "JOIN hospitals h ON br.hospital_id = h.hospital_id "
                + "WHERE br.request_status = 'ACTIVE' "
                + "ORDER BY br.created_at DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                int requestId = rs.getInt("request_id");
                String hospital = rs.getString("hospital_name");
                String bloodGroup = rs.getString("blood_group");
                int units = rs.getInt("units_required");
                String priority = rs.getString("priority");
                String location = rs.getString("location");

                JPanel requestPanelItem = createRequest(
                        requestId,
                        hospital,
                        bloodGroup,
                        units + " Unit(s)",
                        priority,
                        location
                );

                requestPanel.add(requestPanelItem);
                requestPanel.add(Box.createVerticalStrut(15));
            }

            if (!found) {

                JLabel noRequests =
                        new JLabel("No active blood requests at the moment.");

                noRequests.setFont(
                        new Font("Arial", Font.PLAIN, 16)
                );

                noRequests.setAlignmentX(
                        Component.CENTER_ALIGNMENT
                );

                requestPanel.add(noRequests);
            }

        } catch (Exception ex) {

            JLabel errorLabel =
                    new JLabel(
                            "Unable to load blood requests."
                    );

            errorLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            requestPanel.add(errorLabel);

            System.out.println(
                    "Error loading requests: "
                    + ex.getMessage()
            );
        }
    }

    private JPanel createRequest(
            int requestId,
            String hospital,
            String bloodGroup,
            String units,
            String priority,
            String location) {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(
                new Color(248, 248, 248)
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY
                        ),
                        BorderFactory.createEmptyBorder(
                                12, 15, 12, 15
                        )
                )
        );

        JLabel details = new JLabel(
                "<html><b>" + hospital + "</b><br>" +
                "Blood Group: " + bloodGroup + "<br>" +
                "Required: " + units + "<br>" +
                "Priority: " + priority + "<br>" +
                "Location: " + location + "</html>"
        );

        JButton acceptButton =
                new JButton("ACCEPT");

        JButton declineButton =
                new JButton("DECLINE");

        acceptButton.setBackground(darkRed);
        acceptButton.setForeground(Color.WHITE);

        acceptButton.addActionListener(e -> {

            saveResponse(requestId, "ACCEPTED");
        });

        declineButton.addActionListener(e -> {

            saveResponse(requestId, "DECLINED");
        });

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(
                new Color(248, 248, 248)
        );

        buttonPanel.add(acceptButton);
        buttonPanel.add(declineButton);

        panel.add(details, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        panel.setMaximumSize(
                new Dimension(700, 130)
        );

        return panel;
    }

    private void saveResponse(
            int requestId,
            String response) {

        // Test donor ID
        int donorId = 1;

        String sql =
                "INSERT INTO donor_responses "
                + "(request_id, donor_id, response) "
                + "VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "response = VALUES(response)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, requestId);
            pst.setInt(2, donorId);
            pst.setString(3, response);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Your response has been recorded."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to record your response.\n"
                    + ex.getMessage()
            );
        }
    }
}