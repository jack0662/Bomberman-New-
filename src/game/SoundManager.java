package game;

/**
 * Created by Jack on 20/3/2017.
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {

    static int nBullet = 0;
    static boolean walking = false;

    // this may need modifying
    final static String path = "resources/sounds/";

    // note: having too many clips open may cause
    // "LineUnavailableException: No Free Voices"
    public final static Clip[] bullets = new Clip[15];

    public final static Clip walking1 = getClip("Bomberman SFX (1)");
    public final static Clip walking2 = getClip("Bomberman SFX (2)");
    public final static Clip deployBomb = getClip("Bomberman SFX (3)");
    public final static Clip item = getClip("Bomberman SFX (4)");
    public final static Clip liveDown = getClip("Bomberman SFX (5)");
    public final static Clip gameOver = getClip("Bomberman SFX (6)");
    public final static Clip explode = getClip("bomb");


    public final static Clip[] clips = {walking1, walking2, liveDown, item, deployBomb,
            gameOver,explode};


    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 20; i++) {
            //fire();
            Thread.sleep(100);
        }
        for (Clip clip : clips) {
            play(clip);
            Thread.sleep(1000);
        }
    }

    // methods which do not modify any fields

    public static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    private static Clip getClip(String filename) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
                    + filename + ".wav"));
            clip.open(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }

    // methods which modify (static) fields

    public static void fire() {
        // fire the n-th bullet and increment the index
        Clip clip = bullets[nBullet];
        clip.setFramePosition(0);
        clip.start();
        nBullet = (nBullet + 1) % bullets.length;
    }

    public static void startWalking() {
        if (!walking) {
            walking1.loop(10);
            walking = true;
        }
    }

    public static void stopWalking() {
        walking1.loop(0);
        walking = false;
    }

    public static void startManager() {
        return;
    }

    // Custom methods playing a particular sound
    // Please add your own methods below


    public static void setLiveDown() {
        play(liveDown);
    }

    public static void setLiveUp() {
        play(item);
    }

    public static void gameOver() {
        for (int i = 0; i < 3; i++)
            play(gameOver);
    }
}