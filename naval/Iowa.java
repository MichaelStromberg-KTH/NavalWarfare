// naval.Iowa
package naval;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.lang.*;

import sea.Coord;
import sea.Sea;
import naval.Ship;

/**
 * This battleship is modeled after the USS Wisconsin - an Iowa Class 
 * Battleship (assuming each hex is equal to 60m in height).
 */
public class Iowa extends Ship implements ImageObserver {

  /**
   * The images of the ship. Uses a Coord-style direction as
   * index.
   */
  Image image[];
  int img_width, img_height;

  /**
   * The sea this ship exists on. This variable is necessary 
   * to make the ship able to draw itself when the image is
   * actually loaded.
   */
  Sea sea;

  public Iowa(Coord pos, int direction, Applet app, Sea sea) {
    super("Iowa class Battleship", pos, direction);
    this.sea = sea;

    floating.addElement(pos);
    floating.addElement(pos.displaced(direction, 1));
    floating.addElement(pos.displaced(direction, 2));
    floating.addElement(pos.displaced(Coord.otherWay(direction), 1));
    floating.addElement(pos.displaced(Coord.otherWay(direction), 2));

    image = new Image[6];
    image[Coord.NORTH] = app.getImage(app.getDocumentBase(), 
				      "ships/Iowa-n.gif");
    image[Coord.SOUTH] = app.getImage(app.getDocumentBase(), 
				      "ships/Iowa-s.gif");
    image[Coord.NORTHEAST] = app.getImage(app.getDocumentBase(), 
				      "ships/Iowa-ne.gif");
    image[Coord.SOUTHEAST] = app.getImage(app.getDocumentBase(), 
				      "ships/Iowa-se.gif");
    image[Coord.NORTHWEST] = app.getImage(app.getDocumentBase(), 
				      "ships/Iowa-nw.gif");
    image[Coord.SOUTHWEST] = app.getImage(app.getDocumentBase(), 
				      "ships/Iowa-sw.gif");

    getImgInfo();
  }

  public void getImgInfo() {
    img_width  = image[direction].getWidth(this);
    img_height = image[direction].getHeight(this);
  }

    
  public boolean imageUpdate(Image img, int infoflags,
			     int x, int y, int width, int height) {
    if((infoflags & ALLBITS) != 0) {
      System.out.println("  ...time to draw!");
      draw(sea.getGraphics());
      return false;
    } 
    if((infoflags & WIDTH) != 0) {
      System.out.println("  ...the image width is known to be " + width);
      img_width = width;
    }
    if((infoflags & HEIGHT) != 0) {
      System.out.println("  ...the image height is known to be " + height);
      img_height = height;
    }
    return true;
  }

  public void draw(Graphics grp) {
    if((img_width != -1) && (img_height != -1))
      grp.drawImage(image[direction], pos.x0()-img_width/2,
		    pos.y0()-img_height/2, this);
    else
      getImgInfo();
    grp.setColor(black);
    grp.drawOval(pos.x0()-10, pos.y0()-10, 20, 20);    
  }    

};


// end of naval.Iowa
