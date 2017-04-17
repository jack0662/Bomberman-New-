package game.GameObject.Object.Blast;

import game.GameObject.GameObject;
import game.GameObject.Individual.*;
import game.GameObject.Object.Bomb;
import game.GameObject.Object.Brick;
import game.GameObject.Object.Object;
import game.GameObject.Object.Portal;
import game.GameObject.Object.item.*;
import game.Holder;
import game.SoundManager;
import game.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Jack on 25/1/2017.
 */
public abstract class Blast extends Object {

    protected int counter;
    private Individual owner;
    private final int blastscaleW , blastscaleH;
    private GameObject object; //this blast bombed someone? if yes save it prevent bomb again

    public static final int DEFAULT_WIDTH = Tile.WIDTH, DEFAULT_HEIGHT = Tile.HEIGHT;

    public Blast(Holder holder, double x, double y, Individual owner) {
        super(holder, x * Tile.WIDTH, y * Tile.HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        blastscaleW = (int) (DEFAULT_WIDTH * 1.2 - DEFAULT_WIDTH);
        blastscaleH = (int) (DEFAULT_HEIGHT * 1.1 - DEFAULT_HEIGHT);
        counter = 90;
        object = null;
        this.setOwner(owner);

    }

    @Override

    public void update() {

        blastCollision(0, 0);
        if (counter <= 0)
            dead = true;
        counter--;
    }

    public void blastCollision(double x, double y) {

        for (GameObject object : holder.getWorld().getGameObjectManager().getGameObjects()) {

            if (object.equals(this) || !object.isDestroyable())
                continue;

            if (object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {

                // any object touched blast will be dead
                if (this instanceof Blast && object instanceof Item)
                    if (((Item) object).getBlast().equals(this))
                        continue;

                if (this instanceof Blast && !(object instanceof Blast)) {

                    //random item for Monster
                    Blast me = this;
                    if (object instanceof Individual && me.getObject() == null) {

                        Rectangle c = object.collisionBox(object.getPosX() + blastscaleW, object.getPosY() + blastscaleH,
                                Tile.WIDTH - blastscaleW*2, Tile.HEIGHT - blastscaleH*2);
                        //System.out.println(c + "AA   " + this.collisionBox(0,0));
                        if (!c.intersects(this.collisionBox(x, y)))
                            continue;
                        else {
                            int live = ((Individual) object).getLive() - 1;
                            if (object instanceof Bomberman)
                                SoundManager.play(SoundManager.liveDown);

                            ((Individual) object).setLive(live);
                            me.setObject(object);

                            if (live < 1)
                                object.dead = true;
                        }
                    }

                    if (object instanceof Bomb){

                        ((Bomb) object).createBlast();
                    }
                    if (!(object instanceof Bomberman)) {

                        object.dead = true;
                        if (object instanceof Monster && this.getOwner() instanceof Bomberman) {
                            Bomberman b = (Bomberman) this.getOwner();
                            int score = holder.getWorld().getGameObjectManager().getScore();

                            if (object instanceof Dall)
                                score += 500;
                            else if (object instanceof Smart)
                                score += 200;
                            else if (object instanceof Ballons)
                                score += 100;

                            //System.out.println(score + "ACX");
                            holder.getWorld().getGameObjectManager().setScore(score);
                        }
                    }


                    if (object instanceof Brick && Math.random() > 0.6) {


                        double ran = Math.random();
                        //ran = 0.9;

                        if (ran < 1) {
                            int[] pos = holder.getWorld().randomPos(((Brick) object).posX, ((Brick) object).posY);
                            Portal p1 = new Portal(holder, ((Brick) object).posX, ((Brick) object).posY);
                            Portal p2 = new Portal(holder, pos[0], pos[1]);
                            p1.setPair(p2);
                            p2.setPair(p1);
                            holder.getWorld().getGameObjectManager().add(new ArrayList<GameObject>(Arrays.asList(p1, p2)));
                        } else if (ran < 0.6)
                            holder.getWorld().getGameObjectManager().add(new SpeedBoost(holder, ((Brick) object).posX, ((Brick) object).posY, this));
                        else if (ran < 0.7)
                            holder.getWorld().getGameObjectManager().add(new BombBoost(holder, ((Brick) object).posX, ((Brick) object).posY, this));
                        else if (ran < 0.95)
                            holder.getWorld().getGameObjectManager().add(new BombRangeBoost(holder, ((Brick) object).posX, ((Brick) object).posY, this));
                        else
                            holder.getWorld().getGameObjectManager().add(new LifeBoost(holder, ((Brick) object).posX, ((Brick) object).posY, this));
                    }
                }
            }
        }
    }


    public GameObject getObject() {
        return object;
    }

    public void setObject(GameObject object) {
        this.object = object;
    }

    public Individual getOwner() {
        return owner;
    }

    public void setOwner(Individual owner) {
        this.owner = owner;
    }

}