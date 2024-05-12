import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Main extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private char currentPlayer;
    private JLabel statusLabel;
    private JButton resetButton;

    public Main() {
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 400); // Adjusted size to fit the entire game
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        // Load the image for the icon/logo
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource("./Assets/codingg.jpeg")); // Change "./Assets/codingg.jpeg" to your image file name
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (icon != null) {
            setIconImage(icon); // Set the image as the icon/logo
        }

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        currentPlayer = 'X';

        int buttonSize = Math.min(100, Math.min(getWidth(), getHeight()) / 3); // Size of buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, buttonSize / 3);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttonFont);
                buttons[i][j].addActionListener(this);
                buttons[i][j].setPreferredSize(new Dimension(buttonSize, buttonSize)); // Set preferred size
                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Player " + currentPlayer + "'s turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("")) {
            button.setText(String.valueOf(currentPlayer));
            if (checkWin()) {
                updateButtonColors(Color.GREEN);
                int choice = JOptionPane.showConfirmDialog(this, "Player " + currentPlayer + " wins! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            } else if (isBoardFull()) {
                updateButtonColors(Color.ORANGE);
                int choice = JOptionPane.showConfirmDialog(this, "It's a draw! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText("Player " + currentPlayer + "'s turn");
            }
        }
    }
    private void updateButtonColors(Color color) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setBackground(color);
            }
        }
    }
    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true; // Check horizontal win
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true; // Check vertical win
            }
        }
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true; // Check diagonal win (top-left to bottom-right)
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true; // Check diagonal win (top-right to bottom-left)
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false; // Board is not full
                }
            }
        }
        return true; // Board is full
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null); // Reset button colors
            }
        }
        currentPlayer = 'X';
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }

    public static void main(String[] args) {
        new Main();
    }
}
