import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    private static Function<Double, Double> funcToAnalyze = (Double arg) -> arg * arg + 2 * arg - 4;
    private static double leftBound = -10, rightBound = 20;
    private static double eps = 0.001;

    private static void methodOfDichotomy(BufferedWriter writer) throws IOException {
        double a = leftBound, b = rightBound;

        writer.write("Source range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n\n");
        double w = eps / 3;
        int amountOfCalculations = 0;
        while (b - a > eps) {
            writer.write("Current range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n");
            amountOfCalculations += 2;
            double length = b - a;
            double nwa = (b + a) / 2 - w;
            double nwb = (b + a) / 2 + w;
            if (funcToAnalyze.apply(nwa) < funcToAnalyze.apply(nwb)) {
                b = nwb;
            } else {
                a = nwa;
            }
            writer.write("x1 = " + Double.toString(nwa) + " f(x1) = " + Double.toString(funcToAnalyze.apply(nwa)) + "\n" +
                    "x2 = " + Double.toString(nwb) + " f(x2) = " + Double.toString(funcToAnalyze.apply(nwb)) + "\n" +
                    "New range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n" +
                    "Relationship of ranges: " + Double.toString(length / (b - a)) + "\n\n");
        }
        writer.write("The result is " + Double.toString((a + b) / 2) + "\n" +
                "Amount of calculations is " + Integer.toString(amountOfCalculations));
    }

    private static void methodOfGoldenSection(BufferedWriter writer) throws IOException {
        double a = leftBound, b = rightBound;
        double nwa = a + 0.381966011 * (b - a), nwaValue = funcToAnalyze.apply(nwa);
        double nwb = a + 0.618003399 * (b - a), nwbValue = funcToAnalyze.apply(nwb);
        writer.write("Source range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n\n");
        int amountOfCalculations = 2;
        do {
            writer.write("Current range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n");
            double length = b - a;
            if (nwaValue < nwbValue) {
                b = nwb;
            } else {
                a = nwa;
            }
            writer.write("x1 = " + Double.toString(nwa) + " f(x1) = " + Double.toString(funcToAnalyze.apply(nwa)) + "\n" +
                    "x2 = " + Double.toString(nwb) + " f(x2) = " + Double.toString(funcToAnalyze.apply(nwb)) + "\n" +
                    "New range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n" +
                    "Relationship of ranges: " + Double.toString(length / (b - a)) + "\n\n");
            if (nwaValue < nwbValue) {
                nwb = nwa;
                nwbValue = nwaValue;
                nwa = a + 0.381966011 * (b - a);
                nwaValue = funcToAnalyze.apply(nwa);
            } else {
                nwa = nwb;
                nwaValue = nwbValue;
                nwb = a + 0.618003399 * (b - a);
                nwbValue = funcToAnalyze.apply(nwb);
            }
            amountOfCalculations += 1;
        } while (b - a > eps);
        writer.write("The result is " + Double.toString((a + b) / 2) + "\n" +
                "Amount of calculations is " + Integer.toString(amountOfCalculations - 1));
    }

    private static List<Double> buildFibonacciSequence() {
        List<Double> fibonacciSequence = new ArrayList<>();
        fibonacciSequence.add(1.);
        fibonacciSequence.add(1.);
        for (int i = 2; fibonacciSequence.get(i - 1) < (rightBound - leftBound) / eps; ++i) {
            fibonacciSequence.add(fibonacciSequence.get(i - 1) + fibonacciSequence.get(i - 2));
        }
        return fibonacciSequence;
    }

    private static void methodOfFibonacci(BufferedWriter writer) throws IOException {
        List<Double> fibonacciSequence = buildFibonacciSequence();
        int n = fibonacciSequence.size() - 1;
        double a = leftBound, b = rightBound;
        writer.write("Source range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n\n");
        double nwa = a + fibonacciSequence.get(n - 2) / fibonacciSequence.get(n) * (b - a);
        double nwb = a + fibonacciSequence.get(n - 1) / fibonacciSequence.get(n) * (b - a);
        double nwaValue = funcToAnalyze.apply(nwa);
        double nwbValue = funcToAnalyze.apply(nwb);
        for (int k = 0; k < n - 1; ++k) {
            writer.write("Current range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n" +
                    "x1 = " + Double.toString(nwa) + " f(x1) = " + Double.toString(funcToAnalyze.apply(nwa)) + "\n" +
                    "x2 = " + Double.toString(nwb) + " f(x2) = " + Double.toString(funcToAnalyze.apply(nwb)) + "\n");
            double length = b - a;
            if (nwaValue > nwbValue) {
                a = nwa;
                nwa = nwb;
                nwaValue = nwbValue;
                nwb = a + fibonacciSequence.get(n - k - 1) / fibonacciSequence.get(n - k) * (b - a);
                nwbValue = funcToAnalyze.apply(nwb);
            } else {
                b = nwb;
                nwb = nwa;
                nwbValue = nwaValue;
                nwa = a + fibonacciSequence.get(n - k - 2) / fibonacciSequence.get(n - k) * (b - a);
                nwaValue = funcToAnalyze.apply(nwa);
            }
            writer.write("New range: [" + Double.toString(a) + " : " + Double.toString(b) + "]\n" +
                    "Relationship of ranges: " + Double.toString(length / (b - a)) + "\n\n");
        }
        writer.write("The result is " + Double.toString((a + b) / 2) + "\n" +
                "Amount of calculations is " + Integer.toString(n - 1));
    }

    private static void methodOfSearchOnALine(BufferedWriter writer) throws IOException {
        double a = leftBound, nwa, h;
        if (funcToAnalyze.apply(a) > funcToAnalyze.apply(a + eps)) {
            nwa = a + eps;
            h = eps;
        } else {
            nwa = a - eps;
            h = -eps;
        }
        double aValue = funcToAnalyze.apply(a);
        double nwaValue = funcToAnalyze.apply(nwa);
        int amountOfCalculations = 2;
        writer.write("Current x: " + Double.toString(a) + ", current h: " + Double.toString(h) + "\n");
        writer.write("f(x) = " + Double.toString(funcToAnalyze.apply(a)) + ", f(x + h) = "
                + Double.toString(funcToAnalyze.apply(nwa)) + "\n\n");
        while (aValue > nwaValue) {
            h *= 2;
            a = nwa;
            nwa = a + h;
            aValue = nwaValue;
            nwaValue = funcToAnalyze.apply(nwa);
            amountOfCalculations++;
            writer.write("Current x: " + Double.toString(a) + ", current h: " + Double.toString(h) + "\n");
            writer.write("f(x) = " + Double.toString(funcToAnalyze.apply(a)) + ", f(x + h) = "
                    + Double.toString(funcToAnalyze.apply(nwa)) + "\n\n");
        }
        writer.write("The result is [" + Double.toString(a - h / 2) + " : " + Double.toString(a + h) + "]\n" +
                "Amount of calculations is " + Integer.toString(amountOfCalculations) + "\n");
    }

    public static void main(String args[]) {
        try (BufferedWriter dichotomyWriter = new BufferedWriter(new FileWriter("Method of dichotomy"))) {
            methodOfDichotomy(dichotomyWriter);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try (BufferedWriter goldenSectionWriter = new BufferedWriter(new FileWriter("Method of Golden section"))) {
            methodOfGoldenSection(goldenSectionWriter);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try (BufferedWriter fibonacciWriter = new BufferedWriter(new FileWriter("Method of Fibonacci"))) {
            methodOfFibonacci(fibonacciWriter);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try (BufferedWriter searchOnALineWriter = new BufferedWriter(new FileWriter("Search on a line"))) {
            methodOfSearchOnALine(searchOnALineWriter);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
