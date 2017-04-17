package game.states;

import game.Controller.Keyboard;
import game.Controller.MenuKey;
import game.GameObject.Individual.Bomberman;
import game.Holder;
import game.SoundManager;
import game.View;
import game.resources.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jack on 17/11/2016.
 */
public class MenuState extends State {

    private View view;
    private State gameState;


    public MenuState(Holder holder, View view) {
        super(holder);
        this.view = view;
        SoundManager.play(SoundManager.gameOver);
    }

    @Override
    public void update() {

        if (holder.getGame().getmenuKey().menuAction.space){

            view.getFrame().removeKeyListener(holder.getGame().getmenuKey());
            Keyboard keyboard = new Keyboard();
            holder.getGame().setKeyboard(keyboard);
            view.getFrame().addKeyListener(keyboard);
            State.setState(new GameState(holder,view));
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int scale = holder.getScale() * 2;
        g2d.setColor(Color.black);
        g2d.fillRect(0,0,holder.getWidth(), holder.getHeight());

        g2d.scale(scale,scale);
        int x = (holder.getWidth() / scale - Assets.menu.getWidth(null)) / 2;
        int y = (holder.getHeight() / scale - Assets.menu.getHeight(null)) / 2;
        //g2d.scale(scale, scale);
        g2d.drawImage(Assets.menu, x, y, null);

        g2d.scale(0.25, 0.25);


        g2d.setColor(Color.orange);
        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
        g2d.drawString("Instructions", 100, 750);
        g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 35));
        g2d.drawString("Up : W / Up Arrow", 100, 800);
        g2d.drawString("Left : A / Left Arrow", 100, 850);
        g2d.drawString("Down : S / Down Arrow", 100, 900);
        g2d.drawString("Right : D / Right Arrow", 100, 950);
        g2d.drawString("Space for Bombs ", 100, 1000);
        g2d.setColor(Color.white);
        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 56));
        g2d.drawString("Press Space To Start", 600, 800);
        //Scale 2.0




    }
}
