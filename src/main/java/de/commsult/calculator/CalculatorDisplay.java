package de.commsult.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorDisplay extends JFrame implements CalculatorUI {
    private JTextField display;
    private Calculator calculator;

    public CalculatorDisplay() {
        // Constructor kosong
    }

    @Override
    public void createAndShowGUI(Calculator calculator) {
        this.calculator = calculator;

        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);

        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            String buttonText = source.getText();

            if (buttonText.equals("=") || event.getActionCommand().equals("\n")) {
                // Handle equals sign or Enter key press
                String formula = display.getText();
                try {
                    double result = calculator.calculate(formula);
                    display.setText(Double.toString(result));
                } catch (Exception e) {
                    e.printStackTrace();
                    display.setText("Error: " + e.getMessage());
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
