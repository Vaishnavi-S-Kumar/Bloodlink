import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminFrame extends JFrame {

    Color darkRed = new Color(150, 30, 45);

    public AdminFrame() {

        setTitle("BloodLink - Admin");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top bar

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(
                new Dimension(1000, 70)
        );

        JLabel title =
                new JLabel("  BLOODLINK");

        title.setForeground(Color.WHITE);
        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JLabel welcome =
                new JLabel("Authorized Admin  ");

        welcome.setForeground(Color.WHITE);
        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        topPanel.add(
                title,
                BorderLayout.WEST
        );

        topPanel.add(
                welcome,
                BorderLayout.EAST
        );

        // Left menu

        JPanel menuPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                5,
                                5
                        )
                );

        menuPanel.setBackground(
                new Color(245, 245, 245)
        );

        menuPanel.setPreferredSize(
                new Dimension(230, 530)
        );

        JButton verificationButton =
                new JButton("User Verification");

        JButton requestsButton =
                new JButton("Manage Requests");

        JButton usersButton =
                new JButton("Manage Users");

        JButton reportsButton =
                new JButton("Reports");

        menuPanel.add(
                verificationButton
        );

        menuPanel.add(
                requestsButton
        );

        menuPanel.add(
                usersButton
        );

        menuPanel.add(
                reportsButton
        );

        // Center

        JPanel centerPanel =
                new JPanel();

        centerPanel.setBackground(Color.WHITE);

        centerPanel.setLayout(
                new BoxLayout(
                        centerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading =
                new JLabel("Admin Dashboard");

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel message =
                new JLabel(
                        "Verify users and manage the BloodLink system"
                );

        message.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        message.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        centerPanel.add(
                Box.createVerticalStrut(100)
        );

        centerPanel.add(heading);

        centerPanel.add(
                Box.createVerticalStrut(15)
        );

        centerPanel.add(message);

        // Button actions

        verificationButton.addActionListener(
                e -> showPendingUsers()
        );

        requestsButton.addActionListener(
                e -> showRequests()
        );

        usersButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "User Management will be connected next."
            );
        });

        reportsButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Reports will be connected next."
            );
        });

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                menuPanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }

    // SHOW PENDING USERS

    private void showPendingUsers() {

        String sql =
                "SELECT user_id, username, email, role, status " +
                "FROM users " +
                "WHERE status = 'PENDING' " +
                "AND role IN ('DONOR', 'HOSPITAL') " +
                "ORDER BY user_id";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            DefaultListModel<String> model =
                    new DefaultListModel<>();

            while (rs.next()) {

                int userId =
                        rs.getInt("user_id");

                String username =
                        rs.getString("username");

                String email =
                        rs.getString("email");

                String role =
                        rs.getString("role");

                model.addElement(
                        "ID: " + userId
                        + " | Username: " + username
                        + " | Email: " + email
                        + " | Role: " + role
                );
            }

            if (model.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No pending users found."
                );

                return;
            }

            JList<String> userList =
                    new JList<>(model);

            userList.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION
            );

            userList.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            13
                    )
            );

            JScrollPane scrollPane =
                    new JScrollPane(userList);

            scrollPane.setPreferredSize(
                    new Dimension(750, 250)
            );

            JButton approveButton =
                    new JButton("APPROVE");

            JButton rejectButton =
                    new JButton("REJECT");

            JPanel buttonPanel =
                    new JPanel();

            buttonPanel.add(
                    approveButton
            );

            buttonPanel.add(
                    rejectButton
            );

            JPanel panel =
                    new JPanel(new BorderLayout());

            panel.add(
                    scrollPane,
                    BorderLayout.CENTER
            );

            panel.add(
                    buttonPanel,
                    BorderLayout.SOUTH
            );

            JDialog dialog =
                    new JDialog(
                            this,
                            "Pending User Verification",
                            true
                    );

            dialog.setSize(800, 350);
            dialog.setLocationRelativeTo(this);

            dialog.add(panel);

            approveButton.addActionListener(e -> {

                String selected =
                        userList.getSelectedValue();

                if (selected == null) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Please select a user."
                    );

                    return;
                }

                int userId =
                        getUserId(selected);

                updateUserStatus(
                        userId,
                        "APPROVED"
                );

                dialog.dispose();

                showPendingUsers();
            });

            rejectButton.addActionListener(e -> {

                String selected =
                        userList.getSelectedValue();

                if (selected == null) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Please select a user."
                    );

                    return;
                }

                int userId =
                        getUserId(selected);

                updateUserStatus(
                        userId,
                        "REJECTED"
                );

                dialog.dispose();

                showPendingUsers();
            });

            dialog.setVisible(true);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load pending users.\n"
                            + e.getMessage()
            );
        }
    }

    // SHOW BLOOD REQUESTS

    private void showRequests() {

        String sql =
                "SELECT br.request_id, " +
                "h.hospital_name, " +
                "br.patient_name, " +
                "br.blood_group, " +
                "br.units_required, " +
                "br.location, " +
                "br.priority, " +
                "br.request_status " +
                "FROM blood_requests br " +
                "JOIN hospitals h " +
                "ON br.hospital_id = h.hospital_id " +
                "ORDER BY br.created_at DESC";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            DefaultListModel<String> model =
                    new DefaultListModel<>();

            while (rs.next()) {

                model.addElement(
                        "ID: "
                        + rs.getInt("request_id")
                        + " | Hospital: "
                        + rs.getString("hospital_name")
                        + " | Patient: "
                        + rs.getString("patient_name")
                        + " | Blood: "
                        + rs.getString("blood_group")
                        + " | Units: "
                        + rs.getInt("units_required")
                        + " | Priority: "
                        + rs.getString("priority")
                        + " | Status: "
                        + rs.getString("request_status")
                );
            }

            if (model.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No blood requests found."
                );

                return;
            }

            JList<String> requestList =
                    new JList<>(model);

            requestList.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION
            );

            requestList.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            12
                    )
            );

            JScrollPane scrollPane =
                    new JScrollPane(requestList);

            scrollPane.setPreferredSize(
                    new Dimension(850, 300)
            );

            JButton closeButton =
                    new JButton("CLOSE REQUEST");

            JButton cancelButton =
                    new JButton("CANCEL REQUEST");

            JPanel buttonPanel =
                    new JPanel();

            buttonPanel.add(
                    closeButton
            );

            buttonPanel.add(
                    cancelButton
            );

            JPanel panel =
                    new JPanel(new BorderLayout());

            panel.add(
                    scrollPane,
                    BorderLayout.CENTER
            );

            panel.add(
                    buttonPanel,
                    BorderLayout.SOUTH
            );

            JDialog dialog =
                    new JDialog(
                            this,
                            "Manage Blood Requests",
                            true
                    );

            dialog.setSize(900, 400);
            dialog.setLocationRelativeTo(this);

            dialog.add(panel);

            closeButton.addActionListener(e -> {

                String selected =
                        requestList.getSelectedValue();

                if (selected == null) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Please select a request."
                    );

                    return;
                }

                int requestId =
                        getRequestId(selected);

                updateRequestStatus(
                        requestId,
                        "COMPLETED"
                );

                dialog.dispose();

                showRequests();
            });

            cancelButton.addActionListener(e -> {

                String selected =
                        requestList.getSelectedValue();

                if (selected == null) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Please select a request."
                    );

                    return;
                }

                int requestId =
                        getRequestId(selected);

                updateRequestStatus(
                        requestId,
                        "CANCELLED"
                );

                dialog.dispose();

                showRequests();
            });

            dialog.setVisible(true);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load blood requests.\n"
                            + e.getMessage()
            );
        }
    }

    // GET USER ID

    private int getUserId(String selected) {

        String idPart =
                selected.substring(
                        selected.indexOf("ID: ") + 4,
                        selected.indexOf(" |")
                );

        return Integer.parseInt(
                idPart.trim()
        );
    }

    // GET REQUEST ID

    private int getRequestId(String selected) {

        String idPart =
                selected.substring(
                        selected.indexOf("ID: ") + 4,
                        selected.indexOf(" |")
                );

        return Integer.parseInt(
                idPart.trim()
        );
    }

    // UPDATE USER STATUS

    private void updateUserStatus(
            int userId,
            String status
    ) {

        String sql =
                "UPDATE users " +
                "SET status = ? " +
                "WHERE user_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    userId
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "User "
                                + status.toLowerCase()
                                + " successfully."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "User not found."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update user status.\n"
                            + e.getMessage()
            );
        }
    }

    // UPDATE REQUEST STATUS

    private void updateRequestStatus(
            int requestId,
            String status
    ) {

        String sql =
                "UPDATE blood_requests " +
                "SET request_status = ? " +
                "WHERE request_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    requestId
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Request "
                                + status.toLowerCase()
                                + " successfully."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Request not found."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update request.\n"
                            + e.getMessage()
            );
        }
    }
}