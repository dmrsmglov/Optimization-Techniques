import matrix.*;

import java.util.ArrayList;
import java.util.List;

public class TranshipmentProblem {

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

        inventoryPotential = new ArrayList<>(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            inventoryPotential.add(0.);
        }

        requirementsPotential = new ArrayList<>(requirements.size());
        for (int i = 0; i < requirements.size(); i++) {
            requirementsPotential.add(0.);
        }
    }

    private void northEastDistribution() {
        for (int j = 0; j < requirements.size(); ++j) {
            int i = 0;
            while (requirements.get(j) > 0) {
                if (i >= inventory.size()) {
                    throw new IndexOutOfBoundsException("Problem is not balanced.\n You should add fake vendor.");
                }
                if (requirements.get(j) < inventory.get(i)) {
                    deliveryDistribution.setItem(i, j, requirements.get(j));
                    inventory.set(i, inventory.get(i) - requirements.get(j));
                    requirements.set(j, 0.);
                } else {
                    deliveryDistribution.setItem(i, j, inventory.get(i));
                    requirements.set(j, requirements.get(j) - inventory.get(i));
                    inventory.set(i, 0.);
                    i++;
                }
            }
        }
        for (Double anInventory : inventory) {
            if (anInventory > 0) {
                throw new IndexOutOfBoundsException("Problem is not balanced.\n You should add fake customer.");
            }
        }
    }

    private void calculatePotentials() {
        inventoryPotential.set(0, 0.);
        for (int j = 0; j < requirementsPotential.size(); ++j) {
            requirementsPotential.set(j, transhipmentCost.getItem(0, j) - inventoryPotential.get(0));
        }
        for (int i = 1; i < inventoryPotential.size(); i++) {
            inventoryPotential.set(i, transhipmentCost.getItem(i, 0) - requirementsPotential.get(0));
        }
    }

    private void markCurrentDistribution() {
        for (int i = 0; i < inventory.size(); ++i) {
            for (int j = 0; j < requirements.size(); ++j) {
                if (deliveryDistribution.getItem(i, j) == 0) {
                    markDistribution.setItem(i, j, transhipmentCost.getItem(i, j) - inventoryPotential.get(i)
                            - requirementsPotential.get(j));
                }
                else {
                    markDistribution.setItem(i, j, 0);
                }
            }
        }
    }

    private boolean isOptimal(){
        boolean optimal = true;
        for (int i = 0; i < inventory.size(); ++i) {
            for (int j = 0; j < requirements.size(); ++j) {
                optimal = markDistribution.getItem(i, j) >= 0;
            }
        }
        return optimal;
    }

    private void findCycle(List<Integer> cycleRow, List<Integer> cycleColumn) {
        //TODO how to find cycle
    }

    void execute() {
        prepareForExecution();
        northEastDistribution();
        calculatePotentials();
        markCurrentDistribution();

        while (!isOptimal()) {
            int minRow = -1;
            int minColumn = -1;
            double minValue = Integer.MAX_VALUE / 2;

            for (int i = 0; i < inventory.size(); i++) {
                for (int j = 0; j < requirements.size(); j++) {
                    double item = markDistribution.getItem(i, j);
                    if (item < minValue) {
                        minRow = i;
                        minColumn = j;
                    }
                }
            }

            List<Integer> cycleRow = new ArrayList<>();
            List<Integer> cycleColumn = new ArrayList<>();
            cycleRow.add(minRow);
            cycleColumn.add(minColumn);

            findCycle(cycleRow, cycleColumn);

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
                }
                else {
                    deliveryDistribution.setItem(cycleRow.get(i), cycleColumn.get(i),
                            deliveryDistribution.getItem(cycleRow.get(i), cycleColumn.get(i)) + minValue);
                }
            }
            markCurrentDistribution();
        }
    }
}
