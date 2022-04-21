package Controller;

import Model.Polynomial;
import View.Calculator;

/**
 * Is the main object that takes the input and decides where it should go
 */
public class Controller {
    private Polynomial leftOperand = new Polynomial();
    private Polynomial rightOperand = new Polynomial();

    private Calculator GUI = new Calculator();

    /**
     * The method which links the changes made by the user in the front-end with the corresponding back-end actions.
     * Contains the actionListeners for all the buttons seen on the user interface
     */
    public void control() {
        // add button action listener
        GUI.getAddButton().addActionListener(e -> {
            // get the left and right operands
            leftOperand.createPolynomial(GUI.getLeftOperandInput().getText());
            rightOperand.createPolynomial(GUI.getRightOperandInput().getText());

            // perform the addition, convert the result to a string and save it
            String result = Operations.addPolynomial(leftOperand, rightOperand).printPolynomial();

            // set the result text to visible and add the result
            GUI.getResultText().setVisible(true);
            GUI.getQuotientText().setText(result);
            GUI.getQuotientText().setVisible(true);
            GUI.getRemainderText().setVisible(false);

            // empty the operands
            leftOperand.empty();
            rightOperand.empty();
        });

        GUI.getSubButton().addActionListener(e -> {
            // get the left and right operands
            leftOperand.createPolynomial(GUI.getLeftOperandInput().getText());
            rightOperand.createPolynomial(GUI.getRightOperandInput().getText());

            // perform the subtraction, convert the result to a string and save it
            String print = Operations.subPolynomial(leftOperand, rightOperand).printPolynomial();

            // set the result text to visible and add the result
            GUI.getResultText().setVisible(true);
            GUI.getQuotientText().setText(print);
            GUI.getQuotientText().setVisible(true);
            GUI.getRemainderText().setVisible(false);

            // empty the operands
            leftOperand.empty();
            rightOperand.empty();
        });

        GUI.getMulButton().addActionListener(e -> {
            // get the left and right operands
            leftOperand.createPolynomial(GUI.getLeftOperandInput().getText());
            rightOperand.createPolynomial(GUI.getRightOperandInput().getText());

            // perform the multiplication, convert the result to a string and save it
            String print = Operations.multiplication(leftOperand, rightOperand).printPolynomial();

            // set the result text to visible and add the result
            GUI.getResultText().setVisible(true);
            GUI.getQuotientText().setText(print);
            GUI.getQuotientText().setVisible(true);
            GUI.getRemainderText().setVisible(false);

            // empty the operands
            leftOperand.empty();
            rightOperand.empty();
        });

        GUI.getDivButton().addActionListener(e -> {
            // get the left and right operands
            leftOperand.createPolynomial(GUI.getLeftOperandInput().getText());
            rightOperand.createPolynomial(GUI.getRightOperandInput().getText());

            // perform the division, convert the result to the 2 corresponding strings and save them
            String quotient = Operations.division(leftOperand, rightOperand).get(0).printPolynomial();
            String remainder = Operations.division(leftOperand, rightOperand).get(1).printPolynomial();

            // set the result text to visible and add the result
            GUI.getResultText().setVisible(true);
            GUI.getQuotientText().setText("Quotient: " + quotient);
            GUI.getQuotientText().setVisible(true);
            GUI.getRemainderText().setText("Remainder: " + remainder);
            GUI.getRemainderText().setVisible(true);

            // empty the operands
            leftOperand.empty();
            rightOperand.empty();
        });

        GUI.getDerButton().addActionListener(e -> {
            // get the input
            leftOperand.createPolynomial(GUI.getLeftOperandInput().getText());
            String result;

            if(GUI.getLeftOperandInput().getText().equals(""))
                result = "Please use the left operand.";
            // perform the derivation, convert the result to a string and save it
            else result = Operations.derivative(leftOperand).printPolynomial();

            // set the result text to visible and add the result
            GUI.getResultText().setVisible(true);
            GUI.getQuotientText().setText(result);
            GUI.getQuotientText().setVisible(true);
            GUI.getRemainderText().setVisible(false);

            // empty the operand
            leftOperand.empty();
        });

        GUI.getIntButton().addActionListener(e -> {
            // get the input
            leftOperand.createPolynomial(GUI.getLeftOperandInput().getText());
            String result;

            if(GUI.getLeftOperandInput().getText().equals(""))
                result = "Please use the left operand.";
            // perform the integration, convert the result to a string and save it
            else result = Operations.integration(leftOperand).printPolynomial();

            // set the result text to visible and add the result
            GUI.getResultText().setVisible(true);
            GUI.getQuotientText().setText(result);
            GUI.getQuotientText().setVisible(true);
            GUI.getRemainderText().setVisible(false);

            // empty the operands
            leftOperand.empty();
        });
    }
}