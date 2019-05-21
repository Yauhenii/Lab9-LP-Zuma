package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PutinPane extends JPanel {

    double tetha;

    PutinPane(){
        tetha=0;
    }

    public void rotate(double tetha){
        this.tetha=tetha;
        repaint();
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        int w2 = getWidth() / 2;
        int h2 = getHeight() / 2;
        g2d.drawImage(ImageParser.getTextureImage(),0,0,getWidth(),getHeight(),null);
        g2d.rotate(tetha, w2, h2);
        g2d.drawImage(ImageParser.getPutinImage(),0,0,null);
        super.paintComponent(g);
    }

}
