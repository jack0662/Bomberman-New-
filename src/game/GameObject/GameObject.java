package game.GameObject;

import game.GameObject.Individual.Ballons;
import game.GameObject.Individual.Bomberman;
import game.GameObject.Individual.Monster;
import game.GameObject.Object.Blast.Blast;
import game.GameObject.Object.Bomb;
import game.GameObject.Object.Brick;
import game.GameObject.Object.Portal;
import game.GameObject.Object.item.*;
import game.Holder;
import game.SoundManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Jack on 17/11/2016.
 */
public abstract class GameObject {

    protected Holder holder;
    public double posX, posY;
    protected int width, height;
    public boolean dead;
    protected Rectangle bounds; // used for isCollision
    protected boolean destroyable;

    public GameObject(Holder game, double posX, double posY, int width, int height) {

        this.holder = game;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(0, 0, width, height);
        destroyable = true;
        dead = false;

    }

    //called in bomberman(creature), if the next move is collide with other object return true
    //if any object touched blast it will be dead
    //

    /**
     * public boolean isCollision(double x, double y) {
     * <p>
     * for (GameObject object : holder.getWorld().getGameObjectManager().getGameObjects()) {
     * <p>
     * if (object.equals(this))
     * continue;
     * <p>
     * if (object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {
     * <p>
     * if (object instanceof Item) {
     * return false;
     * }
     * if (object instanceof Portal) {
     * return false;
     * }
     * if (object instanceof Bomb) {
     * if (((Bomb) object).justDeploy) {
     * return false;
     * }
     * }
     * if (object instanceof Blast) {
     * return false;
     * }
     * return true;
     * <p>
     * } else if (object instanceof Bomb) {
     * ((Bomb) object).justDeploy = false;
     * }
     * }
     * <p>
     * return false;
     * }
     */


    public void itemCollision(double x, double y) {

        for (GameObject object : holder.getWorld().getGameObjectManager().getGameObjects()) {

            if (object.equals(this))
                continue;

            if (this instanceof SpeedBoost && object instanceof Bomberman
                    && object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {

                SoundManager.play(SoundManager.item);
                this.dead = true;
                double speed = ((Bomberman) object).getSpeed() + 0.25;
                if (speed > 3) // 3 is Max speed
                    speed = 3;
                ((Bomberman) object).setSpeed(speed);

            } else if (this instanceof BombBoost && object instanceof Bomberman
                    && object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {

                SoundManager.play(SoundManager.item);
                this.dead = true;
                int bombLimit = ((Bomberman) object).getBombLimit() + 1;
                if (bombLimit > 4) // 3 is Max speed
                    bombLimit = 4;
                ((Bomberman) object).setBombLimit(bombLimit);

            } else if (this instanceof BombRangeBoost && object instanceof Bomberman
                    && object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {

                SoundManager.play(SoundManager.item);
                this.dead = true;
                int blastR = ((Bomberman) object).getBlastRadius() + 1;
                if (blastR > 3) // 3 is Max speed
                    blastR = 3;
                ((Bomberman) object).setBlastRadius(blastR);

            }else if (this instanceof LifeBoost && object instanceof Bomberman
                    && object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {

                SoundManager.play(SoundManager.item);
                this.dead = true;
                int live = ((Bomberman) object).getLive() + 1;
                ((Bomberman) object).setLive(live);

            }
        }
    }

    public boolean bombDeployCollision(double x, double y) {

        for (GameObject object : holder.getWorld().getGameObjectManager().getGameObjects()) {

            if (object.equals(this))
                continue;

            if (object instanceof Bomb && object.collisionBox(0, 0).intersects(this.collisionBox(x, y)))
                return true;
        }
        return false;
    }


    //xLocation, yLocation, width, height
    public Rectangle collisionBox(double x, double y) {

        return new Rectangle((int) (posX + bounds.x + x), (int) (posY + bounds.y + y), bounds.width, bounds.height);
    }

    public Rectangle collisionBox(double x, double y, int width, int height) {

        return new Rectangle((int) (x), (int) (y), width, height);
    }

    public boolean portalCollision(double x, double y) {

        Portal portal = (Portal) this;
        for (GameObject object : holder.getWorld().getGameObjectManager().getGameObjects()) {

            if (object.equals(this))
                continue;
            if (this instanceof Portal) {
                //System.out.println(this);
            }
            if (object instanceof Bomberman && !portal.used &&
                    object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {


                object.posX = portal.otherPortal.getPosX();
                object.posY = portal.otherPortal.getPosY();
                portal.otherPortal.used = true;
                portal.used = true;

                portal.otherPortal.dead = true;
                return true;
            }
        }
        return false;
    }


    public abstract void update();

    public abstract void render(Graphics g);

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public void setDestroyable(boolean bool) {
        destroyable = bool;
    }
}
