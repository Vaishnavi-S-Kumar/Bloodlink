import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BloodRequestFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    private int userId;
    private int donorId;

    private JPanel requestPanel;

    public BloodRequestFrame(int userId) {

        this.userId = userId;

        setTitle("BloodLink - Blood Requests");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // TOP BAR

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(850, 65));

        JLabel title =
                new JLabel("  BLOODLINK - BLOOD REQUESTS");

        title.setForeground(Color.WHITE);
        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        topPanel.add(title, BorderLayout.WEST);


        // REQUEST PANEL

        requestPanel = new JPanel();

        requestPanel.setBackground(Color.WHITE);

        requestPanel.setLayout(
                new BoxLayout(
                        requestPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel heading =
                new JLabel("Blood Requests Matching You");

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        requestPanel.add(
                Box.createVerticalStrut(30)
        );

        requestPanel.add(heading);

        requestPanel.add(
                Box.createVerticalStrut(25)
        );


        findDonorAndLoadRequests();


        JScrollPane scrollPane =
                new JScrollPane(requestPanel);

        scrollPane.setBorder(null);


        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        add(mainPanel);
    }


    private void findDonorAndLoadRequests() {

        String sql =
                "SELECT donor_id "
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

                    donorId =
                            rs.getInt("donor_id");

                    loadRequests();

                } else {

                    showMessage(
                            "Donor profile not found."
                    );
                }
            }

        } catch (Exception ex) {

            showMessage(
                    "Unable to load donor information."
            );

            System.out.println(
                    "Error finding donor: "
                    + ex.getMessage()
            );
        }
    }


    private void loadRequests() {

        String sql =
                "SELECT br.request_id, "
                + "h.hospital_name, "
                + "br.blood_group, "
                + "br.units_required, "
                + "br.priority, "
                + "br.location, "
                + "dr.response, "
                + "dr.responded_at "
                + "FROM blood_requests br "
                + "JOIN hospitals h "
                + "ON br.hospital_id = h.hospital_id "
                + "JOIN donors d "
                + "ON d.blood_group = br.blood_group "
                + "LEFT JOIN donor_responses dr "
                + "ON dr.request_id = br.request_id "
                + "AND dr.donor_id = d.donor_id "
                + "WHERE br.request_status = 'ACTIVE' "
                + "AND d.donor_id = ? "
                + "AND d.availability = 'AVAILABLE' "
                + "AND d.eligibility = 'ELIGIBLE' "
                + "ORDER BY br.created_at DESC";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, donorId);

            try (
                    ResultSet rs =
                            pst.executeQuery()
            ) {

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    int requestId =
                            rs.getInt("request_id");

                    String hospital =
                            rs.getString(
                                    "hospital_name"
                            );

                    String bloodGroup =
                            rs.getString(
                                    "blood_group"
                            );

                    int units =
                            rs.getInt(
                                    "units_required"
                            );

                    String priority =
                            rs.getString(
                                    "priority"
                            );

                    String location =
                            rs.getString(
                                    "location"
                            );

                    String response =
                            rs.getString(
                                    "response"
                            );

                    java.sql.Timestamp respondedAt =
                            rs.getTimestamp(
                                    "responded_at"
                            );


                    JPanel item =
                            createRequest(
                                    requestId,
                                    hospital,
                                    bloodGroup,
                                    units + " Unit(s)",
                                    priority,
                                    location,
                                    response,
                                    respondedAt
                            );

                    requestPanel.add(item);

                    requestPanel.add(
                            Box.createVerticalStrut(15)
                    );
                }


                if (!found) {

                    JLabel noRequests =
                            new JLabel(
                                    "No blood requests currently match your blood group."
                            );

                    noRequests.setFont(
                            new Font(
                                    "Arial",
                                    Font.PLAIN,
                                    16
                            )
                    );

                    noRequests.setAlignmentX(
                            Component.CENTER_ALIGNMENT
                    );

                    requestPanel.add(noRequests);
                }
            }

        } catch (Exception ex) {

            showMessage(
                    "Unable to load blood requests."
            );

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
            String location,
            String response,
            java.sql.Timestamp respondedAt) {


        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(
                new Color(248, 248, 248)
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY
                        ),
                        BorderFactory.createEmptyBorder(
                                12,
                                15,
                                12,
                                15
                        )
                )
        );


        JLabel details =
                new JLabel(
                        "<html><b>"
                        + hospital
                        + "</b><br>"
                        + "Blood Group: "
                        + bloodGroup
                        + "<br>"
                        + "Required: "
                        + units
                        + "<br>"
                        + "Priority: "
                        + priority
                        + "<br>"
                        + "Location: "
                        + location
                        + "</html>"
                );


        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(
                new Color(248, 248, 248)
        );


        if (response == null) {

            JButton acceptButton =
                    new JButton("ACCEPT");

            JButton declineButton =
                    new JButton("DECLINE");


            acceptButton.setBackground(darkRed);
            acceptButton.setForeground(Color.WHITE);


            acceptButton.addActionListener(e -> {

                saveResponse(
                        requestId,
                        "ACCEPTED"
                );
            });


            declineButton.addActionListener(e -> {

                saveResponse(
                        requestId,
                        "DECLINED"
                );
            });


            buttonPanel.add(acceptButton);
            buttonPanel.add(declineButton);


        } else {

            JLabel responseLabel;


            if (response.equals("ACCEPTED")) {

                responseLabel =
                        new JLabel(
                                "✓ ACCEPTED"
                        );

            } else {

                responseLabel =
                        new JLabel(
                                "✕ DECLINED"
                        );
            }


            responseLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            14
                    )
            );


            buttonPanel.add(responseLabel);


            if (respondedAt != null) {

                long currentTime =
                        System.currentTimeMillis();

                long responseTime =
                        respondedAt.getTime();

                long difference =
                        currentTime - responseTime;


                long fiveMinutes =
                        5 * 60 * 1000;


                if (difference <= fiveMinutes) {

                    JButton cancelButton =
                            new JButton("CANCEL");


                    cancelButton.addActionListener(e -> {

                        cancelResponse(
                                requestId
                        );
                    });


                    buttonPanel.add(
                            cancelButton
                    );
                }
            }
        }


        panel.add(
                details,
                BorderLayout.CENTER
        );

        panel.add(
                buttonPanel,
                BorderLayout.EAST
        );


        panel.setMaximumSize(
                new Dimension(700, 130)
        );


        return panel;
    }


    private void saveResponse(
            int requestId,
            String response) {

        String sql =
                "INSERT INTO donor_responses "
                + "(request_id, donor_id, response) "
                + "VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "response = VALUES(response), "
                + "responded_at = CURRENT_TIMESTAMP";


        try (
                Connection con =
                        DBConnection.getConnection();

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


            refreshRequests();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to record your response.\n"
                    + ex.getMessage()
            );
        }
    }


    private void cancelResponse(
            int requestId) {

        String sql =
                "DELETE FROM donor_responses "
                + "WHERE request_id = ? "
                + "AND donor_id = ? "
                + "AND responded_at >= "
                + "DATE_SUB(NOW(), INTERVAL 5 MINUTE)";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, requestId);
            pst.setInt(2, donorId);


            int rows =
                    pst.executeUpdate();


            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Your response has been cancelled."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "The cancellation period has expired."
                );
            }


            refreshRequests();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to cancel your response.\n"
                    + ex.getMessage()
            );
        }
    }


    private void refreshRequests() {

        requestPanel.removeAll();


        JLabel heading =
                new JLabel(
                        "Blood Requests Matching You"
                );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        requestPanel.add(
                Box.createVerticalStrut(30)
        );

        requestPanel.add(heading);

        requestPanel.add(
                Box.createVerticalStrut(25)
        );


        loadRequests();


        requestPanel.revalidate();
        requestPanel.repaint();
    }


    private void showMessage(
            String message) {

        JLabel label =
                new JLabel(message);

        label.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        requestPanel.add(label);
    }
}