package Punkty;

import java.util.*;
import javaIn.*;

public class Test {
  public static void main(String [] argv){
    LinkedList<Punkt3D> punkty = new LinkedList<Punkt3D>();
    int opt = 0;
    do {
      System.out.print("1. Wczytaj punkt 3D\n");
      System.out.print("2. Wyświetl wszystkie punkty\n");
      System.out.print("3. Oblicz odległość\n");
      System.out.print("4. Zakończ\n");
      System.out.print("\nTwój wybór: ");
      opt = JIn.getInt();
      switch(opt) {
	case 1:
	  System.out.print("Podaj x: ");
	  double x = JIn.getDouble();
	  System.out.print("Podaj y: ");
	  double y = JIn.getDouble();
	  System.out.print("Podaj z: ");
	  double z = JIn.getDouble();
	  Punkt3D p = new Punkt3D(x, y, z);
	  punkty.add(p);
	  break;
	case 2:
	  for(int i=0; i<punkty.size(); i++) {
	    Punkt3D pt = punkty.get(i);
	    System.out.print((i+1)+": "+pt.getX()+"x"+pt.getY()+"x"+pt.getZ()+"\n");
	  }
	  break;
	case 3:
	  System.out.print("Podaj nr 1. punktu: ");
	  int p1 = JIn.getInt();
	  System.out.print("Podaj nr 2. punktu: ");
	  int p2 = JIn.getInt();
	  System.out.print("Odległość między punktami jest równa: "+punkty.get(p1-1).distance(punkty.get(p2-1))+"\n");
	  break;
      }
    } while(opt!=4);
  }
}