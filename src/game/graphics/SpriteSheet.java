package game.graphics;

import java.awt.image.BufferedImage;

/**
 * Created by Jack on 16/11/2016.
 */
public class SpriteSheet {

    public BufferedImage sheet;

    public SpriteSheet(BufferedImage sheet) {

        this.sheet = sheet;
    }

    public BufferedImage crop(int x, int y, int width, int height) {

        return sheet.getSubimage(x, y, width, height);
    }
}
