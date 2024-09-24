package ua.numbers.task.pages;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.util.Random;

public class NumbersPage {
    private final JFrame nFrame;
    private final JPanel numbersPanel;
    private boolean isDescending = true;
    private int[] numbers;
    private final JButton sortButton;
    private final Random rand = new Random();

    private static final Font FONT = new Font("Arial", Font.PLAIN, 11);
    private static final Dimension BUTTON = new Dimension(60, 25);
    private static final Insets INSETS = new Insets(10, 10, 10, 10);
    private static final Insets BUTTON_MARGIN = new Insets(2, 5, 2, 5);

    public NumbersPage(int count, JFrame frame) {
        nFrame = frame;
        nFrame.getContentPane().removeAll();
        nFrame.setSize(800, 520);
        nFrame.setLayout(new BorderLayout());

        numbers = generateRandomNumbers(count);
        numbersPanel = new JPanel();
        numbersPanel.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(numbersPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        displayNumbers(numbers);

        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = INSETS;

        sortButton = new JButton("Sort");
        sortButton.setFont(FONT);
        sortButton.setMargin(BUTTON_MARGIN);
        sortButton.setPreferredSize(BUTTON);
        sortButton.setBackground(Color.GREEN);
        sortButton.setForeground(Color.WHITE);
        buttonsPanel.add(sortButton, gbc);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(FONT);
        resetButton.setPreferredSize(BUTTON);
        resetButton.setMargin(BUTTON_MARGIN);
        resetButton.setBackground(Color.GREEN);
        resetButton.setForeground(Color.WHITE);
        gbc.gridy = 1;
        buttonsPanel.add(resetButton, gbc);
        controlPanel.add(buttonsPanel, BorderLayout.NORTH);
        sortButton.addActionListener(e -> sortNumbers());
        resetButton.addActionListener(e -> new LaunchPage(nFrame));

        nFrame.add(scrollPane, BorderLayout.CENTER);
        nFrame.add(controlPanel, BorderLayout.EAST);
        nFrame.revalidate();
        nFrame.repaint();
    }

    private void displayNumbers(int[] nums) {
        numbersPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = INSETS;

        int maxInRow = 10;
        for (int i = 0; i < nums.length; i++) {
            gbc.gridx = i / maxInRow;
            gbc.gridy = i % maxInRow;

            JButton numberButton = new JButton(String.valueOf(nums[i]));
            numberButton.setPreferredSize(BUTTON);
            numberButton.setFont(FONT);
            numberButton.setBackground(Color.BLUE);
            numberButton.setForeground(Color.WHITE);
            int finalI = i;
            numberButton.addActionListener(e -> handleNumberClick(nums[finalI]));
            numbersPanel.add(numberButton, gbc);
        }
        gbc.weightx = 1.0;
        gbc.gridx++;
        numbersPanel.add(new JLabel(), gbc);

        numbersPanel.revalidate();
        numbersPanel.repaint();
    }

    private int[] generateRandomNumbers(int count) {
        int[] nums = new int[count];
        boolean less30Condition = false;
        for (int i = 0; i < count; i++) {
            nums[i] = rand.nextInt(1001);
            if (nums[i] <= 30) {
                less30Condition = true;
            }
        }
        if (!less30Condition) {
            nums[rand.nextInt(count)] = rand.nextInt(31);
        }

        return nums;
    }

    private void handleNumberClick(int number) {
        if (number <= 30) {
            numbers = new int[number];
            numbers = generateRandomNumbers(numbers.length);
            displayNumbers(numbers);
        } else {
            JOptionPane.showMessageDialog(nFrame, "Please select a value smaller or equal to 30.");
        }
    }

    private void sortNumbers() {
        new Thread(() -> {
            quickSort(numbers, 0, numbers.length - 1);
            isDescending = !isDescending;
            SwingUtilities.invokeLater(() -> sortButton.setText(isDescending ? "Sort" : "Sort Asc"));
        }).start();
    }

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            displayNumbers(array);
            pauseForVisualization();
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (isDescending) {
                if (array[j] >= pivot) {
                    i++;
                    swap(array, i, j);
                    displayNumbers(array);
                    pauseForVisualization();
                }
            } else {
                if (array[j] <= pivot) {
                    i++;
                    swap(array, i, j);
                    displayNumbers(array);
                    pauseForVisualization();
                }
            }
        }
        swap(array, i + 1, high);
        displayNumbers(array);
        pauseForVisualization();
        return i + 1;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void pauseForVisualization() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
