package game;

import game.GameObject.Individual.*;
import game.GameObject.GameObject;
import game.GameObject.GameObjectManager;
import game.GameObject.Object.Blast.Blast;
import game.GameObject.Object.Bomb;
import game.GameObject.Object.Brick;
import game.Utilities.Utilities;
import game.tiles.Tile;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Jack on 17/11/2016.
 */
public class World {
    //How many tiles in a whole world, represented in posX,posY array
    private int width, height, nodeWidth, nodeHeight, monstersLimit;
    private int[][] tiles;
    private Holder holder;
    private ArrayList<Brick> bricks;
    private GameObjectManager gameObjectManager;
    private Node[][] nodes;
    public int walkableTiles;
    public boolean gameOver;

    public World(Holder holder,int gameLives, String path) {
        this.holder = holder;
        World.generateWorld(17, 35);
        gameOver = false;
        setBricks(new ArrayList<>());
        loadWorld(path);
        gameObjectManager = new GameObjectManager(holder, new Bomberman(holder,gameLives, 32, 32), getBricks());
        monstersLimit = 10;

        generateMonsters();


        gameObjectManager.add(new Dall(holder,96,32*13));
        //gameObjectManager.add(new Smart(holder,96,32*13));
        //gameObjectManager.add(new Dall(holder,96,32*13));

        //gameObjectManager.add(new Smart(holder,7*32,32*7));
        //gameObjectManager.add(new Smart(holder,96,32*13));
        //gameObjectManager.add(new Ballons(holder,140,96));
        walkableTiles = -1;

    }


    public void update() {

        getGameObjectManager().update();

    }

    //render the world
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //Scale 2.0
        g2d.scale(holder.getScale(), holder.getScale());
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                getTile(i, j).render(g2d, (int) (i * Tile.WIDTH - holder.getGame().getCamera().getxOffset()),
                        (int) (j * Tile.HEIGHT - holder.getGame().getCamera().getyOffset()));

            }

        }
        getGameObjectManager().render(g2d);

    }

    //load the word when start a game state
    private void loadWorld(String path) {

        String[] world = Utilities.stringToArray(Utilities.loadFileAsString(path));
        width = Integer.parseInt(world[1]);
        height = Integer.parseInt(world[0]);
        nodeWidth = width;
        nodeHeight = height;
        nodes = new Node[nodeWidth][nodeHeight];
        createNodes();


        setTiles(new int[width][height]);
        //System.out.println("width : " + width);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                getTiles()[i][j] = Integer.parseInt(world[(i + j * width) + 2]);
                if (Integer.parseInt(world[(i + j * width) + 2]) == 2) {
                    bricks.add(new Brick(holder, i * Brick.DEFAULT_WIDTH, j * Brick.DEFAULT_HEIGHT));
                }

            }
        }

    }


    //Render a new world, save it to txt
    public static void generateWorld(int height, int width) {

        try {

            PrintWriter out = new PrintWriter("resources/worlds/default.txt");
            out.write(height + " " + width + '\n');
            for (int i = 0; i < height; i++) {

                String s1 = "";
                int numOfBrick = 0;
                for (int j = 0; j < width; j++) {
                    if (i == 0 || j == 0 || i == height - 1 || j == width - 1) {
                        s1 += "1 ";
                    } else if (i % 2 == 0 && j % 2 == 0) {
                        s1 += "1 ";
                    } else if (Math.random() > 0.7 && numOfBrick < 20) {

                        if (i < 3 && j < 3) {
                            s1 += "0 ";
                        } else {
                            s1 += "2 ";
                            numOfBrick++;
                        }
                    } else
                        s1 += "0 ";
                }
                out.write(s1.substring(0, s1.length() - 1) + '\n');
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void generateMonsters() {
        int x1y0spawn, x2y0spawn, x3y0spawn, x0y1spawn, x1y1spawn, x2y1spawn, x3y1spawn;
        x1y0spawn = x2y0spawn = x3y0spawn = x0y1spawn = x1y1spawn = x2y1spawn = x3y1spawn = 2;
        int w = width /4;
        int h = height /2;
        //System.out.println(w+" xxx "+h + ""+height);
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {

                if (isGameObjectOrTiles(i,j))
                    continue;
                //System.out.println(i+"    "+j);
                if (i > w  && i< w*2 && j <= h) {
                    if (iSspwanMonsters(i, j) && Math.random() > 0.7 && x1y0spawn > 0) {
                        gameObjectManager.add(new Ballons(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        x1y0spawn--;
                    }
                }else if (i > w*2  && i< w*3 && j <= h) {
                    if (iSspwanMonsters(i, j) && Math.random() > 0.7 && x2y0spawn > 0) {
                        gameObjectManager.add(new Ballons(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        x2y0spawn--;
                    }
                }else if (i > w*3  && i< w*4 && j <= h) {
                    if (iSspwanMonsters(i, j) && Math.random() > 0.7 && x3y0spawn > 0) {
                        gameObjectManager.add(new Smart(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        x3y0spawn--;
                    }
                }

                else if (i <= w && j > h && j <= h*2) {
                    if (iSspwanMonsters(i, j) && Math.random() > 0.7 && x0y1spawn > 0) {
                        gameObjectManager.add(new Ballons(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        x0y1spawn--;
                    }
                }else if (i > w  && i< w*2 && j > h && j <= h*2) {
                    if (iSspwanMonsters(i, j) && Math.random() > 0.7 && x1y1spawn > 0) {
                        gameObjectManager.add(new Ballons(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        x1y1spawn--;
                    }
                }else if (i > w*2  && i< w*3 && j > h && j <= h*2) {
                    if (iSspwanMonsters(i, j) && Math.random() > 0.7 && x2y1spawn > 0) {
                        if (Math.random()>0.3)
                            gameObjectManager.add(new Dall(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        else
                            gameObjectManager.add(new Smart(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        x2y1spawn--;
                    }
                }else if (i > w*3  && i< w*4 && j > h && j <= h*2) {
                    if (iSspwanMonsters(i, j) && Math.random() > 0.7 && x3y1spawn > 0) {
                        if (Math.random()>0.7)
                            gameObjectManager.add(new Smart(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        else
                            gameObjectManager.add(new Dall(holder, i * Tile.WIDTH, j * Tile.HEIGHT));
                        x3y1spawn--;
                    }
                }


            }
        }

    }




    private boolean iSspwanMonsters(int x, int y) {

        if (isGameObjectOrTiles(x,y))
            return false;
        updateTraversable();

        AStarAlgorithm(x, y, 0, 0);
        return walkableTiles >= 10;


    }

    //represent in tile(int)
    public int[] randomPos(double x, double y) {
        //System.out.println(x);
        int[] pos = new int[2];
        int randomPosX, randomPosY, minimum, maximum;

        if (x / 32 < width / 2) {

            minimum = width / 2;
            maximum = width;
            //minimum = 0;
            //maximum = width / 3;

        } else {
            minimum = 0;
            maximum = width / 2;
        }
        do {
            randomPosX = ThreadLocalRandom.current().nextInt(minimum, maximum + 1);
            randomPosY = ThreadLocalRandom.current().nextInt(0, height + 1);
            //randomPosX = 3;
            //randomPosY = 13;
            //System.out.println(" X: " + randomPosX + " Y: " + randomPosY);
        } while (!isRandValid(randomPosX, randomPosY));
        //System.out.println("Valid  X: " + randomPosX + " Y: " + randomPosY);
        pos[0] = randomPosX * Tile.WIDTH;
        pos[1] = randomPosY * Tile.HEIGHT;
        return pos;
    }

    public boolean isRandValid(int x, int y) {

        //First check whether the spawn location have object or tiles
        if (isGameObjectOrTiles(x, y))
            return false;

        //First check whether the Monster are able to deply the bomb and not affewct
        if (checkDiagonalValid(x, y))
            return true;

        updateTraversable();
        AStarAlgorithm(x,y,0,0);

        if (walkableTiles>5) {
            //System.out.println("yeAH");
            return true;
        }
        return false;
        //all diagonal point  have object will return false
        //System.out.println("Ans : " + checkhorizontalValid(x,y));


    }

    private boolean checkDiagonalValid(int x, int y) {

        if (!isGameObjectOrTiles(x - 1, y) && !isGameObjectOrTiles(x - 1, y + 1))
            return true;
        if (!isGameObjectOrTiles(x, y + 1) && !isGameObjectOrTiles(x - 1, y + 1))
            return true;
        if (!isGameObjectOrTiles(x + 1, y) && !isGameObjectOrTiles(x + 1, y + 1))
            return true;
        if (!isGameObjectOrTiles(x, y + 1) && !isGameObjectOrTiles(x + 1, y + 1))
            return true;
        if (!isGameObjectOrTiles(x - 1, y) && !isGameObjectOrTiles(x - 1, y - 1))
            return true;
        if (!isGameObjectOrTiles(x, y - 1) && !isGameObjectOrTiles(x - 1, y - 1))
            return true;
        if (!isGameObjectOrTiles(x, y - 1) && !isGameObjectOrTiles(x + 1, y - 1))
            return true;
        if (!isGameObjectOrTiles(x + 1, y) && !isGameObjectOrTiles(x + 1, y - 1))
            return true;
        return false;
    }

    //check horizontal space , how many tile that the Monster can move horizontally
    private int counthorizontalLeft(int x, int y, int z) {


        if (isGameObjectOrTiles(x, y)) {
            return z - 1 > 0 ? z - 1 : 0;
        } else {
            x--;
            z++;
            return counthorizontalLeft(x, y, z);
        }

    }



    private int counthorizontalRight(int x, int y, int z) {


        if (isGameObjectOrTiles(x, y)) {
            return z - 1 > 0 ? z - 1 : 0;
        } else {
            x++;
            z++;
            return counthorizontalRight(x, y, z);
        }

    }

    private int countVerticalDown(int x, int y, int z) {


        if (isGameObjectOrTiles(x, y)) {
            return z - 1 > 0 ? z - 1 : 0;
        } else {
            y--;
            z++;
            return countVerticalDown(x, y, z);
        }

    }

    private int countVerticalUp(int x, int y, int z) {


        if (isGameObjectOrTiles(x, y)) {
            return z - 1 > 0 ? z - 1 : 0;
        } else {
            y++;
            z++;
            return countVerticalUp(x, y, z);
        }

    }

    //method check a specfic location whether have gameObject
    public boolean isGameObjectOrTiles(int x, int y) {
        if (isTiles(x, y))
            return true;

        Brick test = new Brick(holder, x * Tile.WIDTH, y * Tile.HEIGHT);
        for (GameObject object : gameObjectManager.getGameObjects()) {

            if (object.collisionBox(0, 0).intersects(test.collisionBox(0, 0))) {
                //System.out.println(test.getPosX() + "  " + test.getPosY());
                //System.out.println(object.getClass() + " " + object.getPosX() + " " + object.getPosY());
                return true;
            }
        }

        return false;

    }

    public boolean isGameObjectOrTilesNoBomb(int x, int y) {
        if (isTiles(x, y))
            return true;

        Brick test = new Brick(holder, x * Tile.WIDTH, y * Tile.HEIGHT);
        for (GameObject object : gameObjectManager.getGameObjects()) {

            if (object.collisionBox(0, 0).intersects(test.collisionBox(0, 0))) {
                //System.out.println(test.getPosX() + "  " + test.getPosY());
                //System.out.println(object.getClass() + " " + object.getPosX() + " " + object.getPosY());
                if (!(object instanceof Bomb) && !(object instanceof Blast))
                    return true;
            }
        }

        return false;

    }

    public boolean isBrickOrTilesOrBomb(int x, int y, GameObject me) {
        if (isTiles(x, y))
            return true;

        Brick test = new Brick(holder, x * Tile.WIDTH, y * Tile.HEIGHT);
        for (GameObject object : gameObjectManager.getGameObjects()) {

            if (object.equals(me))
                continue;
            if (object.collisionBox(0, 0).intersects(test.collisionBox(0, 0))) {

                if (object instanceof Bomb || object instanceof Brick)
                    //System.out.println(test.getPosX() + "  " + test.getPosY());
                    //System.out.println(object.getClass() + " " + object.getPosX() + " " + object.getPosY());
                    return true;
            }
        }

        return false;

    }

    public boolean isBrickOrTilesOrBomborBomberman(int x, int y, GameObject me) {
        if (isTiles(x, y))
            return true;

        Brick test = new Brick(holder, x * Tile.WIDTH, y * Tile.HEIGHT);
        for (GameObject object : gameObjectManager.getGameObjects()) {

            if (object.equals(me))
                continue;
            if (object.collisionBox(0, 0).intersects(test.collisionBox(0, 0))) {

                if (object instanceof Bomb || object instanceof Brick || object instanceof Bomberman)
                    //System.out.println(test.getPosX() + "  " + test.getPosY());
                    //System.out.println(object.getClass() + " " + object.getPosX() + " " + object.getPosY());
                    return true;
            }
        }

        return false;

    }

    public boolean isBomb(int x, int y) {

        Brick test = new Brick(holder, x * Tile.WIDTH, y * Tile.HEIGHT);
        for (GameObject object : gameObjectManager.getGameObjects()) {

            if (!(object instanceof Bomb))
                continue;
            if (object.collisionBox(0, 0).intersects(test.collisionBox(0, 0))) {

                    return true;
            }
        }

        return false;

    }


    //method check a specfic location whether have gameObject
    public boolean isTiles(int x, int y) {

        return getTile(x, y) != Tile.tileFloor;

    }

    public boolean isTiles(double x, double y) {

        int xx = (int) x / Tile.WIDTH;
        int yy = (int) y / Tile.HEIGHT;

        return isTiles(xx, yy);

    }


    //get tiles in a specific location (posX,posY array)
    public Tile getTile(int x, int y) {

        //if posX and posY is out of the map, return floor as default
        if (x < 0 || y < 0 || x >= width || y >= height)
            return null;
        Tile tile = Tile.tiles[getTiles()[x][y]];
        if (tile == null)
            return Tile.tileFloor; // default tiles
        //System.out.println(tile.id==0?"tileFloor":"tileWall");
        return tile;
    }

    //getter

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //setter anf getters
    public int[][] getTiles() {
        return tiles;
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public GameObjectManager getGameObjectManager() {
        return gameObjectManager;
    }

    public void setGameObjectManager(GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
    }


    //13/3
    public ArrayList<Node> AStarAlgorithm(int sx, int sy, int tx, int ty) {

        ArrayList<Node> open = new ArrayList<>();
        Set<Node> closed = new HashSet<>();
        Node startNode = nodes[sx][sy];
        //System.out.println(sx+"  "+sy);
        Node targetNode = nodes[tx][ty];
        open.add(startNode);
        int countTileFloor = 0;
        walkableTiles = -1;

        while (!open.isEmpty()) {

            //System.out.println(open.size());
            Node currentNode = lowestFcost(open);
            countTileFloor++;


            open.remove(currentNode);
            closed.add(currentNode);


            if (currentNode.equals(targetNode)) {
                return calPath(startNode, targetNode);

            }


            for (Node neighbour : getNeighbours(currentNode)) {

                if (!neighbour.traversable || closed.contains(neighbour))
                    continue;

                int newCostToNeighbour = currentNode.getgCost() + getDistance(currentNode, neighbour);
                if (newCostToNeighbour < neighbour.getgCost() || !open.contains(neighbour)) {

                    neighbour.setgCost(newCostToNeighbour);
                    neighbour.sethCost(getDistance(neighbour, targetNode));
                    neighbour.setParent(currentNode);

                    if (!open.contains(neighbour))
                        open.add(neighbour);
                }


            }

        }
        //System.out.println(countTileFloor);
        //System.out.println("Not possible");
        walkableTiles = countTileFloor;
        return open;

    }


    public void createNodes() {

        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {

                nodes[i][j] = new Node(holder, i, j);

            }
        }

    }

    public void updateTraversableNoBomberman() {

        for (int i = 0; i < nodes.length; i++)
            for (int j = 0; j < nodes[i].length; j++)
                updateTraversable(nodes[i][j]);


    }

    public void updateTraversable() {

        for (int i = 0; i < nodes.length; i++)
            for (int j = 0; j < nodes[i].length; j++)
                updateTraversable(nodes[i][j]);


    }

    public void updateTraversable(Smart s) {

        for (int i = 0; i < nodes.length; i++)
            for (int j = 0; j < nodes[i].length; j++)
                if (s instanceof Dall)
                    updateTraversableNoBomberman(nodes[i][j]);
                else
                    updateTraversable(nodes[i][j]);


    }

    public void updateTraversable(Node n) {

        n.traversable = !isBrickOrTilesOrBomb(n.getX(), n.getY(), new Ballons(holder, 0, 0));
    }

    public void updateTraversableNoBomberman(Node n) {

        n.traversable = !isBrickOrTilesOrBomborBomberman(n.getX(), n.getY(), new Ballons(holder, 0, 0));
    }

    public ArrayList<Node> getNeighbours(Node node) {
        ArrayList<Node> neighbours = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (Math.abs(x) == Math.abs(y))
                    continue;

                int newX = node.getX() + x;
                int newY = node.getY() + y;

                if (newX >= 0 && newX < nodeWidth && newY >= 0 && newY < nodeHeight) {
                    neighbours.add(nodes[newX][newY]);
                }
            }
        }

        return neighbours;
    }

    private Node lowestFcost(ArrayList<Node> open) {
        Node currentNode = open.get(0);
        for (Node n : open) {
            if (n.getfCost() <= currentNode.getfCost() && n.gethCost() < currentNode.gethCost())
                currentNode = n;
        }
        return currentNode;
    }


    private ArrayList<Node> calPath(Node startNode, Node endNode) {

        ArrayList<Node> path = new ArrayList<>();
        Node currentNode = endNode;
        while (!currentNode.equals(startNode)) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);


        //System.out.println("Ans" + path);
        path = patchPath(path);
        //System.out.println(path);
        return path;

    }

    public ArrayList<Node> patchPath(ArrayList<Node> unpatch) {

        ArrayList<Node> patched = unpatch;
        if (!unpatch.isEmpty()) {

            for (int i = 0; i < unpatch.size(); i++) {

                if (i + 1 >= unpatch.size()) {
                    if (i + 1 >= unpatch.size() && patched.get(i).getNextDirection() == null)
                        patched.get(i).setNextDirection(Directions.STAY);
                } else {

                    Node thisNode = patched.get(i);
                    Node nextNode = patched.get(i + 1);
                    if (thisNode.getDirection(nextNode) == Directions.LEFT)
                        thisNode.setNextDirection(Directions.LEFT);
                    else if (thisNode.getDirection(nextNode) == Directions.RIGHT)
                        thisNode.setNextDirection(Directions.RIGHT);
                    else if (thisNode.getDirection(nextNode) == Directions.UP)
                        thisNode.setNextDirection(Directions.UP);
                    else if (thisNode.getDirection(nextNode) == Directions.DOWN)
                        thisNode.setNextDirection(Directions.DOWN);
                }

            }

        }
        return patched;
    }

    public int getDistance(Node nodeA, Node nodeB) {
        int dstX = Math.abs(nodeA.getX() - nodeB.getX());
        int dstY = Math.abs(nodeA.getY() - nodeB.getY());
        return (int) Math.sqrt(dstX * dstX + dstY * dstY);
        //System.out.println(dstX+"  "+ dstY);

        /*
        if (dstX > dstY) {
            return 45 * dstY + 32 * (dstX - dstY);
        }
        return 45 * dstX + 32 * (dstY - dstX);
        */
    }

    public ArrayList<Node> findAndDelete(ArrayList<Node> list, Node n) {

        for (Node node : list) {
            if (node.equals(n))
                list.remove(n);
        }
        return list;

    }


    //Test generateWorld
    public static void main(String[] args) {

        World.generateWorld(17, 35);
        //Rectangle rect1 = new Rectangle(288, 160, 32, 32);
        // rect2 = new Rectangle(288, 128, 32, 32);
        //Rectangle intersection = rect1.intersection(rect2);
        //System.out.println(rect1.intersects(rect2));

    }
}
