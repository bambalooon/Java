/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Matrix;

/**
 *
 * @author BamBalooon
 */
public class Matrix {
    public int x,y;
    public double[][] numbers;
    
    Matrix(int x, int y) {
        this.x = x;
        this.y = y;
        numbers = new double[x][y];
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                numbers[i][j] = 1;
            }
        }
    }
    
    Matrix() {
        this(1,1);
    }
    
    Matrix add(Matrix A) {
        Matrix tmp = new Matrix(x,y);
        if(A.x==x && A.y==y) {
           for(int i=0; i<x; i++) {
                for(int j=0; j<y; j++) {
                    tmp.numbers[i][j] = A.numbers[i][j]+numbers[i][j];
                }
            }
        }
        return tmp; //error
    }
    Matrix sub(Matrix A) {
        Matrix tmp = new Matrix(x,y);
        if(A.x==x && A.y==y) {
           for(int i=0; i<x; i++) {
                for(int j=0; j<y; j++) {
                    tmp.numbers[i][j] = numbers[i][j]-A.numbers[i][j];
                }
            }
        }
        return tmp; //error
    }
    Matrix mul(Matrix A) {
        Matrix tmp = new Matrix(x,A.y);
        if(A.x==y) {
           for(int i=0; i<x; i++) {
               for(int j=0; j<A.y; j++) {
                   double sum=0;
                   for(int k=0; k<y; k++) {
                       sum+=numbers[i][k]*A.numbers[k][j];
                   }
                   tmp.numbers[i][j] = sum;
               }
           } 
        }
        return tmp; //error
    }
    
    void dump() {
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                System.out.print(numbers[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
}
