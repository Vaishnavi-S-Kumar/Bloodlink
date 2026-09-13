import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DonorNotificationFrame extends JPanel {

    Color darkRed = new Color(150, 30, 45);

    private int userId;

    private JPanel notificationPanel;

    public DonorNotificationFrame(int userId) {

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
                new JLabel("  BLOODLINK - NOTIFICATIONS");

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
                        "Notifications",
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

        // Notification panel

        notificationPanel = new JPanel();

        notificationPanel.setBackground(Color.WHITE);

        notificationPanel.setLayout(
                new BoxLayout(
                        notificationPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(notificationPanel);

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

        loadNotifications();
    }

    // Load notifications from database

    private void loadNotifications() {

        notificationPanel.removeAll();

        String sql =
                "SELECT notification_id, message, is_read, created_at " +
                "FROM notifications " +
                "WHERE user_id = ? " +
                "ORDER BY created_at DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);

            ResultSet rs =
                    pst.executeQuery();

            boolean hasNotifications = false;

            while (rs.next()) {

                hasNotifications = true;

                int notificationId =
                        rs.getInt("notification_id");

                String message =
                        rs.getString("message");

                boolean isRead =
                        rs.getBoolean("is_read");

                Timestamp createdAt =
                        rs.getTimestamp("created_at");

                JPanel notification =
                        createNotification(
                                notificationId,
                                message,
                                isRead,
                                createdAt
                        );

                notificationPanel.add(notification);

                notificationPanel.add(
                        Box.createVerticalStrut(15)
                );
            }

            if (!hasNotifications) {

                JLabel noNotifications =
                        new JLabel(
                                "No notifications available yet.",
                                SwingConstants.CENTER
                        );

                noNotifications.setFont(
                        new Font(
                                "Arial",
                                Font.PLAIN,
                                16
                        )
                );

                noNotifications.setAlignmentX(
                        Component.CENTER_ALIGNMENT
                );

                notificationPanel.add(
                        Box.createVerticalStrut(60)
                );

                notificationPanel.add(
                        noNotifications
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading notifications:\n"
                            + e.getMessage()
            );
        }

        notificationPanel.revalidate();
        notificationPanel.repaint();
    }

    // Create one notification

    private JPanel createNotification(
            int notificationId,
            String message,
            boolean isRead,
            Timestamp createdAt
    ) {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(
                new Color(250, 250, 250)
        );

        panel.setBorder(
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

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );

        JLabel messageLabel =
                new JLabel(
                        "<html>"
                                + message
                                + "</html>"
                );

        messageLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        String dateText = "";

        if (createdAt != null) {

            SimpleDateFormat formatter =
                    new SimpleDateFormat(
                            "dd-MM-yyyy HH:mm"
                    );

            dateText =
                    formatter.format(createdAt);
        }

        JLabel dateLabel =
                new JLabel(
                        dateText
                );

        dateLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        dateLabel.setForeground(
                Color.GRAY
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setBackground(
                new Color(250, 250, 250)
        );

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.add(messageLabel);

        textPanel.add(
                Box.createVerticalStrut(8)
        );

        textPanel.add(dateLabel);

        panel.add(
                textPanel,
                BorderLayout.CENTER
        );

        if (!isRead) {

            JButton readButton =
                    new JButton("Mark as Read");

            readButton.setBackground(darkRed);
            readButton.setForeground(Color.WHITE);
            readButton.setFocusPainted(false);

            readButton.addActionListener(
                    e -> markAsRead(notificationId)
            );

            panel.add(
                    readButton,
                    BorderLayout.EAST
            );
        } else {

            JLabel readLabel =
                    new JLabel("READ");

            readLabel.setForeground(
                    new Color(80, 120, 80)
            );

            readLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            12
                    )
            );

            panel.add(
                    readLabel,
                    BorderLayout.EAST
            );
        }

        return panel;
    }

    // Mark notification as read

    private void markAsRead(int notificationId) {

        String sql =
                "UPDATE notifications " +
                "SET is_read = TRUE " +
                "WHERE notification_id = ? " +
                "AND user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, notificationId);
            pst.setInt(2, userId);

            int rowsUpdated =
                    pst.executeUpdate();

            if (rowsUpdated > 0) {

                loadNotifications();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating notification:\n"
                            + e.getMessage()
            );
        }
    }

    // Compatibility method for current DonorFrame navigation

    public Container getContentPane() {

        return this;
    }
}