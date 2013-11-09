/*
 * Klasa definiujacą wierzchołek
 */
package graph.representation;

/**
 *
 * @author Rael
 */
public class MyNode
{
    private int id; // pytanie - czy wierzchołek ma pamiętać swój stopień?
    //int degr;

    /**
     * @return the id
     */
    public MyNode(int _id)
    {
        this.id = _id;
    }
    public int getId()
    {
        return id;
    }   
    
    public String toString()
    {
        return "V"+id;
    }
    
}
