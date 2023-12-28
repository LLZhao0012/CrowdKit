package crowdos.crowdkit.algorithm.library.allocation;


import crowdos.crowdkit.entity.MetaTask;
import crowdos.crowdkit.entity.MetaWorker;

import java.util.*;

public class NNP implements AllocationAlgo{
	private List<MetaTask> taskList;

    private List<MetaWorker> workerList;

    public NNP(List<MetaTask> taskList, List<MetaWorker> workerList) {
        this.taskList = taskList;
        this.workerList = workerList;
    }

    
    @Override

    public Object call() throws Exception {
        if(taskList == null || workerList == null){
            return null;
        }
        Map<MetaTask, List<MetaWorker>> result = new HashMap<MetaTask, List<MetaWorker>>();
        for(MetaTask task: taskList){
            result.put(task, workers4Task(task));
        }
        return result;
    }

    private List<MetaWorker> workers4Task(MetaTask task){
        List<MetaWorker> workers = new ArrayList<MetaWorker>();
        Map<MetaWorker, Double> dis2Task = new HashMap<MetaWorker, Double>();
        for(MetaWorker worker: workerList){
            dis2Task.put(worker, getDistance(worker, task));
        }
        List<Map.Entry<MetaWorker, Double>> entryList = new ArrayList<Map.Entry<MetaWorker, Double>>(dis2Task.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<MetaWorker, Double>>() {
            @Override
            public int compare(Map.Entry<MetaWorker, Double> o1, Map.Entry<MetaWorker, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (int i = 0; i < task.getNumber() && i < entryList.size(); i++){
            workers.add(entryList.get(i).getKey());
        }
        return workers;
    }
    private double getDistance(MetaWorker worker, MetaTask task){
        return getDistance(worker.getLatitude(), worker.getLongitude(),
                task.getLatitude(), task.getLongitude());
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2){
        double EARTH_RADIUS = 6371000;
        double radiansAX = Math.toRadians(lon1);
        double radiansAY = Math.toRadians(lat1);
        double radiansBX = Math.toRadians(lon2);
        double radiansBY = Math.toRadians(lat2);
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
        double acos = Math.acos(cos);
        return EARTH_RADIUS * acos;
    }
}
