class SimplexMethod {
    private String fileA = "A";
    private String fileB = "B";
    private String fileC = "C";
    private String fileE = "E";

    void execute() {
        MatrixReaderCreator matrixCreator = new MatrixReaderCreator();
        Matrix a = matrixCreator.readAndCreate(fileA);
        Matrix b = matrixCreator.readAndCreate(fileB);
        Matrix c = matrixCreator.readAndCreate(fileC);
        Matrix e = matrixCreator.readAndCreate(fileE);

        a.concat(e);
        a.concat(b);
        for (int i = 0; i < e.getNumberOfColumns() + 1; ++i) {
            c.getRow(0).add(0);
        }
        a.addRow(c.getRow(0));

        MatrixPrinter matrixPrinter = new MatrixPrinter();
        matrixPrinter.printToSout(a);

    }



}
