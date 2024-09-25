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


/**
 * The NumbersPage class represents a page where random numbers are displayed
 * in a grid of buttons, allowing users to sort, reset, or handle click events on the numbers.
 * If the clicked number is 30 or below, a new set of random numbers is generated.
 *
 * @author Stas
 * @version 1.0.0
 */
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


    /**
     * Constructor of NumbersPage, creates a new NumbersPage with the specified number of random numbers buttons.
     *
     * @param count The number of random numbers to generate.
     * @param frame The frame where the page will be displayed.
     */
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

        sortButton = createButton("Sort", Color.GREEN);
        buttonsPanel.add(sortButton, gbc);

        JButton resetButton = createButton("Reset", Color.GREEN);
        gbc.gridy = 1;
        buttonsPanel.add(resetButton, gbc);
        controlPanel.add(buttonsPanel, BorderLayout.NORTH);

        //Action listeners for buttons, for SortButton - sorting, for ResetButton - returning user to LaunchPage
        sortButton.addActionListener(e -> sortNumbers());
        resetButton.addActionListener(e -> new LaunchPage(nFrame));

        nFrame.add(scrollPane, BorderLayout.CENTER);
        nFrame.add(controlPanel, BorderLayout.EAST);
        nFrame.revalidate();
        nFrame.repaint();
    }

    /**
     * Creates a button with the specified text and background color.
     *
     * @param text The text to display on the button.
     * @param bgColor The background color of the button.
     * @return The created JButton instance.
     */
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(FONT);
        button.setPreferredSize(BUTTON);
        button.setMargin(BUTTON_MARGIN);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void displayNumbers(int[] nums) {
        numbersPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = INSETS;

        int maxInRow = 10;

        // Create buttons for each number and add them to the panel
        for (int i = 0; i < nums.length; i++) {
            gbc.gridx = i / maxInRow;
            gbc.gridy = i % maxInRow;

            JButton numberButton = createButton(String.valueOf(nums[i]), Color.BLUE);
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

    /**
     * Generates an array of random numbers within the range of 0 to 1000.
     * At least one number will be less than or equal to 30.
     *
     * @param count The number of random numbers to generate.
     * @return The generated array of random numbers.
     */
    private int[] generateRandomNumbers(int count) {
        int[] nums = new int[count];
        boolean less30Condition = false;

        // Generate random numbers and ensure one is <= 30
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

    /**
     * Handles the event when a number button is clicked.
     * If the clicked number is 30 or below, new random numbers are generated.
     *
     * @param number The number that was clicked.
     */
    private void handleNumberClick(int number) {
        if (number <= 30) {
            numbers = new int[number];
            numbers = generateRandomNumbers(numbers.length);
            displayNumbers(numbers);
        } else {
            JOptionPane.showMessageDialog(nFrame, "Please select a value smaller or equal to 30.");
        }
    }

    /**
     * Sorts the numbers array using the quick sort algorithm.
     * The sorting order alternates between ascending and descending each time the sort button is clicked.
     */
    private void sortNumbers() {
        new Thread(() -> {
            quickSort(numbers, 0, numbers.length - 1);
            isDescending = !isDescending;
            SwingUtilities.invokeLater(() -> sortButton.setText(isDescending ? "Sort" : "Sort Asc"));
        }).start();
    }

    /**
     * Recursively sorts the array using the quick sort algorithm.
     * Visualizing each iteration with animation.
     *
     * @param array The array to sort.
     * @param low The starting index of the sub-array to be sorted.
     * @param high The ending index of the sub-array to be sorted.
     */
    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            displayNumbers(array);
            pauseForVisualization();
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    /**
     * Partitions the array based on the pivot element.
     *
     * @param array The array to partition.
     * @param low The starting index of the sub-array.
     * @param high The ending index of the sub-array.
     * @return The index of the pivot element after partitioning.
     */
    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);

        // Swap elements based on sorting order (ascending/descending)
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
