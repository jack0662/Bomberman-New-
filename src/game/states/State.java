package game.states;

import game.Holder;

import java.awt.*;

/**
 * Created by Jack on 17/11/2016.
 */
public abstract class State {

    protected Holder holder;

    public State(Holder holder){
        this.holder = holder;
    }

    public static State currentState = null;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getCurrentState() {

        return currentState;
    }

    public abstract void update();

    public abstract void render(Graphics g);
}
