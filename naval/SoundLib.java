// naval.SoundLib
package naval;

import java.applet.*;
import java.net.*;

/**
 * SoundLib keeps track of all the various audio clips in Naval Warefare,
 * and plays any of them by request.
 * @author Rasmus Kaj <kaj@it.kth.se>
 */
public class SoundLib {
  public final static int MISS = 0;
  public final static int YOUHITME = 1;
  public final static int IHITYOU = 2;
  public final static int YOUSANKME = 3;
  public final static int ISANKYOU = 4;
  public final static int YOUWIN = 5;
  public final static int IWIN = 6;

  protected final static int N_SOUNDS = 7;
  protected AudioClip sound[];
  
  /**
   * Create a SoundLib and load the sounds via the applet.
   * @param app the Applet that will do the loading
   */
  public SoundLib(Applet app) {
    sound = new AudioClip[N_SOUNDS];
    
    try {
      URL soundbase = new URL(app.getDocumentBase(), "sound/");
      sound[MISS] = app.getAudioClip(soundbase, "fire.au");
      sound[YOUHITME] = app.getAudioClip(soundbase, "pang.au");
      sound[IHITYOU] = app.getAudioClip(soundbase, "storpang.au");
      sound[YOUSANKME] = app.getAudioClip(soundbase, "yousankmybattleship.au");
      sound[ISANKYOU] = app.getAudioClip(soundbase, "yeow.au");
      sound[YOUWIN] = app.getAudioClip(soundbase, "gotcha.au");
      sound[IWIN] = app.getAudioClip(soundbase, "thatshard.au");
    } catch (Exception e) {
      System.out.println("Ljudet sket sig.");
    }
  }

  /**
   * Play a sound defined by it's id.
   */
  public void play(int sound_id) {
    System.out.println("Playing the sound " + sound_id);
    sound[sound_id].play();
  }

}

// end of naval.SoundLib
