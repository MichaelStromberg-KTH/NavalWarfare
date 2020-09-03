// naval.OnePlayer
package naval;

import java.applet.Applet;
import java.awt.*;
import java.lang.Integer;
import java.lang.String;

import sea.Coord;
import naval.Naval;
import naval.SoundLib;

/**
 * This is the main user interface for a one-player game. There should be 
 * another class for multiplayer game, most of the other classes should
 * be the same...  But maybe we will have to make a separate version of
 * naval as well.
 * @author Rasmus Kaj <kaj@it.kth.se>
 * @see naval.Naval
 */
public class OnePlayer extends Applet {
  Naval nav;
  Label status;
  SoundLib sounds;

  public void init() {
    setBackground(Color.white);
    System.out.println("Wellcome to the battlefield!");
    GridBagLayout grid = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(grid);
    c.fill = GridBagConstraints.NONE;
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.gridwidth = GridBagConstraints.REMAINDER;

    sounds = new SoundLib(this);

    status = new Label("Wellcome to the BattleField");
    nav = new Naval(status, sounds, this);
    grid.setConstraints(nav, c);
    add(nav);
    c.fill = GridBagConstraints.BOTH;
    c.gridheight = GridBagConstraints.REMAINDER;
    grid.setConstraints(status, c);
    add(status);
  }
}

// end of naval.OnePlayer
