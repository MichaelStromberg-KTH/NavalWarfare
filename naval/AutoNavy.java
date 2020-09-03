// naval.AutoNavy
package naval;

import java.applet.Applet;
import java.awt.*;
import java.lang.*;
import java.util.*;

import sea.Coord;
import sea.Sea;
import naval.Ship;
import naval.Navy;

/**
 * A Navy played by the computer. This is where the intelligence should
 * go.
 * @author Rasmus Kaj <kaj@it.kth.se>
 */
public class AutoNavy extends Navy {

  /**
   * This class needs knowledge about the applet it belongs to.
   */
  Applet app;

  /**
   * This is a position where we know we did hit something.
   */
  Coord hitpos = null;

  /**
   * This constructor places the computers ships in a semirandom
   * way. This currently works this (simple) way: Take a random number
   * n and select the n'th of a fixed set of layouts.
   */
  public AutoNavy(Applet app, WorriedSea sea) {
    super(3);
    Random numbers = new Random();
    switch(Math.abs(numbers.nextInt() %5)) {
    case 0:
      placeShip(new Iowa(new Coord(3,3), Coord.SOUTH, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(7, 2), Coord.SOUTHWEST, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(11, 4), Coord.SOUTHEAST, app, (Sea)sea), sea);
      break;
    case 1:
      placeShip(new Iowa(new Coord(8, 3), Coord.SOUTH, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(3, 4), Coord.NORTHWEST, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(3, 2), Coord.NORTHWEST, app, (Sea)sea), sea);
      break;
    case 2:
      placeShip(new Iowa(new Coord(3, 3), Coord.NORTHEAST, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(10, 4), Coord.NORTHWEST, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(7, 3), Coord.SOUTH, app, (Sea)sea), sea);
      break;
    case 3:
      placeShip(new Iowa(new Coord(2, 1), Coord.SOUTHWEST, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(4, 4), Coord.SOUTH, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(14, 3), Coord.SOUTHWEST, app, (Sea)sea), sea);
      break;
    case 4:
      placeShip(new Iowa(new Coord(18, 2), Coord.SOUTH, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(15, 2), Coord.SOUTH, app, (Sea)sea), sea);
      placeShip(new Iowa(new Coord(12, 5), Coord.SOUTHEAST, app, (Sea)sea), sea);
      break;
    }
  }
  
  /**
   * Find a good place to drop a bomb.
   */
  public void MakeMove(WorriedSea sea) {
    Random numbers = new Random();
    Coord c = null;
    Vector prev_hits = sea.getBombedHits();
    Vector prev_miss = sea.getBombedMisses();
    get_a_pos: do
      if(hitpos == null) {
	c = new Coord(Math.abs(numbers.nextInt() % sea.getWidth()),
		      Math.abs(numbers.nextInt() % sea.getHeight()));
      } else {
	System.err.println("We'we got something here....");

	Vector interresting_places = new Vector();
	neighbours: for(int d=0; d<6; ++d) {
	  Coord neigbour = hitpos.displaced(d, 1);
	  System.err.println("Checking neighbour " + d + "; " + neigbour);
	  if(prev_hits.contains(neigbour)) {
	    interresting_places.removeAllElements();

	    Coord tmp = (Coord) neigbour.clone();
	    for(; prev_hits.contains(tmp); tmp.displace(d, 1));
	    if(sea.legalMove(this, tmp) && !prev_miss.contains(tmp))
	      interresting_places.addElement(tmp);
	    
	    d = Coord.otherWay(d);
	    tmp = hitpos.displaced(d, 1);
	    for(; prev_hits.contains(tmp); tmp.displace(d, 1));
	    if(sea.legalMove(this, tmp) && !prev_miss.contains(tmp))
	      interresting_places.addElement(tmp);
	    
	    break neighbours;
	  }
	  if(!prev_miss.contains(neigbour))
	    interresting_places.addElement(neigbour);
	}
	if(interresting_places.isEmpty()) {
	  c = hitpos;		// this is necesarry since continue does 
				// check the loopcondition.
	  hitpos = null;
	  continue get_a_pos;
	}
	c = (Coord) interresting_places.elementAt(Math.abs(numbers.nextInt() % 
						  interresting_places.size()));
      }
    while (!sea.legalMove(this, c) ||
	   prev_hits.contains(c) || prev_miss.contains(c));
    
    if(sea.placeBomb("I", c, sea.getOpponent(this)))
      hitpos = c;
  }
  
}

// end of naval.AutoNavy
