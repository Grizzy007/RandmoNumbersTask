package ua.numbers.task.pages;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Color;

/**
 * The LaunchPage class represents the initial page of the application,
 * where the user can input the number of random numbers to display.
 * Upon valid input, it launches the NumbersPage with the given number.
 *
 * @author Stas
 * @version 1.0.0
 */
public class LaunchPage {
    private final JFrame frame;
    private final JTextField textField = new JTextField(10);

    private static final Insets INSETS = new Insets(10, 10, 10, 10);
    private static final Dimension SIZE = new Dimension(100, 25);

    /**
     * Constructor of LaunchPage, creates a new LaunchPage with the given frame.
     *
     * @param jFrame The main application frame where the page will be displayed.
     */
    public LaunchPage(JFrame jFrame) {
        frame = jFrame;
        frame.getContentPane().removeAll();
        frame.getContentPane().removeAll();
        frame.setSize(800, 520);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);

        JLabel label = new JLabel("How many numbers to display?");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = INSETS;
        frame.add(label, gbc);

        gbc.gridy = 1;
        textField.setPreferredSize(SIZE);
        frame.add(textField, gbc);

        gbc.gridy = 2;
        JButton myButton = new JButton("Enter");
        frame.add(myButton, gbc);

        myButton.setBackground(Color.BLUE);
        myButton.setForeground(Color.WHITE);
        myButton.setPreferredSize(SIZE);

        // Action listener for button to handle input validation and navigation to NumbersPage
        myButton.addActionListener(e -> {
            String input = textField.getText();
            try {
                int count = Integer.parseInt(input);
                if (count <= 0 || count > 1000) {
                    throw new NumberFormatException();
                }
                new NumbersPage(count, frame);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number (1-1000).");
            }
        });

        // Refresh the frame
        frame.revalidate();
        frame.repaint();
    }
}
