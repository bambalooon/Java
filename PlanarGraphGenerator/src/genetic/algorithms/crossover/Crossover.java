package genetic.algorithms.crossover;

import genetic.chromosom.Chromosom;

public interface Crossover {
    public Chromosom[] cross(Chromosom A, Chromosom B);
}
