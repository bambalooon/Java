package genetic.algorithms.mutation;

import genetic.chromosom.Chromosom;
import java.util.Random;

public class MutationRandomInversion implements Mutation {
    
    @Override
    public void mutate(Chromosom X) {
        Random rand = new Random();
        for(int i = 0; i<rand.nextInt(Chromosom.size); i++)
            X.flip(rand.nextInt(Chromosom.size));
    }
}
