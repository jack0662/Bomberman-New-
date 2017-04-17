package game.Controller;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Jack on 8/11/2016.
 */
public class Keyboard extends KeyAdapter implements Controller {
    private Action action;

    public Keyboard(){

        action = new Action();
    }
    public Action getAction() {
        return action;
    }

    public void keyPressed(KeyEvent event) {

        int key = event.getKeyCode();
        switch (key) {

            case KeyEvent.VK_UP:
                action.up = true;
                break;
            case KeyEvent.VK_W:
                action.up = true;

            case KeyEvent.VK_DOWN:
                action.down = true;
                break;
            case KeyEvent.VK_S:
                action.down = true;
                break;

            case KeyEvent.VK_LEFT:
                action.left = true;
            case KeyEvent.VK_A:
                action.left = true;
                break;

            case KeyEvent.VK_RIGHT:
                action.right = true;
                break;
            case KeyEvent.VK_D:
                action.right = true;
                break;

            case KeyEvent.VK_SPACE:
                action.deploy= true;
                break;
            case KeyEvent.VK_ESCAPE:
                action.back= true;
                break;
        }
    }

    public void keyReleased(KeyEvent event) {

        int key = event.getKeyCode();
        switch (key) {

            case KeyEvent.VK_UP:
                action.up = false;
                break;
            case KeyEvent.VK_W:
                action.up = false;

            case KeyEvent.VK_DOWN:
                action.down = false;
                break;
            case KeyEvent.VK_S:
                action.down = false;
                break;

            case KeyEvent.VK_LEFT:
                action.left = false;
            case KeyEvent.VK_A:
                action.left = false;
                break;

            case KeyEvent.VK_RIGHT:
                action.right = false;
                break;
            case KeyEvent.VK_D:
                action.right = false;
                break;

            case KeyEvent.VK_SPACE:
                action.deploy= false;
                break;

            case KeyEvent.VK_ESCAPE:
                action.back= false;
                break;
        }
    }
}

