package game;

/**
 * Created by Jack on 19/11/2016.
 */
public class Collision {

    private Holder holder;

    public Collision(Holder holder){

        this.holder = holder;
    }

    public boolean isCollision(int x, int y){

        return holder.getWorld().getTile(x, y).isWalkable();
    }
}
