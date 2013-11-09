package genetic.algorithms.evaluation;

import genetic.chromosom.Chromosom;

public class EVEvaluation implements Evaluation {
        @Override
	public int evaluate(Chromosom X) {
                int rank = (int) (X.getVertexesCount()+Math.floor(Math.sqrt(X.getEdgesCount())));
                if(X.isClique())
                    rank += (int) (X.getVertexesCount()/2); //a moze rank x2???
                return rank;
	}
}
