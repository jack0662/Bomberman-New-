package game;

import game.GameObject.Individual.Directions;

/**
 * Created by Jack on 14/3/2017.
 */
public class Node {

    private int gCost;
    private int hCost;
    private int fCost;

    private int x;
    private int y;

    private Directions nextDirection;

    public boolean traversable;

    private Holder holder;
    private Node parent;


    public Node(Holder holder, int x, int y) {
        this.x = x;
        this.y = y;
        this.traversable = true;
    }


    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getfCost() {
        return fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }


    public boolean equals(Node other) {

        return other.x == this.x && other.y == this.y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {

        this.parent = parent;
    }

    public Directions getDirection(Node other) {
        //NO diagoloun
        if (other.x < this.x && other.y == this.y)
            return Directions.LEFT;
        else if (other.x > this.x && other.y == this.y)
            return Directions.RIGHT;
        else if (other.x == this.x && other.y < this.y)
            return Directions.UP;
        else if (other.x == this.x && other.y > this.y)
            return Directions.DOWN;

        return null;
    }


    public String toString() {

        return "X " + x + " Y " + y + "Parent : " + "D:" + nextDirection;
    }

    public Directions getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(Directions nextDirection) {
        this.nextDirection = nextDirection;
    }
}
