import matrix.*;

import java.util.*;

public class TranshipmentProblem {
    class Pair{
        private int x, y;
        private Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private MatrixReaderCreator matrixReaderCreator = new MatrixReaderCreator();
    private List<Double> requirements = new ArrayList<>(0);
    private List<Double> inventory = new ArrayList<>(0);
    private List<Double> inventoryPotential = new ArrayList<>(0);
    private List<Double> requirementsPotential = new ArrayList<>(0);

    private Matrix transhipmentCost = new Matrix(0, 0);
    private Matrix deliveryDistribution = new Matrix(0, 0);
    private Matrix markDistribution = new Matrix(0, 0);

    private MatrixPrinter matrixPrinter = new MatrixPrinter();

    private void prepareForExecution() {
        String fileNameRequirements = "resources/Requirements";
        String fileNameInventory = "resources/Inventory";
        String fileNameTranshipmentCosts = "resources/TranshipmentCost";

        inventory = matrixReaderCreator.readAndCreate(fileNameInventory).getRow(0);
        requirements = matrixReaderCreator.readAndCreate(fileNameRequirements).getRow(0);
        transhipmentCost = matrixReaderCreator.readAndCreate(fileNameTranshipmentCosts);
        deliveryDistribution = matrixReaderCreator.createAndFill(inventory.size(), requirements.size(), .0);
        markDistribution = matrixReaderCreator.createAndFill(inventory.size(), requirements.size(), 0.);
    }

    private void refreshPotentials() {
        inventoryPotential = new ArrayList<>(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            inventoryPotential.add(Integer.MAX_VALUE + 0.);
        }

        requirementsPotential = new ArrayList<>(requirements.size());
        for (int i = 0; i < requirements.size(); i++) {
            requirementsPotential.add(Integer.MAX_VALUE + 0.);
        }
    }

    private void minimalCostsDistribution() {
        List<Pair> orderOfMinimalCosts = new ArrayList<>(0);
        double currentMinimum = Integer.MAX_VALUE / 2;
        double lowerBound = -1;
        while (orderOfMinimalCosts.size() != inventory.size() * requirements.size()) {
            for (int i = 0; i < inventory.size(); ++i) {
                for (int j = 0; j < requirements.size(); ++j) {
                    if (transhipmentCost.getItem(i, j) < currentMinimum && transhipmentCost.getItem(i, j) > lowerBound) {
                        currentMinimum = transhipmentCost.getItem(i, j);
                    }
                }
            }
            for (int i = 0; i < inventory.size(); ++i) {
                for (int j = 0; j < requirements.size(); ++j) {
                    if (transhipmentCost.getItem(i, j) == currentMinimum) {
                        orderOfMinimalCosts.add(new Pair(i, j));
                    }
                }
            }
            lowerBound = currentMinimum;
            currentMinimum = Integer.MAX_VALUE / 2;
        }
        for (int i = 0; i < orderOfMinimalCosts.size(); ++i) {
            int x = orderOfMinimalCosts.get(i).x;
            int y = orderOfMinimalCosts.get(i).y;
            if (requirements.get(y) < inventory.get(x)) {
                deliveryDistribution.setItem(x, y, requirements.get(y));
                inventory.set(x, inventory.get(x) - requirements.get(y));
                requirements.set(y, 0.);
            } else {
                deliveryDistribution.setItem(x, y, inventory.get(x));
                requirements.set(y, requirements.get(y) - inventory.get(x));
                inventory.set(x, 0.);
            }
        }
    }

    private void calculatePotentials() {
        refreshPotentials();
        List<Pair> potentialSetterQueue = new ArrayList<>(0);
        int potentialSetterQueuePointer = 0;
        for (int i = 0; i < inventory.size(); ++i) {
            if (deliveryDistribution.getItem(i, 0) != 0) {
                inventoryPotential.set(i, 0.);
                potentialSetterQueue.add(new Pair(i, 0));
                break;
            }
        }
        while (potentialSetterQueuePointer != potentialSetterQueue.size()) {
            int x = potentialSetterQueue.get(potentialSetterQueuePointer).x;
            int y = potentialSetterQueue.get(potentialSetterQueuePointer).y;
            potentialSetterQueuePointer++;
            if (requirementsPotential.get(y) == Integer.MAX_VALUE) {
                requirementsPotential.set(y, transhipmentCost.getItem(x, y) - inventoryPotential.get(x));
            }
            if (inventoryPotential.get(x) == Integer.MAX_VALUE) {
                inventoryPotential.set(x, transhipmentCost.getItem(x, y) - requirements.get(y));
            }
            for (int i = 0; i < inventory.size(); ++i) {
                if (deliveryDistribution.getItem(i, y) != 0 && inventoryPotential.get(i) == Integer.MAX_VALUE
                        && requirementsPotential.get(y) != Integer.MAX_VALUE) {
                    potentialSetterQueue.add(new Pair(i, y));
                }
            }
            for (int i = 0; i < requirements.size(); ++i) {
                if (deliveryDistribution.getItem(x, i) != 0 &&  requirementsPotential.get(i) == Integer.MAX_VALUE
                        && inventoryPotential.get(x) != Integer.MAX_VALUE) {
                    potentialSetterQueue.add(new Pair(x, i));
                }
            }
        }
    }

    private void markCurrentDistribution() {
        for (int i = 0; i < inventory.size(); ++i) {
            for (int j = 0; j < requirements.size(); ++j) {
                if (deliveryDistribution.getItem(i, j) == 0) {
                    markDistribution.setItem(i, j, transhipmentCost.getItem(i, j) - inventoryPotential.get(i)
                            - requirementsPotential.get(j));
                } else {
                    markDistribution.setItem(i, j, 0);
                }
            }
        }
    }

    private boolean isOptimal() {
        for (int i = 0; i < inventory.size(); ++i) {
            for (int j = 0; j < requirements.size(); ++j) {
                if (markDistribution.getItem(i, j) < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> cycleRow = new ArrayList<>(0);
    private List<Integer> cycleColumn = new ArrayList<>(0);

    private void findCycle() {
        findVertical(cycleRow.get(0), cycleColumn.get(0));
    }

    private boolean findVertical(int x, int y) {
        for (int i = 0; i < inventory.size(); ++i) {
            if (i != x && deliveryDistribution.getItem(i, y) != 0) {
                if (i == cycleRow.get(0)) {
                    cycleRow.add(i);
                    cycleColumn.add(y);
                    return true;
                }
                if (findHorizontal(i, y)) {
                    cycleRow.add(i);
                    cycleColumn.add(y);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean findHorizontal(int x, int y) {
        for (int i = 0; i < requirements.size(); ++i) {
            if (i != y && deliveryDistribution.getItem(x, i) != 0) {
                if (findVertical(x, i)) {
                    cycleRow.add(x);
                    cycleColumn.add(i);
                    return true;
                }
            }
        }
        return false;
    }

    private int countNotEmptyContracts() {
        int result = 0;
        for (int i = 0; i < inventory.size(); ++i) {
            for (int j = 0; j < requirements.size(); ++j) {
                if (deliveryDistribution.getItem(i, j) != 0) {
                    result++;
                }
            }
        }
        return result;
    }

    private double calculateTranshipmentCost(){
        double cost = 0;
        for (int i = 0; i < inventory.size(); ++i) {
            for (int j = 0; j < requirements.size(); j++) {
                cost += deliveryDistribution.getItem(i, j) * transhipmentCost.getItem(i, j);
            }
        }
        return cost;
    }

    private void makeBalanced() {
        while (countNotEmptyContracts() != inventory.size() + requirements.size() - 1) {
            Random random = new Random(System.currentTimeMillis());
            int row = random.nextInt(inventory.size());
            int column = random.nextInt(requirements.size());
            if (deliveryDistribution.getItem(row, column) == 0) {
                deliveryDistribution.setItem(row, column, 1e-3);
            }
        }
    }

    void printResult(){
        System.out.println(Math.round(calculateTranshipmentCost()));
        for (int i = 0; i < inventory.size(); i++) {
            for (int j = 0; j < requirements.size(); j++) {
                deliveryDistribution.setItem(i, j, Math.round(deliveryDistribution.getItem(i, j)));
            }
        }
        matrixPrinter.printToSout(deliveryDistribution);
    }

    void execute() {
        prepareForExecution();
        minimalCostsDistribution();
        makeBalanced();
        calculatePotentials();
        markCurrentDistribution();

        while (!isOptimal()) {
            int minRow = -1;
            int minColumn = -1;
            double minValue = 1000.;

            for (int i = 0; i < inventory.size(); i++) {
                for (int j = 0; j < requirements.size(); j++) {
                    double item = markDistribution.getItem(i, j);
                    if (item < minValue) {
                        minRow = i;
                        minColumn = j;
                        minValue = item;
                    }
                }
            }

            cycleRow = new ArrayList<>();
            cycleColumn = new ArrayList<>();
            cycleRow.add(minRow);
            cycleColumn.add(minColumn);
            try {
                findCycle();
            } catch (StackOverflowError error) {
                System.out.println("Stack overflow!");
            }

            minValue = Integer.MAX_VALUE / 2;
            for (int i = 0; i < cycleRow.size(); ++i) {
                if (i % 2 == 1) {
                    if (minValue > deliveryDistribution.getItem(cycleRow.get(i), cycleColumn.get(i))) {
                        minValue = deliveryDistribution.getItem(cycleRow.get(i), cycleColumn.get(i));
                    }
                }
            }

            for (int i = 0; i < cycleRow.size(); ++i) {
                if (i % 2 == 1) {
                    deliveryDistribution.setItem(cycleRow.get(i), cycleColumn.get(i),
                            deliveryDistribution.getItem(cycleRow.get(i), cycleColumn.get(i)) - minValue);
                } else {
                    deliveryDistribution.setItem(cycleRow.get(i), cycleColumn.get(i),
                            deliveryDistribution.getItem(cycleRow.get(i), cycleColumn.get(i)) + minValue);
                }
            }
            makeBalanced();
            calculatePotentials();
            markCurrentDistribution();
        }
    }
}