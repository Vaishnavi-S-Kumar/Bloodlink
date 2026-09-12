import javax.swing.*;
import java.awt.*;

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

        JLabel heading = new JLabel("Emergency Blood Requests");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        requestPanel.add(Box.createVerticalStrut(30));
        requestPanel.add(heading);
        requestPanel.add(Box.createVerticalStrut(25));

        // Request 1
        JPanel request1 = createRequest(
                "City Hospital",
                "O+",
                "2 Units",
                "Urgent",
                "Kollam"
        );

        // Request 2
        JPanel request2 = createRequest(
                "General Hospital",
                "B+",
                "1 Unit",
                "Emergency",
                "Thiruvananthapuram"
        );

        requestPanel.add(request1);
        requestPanel.add(Box.createVerticalStrut(15));
        requestPanel.add(request2);

        JScrollPane scrollPane =
                new JScrollPane(requestPanel);

        scrollPane.setBorder(null);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    JPanel createRequest(
            String hospital,
            String bloodGroup,
            String units,
            String priority,
            String location) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 248, 248));

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)
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

            JOptionPane.showMessageDialog(
                    this,
                    "You have accepted the blood request."
            );
        });

        declineButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "You have declined the blood request."
            );
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
}