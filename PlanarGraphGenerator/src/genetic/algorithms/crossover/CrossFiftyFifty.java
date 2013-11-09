package genetic.algorithms.crossover;

import genetic.chromosom.Chromosom;
import java.util.BitSet;

public class CrossFiftyFifty implements Crossover {

    //Generates 2 children, each containing half of each parent.
    @Override
    public Chromosom[] cross(Chromosom A, Chromosom B) {
        int divPoint = (int) java.lang.Math.ceil(Chromosom.size);
        BitSet childA = A.get(0, divPoint);
        BitSet tmp = B.get(divPoint, Chromosom.size);
        for(int i=0; i<Chromosom.size-divPoint; i++)
            if(tmp.get(i))
                childA.set(divPoint+i);
        BitSet childB = B.get(0, divPoint);
        tmp = A.get(divPoint, Chromosom.size);
        for(int i=0; i<Chromosom.size-divPoint; i++)
            if(tmp.get(i))
                childB.set(divPoint+i);        
        return new Chromosom[] {new Chromosom(childA), new Chromosom(childB)};
    }
    
}
