package crowdos.crowdkit.algorithm.library.allocation;


import crowdos.crowdkit.entity.MetaTask;
import crowdos.crowdkit.entity.MetaWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MTGrdPT implements AllocationAlgo{
    private List<MetaTask> taskList;

    private List<MetaWorker> workerList;

    public MTGrdPT(List<MetaTask> taskList, List<MetaWorker> workerList) {
        this.taskList = taskList;
        this.workerList = workerList;
    }

    @Override
    public Object call() throws Exception {
        if (taskList == null || workerList == null) {
            return null;
        }

        Map<MetaTask, List<MetaWorker>> result = new HashMap<>();
        Random random = new Random(taskList,workerList);

        for (MetaTask task : taskList) {
            List<MetaWorker> availableWorkers = getAvailableWorkers(task);
            if (!availableWorkers.isEmpty()) {
                MetaWorker bestWorker = findBestWorker(task, availableWorkers);
                if (bestWorker != null) {
                    System.out.println("Task " + task.getId() + " assigned to Worker " + bestWorker.getId());
                    result.computeIfAbsent(task, k -> new ArrayList<>()).add(bestWorker);
                } else {
                    System.out.println("No available worker for Task " + task.getId());
                }
            } else {
                System.out.println("No available workers for Task " + task.getId());
            }
        }

        return result;
    }

    private List<MetaWorker> getAvailableWorkers(MetaTask task) {
        List<MetaWorker> availableWorkers = new ArrayList<>();

        for (MetaWorker worker : workerList) {
            if (workerIsAvailable(worker) && workerCanDoTask(worker, task)) {
                availableWorkers.add(worker);
            }
        }

        return availableWorkers;
    }

    private boolean workerIsAvailable(MetaWorker worker) {
        return true;
    }

    private boolean workerCanDoTask(MetaWorker worker, MetaTask task) {
        return true;
    }

    private MetaWorker findBestWorker(MetaTask task, List<MetaWorker> availableWorkers) {
        MetaWorker bestWorker = null;
        double minCost = Double.MAX_VALUE;

        for (MetaWorker worker : availableWorkers) {
            double cost = calculateCost(task, worker);

            if (cost < minCost) {
                minCost = cost;
                bestWorker = worker;
            }
        }

        return bestWorker;
    }

    private double calculateCost(MetaTask task, MetaWorker worker) {
        double availabilityFactor = calculateAvailabilityFactor(worker);
        double capabilityFactor = calculateCapabilityFactor(worker);
        double distanceFactor = calculateDistanceFactor(task, worker);

        return 0.4 * availabilityFactor + 0.3 * capabilityFactor + 0.3 * distanceFactor;
    }

    private double calculateAvailabilityFactor(MetaWorker worker) {
        return 1.0;
    }

    private double calculateCapabilityFactor(MetaWorker worker) {
        return 1.0;
    }

    private double calculateDistanceFactor(MetaTask task, MetaWorker worker) {
        double distance = calculateDistance(task.getLatitude(), task.getLongitude(), worker.getLatitude(), worker.getLongitude());
        return 1.0 / (1.0 + distance);
    }

    private double calculateDistance(double taskLatitude, double taskLongitude, double workerLatitude, double workerLongitude) {
        return Math.sqrt(Math.pow(taskLatitude - workerLatitude, 2) + Math.pow(taskLongitude - workerLongitude, 2));
    }
}
