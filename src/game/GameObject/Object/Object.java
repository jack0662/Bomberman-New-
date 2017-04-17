package game.GameObject.Object;

import game.GameObject.GameObject;
import game.Holder;

/**
 * Created by hyso on 17/11/2016.
 */
public abstract class Object extends GameObject {

    public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;

    public Object(Holder holder, double x, double y, int width, int height) {
        super(holder, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
