package game.tiles;

import game.resources.Assets;

/**
 * Created by Jack on 17/11/2016.
 */
public class TileWall extends Tile {

    public TileWall(int id) {
        super(Assets.wall, id);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

}
