// sea.SeaSpot
package sea;

/**
 * A class to hold information about a place in the sea.
 */
public class SeaSpot {
  public static final int EMPTY = 0;
  public static final int OPENEMPTY = 1;
  public static final int BURNING = 3;
  public int depth;
  public int state;

  public SeaSpot(int depth) {
    this.depth = depth;
    this.state = EMPTY;
  }
  public int d() { return depth; }
};

// end of sea.SeaSpot
