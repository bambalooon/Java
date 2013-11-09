/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Matrix;

/**
 *
 * @author BamBalooon
 */
public class MatrixProgram {
    public static void main(String [] argv){
        Matrix A = new Matrix(3,2);
        Matrix B = new Matrix(3,2);
        Matrix C = new Matrix(2,1);
        A.add(B).dump();
        A.sub(B).dump();
        A.mul(C).dump();
    }
}
