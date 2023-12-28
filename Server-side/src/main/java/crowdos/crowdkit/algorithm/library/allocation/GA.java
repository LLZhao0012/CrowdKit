package crowdos.crowdkit.algorithm.library.allocation;


import crowdos.crowdkit.entity.MetaTask;
import crowdos.crowdkit.entity.MetaWorker;

import java.util.*;

public class GA implements AllocationAlgo{
    private List<MetaTask> taskList;
    private List<MetaWorker> workerList;

    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int maxGenerations;

    public GA(List<MetaTask> taskList, List<MetaWorker> workerList, int populationSize, double crossoverRate, double mutationRate, int maxGenerations) {
        this.taskList = taskList;
        this.workerList = workerList;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.maxGenerations = maxGenerations;
    }

    @Override
    public Object call() throws Exception {
        if (taskList == null || workerList == null) {
            return null;
        }

        Map<MetaTask, List<MetaWorker>> result = new HashMap<>();

        List<Chromosome> population = initializePopulation();

        for (int generation = 0; generation < maxGenerations; generation++) {
            population = evolvePopulation(population);
        }

        Chromosome bestChromosome = getBestChromosome(population);
        List<MetaWorker> allocatedWorkers = decodeChromosome(bestChromosome);

        for (MetaTask task : taskList) {
            result.put(task, allocatedWorkers);
        }

        return result;
    }

    private List<Chromosome> initializePopulation() {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome chromosome = generateRandomChromosome();
            population.add(chromosome);
        }
        return population;
    }

    private Chromosome generateRandomChromosome() {
        List<Integer> taskAssignment = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            taskAssignment.add(i % workerList.size()); // Assign tasks to workers cyclically
        }
        Collections.shuffle(taskAssignment); // Shuffle the task assignments
        return new Chromosome(taskAssignment);
    }

    private List<Chromosome> evolvePopulation(List<Chromosome> population) {
        List<Chromosome> newPopulation = new ArrayList<>();

        while (newPopulation.size() < populationSize) {
            Chromosome parent1 = selection(population);
            Chromosome parent2 = selection(population);

            if (Math.random() < crossoverRate) {
                Chromosome child = crossover(parent1, parent2);
                newPopulation.add(child);
            } else {
                newPopulation.add(parent1);
                newPopulation.add(parent2);
            }
        }

        for (Chromosome chromosome : newPopulation) {
            if (Math.random() < mutationRate) {
                mutate(chromosome);
            }
        }

        return newPopulation;
    }

    private Chromosome selection(List<Chromosome> population) {
        // Tournament selection
        Random random = new Random(taskList,workerList);
        int tournamentSize = 3;
        List<Chromosome> tournament = new ArrayList<>();

        for (int i = 0; i < tournamentSize; i++) {
            tournament.add(population.get(population.size()));
        }

        return getBestChromosome(tournament);
    }

    private Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int crossoverPoint = new java.util.Random().nextInt(taskList.size());
        List<Integer> childGenes = new ArrayList<>();

        childGenes.addAll(parent1.getGenes().subList(0, crossoverPoint));
        childGenes.addAll(parent2.getGenes().subList(crossoverPoint, taskList.size()));

        return new Chromosome(childGenes);
    }

    private void mutate(Chromosome chromosome) {
        int mutationPoint = new java.util.Random().nextInt(taskList.size());
        int newWorker = new java.util.Random().nextInt(workerList.size());
        chromosome.getGenes().set(mutationPoint, newWorker);
    }

    private Chromosome getBestChromosome(List<Chromosome> population) {
        Chromosome bestChromosome = population.get(0);

        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() > bestChromosome.getFitness()) {
                bestChromosome = chromosome;
            }
        }

        return bestChromosome;
    }

    private List<MetaWorker> decodeChromosome(Chromosome chromosome) {
        List<MetaWorker> allocatedWorkers = new ArrayList<>();

        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            int workerIndex = chromosome.getGenes().get(i);
            MetaWorker worker = workerList.get(workerIndex);
            allocatedWorkers.add(worker);
        }

        return allocatedWorkers;
    }

}
