package genetic.algorithms.selection;

import java.util.LinkedList;
import genetic.chromosom.Chromosom;

public interface Selection {
    public LinkedList<Chromosom> select();
}
