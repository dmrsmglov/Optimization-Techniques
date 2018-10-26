import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Main {

    private static BiFunction<Double, Double, Double> derivativeX1 = (x1, x2)
            -> -4 * x2 * x1 + 4 * Math.pow(x1, 2) + Math.pow(x1, 3) - 200 + 200 * x1;

    private static BiFunction<Double, Double, Double> derivativeX2 = (x1, x2)
            -> 2 * x2 - 2 * Math.pow(x1, 2);

    private static BiFunction<Double, Double, Double> funcToAnalyze = (x1, x2)
            -> Math.pow(x2 - Math.pow(x1, 2), 2) + 100 * Math.pow(1 - x1, 2);

    private static List<Double> derivativeInDirection(Double x1, Double x2) {
        return new ArrayList<Double>(){{
            add(derivativeX1.apply(x1, x2));
            add(derivativeX2.apply(x1, x2));
        }};
    }

    private static List<Double> normalizationOfVector(List<Double> vector) {
        Double length = Math.sqrt(vector.stream()
                .mapToDouble(x -> x * x).sum());
        return vector.stream()
                .map(i -> i / length)
                .collect(Collectors.toList());
    }

    private static void gradientDownhill(BufferedWriter writer) throws IOException {
        double x1 = 2.;
        double x2 = 1.4;
        double eps = 0.001;
        int amountOfCalculations = 0;
        do {
            amountOfCalculations++;
            writer.write("Current statement:" + Double.toString(x1) + " " + Double.toString(x2) + '\n');
            List<Double> nwPoint = normalizationOfVector(derivativeInDirection(x1, x2));
            double nwX1 = x1 - eps * nwPoint.get(0);
            double nwX2 = x2 - eps * nwPoint.get(1);
            writer.write("New statement:" + Double.toString(nwX1) + " " + Double.toString(nwX2) + '\n');
            writer.write("f0 = " + Double.toString(funcToAnalyze.apply(x1, x2)) + "\n" +
                    "f1 = " + Double.toString(funcToAnalyze.apply(nwX1, nwX2)) + "\n\n");
            if (funcToAnalyze.apply(x1, x2) > funcToAnalyze.apply(nwX1, nwX2)) {
                x1 = nwX1;
                x2 = nwX2;
            } else {
                break;
            }

        } while (true);

        writer.write("\nThe result is " + Double.toString(funcToAnalyze.apply(x1, x2)) + '\n');
        writer.write("x1 = " + Double.toString(x1) + "x2 = " + Double.toString(x2) + '\n');
        writer.write("Amount of calculations is " + Integer.toString(amountOfCalculations));
    }

    public static void main(String[] args) {
        try (BufferedWriter gradient = new BufferedWriter(new FileWriter("gradient"))){
            gradientDownhill(gradient);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
