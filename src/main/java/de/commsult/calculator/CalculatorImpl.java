package de.commsult.calculator;

public class CalculatorImpl implements Calculator {

    @Override
    public double calculate(String formula) {
        try {
            // Clean up the formula by removing unnecessary whitespaces
            formula = formula.replaceAll("\\s+", "");

            // Check if the formula is not empty
            if (formula.isEmpty()) {
                throw new IllegalArgumentException("Invalid formula");
            }

            // Evaluate the expression
            return evaluateExpression(formula);
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details to the console
            throw new IllegalArgumentException("Error: " + e.getMessage()); // Rethrow with a user-friendly message
        }
    }

    private double evaluateExpression(String expression) {
        // Initialize the result
        double result = 0;

        // Split the expression into numbers and operators
        String[] parts = expression.split("(?=[-+*/])|(?<=[-+*/])");

        // Initialize current values
        double currentNumber = 0;
        String currentOperator = "+";

        // Iterate over the parts
        for (String part : parts) {
            if (isNumber(part)) {
                // If the part is a number, parse it and perform the operation with the current operator
                double number = Double.parseDouble(part);
                switch (currentOperator) {
                    case "+":
                        result += currentNumber;
                        currentNumber = number;
                        break;
                    case "-":
                        result += currentNumber;
                        currentNumber = -number;
                        break;
                    case "*":
                        currentNumber *= number;
                        break;
                    case "/":
                        if (number == 0) {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        currentNumber /= number;
                        break;
                }
            } else if (isOperator(part)) {
                // If the part is an operator, update the current operator
                currentOperator = part;
            } else {
                // Invalid part (neither number nor operator)
                throw new IllegalArgumentException("Invalid formula");
            }
        }

        // Add the last number to the result
        result += currentNumber;

        return result;
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String str) {
        return str.matches("[-+*/]");
    }
}
