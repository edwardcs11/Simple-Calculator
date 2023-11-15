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
        // Split the expression based on addition and subtraction operators
        String[] addSubtractParts = expression.split("[+-]");

        double result = 0;

        for (int i = 0; i < addSubtractParts.length; i++) {
            // Split each part based on multiplication and division operators
            String[] multiplyDivideParts = addSubtractParts[i].split("[*/]");

            // Initialize the current result with the first value
            double currentResult = Double.parseDouble(multiplyDivideParts[0]);

            // Iterate over the rest of the parts
            for (int j = 1; j < multiplyDivideParts.length; j++) {
                String operator = addSubtractParts[i].replaceAll("[0-9.]+", ""); // Extract the operator
                double operand = Double.parseDouble(multiplyDivideParts[j]);

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
            if (i == 0 || expression.charAt(expression.indexOf(addSubtractParts[i]) - 1) == '+') {
                result += currentResult;
            } else {
                result -= currentResult;
            }
        }

        return result;
    }

}
