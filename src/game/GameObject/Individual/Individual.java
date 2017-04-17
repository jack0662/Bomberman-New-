package game.GameObject.Individual;

import game.GameObject.GameObject;
import game.Holder;
import game.graphics.Animation;
import game.tiles.Tile;

import java.awt.image.BufferedImage;

/**
 * Created by Jack on 17/11/2016.
 */
public abstract class Individual extends GameObject {

    //int health;
    public static final double DEFAULT_SPEED = 1; //
    protected double speed, animationSpeed;
    protected double xMove, yMove;
    protected int lives;
    protected Animation upAnimation, downAnimation, leftAnimation, rightAnimation, standAnimation;

    public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;

    public Individual(Holder holder, double x, double y, int width, int height) {

        super(holder, x, y, width, height);
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        lives = 1;
    }

    public void updateAnimation() {

    }

    public BufferedImage getCurrentAnimation() {
        setAnimationSpeed();
        if (xMove > 0)
            return rightAnimation.getImage(animationSpeed);
        else if (xMove < 0)
            return leftAnimation.getImage(animationSpeed);
        else if (yMove > 0)
            return downAnimation.getImage(animationSpeed);
        else if (yMove < 0)
            return upAnimation.getImage(animationSpeed);
        else
            return standAnimation.getImage(animationSpeed);

    }

    public void setAnimationSpeed(){

        if (speed <=1)
            animationSpeed = 10;
        else if (speed >= 1.5 && speed < 2)
            animationSpeed = 8;
        else if (speed >= 2 && speed < 2.5)
            animationSpeed = 6;
        else if (speed >= 2.5 && speed < 3)
            animationSpeed = 5;
        else if (speed >= 3)
            animationSpeed = 4;
    }

    //check next move if collide with other gameobject
    public boolean isCollision(double x, double y) {
        return false;
    }


    public void move() {

        //check isCollision(tiles) with calculating the position for next move
        if (!isCollision(xMove, 0))
            moveX();
        //else
         //   adjustX(xMove);

        if (!isCollision(0, yMove))
            moveY();
        //else
         //   adjustY(yMove);
        //check isCollision(gameObject) with calculating the position for next move

    }

    public void moveX() {


        if (xMove > 0) {//right

            // find the nearest tiles (since Monster can stand in both 2 tiles)

            //convert position from pixel to tiles
            //check upper right and lower right of the bounding box first
            //System.out.println((posX + xMove + bounds.x + bounds.width) / Tile.WIDTH);
            //System.out.println(" bounds.x" + bounds.x + " bounds.width" + bounds.width);
            //upX the tiles trying to move into
            int upX = (int) ((posX + xMove + bounds.x + bounds.width) / Tile.WIDTH);
            int upY = (int) ((posY + bounds.y) / Tile.HEIGHT);
            int loY = (int) ((posY + bounds.y + bounds.height) / Tile.HEIGHT * 0.9999);
            //System.out.println("PosX" + posX + "PosY" + posY);
            //System.out.println(upX + "   " + upY + "   " + loY);
            boolean walkable = !isCollision(upX, upY) && !isCollision(upX, loY);
            //System.out.println("POSY % Tile.HEIGHT :        " + posY % Tile.HEIGHT);
            if (walkable) {
                posX += xMove;
            } else if (!isCollision(upX, upY) && posY % Tile.HEIGHT < Tile.HEIGHT / 5 * 2) {

                //System.out.println(Math.floor(posY / Tile.HEIGHT));
                posY = Math.floor(posY / Tile.HEIGHT) * Tile.HEIGHT;
                posX = upX * Tile.WIDTH - bounds.x - bounds.width;

            } else if (!isCollision(upX, loY) && posY % Tile.HEIGHT > Tile.HEIGHT / 5 * 3) {

                //System.out.println(Math.floor(posY / Tile.HEIGHT));
                posY = Math.ceil(posY / Tile.HEIGHT) * Tile.HEIGHT;
                posX = upX * Tile.WIDTH - bounds.x - bounds.width;

            } else {
                posX = upX * Tile.WIDTH - bounds.x - bounds.width;
            }


        } else if (xMove < 0) {//left

            int upX = (int) ((posX + xMove + bounds.x) / Tile.WIDTH); // the next tile
            int upY = (int) ((posY + bounds.y) / Tile.HEIGHT);
            int loY = (int) ((posY + bounds.y + bounds.height) / Tile.HEIGHT * 0.9999);
            if (!isCollision(upX, upY) && !isCollision(upX, loY)) {
                posX += xMove;
            } else if (!isCollision(upX, upY) && posY % Tile.HEIGHT < Tile.HEIGHT / 5 * 2) {
                //System.out.println("nearUp");
                //System.out.println(Math.floor(posY / Tile.HEIGHT));
                posY = Math.floor(posY / Tile.HEIGHT) * Tile.HEIGHT;
                posX = upX * Tile.WIDTH + Tile.WIDTH - bounds.x;

            } else if (!isCollision(upX, loY) && posY % Tile.HEIGHT > Tile.HEIGHT / 5 * 3) {
                //System.out.println("nearUp");
                //System.out.println(Math.floor(posY / Tile.HEIGHT));
                posY = Math.ceil(posY / Tile.HEIGHT) * Tile.HEIGHT;
                posX = upX * Tile.WIDTH + Tile.WIDTH - bounds.x;

            } else {
                posX = upX * Tile.WIDTH + Tile.WIDTH - bounds.x;
            }


        }
    }


    public void moveY() {

        if (yMove < 0) {//up
            int upY = (int) ((posY + yMove + bounds.y) / Tile.HEIGHT);
            int lX = (int) ((posX + bounds.x) / Tile.WIDTH);
            int rX = (int) ((posX + bounds.x + bounds.width) / Tile.WIDTH * 0.9999);

            //System.out.println(lX + "   " + upY + "   " + rX);
            if (!isCollision(lX, upY) && !isCollision(rX, upY)) {
                posY += yMove;
            } else if (!isCollision(lX, upY) && posX % Tile.WIDTH < Tile.WIDTH / 5 * 2) {
                //System.out.println(Math.floor(posY / Tile.HEIGHT));
                posX = Math.floor(posX / Tile.WIDTH) * Tile.WIDTH;
                posY = upY * Tile.HEIGHT + Tile.HEIGHT - bounds.y;

            } else if (!isCollision(rX, upY) && posX % Tile.WIDTH > Tile.WIDTH / 5 * 3) {
                //System.out.println(Math.floor(posY / Tile.HEIGHT));
                posX = Math.ceil(posX / Tile.WIDTH) * Tile.WIDTH;
                posY = upY * Tile.HEIGHT + Tile.HEIGHT - bounds.y;

            } else {

                posY = upY * Tile.HEIGHT + Tile.HEIGHT - bounds.y;
            }


        } else if (yMove > 0) {//down
            //System.out.println(bounds.height +"          " + bounds.y);
            int downY = (int) ((posY + yMove + bounds.height+ bounds.y) / Tile.HEIGHT);
            int lX = (int) ((posX + bounds.x) / Tile.WIDTH);
            int rX = (int) ((posX + bounds.x + bounds.width) / Tile.WIDTH * 0.9999);

            if (!isCollision(lX, downY) && !isCollision(rX, downY)) {
                posY += yMove;
            } else if (!isCollision(lX, downY) && posX % Tile.WIDTH < Tile.WIDTH / 5 * 2) {
                //System.out.println(Math.floor(posY / Tile.HEIGHT));
                posX = Math.floor(posX / Tile.WIDTH) * Tile.WIDTH;
                posY = downY * Tile.HEIGHT - bounds.y - bounds.height;

            } else if (!isCollision(rX, downY) && posX % Tile.WIDTH > Tile.WIDTH / 5 * 3) {
                //System.out.println(Math.floor(posY / Tile.HEIGHT));
                posX = Math.ceil(posX / Tile.WIDTH) * Tile.WIDTH;
                posY = downY * Tile.HEIGHT - bounds.y - bounds.height;

            } else {

                posY = downY * Tile.HEIGHT - bounds.y - bounds.height;
            }
        }

    }

    public void adjustX(double x) {
        if (x > 0) {

            int upX = (int) ((posX + xMove + bounds.x + bounds.width) / Tile.WIDTH);
            posX = upX * Tile.WIDTH - bounds.x - bounds.width;

        } else {

            int upX = (int) ((posX + xMove + bounds.x) / Tile.WIDTH); // the next tile
            posX = upX * Tile.WIDTH + Tile.WIDTH - bounds.x;
        }
    }

    public void adjustY(double y) {
        if (y < 0) {

            int upY = (int) ((posY + yMove + bounds.y) / Tile.HEIGHT);
            posY = upY * Tile.HEIGHT + Tile.HEIGHT - bounds.y;

        } else {

            int upY = (int) ((posY + yMove + bounds.height + bounds.y) / Tile.HEIGHT);
            posY = upY * Tile.HEIGHT - bounds.y - bounds.height;
        }
    }


    protected boolean isCollision(int x, int y) {

        if (!holder.getWorld().getTile(x, y).isWalkable()) {
            return true;
        }
        return false;
    }

    public int getLive() {
        return lives;
    }

    public void setLive(int lives) {
        this.lives = lives;
    }


}
