import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrame extends JFrame {
    private int maxAttempts;
    private int attempts;
    private int[] randomNumber;
    private JTextArea historyArea;
    private JTextArea answerArea;
    private JTextField guessField;
    private JTextField attemptsField;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private List<String> previousGuesses;
    private int score;
    private JButton startButton;

    public GameFrame() {
        setTitle("Number Guessing Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        attempts = 0;
        score = 0;
        previousGuesses = new ArrayList<>();
        maxAttempts = 10;  // Default value for attempts

        // Main Panel for user interaction
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        // Panel for setting number of attempts
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new FlowLayout());

        JLabel attemptsLabelSetup = new JLabel("Set number of attempts:");
        attemptsField = new JTextField(5);
        attemptsField.setText(String.valueOf(maxAttempts));
        startButton = new JButton("Start Game");

        setupPanel.add(attemptsLabelSetup);
        setupPanel.add(attemptsField);
        setupPanel.add(startButton);

        // Panel for guess input and submission
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel guessLabel = new JLabel("Enter your guess:");
        guessField = new JTextField(10);
        JButton submitButton = new JButton("Submit Guess");
        JButton rulesButton = new JButton("Game Rules");

        inputPanel.add(guessLabel);
        inputPanel.add(guessField);
        inputPanel.add(submitButton);
        inputPanel.add(rulesButton);

        // Panel for history and status
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane historyScrollPane = new JScrollPane(historyArea);

        JPanel statusInfoPanel = new JPanel();
        statusInfoPanel.setLayout(new GridLayout(1, 2));

        attemptsLabel = new JLabel("Attempts left: " + (maxAttempts - attempts));
        scoreLabel = new JLabel("Score: " + score);

        statusInfoPanel.add(attemptsLabel);
        statusInfoPanel.add(scoreLabel);

        statusPanel.add(statusInfoPanel, BorderLayout.NORTH);
        statusPanel.add(historyScrollPane, BorderLayout.CENTER);

        // Panel for showing the correct answer
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BorderLayout());

        answerArea = new JTextArea("The correct number will appear here after game ends.");
        answerArea.setEditable(false);
        JScrollPane answerScrollPane = new JScrollPane(answerArea);

        answerPanel.add(new JLabel("Correct Answer:"), BorderLayout.NORTH);
        answerPanel.add(answerScrollPane, BorderLayout.CENTER);

        // Add panels to main frame
        mainPanel.add(setupPanel);
        mainPanel.add(inputPanel);
        mainPanel.add(statusPanel);
        mainPanel.add(answerPanel);
        add(mainPanel, BorderLayout.CENTER);

        // Event handlers
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRules();
            }
        });

        setLocationRelativeTo(null);
    }

    private void startGame() {
        try {
            maxAttempts = Integer.parseInt(attemptsField.getText());
            if (maxAttempts <= 0) {
                JOptionPane.showMessageDialog(this, "Number of attempts must be greater than 0.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            attempts = 0;
            score = 0;
            previousGuesses.clear();
            generateRandomNumber();
            historyArea.setText("");
            answerArea.setText("The correct number will appear here after game ends.");
            updateStatus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of attempts.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateRandomNumber() {
        Random rand = new Random();
        randomNumber = new int[4];
        for (int i = 0; i < randomNumber.length; i++) {
            randomNumber[i] = rand.nextInt(10); // digits from 0 to 9
        }
    }

    private void processGuess() {
        if (attempts >= maxAttempts) {
            historyArea.append("Game over! You've used all your attempts.\n");
            showCorrectAnswer();
            return;
        }

        try {
            String input = guessField.getText();
            int[] guess = parseGuess(input);
            if (guess == null) {
                historyArea.append("Invalid input. Please enter a 4-digit number.\n");
                return;
            }
            attempts++;
            previousGuesses.add(input);
            String result = evaluateGuess(guess);
            if (result.equals("4 bulls")) {
                score += (maxAttempts - attempts + 1) * 10; // Points based on attempts left
                historyArea.append("Congratulations! You guessed correctly in " + attempts + " attempts.\n");
                historyArea.append("Your score: " + score + "\n");
                int playAgain = JOptionPane.showConfirmDialog(this, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (playAgain == JOptionPane.YES_OPTION) {
                    startGame();
                } else {
                    System.exit(0);
                }
            } else {
                historyArea.append(result + "\n");
                showPreviousGuesses();
            }
            updateStatus();
            guessField.setText("");
        } catch (NumberFormatException e) {
            historyArea.append("Invalid input. Please enter a valid number.\n");
        }
    }

    private int[] parseGuess(String input) {
        if (input.length() != 4) {
            return null;
        }
        int[] guess = new int[4];
        try {
            for (int i = 0; i < 4; i++) {
                guess[i] = Character.getNumericValue(input.charAt(i));
                if (guess[i] < 0 || guess[i] > 9) {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return guess;
    }

    private String evaluateGuess(int[] guess) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < 4; i++) {
            if (guess[i] == randomNumber[i]) {
                bulls++;
            } else if (contains(guess[i], randomNumber)) {
                cows++;
            }
        }
        if (bulls == 4) {
            return "4 bulls";
        }
        return bulls + " bulls and " + cows + " cows";
    }

    private boolean contains(int digit, int[] array) {
        for (int value : array) {
            if (value == digit) {
                return true;
            }
        }
        return false;
    }

    private void showPreviousGuesses() {
        historyArea.append("Previous guesses:\n");
        for (String guess : previousGuesses) {
            historyArea.append(guess + "\n");
        }
    }

    private void showCorrectAnswer() {
        StringBuilder answer = new StringBuilder("Correct number was: ");
        for (int num : randomNumber) {
            answer.append(num);
        }
        answerArea.setText(answer.toString());
    }

    private void updateStatus() {
        attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
        scoreLabel.setText("Score: " + score);
    }

    private void showRules() {
        String rules = "Game Rules:\n\n" +
                "1. The system will generate a random 4-digit number.\n" +
                "2. Your task is to guess the number.\n" +
                "3. For each guess, you will receive feedback in the form of 'bulls' and 'cows':\n" +
                "   - 'Bull': A digit in your guess is correct and in the correct position.\n" +
                "   - 'Cow': A digit in your guess is correct but in the wrong position.\n" +
                "4. You can set the number of attempts at the beginning of the game.\n" +
                "5. Your score is based on the number of attempts you take to guess the number correctly.\n" +
                "6. After using all attempts, the correct number will be revealed.\n";
        JOptionPane.showMessageDialog(this, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame().setVisible(true));
    }
}
