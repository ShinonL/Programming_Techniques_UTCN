package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MonomialTest {

    @Test
    void getExponent() {
        Monomial test = new Monomial(3, 2);
        assertEquals(3, test.getExponent());
    }

    @Test
    void getNumerator() {
        Monomial test = new Monomial(3, -2);
        assertEquals(-2, test.getNumerator());
    }

    @Test
    void getDenominator() {
        Monomial test = new Monomial(3, -2);
        assertEquals(1, test.getDenominator());

        test = new Monomial(1, 2, 3);
        assertEquals(3, test.getDenominator());
    }
}