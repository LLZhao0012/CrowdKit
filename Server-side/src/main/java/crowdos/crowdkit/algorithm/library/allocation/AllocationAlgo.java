package crowdos.crowdkit.algorithm.library.allocation;

import java.util.concurrent.Callable;

public interface AllocationAlgo extends Callable<Object> {
    @Override
    Object call() throws Exception;
}
