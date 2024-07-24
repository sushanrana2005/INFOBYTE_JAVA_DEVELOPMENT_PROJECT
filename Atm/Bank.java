import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, User> users;
    
    public Bank() {
        users = new HashMap<>();
        initializeUsers();
    }
    
    private void initializeUsers() {
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "5678");
        
        users.put(user1.getUserId(), user1);
        users.put(user2.getUserId(), user2);
    }
    
    public User authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.getPin().equals(pin)) {
            return user;
        }
        return null;
    }
}
