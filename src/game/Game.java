package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import game.Controller.Keyboard;
import game.Controller.MenuKey;
import game.states.MenuState;
import game.states.State;
import game.graphics.*;
import game.resources.Assets;

import static game.Constants.windowHeight;
import static game.Constants.windowWidth;

/**
 * Created by Jack on 15/11/2016.
 */
public class Game implements Runnable {

    private Thread thread;
    private boolean running;

    public BufferStrategy bs;
    public Graphics g;

    //States
    public State menuState;

    public View view;
    private int width, height;
    public String title;
    public BufferedImage testImage;

    //INput
    private Keyboard keyboard;

    private MenuKey menuKey;

    //Camera for holder
    private Camera camera;

    //Collision class for this game
    private Collision collision;

    //holder (hold world and game)
    private Holder holder;

    //Object


    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        setMenuKey(new MenuKey());
        setKeyboard(new Keyboard());

    }

    public void init() {

        view = new View(title, width, height);
        int scale = width / Constants.widthRatio;
        //System.out.println(scale+"AAAAAAAAA");

        view.getFrame().addKeyListener(getMenuKey());
        //load the image at the beginning
        Assets.init();

        holder = new Holder(this, scale);
        camera = new Camera(holder, 0, 0);


        menuState = new MenuState(holder, view);
        State.setState(menuState);
        //testImage = ImageLoader.loadImage("/textures/Walls.png");
    }

    public void run() {

        init();

        int fps = 60;
        double timePerUpdate = 1000 / fps; //1sec
        double delta = 0;
        long currentTime;
        long timer = 0;
        int ticks = 0;

        //long lastTime = System.currentTimeMillis();
        while (running) {


            try {
                //synchronized (gameState) {
                long lastTime = System.currentTimeMillis();
                update();
                render();
                Thread.sleep(Math.max(0, 17 + lastTime - System.currentTimeMillis()));
                //}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }




            /*
            currentTime = System.currentTimeMillis();
            delta += (currentTime-lastTime) / timePerUpdate;
            timer += currentTime - lastTime;
            if (delta>=1){
                update();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000) {
                System.out.println(ticks);
                ticks = 0;
                timer = 0;
            }
            */


            /*
            currentTime = System.currentTimeMillis();
            //maintain 60 fps
            // we want now=lastTime+timePerUpdate

            try {
                double delay = lastTime + timePerUpdate - System.currentTimeMillis();
                if (delay<0)
                    //System.out.println(delay+"****************************************************");
                //else System.out.println("delay="+delay);
                Thread.sleep((int) Math.max(delay, 0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() >= lastTime + timePerUpdate) { // render once
                update();
                render();

                lastTime = currentTime;
            }

            */


        }

        stop();
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update() {


        if (State.getCurrentState() != null)
            State.getCurrentState().update();
    }

    public void render() {
        bs = view.getCanvas().getBufferStrategy();
        if (bs == null) {
            view.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        //clear the screen
        g.clearRect(0, 0, width, height);


        //draw
        if (State.getCurrentState() != null)
            State.getCurrentState().render(g);


        //end draw
        bs.show();
        g.dispose();

    }


    //Getter
    public Keyboard getKeyboard() {

        return keyboard;
    }

    public MenuKey getmenuKey() {

        return getMenuKey();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    public Collision getCollision() {
        return collision;
    }


    public static void main(String[] args) {

        Game game = new Game("Bomberman", windowWidth, windowHeight);
        game.start();
    }

    public MenuKey getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(MenuKey menuKey) {
        this.menuKey = menuKey;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }
}
