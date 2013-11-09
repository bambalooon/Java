package Punkty;

import java.lang.Math.*;

public class Punkt3D extends Punkt2D {
  protected double z;
  public Punkt3D(double tx, double ty, double tz) {
    super(tx, ty);
    z = tz;
  };
  public double getZ() {
    return z;
  }
  public void setZ(double tz) {
    z = tz;
  }
  public double distance(Punkt3D pkt) {
    return Math.sqrt(Math.pow(pkt.getX()-x, 2)+Math.pow(pkt.getY()-y, 2)+Math.pow(pkt.getZ()-z, 2));
  }
}