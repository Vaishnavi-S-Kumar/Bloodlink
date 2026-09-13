import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DonorAvailabilityFrame extends JPanel {

    Color darkRed = new Color(150, 30, 45);

    private int userId;
    private JComboBox<String> availabilityBox;

    public DonorAvailabilityFrame(int userId) {

        this.userId = userId;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(700, 65));

        JLabel title = new JLabel("  BLOODLINK - AVAILABILITY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        topPanel.add(title, BorderLayout.WEST);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(
                new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        JLabel heading = new JLabel("Set Your Availability");
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message = new JLabel(
                "Let hospitals know whether you are available to donate."
        );
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        availabilityBox = new JComboBox<>(
                new String[]{
                        "Available",
                        "Not Available"
                }
        );

        availabilityBox.setMaximumSize(
                new Dimension(250, 35)
        );

        availabilityBox.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // Load current availability
        loadAvailability();

        JButton saveButton = new JButton("SAVE");

        saveButton.setBackground(darkRed);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        saveButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        saveButton.addActionListener(e -> saveAvailability());

        centerPanel.add(
                Box.createVerticalStrut(70)
        );

        centerPanel.add(heading);

        centerPanel.add(
                Box.createVerticalStrut(15)
        );

        centerPanel.add(message);

        centerPanel.add(
                Box.createVerticalStrut(30)
        );

        centerPanel.add(availabilityBox);

        centerPanel.add(
                Box.createVerticalStrut(20)
        );

        centerPanel.add(saveButton);

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

    // Load current availability from database

    private void loadAvailability() {

        String sql =
                "SELECT availability " +
                "FROM donors " +
                "WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String availability =
                        rs.getString("availability");

                if ("AVAILABLE".equalsIgnoreCase(availability)) {
                    availabilityBox.setSelectedItem("Available");
                } else {
                    availabilityBox.setSelectedItem("Not Available");
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading availability:\n" + e.getMessage()
            );
        }
    }

    // Save availability to database

    private void saveAvailability() {

        String selectedStatus =
                (String) availabilityBox.getSelectedItem();

        String databaseStatus;

        if ("Available".equals(selectedStatus)) {
            databaseStatus = "AVAILABLE";
        } else {
            databaseStatus = "NOT AVAILABLE";
        }

        String sql =
                "UPDATE donors " +
                "SET availability = ? " +
                "WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, databaseStatus);
            pst.setInt(2, userId);

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Availability updated to: "
                                + selectedStatus
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
                    "Error updating availability:\n"
                            + e.getMessage()
            );
        }
    }

    // Compatibility method for current DonorFrame navigation

    public Container getContentPane() {
        return this;
    }
}