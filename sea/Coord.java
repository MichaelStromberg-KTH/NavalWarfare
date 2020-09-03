// sea.Coord
package sea;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Polygon;
import java.lang.Integer;
import java.lang.String;
import java.lang.Cloneable;
import java.lang.IllegalArgumentException;

/**
 * Coord represents a coordinate on the hexagonal grid.
 */
public class Coord implements Cloneable {
  public static final int NORTH = 0;
  public static final int NORTHWEST = 1;
  public static final int SOUTHWEST = 2;
  public static final int SOUTH = 3;
  public static final int SOUTHEAST = 4;
  public static final int NORTHEAST = 5;
  int x, y;

  /**
   * Create a new coord for the x and y coordinates.
   */
  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Return a clone (copy) of this coord. Might not need to be implemented.
   */
  public Object clone() {
    return new Coord(x, y);
  }

  public boolean equals(Object obj) {
    return ((obj instanceof Coord) &&
	    (((Coord)obj).x == x) && (((Coord)obj).y == y));
  }
  public int xpos() { return x; }
  public int ypos() { return y; }


  /**
   * Get the x pixelcoordinate for the center of this coord.
   */
  public int x0() {  // in pixels
    return x*24 + 13;
  }

  /**
   * Get the y pixelcoordinate for the center of this coord.
   */
  public int y0() {  // in pixels
    int t_y = y*28 + 12;
    if(x%2 == 1) t_y += 12;
    return t_y;
  }

  /**
   * Displace this coord by a given distance in a given direction.
   */
  public Coord displace(int direction, int distance) {
    switch(direction) {
    case NORTH:     y -= distance;
        	    break;
    case SOUTH:     y += distance;
        	    break;
    case NORTHWEST: x -= distance;
        	    y -= (distance+x%2)/2;
        	    break;
    case SOUTHWEST: x -= distance;
        	    y += (distance+1-x%2)/2;
        	    break;
    case NORTHEAST: x += distance;
        	    y -= (distance+x%2)/2;
        	    break;
    case SOUTHEAST: x += distance;
        	    y += (distance+1-x%2)/2;
        	    break;
    default:        throw new IllegalArgumentException("Illegal Direction " + 
						       direction);
    }
    return this;
  }

  /**
   * This is going to be tricky...
   */
  public int directionTo(Coord pos) {
    if(pos.x > x) {
      if(pos.y0() > y0()) return SOUTHEAST;
      else return NORTHEAST;
    } else if(pos.x < x) {
      if(pos.y0() > y0()) return SOUTHWEST;
      else return NORTHWEST;
    } else {
      if(pos.y > y) return SOUTH;
      else return NORTH;
    }
    
  }
  /**
   * Return an int representing the opposit way of direction.
   */
  public static int otherWay(int direction) {
    switch(direction) {
    case NORTH:     return SOUTH;
    case SOUTH:     return NORTH;
    case NORTHWEST: return SOUTHEAST;
    case SOUTHWEST: return NORTHEAST;
    case NORTHEAST: return SOUTHWEST;
    case SOUTHEAST: return NORTHWEST;
    default:        throw new IllegalArgumentException("Illegal Direction " + 
						       direction);
    }
  }

  /**
   * Displace a clone of this coord by a given distance in a
   * given direction.
   */
  public Coord displaced(int direction, int distance) {
    return ((Coord)clone()).displace(direction, distance);
  }

  /**
   * Calculate the coord that coresponds to a given pair of (pixel) 
   * screencoordinates.
   */
  public static Coord getPos(int x, int y) {
    int tx = (x-20 +12)/24, ty = y;
    if(tx%2 == 1) ty -= 12;
    ty = (ty-13 +14)/28;
    return new Coord(tx, ty);
  }

  public String toString() {
    return "[" + x + "," + y + "]";
  }
};     

// end of sea.Coord

