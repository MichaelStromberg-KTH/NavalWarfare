// naval.Navy
package naval;

import java.applet.Applet;
import java.awt.*;
import java.lang.*;
import java.util.*;

import sea.Coord;
import sea.Sea;
import naval.Ship;


/**
 * A navy is one of the sides in a war at sea. This class keeps track of
 * the ships in one navy, how alive they are, etc.
 *
 * @author Rasmus Kaj <kaj@it.kth.se>
 */
public class Navy {
  Ship ship[];
  int placed_ship;

  /**
   * Construct a navy. If place_ships is <code>true</code>, place
   * the ships at random positions.
   * @param n_ships - the maximum number of ships in the navy.
   */
  Navy(int n_ships) {
    ship = new Ship[n_ships];
    placed_ship = 0;
  }

  /**
   * Place a ship on the sea. The ship is only placed if it's completley
   * inside this players area.
   * @param one_ship	the ship to place. It already knows it coordinate.
   * @param sea		the sea the ship is placed on.
   * @return True if the ship is placed, false otherwise.
   */
  public boolean placeShip(Ship one_ship, WorriedSea sea) {
    // This throws indexoutofbounds if one calls placeShip to often.
    // that should be documented.

    for(Enumeration p = one_ship.floating.elements(); p.hasMoreElements();) {
      Coord tmp = (Coord) p.nextElement();
      if(!sea.legalMove(sea.getOpponent(this), tmp)) {
	System.out.println("Illegal pos for " + this + ": " + tmp);
	return false;
      }
    }
    ship[placed_ship] = one_ship; 
    ++placed_ship;
    return true;
 }

  void draw(Graphics g) {
    for(int i = 0; i< ship.length; i++)
      if(ship[i] != null) ship[i].draw(g);
  }

  /**
   * Returns true if all the ships is placed, i.e. the fleet is
   * ready for combat.
   */
  boolean allPlaced() {
    return placed_ship >= ship.length;
  }

  /**
   * If there is a ship covering <code>pos</code>, this function bombs
   * that ship
   * @param  pos  Where the bomb hits.
   * @return Whats left of the bombed ship, or null if the shot was
   *	     a miss.
   */
  Ship hitBy(Coord pos) {
    for(int i = 0; i< ship.length; i++)
      if(ship[i] != null && ship[i].hitBy(pos))
	return ship[i];
    // else
    return null;
  }

  /**
   * @return True if there is still life on any ship in this navy.
   */
  public boolean stillLife() {
    for(int i = 0; i< ship.length; i++)
      if(ship[i] != null && !ship[i].sunk())
	return true;
    return false;
  }
  
}

// end of naval.Navy
