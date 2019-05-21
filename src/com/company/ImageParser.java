package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageParser {

    //texture
    public static final String TEXTURE_IMAGE_NAME="texture.png";

    private static BufferedImage textureImage=null;
    //putin
    public static final String PUTIN_IMAGE_NAME="putin.png";

    private static BufferedImage putinImage=null;

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
            //texture
            textureImage=ImageIO.read(new File(TEXTURE_IMAGE_NAME));
            //putin
            putinImage=ImageIO.read(new File(PUTIN_IMAGE_NAME));
            putinImage=scale(putinImage,2.1);
            //background
            backgroundImage=ImageIO.read(new File(BACKGROUND_IMAGE_NAME));
            backgroundImage=scale(backgroundImage,2.1);
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
    public static BufferedImage getTextureImage(){
        return textureImage;
    }
    public static BufferedImage getPutinImage(){
        return putinImage;
    }
    public static BufferedImage getBackgroundImage() {
        return backgroundImage;
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
