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
        // You need to implement the actual expression evaluation logic here
        // For simplicity, let's assume a basic calculator that supports addition and subtraction

        // Split the expression based on addition and subtraction operators
        String[] addSubtractParts = expression.split("[+-]");

        double result = 0;

        for (String part : addSubtractParts) {
            // Split each part based on multiplication and division operators
            String[] multiplyDivideParts = part.split("[*/]");

            // Initialize the current result with the first value
            double currentResult = Double.parseDouble(multiplyDivideParts[0]);

            // Iterate over the rest of the parts
            for (int i = 1; i < multiplyDivideParts.length; i++) {
                String operator = part.replaceAll("[0-9.]+", ""); // Extract the operator
                double operand = Double.parseDouble(multiplyDivideParts[i]);

                // Perform the operation based on the operator
                switch (operator) {
                    case "*":
                        currentResult *= operand;
                        break;
                    case "/":
                        if (operand == 0) {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        currentResult /= operand;
                        break;
                }
            }

            // Update the overall result based on the current part
            if (expression.contains("-")) {
                result -= currentResult;
            } else {
                result += currentResult;
            }
        }

        return result;
    }
}
