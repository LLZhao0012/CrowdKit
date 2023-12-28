package crowdos.crowdkit.algorithm.library.allocation;

import crowdos.crowdkit.entity.MetaTask;
import crowdos.crowdkit.entity.MetaWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Random implements AllocationAlgo {
	private List<MetaTask> taskList;
    private List<MetaWorker> workerList;

    public Random(List<MetaTask> taskList, List<MetaWorker> workerList) {
        this.taskList = taskList;
        this.workerList = workerList;
    }

    @Override
    public Object call() throws Exception {
        if(taskList == null || workerList == null){
            return null;
        }
        java.util.Random random = new java.util.Random();
        Map<MetaTask, List<MetaWorker>> result = new HashMap<MetaTask, List<MetaWorker>>();
        for(MetaTask task: taskList){
            List<MetaWorker> workers = new ArrayList<MetaWorker>();
            if(workerList.size() <= task.getNumber()) {
            	for(MetaWorker worker: workerList) {
            		workers.add(worker);
            	}
            }
            else {
            	MetaWorker selectedWorker = workerList.get(random.nextInt(workerList.size()));
                while(workers.size() < task.getNumber()){
                    if(!workers.contains(selectedWorker)){
                        workers.add(selectedWorker);
                    }
                    selectedWorker = workerList.get(random.nextInt(workerList.size()));
                }
            }
            result.put(task, workers);
        }
        return result;
    }
}
