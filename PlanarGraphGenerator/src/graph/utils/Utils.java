package graph.utils;

/*
 * Klasa z algorytmami grafowymi?
 * 
 */

import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import genetic.chromosom.Chromosom;
import java.util.ArrayList;
import maxclique.MaxClique;


public class Utils {
    
    //TODO:
    //Returns size of a clique in X. If X is not a clique, returns 0;
    static int cliqueSize(Chromosom X) {
        SparseDoubleMatrix2D matrix = MaxClique.instance.matrix;
        ArrayList<Integer> ind = X.getVertexesIndexes();
        for(int i=0; i<ind.size(); i++)
            for(int j=i+1; j<ind.size(); j++)
                if(matrix.getQuick(i, j)<=0)
                    return 0;
        return ind.size();
    }
    
   //  Funkcje zamieniające graf na macierz i vice versa 
        /*
         * public static SparseDoubleMatrix2D graphToSparseMatrix(Graph g,
                                                           NumberEdgeValue nev)
            Returns a SparseDoubleMatrix2D whose entries represent the edge weights for the edges in g, as specified by nev. 

            The (i,j) entry of the matrix returned will be equal to the sum of the weights of the edges connecting the vertex with index i to j. 

            If nev is null, then a constant edge weight of 1 is used. 

            Parameters:
            g - 
            nev -
         */

        /*
         * public static Graph matrixToGraph(DoubleMatrix2D matrix)
            Creates a graph from a square (weighted) adjacency matrix. Equivalent to matrixToGraph(matrix, (NumberEdgeValue)null). 

            Returns:
            a representation of matrix as a JUNG Graph
         */
    
       //Sprawdza, czy jest kliką!!!
        
    
    /*
     * public class CliqueGraphPredicate
        extends GraphPredicate

        Returns true if this graph is a clique (that is, if each vertex in the graph is a neighbor of each other vertex; also known as a complete graph).
     */
        
}
