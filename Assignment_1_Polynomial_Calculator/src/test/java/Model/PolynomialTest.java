package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void printPolynomial() {
        Polynomial test = new Polynomial();
        test.polynomial.add(new Monomial(1, 2));
        test.polynomial.add(new Monomial(2, 2));
        test.reducePolynomial();

        assertEquals("2x^2+2x", test.printPolynomial());

        test.empty();
        test.createPolynomial("2x^2-2x-1");
        assertEquals("2x^2-2x-1", test.printPolynomial());
    }
}