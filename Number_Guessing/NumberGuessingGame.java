import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private int randomNumber;
    private int maxAttempts;
    private int attempts;
    private int score;
    private int round;
    
    private JTextField inputField;
    private JButton guessButton;
    private JLabel messageLabel;
    private JLabel scoreLabel;
    private JLabel attemptsLabel;
    private JLabel roundLabel;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));
        
        // Initialize game variables
        maxAttempts = 10;
        attempts = 0;
        score = 0;
        round = 1;
        generateRandomNumber();
        
        // Create UI components
        inputField = new JTextField();
        guessButton = new JButton("Guess");
        messageLabel = new JLabel("Enter your guess:");
        scoreLabel = new JLabel("Score: " + score);
        attemptsLabel = new JLabel("Attempts: " + attempts + "/" + maxAttempts);
        roundLabel = new JLabel("Round: " + round);
        
        // Add components to frame
        add(messageLabel);
        add(inputField);
        add(guessButton);
        add(scoreLabel);
        add(attemptsLabel);
        add(roundLabel);
        
        // Add action listener to button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });
    }

    private void generateRandomNumber() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1; // Random number between 1 and 100
    }

    private void handleGuess() {
        try {
            int guess = Integer.parseInt(inputField.getText());
            attempts++;
            if (guess == randomNumber) {
                score += (maxAttempts - attempts + 1);
                messageLabel.setText("Correct! You've guessed the number.");
                startNewRound();
            } else if (guess < randomNumber) {
                messageLabel.setText("Higher! Try again.");
            } else {
                messageLabel.setText("Lower! Try again.");
            }
            if (attempts >= maxAttempts) {
                messageLabel.setText("Out of attempts! The number was " + randomNumber);
                startNewRound();
            }
            attemptsLabel.setText("Attempts: " + attempts + "/" + maxAttempts);
            scoreLabel.setText("Score: " + score);
        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid number.");
        }
    }

    private void startNewRound() {
        round++;
        attempts = 0;
        generateRandomNumber();
        messageLabel.setText("Enter your guess:");
        inputField.setText("");
        roundLabel.setText("Round: " + round);
        attemptsLabel.setText("Attempts: " + attempts + "/" + maxAttempts);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGame().setVisible(true);
            }
        });
    }
}
