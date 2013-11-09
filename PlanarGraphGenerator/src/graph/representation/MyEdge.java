/*
 * Klasa definiująca krawędź
 */
package graph.representation;

/**
 *
 * @author Rael
 */
public class MyEdge
{
  private int id;
  static int count;
    public MyEdge()
    {
        this.id = count++;
    }
    public MyEdge(int _id)
    {
        this.id = _id;
    }
    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }
    public String toString()
    {
        return "E"+ id;
    }
  
}
