package crowdos.crowdkit.algorithm.factory.allocation;

import crowdos.crowdkit.algorithm.library.allocation.AllocationAlgo;
import crowdos.crowdkit.algorithm.library.allocation.MTGrdPT;
import crowdos.crowdkit.entity.MetaTask;
import crowdos.crowdkit.entity.MetaWorker;

import java.util.List;

public class MTGrdPTFactory implements AllocationAlgoFactory {
	private List<MetaTask> taskList;
    private List<MetaWorker> workerList;

    public MTGrdPTFactory(List<MetaTask> taskList, List<MetaWorker> workerList) {
        this.taskList = taskList;
        this.workerList = workerList;
    }
    
	@Override
	public AllocationAlgo create() {
		return new MTGrdPT(this.taskList, this.workerList);
	}

}
