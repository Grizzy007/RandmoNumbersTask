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

public class LaunchPage {
    private final JFrame frame;
    private final JTextField textField = new JTextField(10);

    private static final Insets INSETS = new Insets(10, 10, 10, 10);
    private static final Dimension SIZE = new Dimension(100, 25);

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
        frame.revalidate();
        frame.repaint();
    }
}
