package game.resources;

import game.graphics.SpriteSheet;
import game.graphics.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * Created by Jack on 16/11/2016.
 */
//load Assets to the holder
public class Assets {

    public static final int width = 32, height = 32;
    public static BufferedImage monster, wall, bomb, floor, brick,
            blastLeft, blastRight, blastUp, blastDown, blastCenter,
            bombBoost, bombRangeBoost, speedBoost, portal, menu, lifeBoost;

    public static BufferedImage[] upBomberman, leftBomberman,
            rightBomberman, downBomberman, standBomberman, leftBallons,
            rightBallons, leftSmart, rightSmart ,leftSmartRage, rightSmartRage,
            leftIntelligence ,rightIntelligence;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        SpriteSheet menuSheet = new SpriteSheet(ImageLoader.loadImage("/textures/NES.gif"));

        wall = sheet.crop(0, 0, width, height);
        bomb = sheet.crop(width, 0, width, height);
        monster = sheet.crop(width * 2, 0, width, height);
        floor = sheet.crop(width * 3, 0, width, height);
        brick = sheet.crop(width * 4, 0, width, height);

        blastLeft = sheet.crop(0, height * 2, width, height);
        blastRight = sheet.crop(width * 2, height * 2, width, height);
        blastUp = sheet.crop(width, height, width, height);
        blastDown = sheet.crop(width, height * 3, width, height);
        blastCenter = sheet.crop(width, height * 2, width, height);

        bombBoost = sheet.crop(width * 3, height * 2, width, height);
        bombRangeBoost = sheet.crop(width * 3, height, width, height);
        speedBoost = sheet.crop(width * 4, height, width, height);
        lifeBoost = sheet.crop(width * 3, height * 3, width, height);
        portal = sheet.crop(width * 4, height * 2, width, height);

        upBomberman = new BufferedImage[3];
        leftBomberman = new BufferedImage[3];
        rightBomberman = new BufferedImage[3];
        downBomberman = new BufferedImage[3];

        leftBallons = new BufferedImage[3];
        rightBallons = new BufferedImage[3];

        leftSmart = new BufferedImage[3];
        rightSmart = new BufferedImage[3];

        leftSmartRage = new BufferedImage[3];
        rightSmartRage = new BufferedImage[3];

        leftIntelligence = new BufferedImage[3];
        rightIntelligence = new BufferedImage[3];

        standBomberman = new BufferedImage[1];


        downBomberman[0] = sheet.crop(0, height * 4, width, height);
        downBomberman[1] = sheet.crop(width, height * 4, width, height);
        downBomberman[2] = sheet.crop(width * 2, height * 4, width, height);

        rightBomberman[0] = sheet.crop(0, height * 5, width, height);
        rightBomberman[1] = sheet.crop(width, height * 5, width, height);
        rightBomberman[2] = sheet.crop(width * 2, height * 5, width, height);

        upBomberman[0] = sheet.crop(0, height * 6, width, height);
        upBomberman[1] = sheet.crop(width, height * 6, width, height);
        upBomberman[2] = sheet.crop(width * 2, height * 6, width, height);

        leftBomberman[0] = sheet.crop(0, height * 7, width, height);
        leftBomberman[1] = sheet.crop(width, height * 7, width, height);
        leftBomberman[2] = sheet.crop(width * 2, height * 7, width, height);

        leftBallons[0] = sheet.crop(width * 3, height * 4, width, height);
        leftBallons[1] = sheet.crop(width * 3, height * 5, width, height);
        leftBallons[2] = sheet.crop(width * 3, height * 6, width, height);

        rightBallons[0] = sheet.crop(width * 3, height * 7, width, height);
        rightBallons[1] = sheet.crop(width * 3, height * 8, width, height);
        rightBallons[2] = sheet.crop(width * 3, height * 9, width, height);

        leftSmart[0] = sheet.crop(width * 4, height * 4, width, height);
        leftSmart[1] = sheet.crop(width * 4, height * 6, width, height);
        leftSmart[2] = sheet.crop(width * 4, height * 7, width, height);

        rightSmart[0] = sheet.crop(width * 4, height * 4, width, height);
        rightSmart[1] = sheet.crop(width * 4, height * 5, width, height);
        rightSmart[2] = sheet.crop(width * 4, height * 8, width, height);

        leftSmartRage[0] = sheet.crop(width * 5, height * 4, width, height);
        leftSmartRage[1] = sheet.crop(width * 5, height * 6, width, height);
        leftSmartRage[2] = sheet.crop(width * 5, height * 7, width, height);

        rightSmartRage[0] = sheet.crop(width * 5, height * 4, width, height);
        rightSmartRage[1] = sheet.crop(width * 5, height * 5, width, height);
        rightSmartRage[2] = sheet.crop(width * 5, height * 8, width, height);

        leftIntelligence[0] = sheet.crop(width * 6, height * 4, width, height);
        leftIntelligence[1] = sheet.crop(width * 6, height * 5, width, height);
        leftIntelligence[2] = sheet.crop(width * 6, height * 6, width, height);

        rightIntelligence[0] = sheet.crop(width * 6, height * 7, width, height);
        rightIntelligence[1] = sheet.crop(width * 6, height * 8, width, height);
        rightIntelligence[2] = sheet.crop(width * 6, height * 9, width, height);


        standBomberman[0] = downBomberman[0];

        menu = menuSheet.crop(0, 0, 256, 240);


    }
}
