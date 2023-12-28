package crowdos.crowdkit.algorithm.library.allocation;

import java.util.List;

public class Chromosome {
    private List<Integer> genes;

    public Chromosome(List<Integer> genes) {
        this.genes = genes;
    }

    public List<Integer> getGenes() {
        return genes;
    }

    public double getFitness() {
        return 0.0;
    }
}