package game.GameObject.Individual;


import game.GameObject.GameObject;
import game.GameObject.Object.Bomb;
import game.GameObject.Object.Brick;

import game.Holder;
import game.Node;
import game.resources.Assets;
import game.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack on 5/3/2017.
 */
public abstract class Monster extends Individual {

    protected Directions direction, correctDirection;
    protected int currentXTile, currentYTile, lastXTile, lastYTile,
            nextXTile, nextYTile, walkedTile, reverseTimes;
    protected ArrayList<Node> path;
    protected static final int TILELIMIT = 2;
    protected Map<Directions, Integer> probability;
    protected int counter;
    protected int sameTileCounter;
    protected int nextX, nextY;
    protected double lastX, lastY;
    protected ArrayList<Directions> possibleDirections;


    public Monster(Holder holder, double x, double y) {
        super(holder, x, y, Individual.DEFAULT_WIDTH, Individual.DEFAULT_HEIGHT);
        this.holder = holder;
        sameTileCounter = counter = 0;
        speed = 0.7;
        lastX = lastY = 0;
        direction = Directions.LEFT;
        correctDirection = direction;
        possibleDirections = new ArrayList<>();
        probability = new HashMap<>();
        counter = 0;
        path = new ArrayList<>();

    }



    @Override
    public boolean isCollision(double x, double y) {

        for (GameObject object : holder.getWorld().getGameObjectManager().getGameObjects()) {

            if (object.equals(this))
                continue;
            if (object instanceof Monster)
                continue;

            if (object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {

                if (object instanceof Brick || object instanceof Bomb) {
                    return true;
                }
                return false;

            }
        }

        return false;
    }

    protected void getInput() {

        xMove = 0;
        yMove = 0;

        switch (direction) {
            case UP:
                yMove = -speed;
                break;

            case DOWN:

                yMove = speed;
                break;

            case RIGHT:
                xMove = speed;
                break;

            case LEFT:
                xMove = -speed;
                break;
        }
    }

    //Test AI walking

    protected void getInput2() {

        xMove = 0;
        yMove = 0;

        if (holder.getGame().getKeyboard().getAction().up) {

            yMove = -speed;
            direction = Directions.UP;

        } else if (holder.getGame().getKeyboard().getAction().down) {

            yMove = speed;
            direction = Directions.DOWN;

        } else if (holder.getGame().getKeyboard().getAction().left) {

            xMove = -speed;
            direction = Directions.LEFT;

        } else if (holder.getGame().getKeyboard().getAction().right) {

            xMove = speed;
            direction = Directions.RIGHT;

        }

    }

    //End Test

    protected boolean isCentered() {

        if (direction == Directions.LEFT || direction == Directions.RIGHT)

            return posX % 32 < Tile.WIDTH * 2 / 11;

        else if (direction == Directions.UP || direction == Directions.DOWN)
            return posY % 32 < Tile.HEIGHT * 2 / 11;
        return false;
    }




    protected void followPath() {

        if (!path.isEmpty()) {
            Node targetNode = path.get(0);
            //System.out.println(targetNode);
            isJunction(0,0);

            if (currentXTile == targetNode.getX() && currentYTile == targetNode.getY() && checkCanTurn(targetNode.getNextDirection()) && isCentered()) {

                path.remove(0);
            } else {

                if (currentXTile > targetNode.getX() && currentYTile == targetNode.getY())
                    correctDirection = Directions.LEFT;
                else if (currentXTile < targetNode.getX() && currentYTile == targetNode.getY())
                    correctDirection = Directions.RIGHT;
                else if (currentXTile == targetNode.getX() && currentYTile > targetNode.getY())
                    correctDirection = Directions.UP;
                else if (currentXTile == targetNode.getX() && currentYTile < targetNode.getY())
                    correctDirection = Directions.DOWN;
                //else if(currentXTile == targetNode.getX() && currentYTile == targetNode.getY())
                //correctDirection = direction;
                direction = correctDirection;
            }

        }

    }

    @Override
    public void setAnimationSpeed(){

        if (speed <=1)
            animationSpeed = 12;
        else if (speed > 1 && speed <= 1.5)
            animationSpeed = 10;
        else if (speed > 1.5 && speed <= 2)
            animationSpeed = 10;
        else if (speed > 2 && speed <= 2.5)
            animationSpeed = 8;
        else if (speed > 2.5 && speed <= 3)
            animationSpeed = 6;
        else if (speed > 3)
            animationSpeed = 4;
    }


    protected boolean isPossibleDirection(Directions direction) {


        int x = nextX;
        int y = nextY;
        switch (direction) {
            case UP:
                y = nextY - 1;
                //System.out.println(" UP Possible " + !holder.getWorld().isBrickOrTilesOrBomb(x, y, this));
                return !holder.getWorld().isBrickOrTilesOrBomb(x, y, this);

            case DOWN:

                y = nextY + 1;
                //System.out.println("DOWN Possible " + !holder.getWorld().isBrickOrTilesOrBomb(x, y, this));
                return !holder.getWorld().isBrickOrTilesOrBomb(x, y, this);

            case LEFT:

                x = nextX - 1;
                //System.out.println("Left Possible " + !holder.getWorld().isBrickOrTilesOrBomb(x, y, this));
                return !holder.getWorld().isBrickOrTilesOrBomb(x, y, this);

            case RIGHT:
                x = nextX + 1;
                //System.out.println("RIGHT Possible " + !holder.getWorld().isBrickOrTilesOrBomb(x, y, this));
                return !holder.getWorld().isBrickOrTilesOrBomb(x, y, this);

        }
        return false;
    }

    protected boolean checkCanTurn(Directions nextDirextion) {



        if (direction == Directions.LEFT || direction == Directions.RIGHT) {

            if (posX % 32 < Tile.WIDTH * 2 / 11) {

                if (direction == Directions.LEFT)
                    return !holder.getWorld().isBrickOrTilesOrBomb((int)(posX-speed)/Tile.WIDTH, currentYTile, this);
                else
                    return !holder.getWorld().isBrickOrTilesOrBomb((int)(posX+speed)/Tile.WIDTH, currentYTile, this);
                //return checkEastOrWest(nextDirextion);
            } else if (posX % 32 > (Tile.WIDTH - Tile.WIDTH * 2 / 11)) {

                if (direction == Directions.LEFT)
                    return !holder.getWorld().isBrickOrTilesOrBomb((int)(posX-speed)/Tile.WIDTH, currentYTile, this);
                else
                    return !holder.getWorld().isBrickOrTilesOrBomb((int)(posX+speed)/Tile.WIDTH, currentYTile, this);

                //return checkEastOrWest(nextDirextion);
            }


        } else if (direction == Directions.UP || direction == Directions.DOWN) {

            if (posY % 32 < (Tile.HEIGHT * 2 / 11)) {

                if (direction == Directions.UP)
                    return !holder.getWorld().isBrickOrTilesOrBomb(currentXTile, (int)(posY-speed)/Tile.HEIGHT, this);
                else
                    return !holder.getWorld().isBrickOrTilesOrBomb(currentXTile, (int)(posY+speed)/Tile.HEIGHT, this);

                //return checkNorthOrSouth(nextDirextion);
            } else if (posY % 32 > Tile.HEIGHT - Tile.HEIGHT * 2 / 11) {

                //return checkNorthOrSouth(nextDirextion);
            }

        }
        return false;
    }


    //set the probability for Left and right
    protected boolean checkEastOrWest(Directions nextDirextion) {

        return isPossibleDirection(nextDirextion);
    }

    //set the probability for Up and Down
    protected boolean checkNorthOrSouth(Directions nextDirextion) {
        return isPossibleDirection(nextDirextion);
    }


    protected void updateProbability(int U, int D, int L, int R) {
        probability.clear();
        probability.put(Directions.UP, U);
        probability.put(Directions.DOWN, D);
        probability.put(Directions.LEFT, L);
        probability.put(Directions.RIGHT, R);

        updateDirection();
    }

    private void updateDirection() {


        double p = Math.random() * 100;
        double cumulativeProbability = 0.0;

        for (Map.Entry<Directions, Integer> entry : probability.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (p < cumulativeProbability) {
                direction = entry.getKey();
                return;
            }

        }
    }

    protected boolean isJunction(int xOffset, int yOffset) {

        nextX = currentXTile + xOffset;
        nextY = currentYTile + yOffset;
        return !holder.getWorld().isBrickOrTilesOrBomb(nextX, nextY, this);
    }


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {


        g.drawImage(Assets.monster, (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
    }
}
