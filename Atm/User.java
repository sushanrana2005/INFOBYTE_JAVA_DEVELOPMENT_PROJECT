import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;
    
    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getPin() {
        return pin;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }
    
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
        } else {
            transactionHistory.add("Attempted Withdrawal: $" + amount + " (Failed - Insufficient Funds)");
        }
    }
    
    public void transfer(User recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.getUserId());
        } else {
            transactionHistory.add("Attempted Transfer: $" + amount + " to " + recipient.getUserId() + " (Failed - Insufficient Funds)");
        }
    }
    
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}
