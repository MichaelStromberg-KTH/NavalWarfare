// naval.Naval
package naval;

import java.applet.Applet;
import java.awt.*;
import java.lang.*;
import java.util.*;
import java.net.*;

import sea.Coord;
import sea.Sea;
import naval.Ship;
import naval.Navy;
import naval.AutoNavy;
import naval.SoundLib;

/**
 * Naval is the main class in Naval Warefare.  This class keeps track
 * of navys at war. Maybe this class should be named OnePlayerBattle or
 * something like that, since there seems to be some one-player-designs
 * in this class.<p>
 * Probably it would be better to cut this to a class with most of this
 * functionality, and a subclass each for singleplayer and multiplayer
 * games.
 * @author Rasmus Kaj <kaj@it.kth.se>
 */
public class Naval extends Sea  implements WorriedSea {
  static final int STATE_PLACING_SHIPS = 0;
  static final int STATE_PLAYING = 1;
  static final int STATE_ENDGAME = 2;
  static final int EXPLODE_SHOT = 0;
  static final int EXPLODE_MISS = 1;
  static final int EXPLODE_HIT = 2;

  /**
   * The enemys background (where the user drops bombs) is filled whith
   * this color to make it differ.
   */
  final Color enemy_bg = new Color(255, 140, 140);
  Image[] explosion;

  SoundLib sounds;
  Applet app;
  int state_of_game;
  
  Coord current_held_pos;

  /**
   * The heros (that is, the users) navy.
   */
  Navy hero;
  /**
   * The navy played by the computer.
   */
  AutoNavy enemy;

  /**
   * A label for status messages. This one lives outside the Sea Panel
   * and should be constructed elsewhere.
   */
  Label status_line;

  /**
   * A vector of sea.Coord's where the player have hit the enemy.
   */
  Vector bombed_hit;

  /**
   * A vector of sea.Coord's where the player have made holes in the sea.
   */
  Vector bombed_miss;

  public Naval(Label status_line, SoundLib sounds, Applet app) {
    super();
    this.status_line = status_line;
    this.sounds = sounds;
    this.app = app;
    // Initialize the navys
    hero = new Navy(3);
    enemy = new AutoNavy(app, this);
    
    // Get the explosion images
    try {
      URL docbase = new URL(app.getDocumentBase(), "ships/");
      explosion = new Image[4];
      explosion[EXPLODE_SHOT] = app.getImage(docbase, "shot.gif");
      explosion[EXPLODE_HIT]  = app.getImage(docbase, "hit.gif");
      explosion[EXPLODE_MISS] = app.getImage(docbase, "miss.gif");
    } catch (MalformedURLException e) {
      System.out.println("Det sket sig och blev inga explosioner...");
    }

    bombed_hit = new Vector();
    bombed_miss = new Vector();
  }

  /**
   * Oh, heck... This function isn't called anyway.
   */
  public void init() {
    // Cause them to get loaded (maybe not the most goodlooking way...
    // This explosions (probably) won't be seen.
    System.out.println("Init is called!");
    Graphics grp = getGraphics();
    grp.drawImage(explosion[EXPLODE_SHOT], 20, 20, this);
    grp.drawImage(explosion[EXPLODE_HIT],  20, 20, this);
    grp.drawImage(explosion[EXPLODE_MISS], 20, 20, this);      
    status_line.setText("Place your fleet");
  }

  /**
   * Draw the sea with a war on it.
   */
  public void paint(Graphics grp) {
    Rectangle rect = bounds();
    grp.setColor(enemy_bg);
    grp.fillRect(0, 0, rect.width, rect.height/2);

    super.paint(grp);

    // now draw the ships
    hero.draw(grp);
    // Don't draw the enemy!

    // and now draw the 'explosions'
    for(Enumeration e = bombed_hit.elements(); e.hasMoreElements(); ) {
      Coord pos = (Coord)e.nextElement();
      grp.drawImage(explosion[EXPLODE_HIT], pos.x0()-20, pos.y0()-20, null);
    }
    for(Enumeration e = bombed_miss.elements(); e.hasMoreElements();) {
      Coord pos = (Coord)e.nextElement();
      grp.drawImage(explosion[EXPLODE_MISS], pos.x0()-20, pos.y0()-20, null);
    }
  }


  public void mouseDown(Coord pos, Event evt) {
    current_held_pos = pos;
    switch(state_of_game) {
    case STATE_PLACING_SHIPS:
      status_line.setText("Place a ship at " + pos + " ?");
      getGraphics().setColor(Color.black);
      getGraphics().fillOval(pos.x0()-5, pos.y0()-5, 10, 10);
      break;
    }
  }
  /**
   * The user touched the sea. Depending of the <code>state_of_game</code>
   * this means (s)he either places a ship or drops a bomb. In the later
   * case the <code>enemy<code> drops a bomb as well. 
   */
  public void mouseUp(Coord pos, Event evt) {
    Graphics grp = getGraphics();
    state_of_game: switch(state_of_game) {
    case STATE_PLACING_SHIPS:
      // Time to try and place a ship
      Ship ship = new Iowa(current_held_pos, current_held_pos.directionTo(pos),
			   app, this);
      if(!hero.placeShip(ship, this)) {
	status_line.setText("You can't place a ship there!");
	ship = null;
	break state_of_game;
      }
      ship.draw(grp);      

      // ok, we've added the ship. See if we should add another one, or
      // start playing.
      if(hero.allPlaced()) {
	state_of_game = STATE_PLAYING;
	status_line.setText("We begin bombing in five minutes.");
      }
      break;

    case STATE_PLAYING:
      if(legalMove(hero, pos)) {
	placeBomb("You", pos, enemy);
	if(state_of_game == STATE_PLAYING)
	  // Didn't kill the enemy, let him move.
	  enemy.MakeMove(this);
	break;
      } else {
	status_line.setText("You can't drop a bomb there!");
	return;
      } 

    case STATE_ENDGAME:
      status_line.setText("Game ended! Go away!  (To restart, press reload.)");
      break;
    }
    current_held_pos = null;
  }

  /**
   * This is the method that actually detonates the bomb.
   *
   * @param who - the one who is firing
   * @param pos - where the shot hit
   * @param aim - the navy it was aimed at
   * @return true if the bomb hits the aim.
   */
  public boolean placeBomb(String who, Coord pos, Navy aim) {
    Graphics grp = getGraphics();
    grp.drawImage(explosion[EXPLODE_SHOT], pos.x0()-20, pos.y0()-20, null);
    Ship hit_ship = aim.hitBy(pos);

    if(hit_ship != null) {
      if(hit_ship.sunk()) {
	if(aim.equals(enemy))
	  if(!enemy.stillLife()) {
	    sounds.play(sounds.YOUWIN);
	    status_line.setText("Congratulations!  You won the game!");
	    state_of_game = STATE_ENDGAME;
	  } else sounds.play(sounds.YOUSANKME);
	else 
	  if(!hero.stillLife()) {
	    sounds.play(sounds.IWIN);
	    status_line.setText("Sucker!  I won the game!");
	    state_of_game = STATE_ENDGAME;
	  } else sounds.play(sounds.ISANKYOU);
	status_line.setText(who + " sunk a " + hit_ship);
      } else {
	if(aim.equals(enemy)) sounds.play(sounds.YOUHITME);
	else sounds.play(sounds.IHITYOU);
      }
      grp.drawImage(explosion[EXPLODE_HIT], pos.x0()-20, pos.y0()-20, null);
      bombed_hit.addElement(pos);
      return true;
    } else {
      sounds.play(sounds.MISS);
      grp.drawImage(explosion[EXPLODE_MISS], pos.x0()-20, pos.y0()-20, null);
      bombed_miss.addElement(pos);
      return false;
    } 
  }
  
  /**
   * Check if a move is legal
   * @param navy The navy that wants to make the move.
   * @param pos  The coordinate where <code>navy</code> wants to drop
   *		 a bomb.
   * @return True if the move is legal, false otherwise.
   */
  public boolean legalMove(Navy navy, Coord pos) {
    if(navy.equals(hero)) {
      return pos.y0() < preferredSize().height/2;
    } else if(navy.equals(enemy)) {
      return pos.y0() > preferredSize().height/2;
    }
    else System.out.println("Someone other than the hero or the enemy " +
			    "just tried to make a move.");
    return false;
  }


  /**
   * @return The opponent to <code>navy</code>.
   */
  public Navy getOpponent(Navy navy) {
    if(navy.equals(hero)) return enemy;
    return hero;
  }

  /**
   * @return a copy of the vector of bombs that did hit something.
   */
  public Vector getBombedHits() {
    return (Vector) bombed_hit.clone();
  }

  /**
   * @return a copy of the vector of bombs that did not hit something.
   */
  public Vector getBombedMisses() {
    return (Vector) bombed_miss.clone();
  }

}


// end of naval.Naval
