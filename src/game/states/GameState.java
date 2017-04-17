package game.states;

import game.Controller.Keyboard;
import game.Controller.MenuKey;
import game.GameObject.Individual.Bomberman;
import game.GameObject.GameObject;
import game.Holder;
import game.SoundManager;
import game.View;
import game.World;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Jack on 17/11/2016.
 */
public class GameState extends State {

    private static final int gameLives = 3;

    private World world;
    private MenuKey menuKey;
    private View view;

    private List<GameObject> gameObjects;

    public GameState(Holder holder, View view) {
        super(holder);
        world = new World(holder, gameLives, "resources/worlds/default.txt");
        holder.setWorld(world);
        gameObjects = new ArrayList<>();
        setBricks();
        this.view = view;
        this.holder = holder;


    }

    public void setBricks() {
        for (GameObject object : holder.getWorld().getBricks()) {
            gameObjects.add(object);
        }
    }


    @Override
    public void update() {
        world.update();
        if (holder.getWorld().gameOver) {


            if (holder.getGame().getKeyboard().getAction().back) {
                SoundManager.play(SoundManager.gameOver);
                menuKey = new MenuKey();
                holder.getGame().setMenuKey(menuKey);

                view.getFrame().removeKeyListener(holder.getGame().getKeyboard());
                view.getFrame().addKeyListener(menuKey);
                State.setState(new MenuState(holder, view));
            }
        }
    }

    @Override
    public void render(Graphics g) {
        world.render(g);

        if (holder.getWorld().gameOver) {
            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
            g.drawString("Press ESC to menu", 200 ,200);
            g.setColor(Color.BLACK);
            g.drawString("Score : ", 200 ,300);
            g.setColor(Color.BLUE);
            g.drawString(holder.getWorld().getGameObjectManager().getScore()+"", 430    ,300);
        }
    }

    public List<GameObject> getGameObjects() {

        return gameObjects;
    }
}
