// sea.Sea
package sea;

import java.awt.*;
import java.lang.String;
import sea.Coord;
import sea.SeaSpot;

/**
 * A baseclass representing a sea. To be filled with navys at war, or a
 * minefield, or anything that could be at sea...
 */
public abstract class Sea extends Canvas {
  protected int eastwest = 20, northsouth = 13;
  protected Color deep[];
  protected SeaSpot sea[][];

  /**
   * @return The witdh of the sea, in cells.
   */
  public int getWidth() { return eastwest; }
  /**
   * @return The height of the sea, in cells.
   */
  public int getHeight() { return northsouth; }

  /**
   * Construct a new sea.
   */
  public Sea() {
    super();
    // Initialize helper variables
    deep = new Color[4];
    deep[0] = new Color(0, 127, 0);
    deep[1] = new Color(80, 127, 255);
    deep[2] = new Color(30, 64, 250);
    deep[3] = new Color(5, 10, 200);

    // Initialize the sea
    String d_st = "0011212333011211223322221223223332233333012333333311223333232233323323";
    sea = new SeaSpot[eastwest][];
    int x, y;
    for(x = 0; x<eastwest; x++) {
      sea[x] = new SeaSpot[northsouth];
      for(y = 0; y<northsouth; y++)
	sea[x][y] = new SeaSpot(d_st.charAt((x/2)+(y/2)*10) - '0');
    }
  }
  
  /**
   * Draw one of the hexagons making the sea.
   */
  void drawHex(Graphics grp, Coord pos) {
    int x0 = pos.x0(), y0 = pos.y0();  // get center of pos
    int xposar[] = {x0-6, x0+6, x0+14, x0+6, x0-6, x0-14, x0-6};
    int yposar[] = {y0-12, y0-12, y0, y0+12, y0+12, y0, y0-12};
    Polygon p = new Polygon(xposar, yposar, 7);

    grp.setColor(deep[sea[pos.xpos()][pos.ypos()].d()]);
    grp.fillPolygon(p);
    grp.setColor(Color.black);
    grp.drawPolygon(p);
  }

  public void paint(Graphics grp) {
    int x, y;
    for(x = 0; x<eastwest; x++) 
      for(y = 0; y<northsouth; y++)
        drawHex(grp, new Coord(x, y));  
  }
 
  public Dimension minimumSize() {
    Coord c = new Coord(eastwest-1, northsouth-1);
    return new Dimension(c.x0()+14, c.y0()+10);
  }
  public Dimension preferredSize() {
    return minimumSize();
  }
  public boolean mouseDown(Event evt, int x, int y) {
    mouseDown(Coord.getPos(x, y), evt);
    return true;
  }
  public boolean mouseUp(Event evt, int x, int y) {
    mouseUp(Coord.getPos(x, y), evt);
    return true;
  }

  /**
   * In this class, to touch a pos means nothing, but in subclasses...
   */
  public abstract void mouseDown(Coord pos, Event evt);
  public abstract void mouseUp(Coord pos, Event evt);
};

// end of sea.Sea
