package ua.numbers.task.pages;

import javax.swing.*;
import java.awt.*;

public class LaunchPage {
    private final JFrame frame;
    private final JTextField textField = new JTextField(10);

    public LaunchPage() {
        frame = new JFrame("Random Numbers");
        frame.getContentPane().removeAll();
        frame.setSize(800, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);

        JLabel label = new JLabel("How many numbers to display?");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(label, gbc);

        gbc.gridy = 1;
        textField.setPreferredSize(new Dimension(100, 25));
        frame.add(textField, gbc);

        gbc.gridy = 2;
        JButton myButton = new JButton("Enter");
        frame.add(myButton, gbc);

        myButton.setBackground(Color.BLUE);
        myButton.setForeground(Color.WHITE);
        myButton.setPreferredSize(new Dimension(100, 25));
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

    }
}
