package genetic.algorithms.selection;

import genetic.chromosom.Chromosom;
import java.util.LinkedList;
import java.util.Random;
import maxclique.MaxClique;

public class ProbabilitySelection implements Selection {
    public ProbabilitySelection(int min, int max) {
        this.min = min;
        this.max = max;
    }
    
    public LinkedList<Chromosom> select() {
        LinkedList<Chromosom> newPopulation = new LinkedList<>();
        Random random = new Random();
        double highestRank = 0;
        
        for(Chromosom X : MaxClique.population)
            if(X.rank > highestRank)
                highestRank = X.rank;
        
        for(Chromosom X : MaxClique.population) {
            double chance = X.rank/highestRank;
            if(chance < min/100)
                chance = min;
            if(chance > max/100)
                chance = max;
            
            if(random.nextDouble() <= chance)
                newPopulation.add(X);
        }
        
        return newPopulation;
    }
    
    int min;
    int max;
}
