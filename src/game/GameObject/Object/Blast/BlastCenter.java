package game.GameObject.Object.Blast;

import game.Constants;
import game.GameObject.Individual.Individual;
import game.Holder;
import game.resources.Assets;

import java.awt.*;

/**
 * Created by Jack on 25/1/2017.
 */

public class BlastCenter extends Blast {

    public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;

    public BlastCenter(Holder holder, int x, int y, Individual owner) {
        super(holder, x, y,owner);

    }


    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.blastCenter, (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }

}