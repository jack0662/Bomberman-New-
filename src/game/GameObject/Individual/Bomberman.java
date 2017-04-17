package game.GameObject.Individual;

import game.Constants;
import game.GameObject.GameObject;
import game.GameObject.Object.Blast.Blast;
import game.GameObject.Object.Bomb;
import game.GameObject.Object.Brick;
import game.GameObject.Object.Portal;
import game.GameObject.Object.item.Item;
import game.Holder;
import game.SoundManager;
import game.graphics.Animation;
import game.resources.Assets;
import game.tiles.Tile;

import java.awt.*;

import static game.SoundManager.deployBomb;

/**
 * Created by Jack on 17/11/2016.
 */
public class Bomberman extends Individual {

    private Holder holder;
    private int bombLimit;
    private int blastRadius;
    private final int blastscaleW , blastscaleH;
    private Rectangle r;

    public Bomberman(Holder holder, int gameLives ,double x, double y) {

        super(holder, x, y, Individual.DEFAULT_WIDTH, Individual.DEFAULT_HEIGHT);
        this.holder = holder;
        bombLimit = Constants.initalBombLimit;
        blastRadius = Constants.bombBlastRadius;
        lives = gameLives;

        upAnimation = new Animation(Assets.upBomberman);
        downAnimation = new Animation(Assets.downBomberman);
        leftAnimation = new Animation(Assets.leftBomberman);
        rightAnimation = new Animation(Assets.rightBomberman);
        standAnimation = new Animation(Assets.standBomberman);

        blastscaleW = (int) (DEFAULT_WIDTH * 1.2 - DEFAULT_WIDTH);
        blastscaleH = (int) (DEFAULT_HEIGHT * 1.1 - DEFAULT_HEIGHT);

    }

    @Override
    public void update() {
        //dead = true;
        updateAnimation();
        getInput();
        move();
        holder.getGame().getCamera().centerGameObject(this);
        r = this.collisionBox(getPosX() + blastscaleW, getPosY() + blastscaleH, Tile.WIDTH - blastscaleW, Tile.HEIGHT - blastscaleH);



    }

    @Override
    public void updateAnimation() {

        upAnimation.update();
        downAnimation.update();
        leftAnimation.update();
        rightAnimation.update();

    }

    @Override
    public void move() {



        //check isCollision(tiles) with calculating the position for next move
        if (!isCollision(xMove, 0))
            moveX();

        if (!isCollision(0, yMove))
            moveY();
        //check isCollision(gameObject) with calculating the position for next move

    }

    private void getInput() {

        xMove = 0;
        yMove = 0;

        if (holder.getGame().getKeyboard().getAction().up) {

            yMove = -speed;

        } else if (holder.getGame().getKeyboard().getAction().down) {

            yMove = speed;

        } else if (holder.getGame().getKeyboard().getAction().left) {

            xMove = -speed;

        } else if (holder.getGame().getKeyboard().getAction().right) {

            xMove = speed;

        }

        if (holder.getGame().getKeyboard().getAction().deploy && Bomb.bombOnMap < bombLimit) {


            holder.getGame().getKeyboard().getAction().deploy = false;
            if (bombDeployCollision(0, 0))
                return;
            double x = posX + Tile.WIDTH / 2;
            double y = posY + Tile.HEIGHT / 2;
            double x1 = posX % Tile.WIDTH;
            double y1 = posY % Tile.HEIGHT;
            if (x1 > Tile.WIDTH - Tile.WIDTH / 3)
                x++;
            else if (y1 > Tile.HEIGHT - Tile.HEIGHT / 3)
                y++;
            x = Math.floor(x / Tile.WIDTH) * Tile.WIDTH;
            y = Math.floor(y / Tile.HEIGHT) * Tile.HEIGHT;
            holder.getWorld().getGameObjectManager().add(new Bomb(holder, x, y, this, blastRadius));
            SoundManager.play(deployBomb);
        }

    }

    @Override
    public boolean isCollision(double x, double y) {

        for (GameObject object : holder.getWorld().getGameObjectManager().getGameObjects()) {

            if (object.equals(this))
                continue;

            if (object.collisionBox(0, 0).intersects(this.collisionBox(x, y))) {

                if (object instanceof Item) {
                    return false;
                }
                if (object instanceof Portal) {
                    return false;
                }
                if (object instanceof Monster) {

                    Rectangle r = this.collisionBox(object.getPosX() + blastscaleW, object.getPosY() + blastscaleH, Tile.WIDTH - blastscaleW*2, Tile.HEIGHT - blastscaleH*2);
                    //System.out.println(r +"   "+ object.collisionBox(0,0));
                    if (!r.intersects(this.collisionBox(0, 0)))
                        continue;
                    if (!object.dead){
                        SoundManager.play(SoundManager.liveDown);
                        this.lives--;
                        int score = holder.getWorld().getGameObjectManager().getScore() - 50;
                        holder.getWorld().getGameObjectManager().setScore(score);

                    }
                    object.dead = true;

                    if (lives<1)
                        dead = true;
                    return false;
                }
                if (object instanceof Bomb) {
                    if (((Bomb) object).getDeployer().equals(this) && !object.isDestroyable()) {
                        return false;
                    }
                }
                if (object instanceof Blast) {
                    return false;
                }

                if (object instanceof Brick)
                    adjustPosition((Brick) object);
                return true;

            } else if (object instanceof Bomb && ((Bomb) object).getDeployer().equals(this)) {
                object.setDestroyable(true);
            }
        }

        return false;
    }

    private void adjustPosition(Brick brick){
        if (posX < brick.getPosX() && xMove >0){
            posX = brick.getPosX()-Tile.WIDTH;
        }
        else if (brick.getPosX() < posX  && xMove < 0){
            posX = brick.getPosX()+Tile.WIDTH;
        }
        else if (posY < brick.getPosY() && yMove >0){
            posY = brick.getPosY()-Tile.HEIGHT;
        }
        else if (posY > brick.getPosY() && yMove <0){
            posY = brick.getPosY()+Tile.HEIGHT;
        }
    }


    @Override
    public void render(Graphics g) {


        //r = this.collisionBox(getPosX() + blastscaleW, getPosY() + blastscaleH, Tile.WIDTH - blastscaleW, Tile.HEIGHT - blastscaleH);

        g.drawImage(getCurrentAnimation(), (int) (posX - this.holder.getGame().getCamera().getxOffset()),
                (int) (posY - this.holder.getGame().getCamera().getyOffset()), width, height, null);
        //g.drawRect((int)getPosX() + blastscaleW, (int)getPosY() + blastscaleH, Tile.WIDTH - blastscaleW*2, Tile.HEIGHT - blastscaleH*2);
        /*
        g.setColor(Color.red);
        g.fillRect((int) (posX + bounds.x - holder.getCamera().getxOffset()),
                (int) (posY + bounds.y - holder.getCamera().getyOffset()),
                 bounds.width, bounds.height);

                */


    }

    public void setSpeed(double s) {
        this.speed = s;
    }

    public double getSpeed() {
        return speed;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public void setBlastRadius(int blastRadius) {
        this.blastRadius = blastRadius;
    }



}
