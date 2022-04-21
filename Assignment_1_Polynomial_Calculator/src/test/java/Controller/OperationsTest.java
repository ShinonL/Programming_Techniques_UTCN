package Controller;

import Model.Monomial;
import Model.Polynomial;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    @Test
    void addPolynomial() {
        Polynomial leftOperand = new Polynomial();
        Polynomial rightOperand = new Polynomial();

        leftOperand.createPolynomial("x^2+x+1");
        rightOperand.createPolynomial("-3x^4-3x+5x^2");

        Polynomial test = Operations.addPolynomial(leftOperand, rightOperand);

        assertEquals("-3x^4+6x^2-2x+1", test.printPolynomial());
    }

    @Test
    void subPolynomial() {
        Polynomial leftOperand = new Polynomial();
        Polynomial rightOperand = new Polynomial();

        leftOperand.createPolynomial("x^2+x+1");
        rightOperand.createPolynomial("-3x^4-3x+5x^2");

        Polynomial test = Operations.subPolynomial(leftOperand, rightOperand);

        assertEquals("3x^4-4x^2+4x+1", test.printPolynomial());
    }

    @Test
    void multiplication() {
        Polynomial leftOperand = new Polynomial();
        Polynomial rightOperand = new Polynomial();

        leftOperand.createPolynomial("-x^2+x-1");
        rightOperand.createPolynomial("-3x^4-3x+5x^2");

        Polynomial test = Operations.multiplication(leftOperand, rightOperand);

        assertEquals("3x^6-3x^5-2x^4+8x^3-8x^2+3x", test.printPolynomial());
    }

    @Test
    void testMultiplication() {
        Polynomial leftOperand = new Polynomial();
        Monomial rightOperand = new Monomial(2, -1, 2);

        leftOperand.createPolynomial("-3x^4-3x+5x^2");

        Polynomial test = Operations.multiplication(leftOperand, rightOperand);

        assertEquals("1.5x^6-2.5x^4+1.5x^3", test.printPolynomial());
    }

    @Test
    void derivative() {
        Polynomial leftOperand = new Polynomial();

        leftOperand.createPolynomial("-3x^4-3x+5x^2");

        Polynomial test = Operations.derivative(leftOperand);

        assertEquals("-12x^3+10x-3", test.printPolynomial());
    }

    @Test
    void division() {
        Polynomial leftOperand = new Polynomial();
        Polynomial rightOperand = new Polynomial();

        leftOperand.createPolynomial("-3x^4-3x+5x^2");
        rightOperand.createPolynomial("-x^2+x-1");

        Polynomial quotient = Operations.division(leftOperand, rightOperand).get(0);
        Polynomial remainder = Operations.division(leftOperand, rightOperand).get(1);

        assertEquals("3x^2+3x-5", quotient.printPolynomial());
        assertEquals("5x-5", remainder.printPolynomial());
    }

    @Test
    void integration() {
        Polynomial leftOperand = new Polynomial();

        leftOperand.createPolynomial("-3x^4-3x+5x^3+1");

        Polynomial test = Operations.integration(leftOperand);

        assertEquals("-0.6x^5+1.25x^4-1.5x^2+x", test.printPolynomial());
    }
}