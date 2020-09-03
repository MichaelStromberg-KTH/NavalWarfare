// naval.WorriedSea
package naval;

import java.lang.*;
import java.util.*;

import sea.Coord;

public interface WorriedSea {
  
  /**
   * @return a copy of the vector of bombs that did hit something.
   */
  public Vector getBombedHits();
  /**
   * @return a copy of the vector of bombs that did not hit something.
   */
  public Vector getBombedMisses();
  
  /**
   * Check if a move is legal
   * @param navy The navy that wants to make the move.
   * @param pos  The coordinate where <code>navy</code> wants to drop
   *		 a bomb.
   * @return True if the move is legal, false otherwise.
   */
  public boolean legalMove(Navy navy, Coord pos);
  /**
   * This is the method that actually detonates the bomb.
   *
   * @param who - the one who is firing
   * @param pos - where the shot hit
   * @param aim - the navy it was aimed at
   * @return true if the bomb did hit the aim.
   */
  public boolean placeBomb(String who, Coord pos, Navy aim);

  /**
   * @return The opponent to <code>navy</code>.
   */
  public Navy getOpponent(Navy n);

  public int getWidth();
  public int getHeight();
}

// end of naval.WorriedSea
