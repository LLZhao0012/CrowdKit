package crowdos.crowdkit.algorithm.library.dataprocessing;

import java.util.concurrent.Callable;

public interface DataProcessingAlgo extends Callable {
    @Override
    Object call() throws Exception;
}
