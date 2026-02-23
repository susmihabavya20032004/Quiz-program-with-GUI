import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizGameSwingWithTimer extends JFrame implements ActionListener {
    String[] questions = {
        "what is 3*3-5 ?",
        "Which is the Even Prime number among these Numbers ?",
        "What is (9/3*3)+9 ?",
        "What is 4+6-(3*9/3) ?"
    };

    String[][] options = {
        {"2", "-6", "4", "-4"},
        {"6", "8", "4", "2"},
        {"18", "10", "27", "45"},
        {"6", "4", "1", "-1"}
    };

    char[] answers = {'C', 'D', 'A', 'C'};

    JLabel questionLabel, timerLabel;
    JRadioButton[] optionButtons = new JRadioButton[4];
    ButtonGroup optionsGroup;
    JButton nextButton;

    int currentQuestion = 0;
    int score = 0;
    int timeLeft = 15;
    Timer timer;

    public QuizGameSwingWithTimer() {
        setTitle("Java Quiz Game");
        setSize(550, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 1));
        optionsGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionsGroup.add(optionButtons[i]);
            centerPanel.add(optionButtons[i]);
        }

        timerLabel = new JLabel("Time left: 15 seconds", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(timerLabel);

        add(centerPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        add(nextButton, BorderLayout.SOUTH);

        loadQuestion(currentQuestion);
        startTimer();

        setVisible(true);
    }

    public void loadQuestion(int index) {
        questionLabel.setText("Q" + (index + 1) + ": " + questions[index]);
        char option = 'A';
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText("(" + option++ + ") " + options[index][i]);
        }
        optionsGroup.clearSelection();
        timeLeft = 15;
        timerLabel.setText("Time left: 15 seconds");
    }

    public void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft + " seconds");
            if (timeLeft <= 0) {
                timer.stop();
                autoSubmit();
            }
        });
        timer.start();
    }

    public void autoSubmit() {
        JOptionPane.showMessageDialog(this, "Time's up! Auto-submitting...");
        checkAnswer();
        moveToNext();
    }

    public void actionPerformed(ActionEvent e) {
        if (isAnswerSelected()) {
            timer.stop();
            checkAnswer();
            moveToNext();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an answer.");
        }
    }

    public boolean isAnswerSelected() {
        for (JRadioButton btn : optionButtons) {
            if (btn.isSelected()) return true;
        }
        return false;
    }

    public char getSelectedAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                return (char) ('A' + i);
            }
        }
        return ' ';
    }

    void checkAnswer() {
        char selected = getSelectedAnswer();
        if (selected == answers[currentQuestion]) {
            score++;
        }
    }

    void moveToNext() {
        currentQuestion++;
        if (currentQuestion < questions.length) {
            loadQuestion(currentQuestion);
            startTimer();
        } else {
            showResult();
        }
    }

    void showResult() {
        String message = "Your score: " + score + "/" + questions.length;
        if (score == questions.length)
            message += "\nExcellent! Great Score";
        else if (score >= questions.length / 2)
            message += "\nGood job!";
        else
            message += "\nBetter luck next time.";

        JOptionPane.showMessageDialog(this, message);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizGameSwingWithTimer::new);
    }
}
