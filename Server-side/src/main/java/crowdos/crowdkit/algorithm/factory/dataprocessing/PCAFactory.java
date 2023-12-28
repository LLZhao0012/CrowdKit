package crowdos.crowdkit.algorithm.factory.dataprocessing;

import crowdos.crowdkit.algorithm.library.dataprocessing.DataProcessingAlgo;
import crowdos.crowdkit.algorithm.library.dataprocessing.PCA;

import java.io.File;
import java.util.List;

public class PCAFactory implements DataProcessingAlgoFactory {
    private File file;
    private List<String> dataColumns;

    public PCAFactory(File file, List<String> dataColumns) {
        this.file = file;
        this.dataColumns = dataColumns;
    }
    @Override
    public DataProcessingAlgo create() {
        return new PCA(file, dataColumns);
    }
}
