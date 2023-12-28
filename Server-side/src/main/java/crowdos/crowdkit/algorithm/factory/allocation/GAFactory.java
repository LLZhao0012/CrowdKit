package crowdos.crowdkit.algorithm.factory.allocation;


import crowdos.crowdkit.algorithm.library.allocation.AllocationAlgo;
import crowdos.crowdkit.algorithm.library.allocation.GA;
import crowdos.crowdkit.entity.MetaTask;
import crowdos.crowdkit.entity.MetaWorker;

import java.util.List;


public class GAFactory implements AllocationAlgoFactory {
    private List<MetaTask> taskList;
    private List<MetaWorker> workerList;

    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int maxGenerations;

    public GAFactory(List<MetaTask> taskList, List<MetaWorker> workerList,
                     int populationSize, double crossoverRate, double mutationRate, int maxGenerations) {
        this.taskList = taskList;
        this.workerList = workerList;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.maxGenerations = maxGenerations;
    }

    @Override
    public AllocationAlgo create() {
        return new GA(taskList, workerList, populationSize, crossoverRate, mutationRate, maxGenerations);
    }
}
