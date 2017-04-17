package game.GameObject.Object;

import game.Holder;
import game.resources.Assets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * Created by hyso on 23/02/2017.
 */

public class Portal extends Object {
    public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;
    private int counter;
    private int rotate;
    public Portal otherPortal;
    public boolean used;

    //holder , this portal location, other portal location
    public Portal(Holder holder, double x, double y) {

        super(holder, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        destroyable = false;
        used = false;
        counter = 500;
    }


    @Override
    public void update() {
        rotate += 5;
        portalCollision(0, 0);
        if (counter<=0)
            dead = true;
        counter--;
    }

    @Override
    public void render(Graphics g) {
        double rotationRequired = Math.toRadians(rotate);
        double locationX = 32 / 2;
        double locationY = 32 / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

// Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(Assets.portal, null),
                (int) (posX - this.holder.getGame().getCamera().getxOffset())
                , (int) (posY - this.holder.getGame().getCamera().getyOffset()), null);


        //g.drawImage(Assets.portal, (int) (posX - this.holder.getGame().getCamera().getxOffset()),
        //     (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }

    public void setPair(Portal other){
        otherPortal = other;
    }

    public String toString() {

        return "This X,Y : " + posX + " " + posY +
                "Other X,Y : " + otherPortal.posX + otherPortal.posY;
    }
}