package crowdos.crowdkit.algorithm.library.allocation;


import crowdos.crowdkit.entity.MetaTask;
import crowdos.crowdkit.entity.MetaWorker;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RTP implements AllocationAlgo{
	private List<MetaTask> taskList;
	
    private List<MetaWorker> workerList;
    
    public RTP(List<MetaTask> taskList, List<MetaWorker> workerList) {
        this.taskList = taskList;
        this.workerList = workerList;
    }

	@Override
	public Object call() throws Exception {
		if(taskList == null || workerList == null){
            return null;
        }
		Map<MetaTask, List<MetaWorker>> result = new HashMap<MetaTask, List<MetaWorker>>();
		sortWorkerByArrivalTime();
		for(MetaTask task: taskList) {
			int toIndex = task.getNumber() <= workerList.size() ? task.getNumber() : workerList.size();
			List<MetaWorker> workers = workerList.subList(0, toIndex);
			result.put(task, workers);
		}
		return result;
	}
	
	private void sortWorkerByArrivalTime(){
		Collections.sort(this.workerList, new Comparator<MetaWorker>() {

			@Override
			public int compare(MetaWorker o1, MetaWorker o2) {
				return o2.getArrivalTime().compareTo(o1.getArrivalTime());
			}
		});
	}

}
