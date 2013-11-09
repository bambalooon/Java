/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.io;

import graph.representation.MyEdge;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author Rael
 */
public class EdgeFactory implements Factory
{
    public MyEdge create()
            {
                return new MyEdge();
            }
}
