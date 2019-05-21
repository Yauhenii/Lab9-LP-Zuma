package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageParser {

    //lose background
    public static final String LOSE_BACKGROUND_IMAGE_NAME="losebackground.jpg";

    private static BufferedImage lbackgroundImage=null;
    //win background
    public static final String WIN_BACKGROUND_IMAGE_NAME="winbackground.jpg";

    private static BufferedImage wbackgroundImage=null;
    //background
    public static final String MENU_BACKGROUND_IMAGE_NAME="menubackground.jpg";

    private static BufferedImage mbackgroundImage=null;

    //texture
    public static final String TEXTURE_IMAGE_NAME="texture.png";

    private static BufferedImage textureImage=null;
    //putin
    public static final String PUTIN_IMAGE_NAME="putin.png";

    private static BufferedImage putinImage=null;

    //background
    public static final String BACKGROUND2_IMAGE_NAME="background2.jpg";

    private static BufferedImage background2Image=null;

    //background
    public static final String BACKGROUND_IMAGE_NAME="background.jpg";

    private static BufferedImage backgroundImage=null;
    //balls
    public static final Color colors[]={Color.BLUE,Color.YELLOW,Color.GREEN,Color.RED,Color.WHITE};
    public static final String ballFileNames[]={"blueBall.png","yellowBall.png","greenBall.png","redBall.png","whiteBall.png"};
    public static final int COLORS_NUM=colors.length;

    private static BufferedImage ballImages[]=null;

    //
    static{
        try {
            //lose
            lbackgroundImage=ImageIO.read(new File(LOSE_BACKGROUND_IMAGE_NAME));
            lbackgroundImage=scale(lbackgroundImage,2.1);
            //win bg
            wbackgroundImage=ImageIO.read(new File(WIN_BACKGROUND_IMAGE_NAME));
            wbackgroundImage=scale(wbackgroundImage,2.1);
            //menu bg
            mbackgroundImage=ImageIO.read(new File(MENU_BACKGROUND_IMAGE_NAME));
            mbackgroundImage=scale(mbackgroundImage,2.1);
            //texture
            textureImage=ImageIO.read(new File(TEXTURE_IMAGE_NAME));
            //putin
            putinImage=ImageIO.read(new File(PUTIN_IMAGE_NAME));
            putinImage=scale(putinImage,2.1);
            //background
            backgroundImage=ImageIO.read(new File(BACKGROUND_IMAGE_NAME));
            backgroundImage=scale(backgroundImage,2.1);
            //background
            background2Image=ImageIO.read(new File(BACKGROUND2_IMAGE_NAME));
            background2Image=scale(background2Image,2.1);
            //balls
            ballImages=new BufferedImage[COLORS_NUM];
            for(int i=0;i<COLORS_NUM;i++){
                ballImages[i]=ImageIO.read(new File(ballFileNames[i]));
            }
        } catch (IOException e){
            System.err.println("Error");
        }
    }

    //private methods
    private static BufferedImage scale(BufferedImage imageToScale, double c) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            int dWidth=(int) (((double)imageToScale.getWidth())/c);
            int dHeight=(int) (((double)imageToScale.getHeight())/c);
            scaledImage = new BufferedImage(dWidth,dHeight , imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    //public methods
    public static BufferedImage getLoseBackgroundImage(){
        return lbackgroundImage;
    }
    public static BufferedImage getWinBackgroundImage(){
        return wbackgroundImage;
    }
    public static BufferedImage getMenuBackgroundImage(){
        return mbackgroundImage;
    }
    public static BufferedImage getTextureImage(){
        return textureImage;
    }
    public static BufferedImage getPutinImage(){
        return putinImage;
    }
    public static BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
    public static BufferedImage getBackground2Image() {
        return background2Image;
    }
    public static BufferedImage getBallImage(Color color){
        for(int i=0;i<COLORS_NUM;i++){
            if(color==colors[i]){
                return ballImages[i];
            }
        }
        return null;
    }

//    public static Dimension getTextureDim(){
//
//    }
    public static Dimension getPutinDim(){
        return new Dimension(putinImage.getWidth(),putinImage.getHeight());
    }
    public static Dimension getBackgroundDim(){
        return new Dimension(backgroundImage.getWidth(),backgroundImage.getHeight());
    }
    public static Dimension getBallDim(){
        return new Dimension(ballImages[0].getWidth(),ballImages[0].getHeight());
    }
    public static int getBallRadius(){
        return Math.min(ballImages[0].getWidth(),ballImages[0].getHeight());
    }
}
