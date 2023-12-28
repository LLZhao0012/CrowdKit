package crowdos.crowdkit.algorithm.factory.dataprocessing;

import crowdos.crowdkit.algorithm.library.dataprocessing.DataFiltering;
import crowdos.crowdkit.algorithm.library.dataprocessing.DataProcessingAlgo;

import java.io.File;

public class DataFilteringFactory implements DataProcessingAlgoFactory {

    private File file;
    private String dataColumn;
    private double upperLimit;
    private double lowerLimit;

    public DataFilteringFactory(File file, String dataColumn, double upperLimit, double lowerLimit) {
        this.file = file;
        this.dataColumn = dataColumn;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }
    @Override
    public DataProcessingAlgo create() {
        return (DataProcessingAlgo) new DataFiltering(file, dataColumn, upperLimit, lowerLimit);
    }
}
