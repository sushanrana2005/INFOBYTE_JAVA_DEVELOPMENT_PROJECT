import javax.swing.*;
import java.awt.*;

public class ATMFrame extends JFrame {
    private Bank bank;
    private User currentUser;

    public ATMFrame() {
        setTitle("ATM");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        bank = new Bank();

        add(new LoginPanel(this), "login");
        add(new MenuPanel(this), "menu");

        showLoginPanel();
    }

    public void showLoginPanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "login");
    }

    public void showMenuPanel(User user) {
        currentUser = user;
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "menu");
        ((MenuPanel) getContentPane().getComponent(1)).updateUser(currentUser);
    }

    public Bank getBank() {
        return bank;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
