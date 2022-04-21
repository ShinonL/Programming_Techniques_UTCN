package Model;

/**
 * Defines a term of the polynomial as a pair of (coefficient, exponent).
 * Because the coefficient must be an integer, split it into numerator and denominator (as a fraction would be).
 *
 * For integer coefficients, the denominator would be automatically set to 1.
 */
public class Monomial {
    private int exponent;
    private int numerator;
    private int denominator;

    public Monomial(int exponent, int numerator) {
        this.exponent = exponent;
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Monomial(int exponent, int numerator, int denominator) {
        this.exponent = exponent;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getExponent() {
        return exponent;
    }
    public int getNumerator() {
        return numerator;
    }
    public int getDenominator() {
        return denominator;
    }
}
