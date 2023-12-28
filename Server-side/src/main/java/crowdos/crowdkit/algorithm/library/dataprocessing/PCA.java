package crowdos.crowdkit.algorithm.library.dataprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PCA implements DataProcessingAlgo {
    private File file;
    private List<String> dataColumns;

    public PCA(File file, List<String> dataColumns) {
        this.file = file;
        this.dataColumns = dataColumns;
    }

    @Override
    public Object call() throws Exception {
        List<double[]> dataMatrix = readDataMatrix();
        double[][] normalizedData = normalizeData(dataMatrix);
        double[][] covarianceMatrix = calculateCovarianceMatrix(normalizedData);
        double[][] eigenVectors = calculateEigenVectors(covarianceMatrix);

        return eigenVectors;
    }

    private List<double[]> readDataMatrix() throws Exception {
        List<double[]> dataMatrix;
        dataMatrix = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double[] row = new double[dataColumns.size()];

                for (int i = 0; i < dataColumns.size(); i++) {
                    int columnIndex = Integer.parseInt(dataColumns.get(i));
                    row[i] = Double.parseDouble(values[columnIndex]);
                }

                dataMatrix.add(row);
            }
        }

        return dataMatrix;
    }

    private double[][] normalizeData(List<double[]> dataMatrix) {
        return dataMatrix.toArray(new double[0][0]);
    }

    private double[][] calculateCovarianceMatrix(double[][] normalizedData) {
        int numRows = normalizedData.length;
        int numCols = normalizedData[0].length;

        double[][] covarianceMatrix = new double[numCols][numCols];

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numCols; j++) {
                double cov = 0.0;

                for (int k = 0; k < numRows; k++) {
                    cov += (normalizedData[k][i] - mean(normalizedData, i)) * (normalizedData[k][j] - mean(normalizedData, j));
                }

                covarianceMatrix[i][j] = cov / (numRows - 1);
            }
        }

        return covarianceMatrix;
    }

    private double[][] calculateEigenVectors(double[][] covarianceMatrix) {
        return new double[covarianceMatrix.length][covarianceMatrix.length];
    }

    private double mean(double[][] data, int column) {
        int numRows = data.length;
        double sum = 0.0;

        for (int i = 0; i < numRows; i++) {
            sum += data[i][column];
        }

        return sum / numRows;
    }
}