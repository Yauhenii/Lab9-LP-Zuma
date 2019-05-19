package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageParser {

    //background
    public static final String BACKGROUND_IMAGE_NAME="level1.jpg";

    private static BufferedImage backgroundImage=null;
    //balls
    public static final Color colors[]={Color.BLUE,Color.YELLOW,Color.GREEN,Color.RED,Color.MAGENTA,Color.WHITE};
    public static final String ballFileNames[]={"blueBall.png","yellowBall.png","greenBall.png","redBall.png","magnetaBall.png","whiteBall.png"};
    public static final int COLORS_NUM=colors.length;

    private static BufferedImage ballImages[]=null;

    //
    static{
        try {
            backgroundImage=ImageIO.read(new File(BACKGROUND_IMAGE_NAME));

            ballImages=new BufferedImage[COLORS_NUM];
            for(int i=0;i<COLORS_NUM;i++){
                ballImages[i]=ImageIO.read(new File(ballFileNames[i]));
            }
        } catch (IOException e){
            System.err.println("Error");
        }
    }

    //methods
    public static BufferedImage getBallImage(Color color){
        for(int i=0;i<COLORS_NUM;i++){
            if(color==colors[i]){
                return ballImages[i];
            }
        }
        return null;
    }
    public static BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
}
