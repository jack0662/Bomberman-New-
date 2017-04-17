package game.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jack on 17/11/2016.
 */
public class Tile {

    //Store tiles in Array by ID
    public static Tile[] tiles = new Tile[512];
    public static Tile tileFloor = new TileFloor(0);
    public static Tile tileWall = new TileWall(1);


    public static final int WIDTH = 32, HEIGHT = 32;
    public BufferedImage image;
    public final int id;

    public Tile(BufferedImage image, int id) {

        this.image = image;
        this.id = id;

        tiles[id] = this;

    }

    public boolean isWalkable(){
        return true;
    }

    public void update() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(image, x, y, WIDTH, HEIGHT, null);

    }

    public String toString(){
        return this.id+"";
    }
}
