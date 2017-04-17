package game.GameObject.Object.item;

import game.GameObject.GameObject;
import game.GameObject.Object.Blast.Blast;
import game.Holder;

import java.awt.*;

/**
 * Created by Jack on 17/11/2016.
 */
public class Item extends GameObject {

    public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;
    protected int counter;
    protected Blast blast;

    public Item(Holder holder, double x, double y,Blast blast) {
        super(holder, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.blast = blast;
        counter = 240;

    }

    @Override
    public void update() {

        itemCollision(0,0);
        if (counter <=0)
            dead = true;
        counter--;
    }

    @Override
    public void render(Graphics g) {

    }

    public Blast getBlast(){
        return blast;
    }
}
