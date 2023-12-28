package crowdos.crowdkit.algorithm.library.dataprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataFiltering implements crowdos.crowdkit.algorithm.library.dataprocessing.DataProcessingAlgo {
    private File file;
    private String dataColumn;
    private double upperLimit;
    private double lowerLimit;

    public DataFiltering(File file, String dataColumn, double upperLimit, double lowerLimit) {
        this.file = file;
        this.dataColumn = dataColumn;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }

    @Override
    public Object call() throws Exception {
        List<Double> filteredData = filterData();
        return filteredData;
    }

    private List<Double> filterData() throws Exception {
        List<Double> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine();
            String[] headers = header.split(",");

            int columnIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equals(dataColumn)) {
                    columnIndex = i;
                    break;
                }
            }

            if (columnIndex == -1) {
                throw new IllegalArgumentException("Column not found: " + dataColumn);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > columnIndex) {
                    double dataValue = Double.parseDouble(values[columnIndex]);
                    if (dataValue >= lowerLimit && dataValue <= upperLimit) {
                        result.add(dataValue);
                    }
                }
            }
        }

        return result;
    }
}
