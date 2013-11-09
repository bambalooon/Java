package Punkty;

import java.lang.Math.*;

public class Punkt2D {
  protected double x;
  protected double y;
  public Punkt2D(double tx, double ty) {
    x = tx;
    y = ty;
  };
  public double getX() {
    return x;
  }
  public double getY() {
    return y;
  }
  public void setX(double tx) {
    x = tx;
  }
  public void setY(double ty) {
    y = ty;
  }
  public double distance(Punkt2D pkt) {
    return Math.sqrt(Math.pow(pkt.getX()-x, 2)+Math.pow(pkt.getY()-y, 2));
  }
}