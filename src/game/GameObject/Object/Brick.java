package game.GameObject.Object;

import game.GameObject.GameObject;
import game.Holder;
import game.resources.Assets;

import java.awt.*;
import java.lang.*;

/**
 * Created by Jack on 14/1/2017.
 */
public class Brick extends Object {
    public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;

    public Brick(Holder holder, double x, double y) {
        super(holder, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.brick, (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }
}
