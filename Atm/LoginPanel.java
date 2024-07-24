import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private ATMFrame atmFrame;
    private JTextField userIdField;
    private JPasswordField pinField;
    private JLabel messageLabel;

    public LoginPanel(ATMFrame atmFrame) {
        this.atmFrame = atmFrame;
        setLayout(new GridLayout(4, 1));

        userIdField = new JTextField();
        pinField = new JPasswordField();
        messageLabel = new JLabel("", JLabel.CENTER);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String pin = new String(pinField.getPassword());
                User user = atmFrame.getBank().authenticateUser(userId, pin);
                if (user != null) {
                    atmFrame.showMenuPanel(user);
                } else {
                    messageLabel.setText("Invalid ID or PIN");
                }
            }
        });

        add(new JLabel("User ID:", JLabel.CENTER));
        add(userIdField);
        add(new JLabel("PIN:", JLabel.CENTER));
        add(pinField);
        add(loginButton);
        add(messageLabel);
    }
}
