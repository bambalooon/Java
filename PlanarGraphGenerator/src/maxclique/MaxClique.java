package maxclique;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import java.util.LinkedList;

import genetic.algorithms.crossover.Crossover;
import genetic.algorithms.evaluation.Evaluation;
import genetic.algorithms.mutation.Mutation;
import genetic.chromosom.Chromosom;

public class MaxClique implements Runnable {
        public static MaxClique instance;
	private int populationSize;
	private Crossover crossoverMethod;
	private Mutation mutationMethod;
	private Evaluation evalMethod;
	public static LinkedList<Chromosom> population; //nie wiem jeszcze gdzie to przechowywac.. moze potem stworzyc klase Population..
	public SparseDoubleMatrix2D matrix;

    /*public static void main(String[] args) {
        System.out.println("asd");        
        
        
        *
         * Ta klasa może się przydać przy implementacji całego algorytmu
         * public abstract class IterativeProcess
            extends Object

             Provides basic infrastructure for iterative algorithms. Services provided include: 
             storage of current and max iteration count 
             framework for initialization, iterative evaluation, and finalization 
             test for convergence 
             etc. 

             Algorithms that subclass this class are typically used in the following way: 
             FooAlgorithm foo = new FooAlgorithm(...)
             foo.setMaximumIterations(100); //set up conditions
             ...
             foo.evaluate(); //key method which initiates iterative process
             foo.getSomeResult();
             
             Widze ze myslimy podobnie :D
             Moze byc klasa odpowiedzialna za stop.. i tam m. in. te iteracje
             chyba ze chcemy zrobic na szybko troszke mniej ladnie..
         
    }*/
    
    public void run() { //tutaj rozpoczynamy algorytm.. 
    	population = new LinkedList<>();
    	for(int i=0; i<1000; i++) { //populacja o rozmiarze 1000
    		population.add(Chromosom.getRandom(5)); //max 5-klika..
    	}
    	
    }
}