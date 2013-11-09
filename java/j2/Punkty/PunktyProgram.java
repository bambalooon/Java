package Punkty;

public class PunktyProgram {
  public static void main(String [] argv){
    Punkt2D p1 = new Punkt2D(1, 3);
    Punkt2D p2 = new Punkt2D(1, 10);
    System.out.print(p1.distance(p2)+"\n");
    Punkt3D p3 = new Punkt3D(1, 3, 10);
    Punkt3D p4 = new Punkt3D(1, 10, 10);
    System.out.print(p3.distance(p4)+"\n");

  }
}