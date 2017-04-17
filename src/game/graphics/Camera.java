package game.graphics;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import game.Constants;
import game.GameObject.GameObject;
import game.Holder;
import game.tiles.Tile;

/**
 * Created by Jack on 19/11/2016.
 */
public class Camera {

    private double xOffset;
    private double yOffset;

    private Holder holder;

    public Camera(Holder holder, double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.holder = holder;

    }

    public void centerGameObject(GameObject object) {

        //the camera will focus on gameobject
        xOffset = object.getPosX() - Constants.widthRatio / 2 + object.getWidth() / 2;
        yOffset = object.getPosY() - Constants.heightRatio / 2 + object.getHeight() / 2;
        checkCamera();

    }

    public void checkCamera() {
        //return 0 if negative(showing the blank space)
        // or lager than game width/height
        /*
        if (xOffset < 0) {
            xOffset = 0;
        } else if (xOffset > holder.getWorld().getWidth() * Tile.WIDTH - holder.getWidth()) {
            xOffset = holder.getWorld().getWidth() * Tile.WIDTH - holder.getWidth();
        }
        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset > holder.getWorld().getHeight() * Tile.WIDTH - holder.getHeight()) {
            yOffset = holder.getWorld().getHeight() * Tile.WIDTH - holder.getHeight();
        }
        */

        if (xOffset < 0) {
            xOffset = 0;
        } else if (xOffset > holder.getWorld().getWidth() * Tile.WIDTH - Constants.widthRatio) {
            xOffset = holder.getWorld().getWidth() * Tile.WIDTH - Constants.widthRatio;
        }
        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset > holder.getWorld().getHeight() * Tile.HEIGHT -Constants.heightRatio) {
            yOffset = holder.getWorld().getHeight() * Tile.HEIGHT - Constants.heightRatio;
        }
    }

    //move the camera by how many pixels

    public void move(double x, double y) {

        xOffset += x;
        yOffset += y;
        checkCamera();

    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }
}
