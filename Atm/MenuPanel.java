import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private ATMFrame atmFrame;
    private User user;
    private JLabel balanceLabel;
    private JTextArea transactionArea;

    public MenuPanel(ATMFrame atmFrame) {
        this.atmFrame = atmFrame;
        setLayout(new GridLayout(7, 1));

        balanceLabel = new JLabel("Balance: $0.0", JLabel.CENTER);
        transactionArea = new JTextArea(5, 20);
        transactionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionArea);

        JButton historyButton = new JButton("Transaction History");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton transferButton = new JButton("Transfer");
        JButton quitButton = new JButton("Quit");

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionHistory();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleWithdraw();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeposit();
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTransfer();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atmFrame.showLoginPanel();
            }
        });

        add(balanceLabel);
        add(historyButton);
        add(withdrawButton);
        add(depositButton);
        add(transferButton);
        add(quitButton);
        add(scrollPane);
    }

    public void updateUser(User user) {
        this.user = user;
        balanceLabel.setText("Balance: $" + user.getBalance());
    }

    private void showTransactionHistory() {
        transactionArea.setText("");
        for (String transaction : user.getTransactionHistory()) {
            transactionArea.append(transaction + "\n");
        }
    }

    private void handleWithdraw() {
        String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
        try {
            double amount = Double.parseDouble(amountStr);
            user.withdraw(amount);
            balanceLabel.setText("Balance: $" + user.getBalance());
            JOptionPane.showMessageDialog(this, "Withdrawal successful!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.");
        }
    }

    private void handleDeposit() {
        String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");
        try {
            double amount = Double.parseDouble(amountStr);
            user.deposit(amount);
            balanceLabel.setText("Balance: $" + user.getBalance());
            JOptionPane.showMessageDialog(this, "Deposit successful!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.");
        }
    }

    private void handleTransfer() {
        String recipientId = JOptionPane.showInputDialog("Enter recipient User ID:");
        String amountStr = JOptionPane.showInputDialog("Enter amount to transfer:");
        try {
            double amount = Double.parseDouble(amountStr);
            User recipient = atmFrame.getBank().authenticateUser(recipientId, "");
            if (recipient != null) {
                user.transfer(recipient, amount);
                balanceLabel.setText("Balance: $" + user.getBalance());
                JOptionPane.showMessageDialog(this, "Transfer successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Recipient not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.");
        }
    }
}
