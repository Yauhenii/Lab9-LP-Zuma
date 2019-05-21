package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Ball extends JLabel{


    private Color color;

    public Ball(Color color, Point point) {
        super(new ImageIcon(ImageParser.getBallImage(color)));
        this.color = color;

        BufferedImage image=ImageParser.getBallImage(color);
        Dimension dimension=new Dimension(image.getWidth(),image.getHeight());

        setSize(dimension);
        setLocation(point);
        setBorder(new LineBorder(Color.RED,1));
    }

    public Ball(Ball ball){
        super(new ImageIcon(ImageParser.getBallImage(ball.color)));
        this.color=ball.color;

        BufferedImage image=ImageParser.getBallImage(ball.color);
        Dimension dimension=new Dimension(image.getWidth(), image.getHeight());

        setSize(dimension);
        setLocation(ball.getLocation());
    }

    public void delete(){
        Container parent = this.getParent();
        parent.remove(this);
    }

    public Color getColor() {
        return color;
    }

}
