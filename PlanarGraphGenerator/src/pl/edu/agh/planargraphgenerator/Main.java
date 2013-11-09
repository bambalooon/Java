package pl.edu.agh.planargraphgenerator;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

public class Main  {
	
	
	public static void main(String[] argS) {

		
		VDriver vd = new VDriver(400, 400);
		
		UndirectedSparseGraph<Pair<Float>, String> g = vd.planarGraph;

		System.out.println("Planar graph generation completed. |V| = "+g.getVertexCount()+", |E| = "+g.getEdgeCount());
		
	}
}
