package game.tiles;

import game.resources.Assets;


/**
 * Created by Jack on 17/11/2016.
 */
public class TileFloor extends Tile {

    public TileFloor(int id) {
        super(Assets.floor, id);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}
