package game.GameObject.Individual;

import game.Holder;
import game.graphics.Animation;
import game.resources.Assets;
import game.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jack on 8/3/2017. simple AI implemented
 */
//Basic AI Randomly move

public class Ballons extends Monster {

    //1sec 100 loop


    public Ballons(Holder holder, double x, double y) {
        super(holder, x, y);
        this.holder = holder;
        sameTileCounter = reverseTimes = counter = 0;
        speed = 1;
        lastX = lastY = 0;
        direction = Directions.LEFT;
        possibleDirections = new ArrayList<>();
        probability = new HashMap<>();
        probability.put(Directions.LEFT, 25);
        probability.put(Directions.RIGHT, 25);
        probability.put(Directions.UP, 25);
        probability.put(Directions.DOWN, 25);


        leftAnimation = new Animation(Assets.leftBallons);
        rightAnimation = new Animation(Assets.rightBallons);


    }

    @Override
    public void updateAnimation() {
        leftAnimation.update();
        rightAnimation.update();


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
    public void update() {

        currentXTile = (int) posX / Tile.WIDTH;
        currentYTile = (int) posY / Tile.HEIGHT;

        sillyMove();
        getInput();
        move();
        updateAnimation();

        lastXTile = currentXTile;
        lastYTile = currentYTile;
        counter++;

    }


    protected void sillyMove(){

        checkIsJunction();
        isTurn();
        if (lastXTile == currentXTile && currentYTile == lastYTile)
            sameTileCounter++;
        else {
            sameTileCounter = 0;
            walkedTile++;
        }
    }

    //If it stuck go to other direction
    protected void isTurn() {

        //System.out.println(sameTileCounter + " wT" + walkedTile);
        nextXTile = getNextTile(direction, currentXTile, currentYTile)[0];
        nextYTile = getNextTile(direction, currentXTile, currentYTile)[1];
        //System.out.println("CX " + currentXTile + "    " + currentYTile);
        //System.out.println("NX " + nextXTile + "    " + nextYTile);
        if (sameTileCounter > 50) {
            sameTileCounter = 0;
            walkedTile = TILELIMIT+1;
            checkIsJunction();
        }
    }

    protected int[] getNextTile(Directions direction, int x, int y) {

        int[] tiles = new int[2];
        switch (direction) {
            case UP:
                tiles[0] = x;
                tiles[1] = y - 1;
                break;

            case DOWN:
                tiles[0] = x;
                tiles[1] = y + 1;
                break;

            case LEFT:
                tiles[0] = x + 1;
                tiles[1] = y;
                break;

            case RIGHT:
                tiles[0] = x - 1;
                tiles[1] = y;
                break;

        }
        return tiles;
    }

    protected void checkIsJunction() {

        if (direction == Directions.LEFT || direction == Directions.RIGHT) {


            if (posX % 32 < Tile.WIDTH * 2 / 11 && isJunction(0, 0) && walkedTile > TILELIMIT) {

                updateEastOrWest();
            } else if (posX % 32 > (Tile.WIDTH - Tile.WIDTH * 2 / 11) && isJunction(1, 0) && walkedTile > TILELIMIT) {

                updateEastOrWest();
            }


        } else if (direction == Directions.UP || direction == Directions.DOWN) {

            if (posY % 32 < (Tile.HEIGHT * 2 / 11) && isJunction(0, 0) && walkedTile > TILELIMIT) {

                updateNorthOrSouth();
            } else if (posY % 32 > Tile.HEIGHT - Tile.HEIGHT * 2 / 11 && isJunction(0, 1) && walkedTile > TILELIMIT) {

                updateNorthOrSouth();
            }

        }
    }

    protected void updateEastOrWest() {


        if (isPossibleDirection(Directions.UP) && isPossibleDirection(Directions.DOWN)) {

            walkedTile = 0;
            updateProbability(40, 40, 10, 10);

        } else if (isPossibleDirection(Directions.UP)) {

            walkedTile = 0;
            updateProbability(70, 0, 15, 15);

        } else if (isPossibleDirection(Directions.DOWN)) {

            walkedTile = 0;
            updateProbability(0, 70, 15, 15);

        } else if (!isPossibleDirection(Directions.LEFT)) {

            walkedTile = 0;
            updateProbability(0, 0, 0, 100);
        } else if (!isPossibleDirection(Directions.RIGHT)) {

            walkedTile = 0;
            updateProbability(0, 0, 100, 0);
        }
    }

    //set the probability for Up and Down
    protected void updateNorthOrSouth() {


        if (isPossibleDirection(Directions.LEFT) && isPossibleDirection(Directions.RIGHT)) {

            walkedTile = 0;
            updateProbability(10, 10, 40, 40);

        } else if (isPossibleDirection(Directions.LEFT)) {

            walkedTile = 0;
            updateProbability(15, 15, 70, 0);

        } else if (isPossibleDirection(Directions.RIGHT)) {

            walkedTile = 0;
            updateProbability(15, 15, 0, 70);
        } else if (!isPossibleDirection(Directions.UP)) {
            walkedTile = 0;
            updateProbability(0, 100, 0, 0);
        } else if (!isPossibleDirection(Directions.DOWN)) {
            walkedTile = 0;
            updateProbability(100, 0, 0, 0);
        }
    }



    @Override
    public void render(Graphics g) {


        g.drawImage(getCurrentAnimation(), (int) (posX - this.holder.getGame().getCamera().getxOffset()),
               (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);


        //g.fillRect((int) (posX + bounds.x - holder.getCamera().getxOffset()
         //       ),
        //        (int) (posY + bounds.y - holder.getCamera().getyOffset()),
        //        bounds.width, bounds.height);
        //g.setColor(Color.blue);

        //g.fillRect(nextX * 32, nextY * 32, 32, 32);


    }
}
