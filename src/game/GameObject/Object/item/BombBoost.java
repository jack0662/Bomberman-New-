package game.GameObject.Object.item;

import game.GameObject.GameObject;
import game.GameObject.Object.Blast.Blast;
import game.Holder;
import game.resources.Assets;

import java.awt.*;

/**
 * Created by hyso on 07/02/2017.
 */
public class BombBoost extends Item {


    public BombBoost(Holder holder, double x, double y, Blast blast) {
        super(holder, x, y,  blast);
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.bombBoost, (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }
}

