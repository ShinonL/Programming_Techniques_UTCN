package Controller;

import Model.Monomial;
import Model.Polynomial;

import java.util.ArrayList;
import java.util.List;

/**
* For each operation between the monomials with real coefficients (denominator != 1)
* I applied the mathematical rules:
* a/b - c/d = (a*d - b*c) / (b*d)
* a/b * c/d = (a*b) / (c*d)
*/
public class Operations {
    /**
    * Addition, as per the statement of the problem is applied only to polynomials with integer coefficients.
    * Thus, we just have to add the numerators of the monomials with equal exponents, taking into consideration
    * that the lists are sorted by the decreasing order of their exponents.
    * i and j are indexes inside the ArrayList. As long as they both still point somewhere inside the lists, there
    * are 4 possible situations:
    * 1. the term of the left polynomial has a bigger degree than the one of the right polynomial. This means we just
    *    add the higher term to the solution. Increase its corresponding index.
    * 2. same as situation 1 but now the higher polynomial is in the right side
    * 3. the degrees of the monomials are equal. In this case, add to the solution a monomial with the same degree and
    *    with a numerator equal with the numerators of the 2 terms of the lists. Increase both indexes.
    * 4. The solution numerator would be 0, so we just skip it
    *
    * The if any of the lists has any elements left, we just add them
    */
    public static Polynomial addPolynomial(Polynomial P, Polynomial Q) {
        Polynomial sum = new Polynomial();
        Monomial pMonomial, qMonomial;
        int i = 0, j = 0;

        while (i < P.polynomial.size() && j < Q.polynomial.size()) {
            pMonomial = P.polynomial.get(i);
            qMonomial = Q.polynomial.get(j);
            if(pMonomial.getExponent() > qMonomial.getExponent()) {
                sum.polynomial.add(pMonomial);
                i++;
            } else if(pMonomial.getExponent() < qMonomial.getExponent()) {
                sum.polynomial.add(qMonomial);
                j++;
            } else if(qMonomial.getNumerator() + pMonomial.getNumerator() != 0) {
                sum.polynomial.add(new Monomial(qMonomial.getExponent(), qMonomial.getNumerator() + pMonomial.getNumerator()));
                i++;
                j++;
            } else { i++; j++; }
        }
        while (i < P.polynomial.size()) {
            if (P.polynomial.get(i).getNumerator() != 0) sum.polynomial.add(P.polynomial.get(i));
            i++;
        }
        while (j < Q.polynomial.size()) {
            if (Q.polynomial.get(j).getNumerator() != 0) sum.polynomial.add(Q.polynomial.get(j));
            j++;
        }

        sum.reducePolynomial();
        return sum;
    }

    /**
     * Addition, as per the statement of the problem is applied only to polynomials with integer coefficients
     * However, subtraction is also used in division, where we may have real coefficients
     * So the subtraction for two monomials with equal exponents is done by taking into consideration the denominators
     * The algorithm goes the same as the addition, just that the numerator of the right polynomial is inverted
     */
    public static Polynomial subPolynomial(Polynomial P, Polynomial Q) {
        Polynomial diff = new Polynomial();
        Monomial pMonomial, qMonomial;
        int i = 0, j = 0;

        while (i < P.polynomial.size() && j < Q.polynomial.size()) {
            pMonomial = P.polynomial.get(i);
            qMonomial = Q.polynomial.get(j);
            if(pMonomial.getExponent() > qMonomial.getExponent() && pMonomial.getNumerator() != 0) {
                diff.polynomial.add(pMonomial);
                i++;
            } else if(pMonomial.getExponent() < qMonomial.getExponent() && qMonomial.getNumerator() != 0) {
                diff.polynomial.add(new Monomial(qMonomial.getExponent(), -1 * qMonomial.getNumerator(), qMonomial.getDenominator()));
                j++;
            } else if(pMonomial.getExponent() == qMonomial.getExponent() && pMonomial.getNumerator() * qMonomial.getDenominator() - pMonomial.getDenominator() * qMonomial.getNumerator() != 0) {
                diff.polynomial.add(new Monomial(qMonomial.getExponent(), pMonomial.getNumerator() * qMonomial.getDenominator() - qMonomial.getNumerator() * pMonomial.getDenominator(),
                        pMonomial.getDenominator() * qMonomial.getDenominator()));
                i++;
                j++;
            } else { i++; j++; }
        }
        while (i < P.polynomial.size()) {
            if (P.polynomial.get(i).getNumerator() != 0) diff.polynomial.add(P.polynomial.get(i));
            i++;
        }
        while (j < Q.polynomial.size()) {
            if (Q.polynomial.get(j).getNumerator() != 0) diff.polynomial.add(new Monomial(Q.polynomial.get(j).getExponent(), -1 * Q.polynomial.get(j).getNumerator(), Q.polynomial.get(j).getDenominator()));
            j++;
        }
        diff.reducePolynomial();
        return diff;
    }

    /**
    * The multiplication between 2 polynomials is used only directly with the input operands which are guaranteed to have integer coefficients
    * For each monomial in the first polynomial, multiply it with each monomial in the second polynomial
    */
    public static Polynomial multiplication(Polynomial P, Polynomial Q) {
        Polynomial multiplication = new Polynomial();

        for (Monomial m1: P.polynomial) {
            for (Monomial m2: Q.polynomial) {
                if (m1.getNumerator() * m2.getNumerator() != 0)
                    multiplication.polynomial.add(new Monomial(m1.getExponent() + m2.getExponent(), m1.getNumerator() * m2.getNumerator()));
            }
        }
        multiplication.reducePolynomial();
        return multiplication;
    }

    /**
    * This is more of an "internal" function used by the division to multiply a polynomial by a monomial,
    * taking in consideration real coefficients
    */
    public static Polynomial multiplication(Polynomial P, Monomial Q) {
        Polynomial multiplication = new Polynomial();

        for (Monomial m1: P.polynomial) {
            if (m1.getNumerator() * Q.getNumerator() != 0)
                multiplication.polynomial.add(new Monomial(m1.getExponent() + Q.getExponent(), m1.getNumerator() * Q.getNumerator(), Q.getDenominator() * m1.getDenominator()));
        }
        return multiplication;
    }

    /**
    * Each monomial is multiplied by the exponent, and the exponent is decreased by 1
    */
    public static Polynomial derivative(Polynomial P) {
        Polynomial derivative = new Polynomial();
        for (Monomial monomial: P.polynomial) {
            if (monomial.getNumerator() * monomial.getExponent() != 0) {
                derivative.polynomial.add(new Monomial(monomial.getExponent() - 1, monomial.getExponent() * monomial.getNumerator()));
            }
        }
        return derivative;
    }

    /**
    * Division returns a list of polynomials
    * index 0: has the quotient
    * index 1: has the remainder
    *
    * Division is done as the mathematical division:
    * 0. set the remainder to the left operand
    * 1. divide the 2 leading monomials of each polynomial (the remainder and the right operand)
    * 2. add the result to the quotient
    * 3. subtract from the remainder the right operand multiplied by the result of step 1.
    * 4. repeat from step 1 until the remainder is either empty or the exponent of the remainder is lower than the one of the right operand
    */
    public static List<Polynomial> division(Polynomial P, Polynomial Q) {
        List<Polynomial> result = new ArrayList<>();
        Polynomial quotient = new Polynomial();
        Polynomial remainder = new Polynomial();
        Monomial leadingMonomial;
        // step 0
        remainder.polynomial.addAll(P.polynomial);

        // step 4
        while (remainder.polynomial.size() != 0 && remainder.polynomial.get(0).getExponent() >= Q.polynomial.get(0).getExponent()) {
            // step 1
            if (remainder.polynomial.get(0).getNumerator() % Q.polynomial.get(0).getNumerator() == 0)
                leadingMonomial = new Monomial(remainder.polynomial.get(0).getExponent() - Q.polynomial.get(0).getExponent(),
                        remainder.polynomial.get(0).getNumerator() / (Q.polynomial.get(0).getNumerator() * remainder.polynomial.get(0).getDenominator()));
            else leadingMonomial = new Monomial(remainder.polynomial.get(0).getExponent() - Q.polynomial.get(0).getExponent(),
                    remainder.polynomial.get(0).getNumerator(), Q.polynomial.get(0).getNumerator() * remainder.polynomial.get(0).getDenominator());

            // step 2
            quotient.polynomial.add(leadingMonomial);

            // step 3
            Polynomial temp = new Polynomial();
            temp = Operations.multiplication(Q, leadingMonomial);
            remainder = Operations.subPolynomial(remainder, temp);

            remainder.reducePolynomial();
        }

        result.add(quotient);
        result.add(remainder);

        return result;
    }

    /**
     * Each monomial is divided by the exponent + 1, and the exponent is increased by 1
     */
    public static Polynomial integration(Polynomial P) {
        Polynomial primitive = new Polynomial();
        for (Monomial monomial: P.polynomial) {
            if (monomial.getNumerator() % (monomial.getExponent() + 1) == 0) {
                primitive.polynomial.add(new Monomial(monomial.getExponent() + 1, monomial.getNumerator() / (monomial.getExponent() + 1)));
            } else primitive.polynomial.add(new Monomial(monomial.getExponent() + 1, monomial.getNumerator(), monomial.getExponent() + 1));
        }
        return primitive;
    }
}
