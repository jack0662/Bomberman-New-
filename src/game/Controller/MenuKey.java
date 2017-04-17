package game.Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Jack on 19/3/2017.
 */
public class MenuKey extends KeyAdapter{
    public MenuAction menuAction;


    public MenuKey(){

        menuAction = new MenuAction();
    }


    public void keyPressed(KeyEvent event) {

        int key = event.getKeyCode();
        switch (key) {

            case KeyEvent.VK_SPACE:
                menuAction.space = true;
                break;

        }
    }

    public void keyReleased(KeyEvent event) {

        int key = event.getKeyCode();
        switch (key) {

            case KeyEvent.VK_SPACE:
                menuAction.space = false;
                break;

        }
    }
}

