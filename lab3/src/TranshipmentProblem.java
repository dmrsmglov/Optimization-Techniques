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


    private void prepareForExecution(){
        String fileNameRequirements = "resources/Requirements";
        String fileNameInventory = "resources/Inventory";
        String fileNameTranshipmentCosts = "resources/TranshipmentCost";

        inventory = matrixReaderCreator.readAndCreate(fileNameInventory).getRow(0);
        requirements = matrixReaderCreator.readAndCreate(fileNameRequirements).getRow(0);
        transhipmentCost = matrixReaderCreator.readAndCreate(fileNameTranshipmentCosts);
        deliveryDistribution = matrixReaderCreator.createEmpty(inventory.size(), requirements.size());
        markDistribution = matrixReaderCreator.createEmpty(inventory.size(), requirements.size());

        inventoryPotential = new ArrayList<>(inventory.size());
        for (int i = 0; i < inventoryPotential.size(); i++) {
            inventoryPotential.add(0.);
        }

        requirementsPotential = new ArrayList<>(requirementsPotential.size());
        for (int i = 0; i < requirementsPotential.size(); i++) {
            requirementsPotential.add(0.);
        }

    }


    void execute(){
        prepareForExecution();

    }
}
