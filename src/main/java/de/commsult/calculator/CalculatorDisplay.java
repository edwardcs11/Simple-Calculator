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

        setTitle("Swing Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500); // Ukuran diperbesar

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24)); // Ukuran font diperbesar
        display.setHorizontalAlignment(JTextField.RIGHT); // Teks diatur ke kanan
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10)); // Menambahkan sedikit ruang antar tombol

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+", "C" // Tambahkan tombol "C" untuk clear
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
        button.setPreferredSize(new Dimension(80, 80)); // Ukuran tombol diperbesar
        button.setFont(new Font("Arial", Font.PLAIN, 20));

        // Menyesuaikan warna dan bentuk tombol
        button.setBackground(Color.LIGHT_GRAY);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));

        // Mengatur tepi tombol agar lebih rounded
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        button.setBorderPainted(false);

        // Menyesuaikan warna latar belakang "=" button
        if (label.equals("=")) {
            button.setBackground(Color.GREEN);
            button.setForeground(Color.BLACK);
        }

        // Menyesuaikan warna latar belakang operator buttons
        else if (label.matches("[/*+-]")) {
            button.setBackground(Color.ORANGE);
            button.setForeground(Color.BLACK);
        }

        // Menyesuaikan warna latar belakang number buttons
        else if (label.matches("[0-9.]")) {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }

        // Menyesuaikan warna latar belakang "C" button
        else if (label.equals("C")) {
            button.setBackground(Color.RED);
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
            } else if (buttonText.equals("C")) { // Clear jika tombol "C" ditekan
                display.setText("");
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
