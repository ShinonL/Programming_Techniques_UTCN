package View;

import javax.swing.*;
import java.awt.*;

/**
 * The User Interface class
 * This class contains the buttons, labels and text fields
 */
public class Calculator {
    private JFrame frame;
    private JPanel operations, fields;

    private JButton addButton, subButton, mulButton, divButton, derButton, intButton;
    private JTextField leftOperandInput, rightOperandInput;
    private JLabel leftOperandText, rightOperandText, resultText, quotientText, remainderText;

    public Calculator() {
        frame = new JFrame("Polynomial Calculator");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int)screenSize.getWidth()/5,(int)screenSize.getHeight()/2);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLayout(new GridLayout(2, 1));
        frame.setBackground(new Color(227, 212, 255));

        fields = new JPanel();
        fields.setSize((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
        fields.setLayout(new GridLayout(7, 1));
        fields.setBackground(new Color(227, 212, 255));

        leftOperandText = new JLabel(" Left Operand:");
        leftOperandText.setFont(new Font("Courier New", Font.PLAIN, 20));
        fields.add(leftOperandText);

        leftOperandInput = new JTextField();
        leftOperandInput.setFont(new Font("Courier New", Font.PLAIN, 20));
        fields.add(leftOperandInput);

        rightOperandText = new JLabel(" Right Operand:");
        rightOperandText.setFont(new Font("Courier New", Font.PLAIN, 20));
        fields.add(rightOperandText);

        rightOperandInput = new JTextField();
        rightOperandInput.setFont(new Font("Courier New", Font.PLAIN, 20));
        fields.add(rightOperandInput);

        resultText = new JLabel("Result:");
        resultText.setVisible(false);
        resultText.setFont(new Font("Courier New", Font.PLAIN, 20));
        fields.add(resultText);

        quotientText = new JLabel();
        quotientText.setVisible(false);
        quotientText.setFont(new Font("Courier New", Font.PLAIN, 20));
        fields.add(quotientText);

        remainderText = new JLabel();
        remainderText.setVisible(false);
        remainderText.setFont(new Font("Courier New", Font.PLAIN, 20));
        fields.add(remainderText);

        frame.add(fields);

        operations = new JPanel();
        operations.setLayout(new GridLayout(3, 2));
        operations.setSize(frame.getWidth(), frame.getHeight()/2);
        operations.setBackground(new Color(227, 212, 255));

        addButton = new JButton("+");
        addButton.setFont(new Font("Courier New", Font.PLAIN, 40));
        addButton.setBackground(new Color(255, 254, 176));
        operations.add(addButton);

        subButton = new JButton("-");
        subButton.setFont(new Font("Courier New", Font.PLAIN, 40));
        subButton.setBackground(new Color(255, 254, 176));
        operations.add(subButton);

        mulButton = new JButton("*");
        mulButton.setFont(new Font("Courier New", Font.PLAIN, 40));
        mulButton.setBackground(new Color(255, 254, 176));
        operations.add(mulButton);

        divButton = new JButton("/");
        divButton.setFont(new Font("Courier New", Font.PLAIN, 40));
        divButton.setBackground(new Color(255, 254, 176));
        operations.add(divButton);

        derButton = new JButton("()'");
        derButton.setFont(new Font("Courier New", Font.PLAIN, 40));
        derButton.setBackground(new Color(255, 254, 176));
        operations.add(derButton);

        intButton = new JButton("\u222B");
        intButton.setFont(new Font("Courier New", Font.PLAIN, 40));
        intButton.setBackground(new Color(255, 254, 176));
        operations.add(intButton);

        frame.add(operations);
        frame.getContentPane();
        frame.setVisible(true);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getSubButton() {
        return subButton;
    }

    public JButton getMulButton() {
        return mulButton;
    }

    public JButton getDivButton() {
        return divButton;
    }

    public JButton getDerButton() {
        return derButton;
    }

    public JButton getIntButton() {
        return intButton;
    }

    public JTextField getLeftOperandInput() {
        return leftOperandInput;
    }

    public JTextField getRightOperandInput() {
        return rightOperandInput;
    }

    public JLabel getResultText() {
        return resultText;
    }

    public JLabel getQuotientText() {
        return quotientText;
    }

    public JLabel getRemainderText() {
        return remainderText;
    }
}
