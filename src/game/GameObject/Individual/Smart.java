package game.GameObject.Individual;


import game.Holder;
import game.Node;
import game.graphics.Animation;
import game.resources.Assets;
import game.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by Jack on 8/3/2017.
 */
//Basic AI Randomly move

public class Smart extends Ballons {
    private int tx, ty;
    protected boolean correctPosition;
    private Animation leftAnimationRage;
    private Animation rightAnimationRage;
    //1sec 100 loop
    public static final int DISTANCE = 7;

    public Smart(Holder holder, double x, double y) {
        super(holder, x, y);
        tx = ty = 0;
        speed = 1.5;
        correctPosition = true;
        leftAnimation = new Animation(Assets.leftSmart);
        rightAnimation = new Animation(Assets.rightSmart);

        leftAnimationRage = new Animation(Assets.leftSmartRage);
        rightAnimationRage = new Animation(Assets.rightSmartRage);
    }

    @Override
    public void updateAnimation() {

        leftAnimationRage.update();
        rightAnimationRage.update();
        leftAnimation.update();
        rightAnimation.update();


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

        lastXTile = currentXTile;
        lastYTile = currentYTile;
        counter++;
    }

    protected boolean isSmartMove() {

        return getDisToBomberman() > 0 && getDisToBomberman() < DISTANCE;
        //return false;
    }

    protected void smartMove() {

        if (counter >= 100 && posX % 32 < Tile.WIDTH * 2 / 11 && posY % 32 < Tile.HEIGHT * 2 / 11) {
            setTarget();
            //System.out.println(tx+" "+ty);
            holder.getWorld().updateTraversable(this);
            counter = 0;
            //System.out.println("ZZZ    "+currentXTile+" "+currentYTile+" "+tx+" "+ty);
            path = holder.getWorld().AStarAlgorithm(currentXTile, currentYTile, tx, ty);
            if (path.isEmpty())
                correctPosition = true;
            else
                correctPosition = false; // whether is possible to turn (need posX % 32 < Tile.WIDTH * 2 / 11 && posY % 32 < Tile.HEIGHT)


        } else
            sillyMove();
        checkCorrectPosition();
        //System.out.println(direction);

    }

    protected void checkCorrectPosition() {

        if (!correctPosition) {
            if (posX % 32 < Tile.WIDTH * 2 / 11 && posY % 32 < Tile.HEIGHT * 2 / 11 || path.isEmpty()) {
                correctPosition = true; // the object has to be < 2/11 collide so as to turn

            } else if (path.get(0).getX() <= currentXTile || path.get(0).getY() <= currentYTile) {
                if (posX % Tile.WIDTH > Tile.WIDTH * 2 / 11) {
                    direction = Directions.LEFT; // go to the top-left position since the position is store at top left
                } else if (posY % Tile.HEIGHT > Tile.HEIGHT * 2 / 11)
                    direction = Directions.UP;
            }
        } else
            followPath();

    }

    protected void setTarget() {
        if (holder.getWorld().getGameObjectManager().getBomberman() == null)
            return;
        tx = (int) holder.getWorld().getGameObjectManager().getBomberman().getPosX() / Tile.WIDTH;
        ty = (int) holder.getWorld().getGameObjectManager().getBomberman().getPosY() / Tile.HEIGHT;

    }

    protected int getDisToBomberman() {

        if (holder.getWorld().getGameObjectManager().getBomberman() == null)
            return -1;
        int tx = (int) holder.getWorld().getGameObjectManager().getBomberman().getPosX() / Tile.WIDTH;
        int ty = (int) holder.getWorld().getGameObjectManager().getBomberman().getPosY() / Tile.HEIGHT;
        Node target = new Node(holder, tx, ty);
        Node me = new Node(holder, currentXTile, currentYTile);

        int distance = holder.getWorld().getDistance(target, me);
        return distance;
    }

    protected int getDisToBomberman(int x, int y) {

        if (holder.getWorld().getGameObjectManager().getBomberman() == null)
            return -1;
        int tx = (int) holder.getWorld().getGameObjectManager().getBomberman().getPosX() / Tile.WIDTH;
        int ty = (int) holder.getWorld().getGameObjectManager().getBomberman().getPosY() / Tile.HEIGHT;
        Node target = new Node(holder, tx, ty);
        Node me = new Node(holder, x, y);
        int distance = holder.getWorld().getDistance(target, me);
        return distance;
    }

    /*
    protected void checkIsJunction() {

        if (direction == Directions.RIGHT) {

            if (posX % 32 < Tile.WIDTH * 2 / 11) {
                updateEastOrWest(0, 0);
            }
        } else if (direction == Directions.LEFT) {

            if (posX % 32 > Tile.WIDTH - Tile.WIDTH * 2 / 11) {
                updateEastOrWest(Tile.WIDTH, 0);
            }
        } else if (direction == Directions.UP) {

            if (posY % 32 < Tile.HEIGHT * 2 / 11) {
                updateNorthOrSouth(0, 0);
            }
        } else if (direction == Directions.DOWN) {
            if (posY % 32 > Tile.HEIGHT - Tile.HEIGHT * 2 / 11) {
                updateNorthOrSouth(0, 32);
            }
        }


    }
    */

    private void updateEastOrWest(double x, double y) {
        if (isPossibleDirection(Directions.UP, x, y) && isPossibleDirection(Directions.DOWN, x, y))
            updateProbability(49, 49, 1, 1);
        else if (isPossibleDirection(Directions.UP, x, y))
            updateProbability(80, 0, 10, 10);
        else if (isPossibleDirection(Directions.DOWN, x, y))
            updateProbability(0, 80, 10, 10);
    }

    private void updateNorthOrSouth(double x, double y) {
        if (isPossibleDirection(Directions.LEFT, x, y) && isPossibleDirection(Directions.RIGHT, x, y))
            updateProbability(1, 1, 49, 49);
        else if (isPossibleDirection(Directions.LEFT, x, y))
            updateProbability(10, 10, 80, 0);
        else if (isPossibleDirection(Directions.RIGHT, x, y))
            updateProbability(10, 10, 0, 80);
    }


    protected void updateProbability(int N, int S, int E, int W) {
        probability.put(Directions.UP, N);
        probability.put(Directions.DOWN, S);
        probability.put(Directions.LEFT, E);
        probability.put(Directions.RIGHT, W);
        updateDirection();
    }

    private void updateDirection() {

        double p = Math.random() * 100;
        double cumulativeProbability = 0.0;

        for (Map.Entry<Directions, Integer> entry : probability.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (p < cumulativeProbability) {
                direction = entry.getKey();
                //System.out.println(entry.getKey() + "   " + entry.getValue() + "  " + p);
                return;
            }

        }
    }

    protected boolean isPossibleDirection(Directions direction, double x, double y) {

        if (direction == Directions.UP)
            return !holder.getWorld().isTiles(posX + x, posY + y - Tile.HEIGHT);
        else if (direction == Directions.DOWN)
            return !holder.getWorld().isTiles(posX + x, posY + y + Tile.HEIGHT);
        else if (direction == Directions.LEFT)
            return !holder.getWorld().isTiles(posX + x - Tile.WIDTH, posY + y);
        else
            return !holder.getWorld().isTiles(posX + x + Tile.WIDTH, posY + y);
    }


    private void checkObstacles() {
        //System.out.println((int) ((posX + xMove) / 32) + "   " + (int) ((posY + yMove)) / 32);

        if (holder.getWorld().isTiles((int) (posX / 32 + nextX), (int) (posY / 32 + nextY))) {
            //System.out.println("dsdsdad dsd sds d");
            reverse();
        }

    }

    private void reverse() {

        //System.out.println("reversed");
        if (direction == Directions.DOWN) {
            updateProbability(100, 0, 0, 0);
        } else if (direction == Directions.UP) {
            updateProbability(0, 100, 0, 0);
        } else if (direction == Directions.RIGHT) {
            updateProbability(0, 0, 100, 0);
        } else if (direction == Directions.LEFT) {
            updateProbability(0, 0, 0, 100);
        }
    }

    @Override
    public BufferedImage getCurrentAnimation() {
        setAnimationSpeed();
        if (xMove > 0 && !isSmartMove())
            return rightAnimation.getImage(animationSpeed);
        else if (xMove < 0 && !isSmartMove())
            return leftAnimation.getImage(animationSpeed);
        else if (xMove > 0 && isSmartMove())
            return rightAnimationRage.getImage(animationSpeed);
        else if (isSmartMove())
            return leftAnimationRage.getImage(animationSpeed);
        return leftAnimation.getImage(animationSpeed);
    }


    @Override
    public void render(Graphics g) {


        g.drawImage(getCurrentAnimation(), (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);


        /*
        g.setColor(Color.pink);
        g.fillRect((int) (posX + bounds.x - holder.getCamera().getxOffset()),
                (int) (posY + bounds.y - holder.getCamera().getyOffset()),
                bounds.width, bounds.height);

                */


    }
}
