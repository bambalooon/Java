/*
 * Reprezentacja grafu - nie wiem narazie, czy klasy z własnymi wierzchołkami i krawędziami nie są nadmiarowe.
 */
package graph.representation;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

/**
 *
 * @author Rael
 */
public class Representation
{
    Graph<Integer, Boolean> g; // wierzchołki w naszym grafie to po prostu ich identyfikatory, czyli numer bitu w bitstreamie, krawędzie to boole
    
    public Representation()
    {
        g = new SparseGraph<Integer, Boolean>();
    }
    
    
    
}
