package game.graphics;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * Created by Jack on 16/11/2016.
 */
public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
