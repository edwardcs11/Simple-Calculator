package de.commsult.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorDisplay extends JFrame implements CalculatorUI {
    private JTextField display;
    private Calculator calculator;

    public CalculatorDisplay() {
        // Empty constructor
    }

    @Override
    public void createAndShowGUI(Calculator calculator) {
        this.calculator = calculator;

        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600); // Calculator size

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24)); // Font size
        display.setHorizontalAlignment(JTextField.RIGHT); // Text alignment
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10)); // Gaps between buttons

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "DEL", "C" // Add C button to clear calculator and delete button
        };

        for (String label : buttonLabels) {
            JButton button = createStyledButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createStyledButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(100, 100)); // Button size
        button.setFont(new Font("Arial", Font.PLAIN, 25));

        // Color
        button.setBackground(Color.GRAY);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));

        // Button border
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        button.setBorderPainted(false);

        // Result button
        if (label.equals("=")) {
            button.setBackground(Color.BLUE);
            button.setForeground(Color.WHITE);
        }

        // Operation buttons
        else if (label.matches("[/*+-]")) {
            button.setBackground(Color.ORANGE);
            button.setForeground(Color.BLACK);
        }

        // Number buttons
        else if (label.matches("[0-9.]")) {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }

        // Clear button
        else if (label.equals("C")) {
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        }

        //Delete button
        else if (label.equals("DEL")) {
            button.setBackground(Color.GRAY);
            button.setForeground(Color.WHITE);
        }

        return button;
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            String buttonText = source.getText();

            if (buttonText.equals("=")) {
                String formula = display.getText();
                try {
                    double result = calculator.calculate(formula);
                    display.setText(Double.toString(result));
                } catch (Exception e) {
                    display.setText("Error");
                }
            } else if (buttonText.equals("C")) { // Clear button
                display.setText("");
            }else if (buttonText.equals("DEL")) {
                String currentText = display.getText();
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else {
                display.setText(display.getText() + buttonText);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new CalculatorImpl();
            CalculatorUI calculatorUI = new CalculatorDisplay();
            calculatorUI.createAndShowGUI(calculator);
        });
    }
}
