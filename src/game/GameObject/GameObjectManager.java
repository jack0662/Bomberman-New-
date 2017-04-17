package game.GameObject;

import game.GameObject.Individual.Bomberman;
import game.GameObject.Individual.Monster;
import game.GameObject.Object.Bomb;
import game.GameObject.Object.Brick;
import game.Holder;
import game.states.GameState;
import game.states.State;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Jack on 19/1/2017.
 */
public class GameObjectManager {

    private Holder holder;
    private Bomberman bomberman;
    private int bombOnMap;
    private int score;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObject> objectToAdd;

    public GameObjectManager(Holder holder, Bomberman bomberman, ArrayList<Brick> bricks) {

        this.holder = holder;
        this.bomberman = bomberman;
        gameObjects = new ArrayList<>();
        objectToAdd = new ArrayList<>();
        gameObjects.addAll(bricks);
        gameObjects.add(bomberman);

        //gameObjects.add(new Monster(holder,12*31,9*32));
        //gameObjects.add(new Monster(holder,7*29,1*32));

        bombOnMap = score = 0;
    }


    public void update() {

        ArrayList<GameObject> temp = new ArrayList<>();
        for (GameObject g : gameObjects) {
            g.update();
        }

        for (GameObject g : gameObjects) {
            if (!g.dead)
                temp.add(g);
            else if (g.dead && g instanceof Bomb)
                Bomb.bombOnMap--;
            else if (g.dead && g instanceof Bomberman)
                holder.getWorld().gameOver = true;
        }
        getGameObjects().clear();
        int numsMonster = 0;

        for (GameObject g : temp) {
            getGameObjects().add(g);
            if (g instanceof Monster)
                numsMonster++;
        }
        if (numsMonster ==0 && score > 1000)
            holder.getWorld().gameOver = true;

        for (GameObject o : objectToAdd) {
            getGameObjects().add(o);
        }
        objectToAdd.clear();
    }

    public void render(Graphics g) {

        for (GameObject object : gameObjects) {
            object.render(g);
        }

        //Draw score and Live on screen
        g.setColor(Color.darkGray);
        g.fillRect(10,0,40,15);
        g.setColor(Color.red);
        g.drawString("Live : "+ bomberman.getLive(),10,10);

        g.setColor(Color.darkGray);
        g.fillRect(100,0,80,15);
        g.setColor(Color.green);
        g.drawString("Score : "+ getScore(),100,10);
    }

    public void add(GameObject object) {
        objectToAdd.add(object);
    }

    public void add(List<? extends GameObject> list) {

        for (GameObject o : list) {
            objectToAdd.add(o);
        }
    }

    public int getBombOnMap() {
        return bombOnMap;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Bomberman getBomberman() {
        for (GameObject object : gameObjects) {
            if (object instanceof Bomberman)
                return (Bomberman) object;
        }
        return null;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
