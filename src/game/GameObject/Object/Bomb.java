package game.GameObject.Object;

import game.Constants;
import game.GameObject.GameObject;
import game.GameObject.Individual.Bomberman;
import game.GameObject.Individual.Individual;
import game.GameObject.Object.Blast.*;
import game.Holder;
import game.SoundManager;
import game.resources.Assets;
import game.tiles.Tile;

import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jack on 17/11/2016.
 */
public class Bomb extends Object {

    private int counter;
    private Holder holder;
    private int blastRadius;
    public static int bombOnMap = 0;
    private Individual deployer;
    private boolean upBlast, rightBlast, leftBlast, downBlast;

    public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;

    public Bomb(Holder holder, double x, double y, Individual deployer, int radius) {
        super(holder, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // 1857 have 180
        counter = 180; // 5 sec (180/60)
        blastRadius = radius; //Constants.bombBlastRadius;
        this.holder = holder;
        destroyable = false;
        upBlast = rightBlast = leftBlast = downBlast = true;
        bombOnMap++;
        this.deployer = deployer;

    }


    @Override
    public void update() {

        counter--;
        if (counter <= 0)
            dead = true;

        if (dead) {
            createBlast();
        }
    }

    public void createBlast(){

        SoundManager.play(SoundManager.explode);
        int r = 1;

        int xTile = (int) posX / Tile.WIDTH;
        int yTile = (int) posY / Tile.HEIGHT;
        //System.out.println(xTile + " " + yTile + "asa");
        ArrayList<Blast> blasts = new ArrayList<>();
        blasts.add(new BlastCenter(holder, xTile, yTile, deployer));
        //System.out.println(holder.getWorld().getTile((int) posX - 32, (int) posY));
        //System.out.println(posX - 32 + "   " + posY);

        for (int i = 0; i < blastRadius; i++) {
            if (holder.getWorld().getTile(xTile - r, yTile) == Tile.tileFloor && leftBlast) {// Left
                if (holder.getWorld().isGameObjectOrTilesNoBomb(xTile - r, yTile))
                    leftBlast = false;
                blasts.add(new BlastLeft(holder, xTile - r, yTile, deployer));

            } else
                leftBlast = false;

            if (holder.getWorld().getTile(xTile + r, yTile) == Tile.tileFloor && rightBlast) {// Right
                blasts.add(new BlastRight(holder, xTile + r, yTile, deployer));
                if (holder.getWorld().isGameObjectOrTilesNoBomb(xTile + r, yTile))
                    rightBlast = false;
            } else
                rightBlast = false;

            //System.out.println(holder.getWorld().getTile(xTile, yTile - r) + "xxxx");
            if (holder.getWorld().getTile(xTile, yTile - r) == Tile.tileFloor && upBlast) { // Up
                blasts.add(new BlastUp(holder, xTile, yTile - r, deployer));
                //System.out.println(xTile + "  y-1" + yTile);
                if (holder.getWorld().isGameObjectOrTilesNoBomb(xTile, yTile - r))
                    upBlast = false;

            } else
                upBlast = false;

            if (holder.getWorld().getTile(xTile, yTile + r) == Tile.tileFloor && downBlast) { // Down
                blasts.add(new BlastDown(holder, xTile, yTile + r, deployer));
                if (holder.getWorld().isGameObjectOrTilesNoBomb(xTile, yTile + r))
                    downBlast = false;

            } else
                downBlast = false;


            r += 1;
        }
        holder.getWorld().getGameObjectManager().add(blasts);
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.bomb, (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }

    public int getBlastRadius() {

        return blastRadius;
    }

    public Individual getDeployer() {
        return deployer;
    }
}