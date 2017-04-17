package game.GameObject.Object.item;

import game.GameObject.Object.Blast.Blast;
import game.Holder;
import game.resources.Assets;

import java.awt.*;

/**
 * Created by Jack on 19/3/2017.
 */
public class LifeBoost extends Item {


    public LifeBoost(Holder holder, double x, double y, Blast blast) {
        super(holder, x, y,  blast);
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.lifeBoost, (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }
}
