package game.graphics;

import java.awt.image.BufferedImage;

/**
 * Created by Jack on 4/3/2017.
 */
public class Animation {

    public BufferedImage[] images;
    private int index;
    private int counter;
    private double length;

    public Animation(BufferedImage[] images) {

        this.images = images;
        index = 0;
    }

    public void update() {


        if (counter > length) {
            index++;
            counter = 0;
            if (index > images.length-1)
                index = 0;
        }

        counter++;
    }

    public BufferedImage getImage(double speed) {

        this.length = speed;
        return images[index];
    }

}
