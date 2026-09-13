import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DonorHistoryFrame extends JPanel {

    Color darkRed = new Color(150, 30, 45);

    private int userId;

    private JPanel historyPanel;

    public DonorHistoryFrame(int userId) {

        this.userId = userId;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top bar

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkRed);
        topPanel.setPreferredSize(new Dimension(800, 65));

        JLabel title =
                new JLabel("  BLOODLINK - DONATION HISTORY");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        21
                )
        );

        topPanel.add(
                title,
                BorderLayout.WEST
        );

        // Heading

        JLabel heading =
                new JLabel(
                        "Your Donation History",
                        SwingConstants.CENTER
                );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        heading.setForeground(darkRed);

        // History panel

        historyPanel = new JPanel();

        historyPanel.setBackground(Color.WHITE);

        historyPanel.setLayout(
                new BoxLayout(
                        historyPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(historyPanel);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        JPanel centerPanel =
                new JPanel(new BorderLayout());

        centerPanel.setBackground(Color.WHITE);

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        50,
                        25,
                        50
                )
        );

        centerPanel.add(
                heading,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
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

        loadDonationHistory();
    }

    // Load donation history from database

    private void loadDonationHistory() {

        historyPanel.removeAll();

        String sql =
                "SELECT donation_date, hospital_name, blood_group " +
                "FROM donation_history " +
                "WHERE donor_id = (" +
                "SELECT donor_id FROM donors WHERE user_id = ?" +
                ") " +
                "ORDER BY donation_date DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);

            ResultSet rs =
                    pst.executeQuery();

            boolean hasRecords = false;

            while (rs.next()) {

                hasRecords = true;

                String donationDate =
                        rs.getDate("donation_date").toString();

                String hospitalName =
                        rs.getString("hospital_name");

                String bloodGroup =
                        rs.getString("blood_group");

                JPanel recordPanel =
                        createHistoryRecord(
                                donationDate,
                                hospitalName,
                                bloodGroup
                        );

                historyPanel.add(recordPanel);

                historyPanel.add(
                        Box.createVerticalStrut(15)
                );
            }

            if (!hasRecords) {

                JLabel noRecords =
                        new JLabel(
                                "No donation records available yet.",
                                SwingConstants.CENTER
                        );

                noRecords.setFont(
                        new Font(
                                "Arial",
                                Font.PLAIN,
                                16
                        )
                );

                noRecords.setAlignmentX(
                        Component.CENTER_ALIGNMENT
                );

                historyPanel.add(
                        Box.createVerticalStrut(60)
                );

                historyPanel.add(noRecords);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading donation history:\n"
                            + e.getMessage()
            );
        }

        historyPanel.revalidate();
        historyPanel.repaint();
    }

    // Create one donation history record

    private JPanel createHistoryRecord(
            String donationDate,
            String hospitalName,
            String bloodGroup
    ) {

        JPanel recordPanel =
                new JPanel(new BorderLayout());

        recordPanel.setBackground(
                new Color(250, 250, 250)
        );

        recordPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(210, 210, 210)
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        recordPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        100
                )
        );

        JLabel dateLabel =
                new JLabel(
                        "Donation Date: "
                                + donationDate
                );

        dateLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        JLabel hospitalLabel =
                new JLabel(
                        "Hospital: "
                                + hospitalName
                );

        hospitalLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JLabel bloodLabel =
                new JLabel(
                        "Blood Group: "
                                + bloodGroup
                );

        bloodLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JPanel detailsPanel =
                new JPanel();

        detailsPanel.setBackground(
                new Color(250, 250, 250)
        );

        detailsPanel.setLayout(
                new BoxLayout(
                        detailsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        detailsPanel.add(dateLabel);

        detailsPanel.add(
                Box.createVerticalStrut(7)
        );

        detailsPanel.add(hospitalLabel);

        detailsPanel.add(
                Box.createVerticalStrut(5)
        );

        detailsPanel.add(bloodLabel);

        recordPanel.add(
                detailsPanel,
                BorderLayout.WEST
        );

        return recordPanel;
    }

    // Compatibility method for current DonorFrame navigation

    public Container getContentPane() {
        return this;
    }
}