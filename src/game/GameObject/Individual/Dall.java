package game.GameObject.Individual;

import game.Holder;
import game.graphics.Animation;
import game.resources.Assets;
import game.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Jack on 17/3/2017.
 */
public class Dall extends Smart {


    public static final int DISTANCE = 5;

    public Dall(Holder holder, double x, double y) {
        super(holder, x, y);

        correctPosition = true;
        speed = 1.3;

        leftAnimation = new Animation(Assets.leftIntelligence);
        rightAnimation = new Animation(Assets.rightIntelligence);

    }

    @Override
    public void update() {

        currentXTile = (int) posX / Tile.WIDTH;
        currentYTile = (int) posY / Tile.HEIGHT;
        //System.out.println(direction+" "+sameTileCounter);
        if (isSmartMove()) {
            smartMove();

        } else
            sillyMove();

        updateAnimation();
        getInput();
        move();
        System.out.println(direction);

        lastXTile = currentXTile;
        lastYTile = currentYTile;
        counter++;

    }

    @Override
    protected void smartMove() {

        if (posX % 32 < Tile.WIDTH * 2 / 11 && posY % 32 < Tile.HEIGHT * 2 / 11 && counter >= 100) {
            ArrayList<Integer> pos = escape();
            counter = 0;

            holder.getWorld().updateTraversable(this);
            //System.out.println("ZZZ    "+currentXTile+" "+currentYTile+" "+tx+" "+ty);

            path = holder.getWorld().AStarAlgorithm(currentXTile, currentYTile, pos.get(0), pos.get(1));
            System.out.println(path);
            if (path.isEmpty())
                correctPosition = true;
            else
                correctPosition = false; // whether is possible to turn (need posX % 32 < Tile.WIDTH * 2 / 11 && posY % 32 < Tile.HEIGHT)
        } else
            sillyMove();
        checkCorrectPosition();
    }





    protected void reverse() {
        switch (direction) {
            case LEFT:
                direction = Directions.RIGHT;
                break;
            case RIGHT:
                direction = Directions.LEFT;
                break;
            case UP:
                direction = Directions.DOWN;
                break;
            case DOWN:
                direction = Directions.UP;
                break;
        }
    }

    @Override
    protected boolean isSmartMove() {

        return getDisToBomberman() > 0 && getDisToBomberman() < DISTANCE;
        //return false;
    }


    protected ArrayList<Integer> escape() {
        int distance = 0;
        int x = 0;
        int y = 0;
        for (int i = -7; i < 7; i++) {
            for (int j = -7; j < 7; j++) {
                if (holder.getWorld().isBrickOrTilesOrBomb(currentXTile + i, currentYTile + j, this))
                    continue;
                if (getDisToBomberman(currentXTile + i, currentYTile + j) > distance) {
                    x = i + currentXTile;
                    y = j + currentYTile;
                    distance = getDisToBomberman(currentXTile + i, currentYTile + j);
                }
            }
        }
        ArrayList<Integer> position = new ArrayList<>();
        if (x != 0 && y != 0) {
            position.add(x);
            position.add(y);
        }

        return position;

    }



    @Override
    public BufferedImage getCurrentAnimation() {
        setAnimationSpeed();
        if (xMove > 0)
            return rightAnimation.getImage(animationSpeed);
        else if (xMove < 0)
            return leftAnimation.getImage(animationSpeed);
        return leftAnimation.getImage(animationSpeed);
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(getCurrentAnimation(), (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }
}
