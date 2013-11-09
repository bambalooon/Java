package genetic.chromosom;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Random;
import maxclique.MaxClique;

public class Chromosom {
    public static Chromosom getRandom(int maxVcnt) { //zwraca nam chromosom odpowiadajacy grafowi o max maxVcnt wierzcholkach..
            return new Chromosom(maxVcnt);
    }
    
    public Chromosom() {
        genes = new BitSet(size);
        
        Random rand = new Random();
        for(int i=0; i<size; i++)
            if(rand.nextBoolean())
                genes.set(i);
    }
    
    public Chromosom(int maxVcnt) {
    	Random rand = new Random();
    	int vertexCnt = rand.nextInt(maxVcnt);
        genes = new BitSet(Chromosom.size);
        
        for(int i=0; i<vertexCnt; i++) {
        	int index = rand.nextInt(Chromosom.size); //nie wiem czy nie -1 bo moze przekroczyc BitSet?
        	while(genes.get(index)) {
        		index++;
        		if(index>=Chromosom.size)
        			index = 0;
        	}
        	genes.set(index);
        }
    }
    
    public Chromosom(BitSet genes) {
        this.genes = genes;
    }
    
    public double rank;
    
    public BitSet get(int fromIndex, int toIndex) {
        return genes.get(fromIndex, toIndex);
    }
    
    public void flip(int index) {
        genes.flip(index);
    }
    
    public ArrayList<Integer> getVertexesIndexes() {
        ArrayList<Integer> ind = new ArrayList<>();
        for (int i = genes.nextSetBit(0); i >= 0; i = genes.nextSetBit(i+1))
            ind.add(i);
        return ind;
    }
    
    public int getVertexesCount() {
        int k=0;
        for (int i = genes.nextSetBit(0); i >= 0; i = genes.nextSetBit(i+1))
            k++;
        return k;
    }
    
    public int getEdgesCount() {
        SparseDoubleMatrix2D matrix = MaxClique.instance.matrix;
        int edges = 0;
        for (int i = genes.nextSetBit(0); i >= 0; i = genes.nextSetBit(i+1))
            for(int j=0; j<Chromosom.size; j++)
                if(matrix.getQuick(i, j)>0)
                    edges++;
        return edges/2; //2x wiecej krawedzi?
    }
    
    public Boolean isClique() {
        SparseDoubleMatrix2D matrix = MaxClique.instance.matrix;
        ArrayList<Integer> ind = this.getVertexesIndexes();
        for (int i : ind) 
            for(int j : ind)
                if((matrix.getQuick(i, j)<=0) && (i!=j))
                    return false;
        return true;
    }
    
    
   /*
   public mutate(Mutation mut) {
    
   }
    */
    
    private BitSet genes;
    public static int size;
}
