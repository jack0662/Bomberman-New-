package game.GameObject.Object.item;

import game.GameObject.GameObject;
import game.GameObject.Object.Blast.Blast;
import game.Holder;
import game.resources.Assets;

import java.awt.*;

/**
 * Created by hyso on 07/02/2017.
 */
public class SpeedBoost extends Item {


    public SpeedBoost(Holder holder, double x, double y,Blast blast) {
        super(holder, x, y,  blast);
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.speedBoost, (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }
}
