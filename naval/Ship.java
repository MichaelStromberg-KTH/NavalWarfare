// naval.Ship
package naval;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Vector;

import sea.Coord;

/**
 * An abstract base class for a ship
 */
public abstract class Ship {
  static Color black = new Color(0,0,0), 
               darkgray = new Color(24,24,24),
               mediumgray = new Color(64,64,64),
               lightgray = new Color(114,84,44);

  /**
   * The center of aft from center of the ship.
   */
  Coord pos = null; 
  /**
   * A vector of ocupied positions still abowe water. Public to simplify
   * the test 'is this ship placed legally'. May change to private.
   */
  public Vector floating;
  /**
   * Direction from pos to fore.
   */
  int direction;

  /**
   * A short description of what kind of ship this is.
   */
  String ship_type;

//  public Ship() {}
  
  /** 
   * Construct a ship.
   * @param ship_type A short string describeing the ship.
   * @param pos Where the ship is positioned.
   * @param direction Where the ship is headed.
   */
  public Ship(String ship_type, Coord pos, int direction) {
    this.ship_type = ship_type;
    this.pos = pos;
    this.direction = direction;
    floating = new Vector();
  }
  public abstract void draw(Graphics grp);

  /**
   * Drop a bomb and sea if the ship gets hurt. The ship keeps track of
   * how badly hurt it is.
   * @param pos Where to drop the bomb.
   * @return true if the ship is hit.
   */
  public boolean hitBy(Coord pos) {
    if(floating.contains(pos)) {
      floating.removeElement(pos);
      return true;
    }
    return false;
  }
  
  /**
   * Is this ship sunk, or is somone alive aboard?
   * @return true if the ship is sunk, false otherwise.
   */
  public boolean sunk() {
    return floating.size() == 0;
  }
    
  public String toString() {
    return ship_type;
  }
  
};

// end of naval.Ship
