package Model;

import javax.swing.*;
import java.util.*;
import java.util.regex.*;

/**
 * Defines the polynomial as a list of monomials.
 * The methods part of the class are responsible for data managing like input parsing and printing.
 */
public class Polynomial {
    public List<Monomial> polynomial = new ArrayList<Monomial>();

    /**
     * Creates the polynomial from the input string by using a pattern matching Regex.
     *
     * Firstly, the input is passed to a validation method which would return false if any illegal characters or
     * character combinations are introduced.
     * If it is invalid, throw an exception
     * Then, if everything was OK, split the input into monomials based on the regex used to compile the pattern.
     * For each monomial found, find it's numerator and exponent and add the Monomial object to the polynomial list
     *
     * Group(2) contains the sign and the coefficient. If it contains no visible number or no sign, then we should
     * just add a plus because the number is clearly positive and the coefficient is 1. If it has only a "-" sign,
     * then the coefficient is -1. If it is not 1 or -1, convert it to an Integer and add is as it is.
     *
     * Group(6) matches the exponent, while group(4) matches the variable (only x and X are allowed). If nothing is
     * found in group(6), there is no exponent visible => it may be 1 or 0. If we can see an x (or X) then the
     * exponent is 1. If not, it is 0. If group(6) found an exponent, save it.
     *
     * Finally, add the newly found monomial to the polynomial.
     *
     * If something went wrong throw an exception.
     */
    public void createPolynomial(String input) {
        try {
            if (!this.validateInput(input))
                throw new Exception();
            int numerator, exponent;
            input = input.replaceAll("\\s+", "");
            Pattern pattern = Pattern.compile("((([\\+|-])?\\d*)([x|X](\\^(\\d*))?)?)");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                if (matcher.group(2) == null || matcher.group(2).isEmpty() || matcher.group(2).equals("+"))
                    numerator = 1;
                else if(matcher.group(2).equals("-"))
                    numerator = -1;
                else numerator = Integer.parseInt(matcher.group(2));

                if (matcher.group(6) == null || matcher.group(6).isEmpty()) {
                    if (matcher.group(4) != null && (matcher.group(4).contains("x") || matcher.group(4).contains("X")))
                        exponent = 1;
                    else exponent = 0;
                }
                else exponent = Integer.parseInt(matcher.group(6));
                polynomial.add(new Monomial(exponent, numerator));
            }
            // remove the las item which matches the end of string
            polynomial.remove(polynomial.size() - 1);
        } catch (Exception createPolynomial) {
            JOptionPane.showMessageDialog(null, "Wrong input. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
        reducePolynomial();
    }

    /**
     * Gets the polynomial in a string form to be easy to print.
     *
     * Initially the string is empty. If the polynomial is also empty, then the result must be 0.
     * If not, convert each monomial inside the polynomial list.
     * If numerator is zero, add the said monomial only if it is the only monomial in the list so that we would print
     * the result "0". If it is not he last monomial, the we may ignore it.
     *
     * If numerator*denominator is a positive number, then add a "+" (if it is not the first term of the polynomial)
     * and them add the result of dividing the numerator by the denominator (because the polynomial may have real
     * coefficients).
     * If numerator*denominator is a negative number, then add a "-" and them add the result of dividing the numerator
     * by the denominator.
     *
     * If the exponent is 0, we know that x^0 == 1 so we can skip it. If not, print an x.
     * x^1 == x so it is not necessary to print 1 as an exponent, so print only the exponents different then 1.
     */
    public String printPolynomial() {
        String print = "";

        if(polynomial.isEmpty()) return "0";

        for (Monomial monomial: polynomial) {
            if (monomial.getNumerator() == 0) {
                if (polynomial.size() == 1) print = print.concat("0"); continue;
            }
            if (monomial.getNumerator() * monomial.getDenominator() > 0) {
                if (polynomial.indexOf(monomial) != 0) print = print.concat("+");
                if ( ( 1.0 * monomial.getNumerator()) / monomial.getDenominator() == 1.0) {
                    if(monomial.getExponent() == 0) print = print.concat("1");
                } else {
                    if (monomial.getNumerator() % monomial.getDenominator() == 0)
                        print = print.concat(Integer.toString(monomial.getNumerator() / monomial.getDenominator()));
                    else print = print.concat(Double.toString((1.0 * monomial.getNumerator()) / monomial.getDenominator()));
                }
            }
            if (monomial.getNumerator() * monomial.getDenominator() < 0) {
                if ( (1.0 * monomial.getNumerator()) / monomial.getDenominator() == -1.0 && monomial.getExponent() != 0)
                    print = print.concat("-");
                else {
                    if (monomial.getNumerator() % monomial.getDenominator() != 0)
                        print = print.concat(Double.toString((1.0 * monomial.getNumerator()) / monomial.getDenominator()));
                    else print = print.concat(Integer.toString(monomial.getNumerator() / monomial.getDenominator()));
                }
            }

            if (monomial.getExponent() == 0) continue;
            print = print.concat("x");
            if (monomial.getExponent() != 1) print = print.concat("^" + Integer.toString(monomial.getExponent()));
        }

        return print;
    }

    /**
     * Simplifies the input polynomial into the shortest form.
     *
     * First sort by the exponent (biggest exponent at the beginning of the list).
     * Then for each group of equal exponents, add their numerators and denominators to reduce the number of monomials.
     * If we finished all the monomials with a certain exponent, add it to the temporary solution and reset the new
     * monomial to the current one.
     * Add the last monomials group (the one for which the for loop ended).
     *
     * Finally, save the new form
     */
    public void reducePolynomial() {
        if (polynomial.isEmpty()) return;
        this.polynomial.sort((m1, m2) -> m2.getExponent() - m1.getExponent());
        Polynomial temp = new Polynomial();
        int numerator = 0, denominator = 1;
        int exponent = this.polynomial.get(0).getExponent();

        for (Monomial monomial: this.polynomial) {
            if (monomial.getExponent() == exponent) {
                numerator = numerator * monomial.getDenominator() + denominator * monomial.getNumerator();
                denominator *= monomial.getDenominator();
                continue;
            }

            temp.polynomial.add(new Monomial(exponent, numerator, denominator));

            exponent = monomial.getExponent();
            numerator = monomial.getNumerator();
            denominator = monomial.getDenominator();
        }
        temp.polynomial.add(new Monomial(exponent, numerator, denominator));

        this.polynomial = temp.polynomial;
    }

    /**
     * Validates the input polynomial introduced by the user.
     */
    private boolean validateInput(String input) {
        if(!input.matches("^[xX0-9\\^\\-\\+ ]*")) return false;

        Pattern polyFormat = Pattern.compile("[0-9]\\^");
        Matcher m = polyFormat.matcher(input);
        String s = "";
        while(m.find())  s = m.group();
        if(!s.isEmpty()) return false;

        polyFormat = Pattern.compile("[xX][xX]");
        m = polyFormat.matcher(input);
        s = "";
        while(m.find()) s = m.group();
        if(!s.isEmpty()) return false;

        polyFormat = Pattern.compile("\\^\\-");
        m = polyFormat.matcher(input);
        s = "";
        while(m.find()) s = m.group();
        if(!s.isEmpty()) return false;

        polyFormat = Pattern.compile("\\^$");
        m = polyFormat.matcher(input);
        s = "";
        while(m.find()) s = m.group();
        if(!s.isEmpty()) return false;

        return true;
    }

    /**
     * Clears the polynomial
     */
    public void empty() {
        polynomial.clear();
    }
}
