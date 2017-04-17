package game;

import game.Controller.Controller;
import game.graphics.Camera;

/**
 * Created by hyso on 18/11/2016.
 */
//hold holder and world together

public class Holder {

    private Game game;
    private World world;
    private Collision collision;
    private int scale;

    public Holder(Game game, int scale) {

        this.game = game;
        this.scale = scale;

    }

    public Game getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getWidth() {
        return game.getWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public int getScale() {
        return scale;
    }

    public Controller getCtrl() {
        return game.getKeyboard();
    }

    public Camera getCamera() {
        return game.getCamera();
    }


}
