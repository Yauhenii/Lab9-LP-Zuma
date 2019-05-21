package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GamePane extends JLayeredPane {

    BackgroundPane bgPane;
    PutinPane putinPane;
    Dimension putinDim;

    GamePane(){
        setSize(getPreferredSize());
        setPutinProperties();
        setBackgroundProperties();
    }
    private void setPutinProperties(){
        //putin
        putinPane=new PutinPane();
        putinDim=ImageParser.getPutinDim();
        putinPane.setBounds(new Rectangle(new Point((getWidth()-putinDim.width)/2,(getHeight()-putinDim.height)/2), putinDim));
        putinPane.setOpaque(true);
        putinPane.setBackground(new Color(0,0,0,0));
        add(putinPane,JLayeredPane.DEFAULT_LAYER);
    }
    private void setBackgroundProperties(){
        //background
        bgPane=new BackgroundPane();
        bgPane.setBounds(new Rectangle(new Point(0, 0), ImageParser.getBackgroundDim()));
        add(bgPane, JLayeredPane.DEFAULT_LAYER);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ImageParser.getBackgroundImage().getWidth(),ImageParser.getBackgroundImage().getHeight());
    }

}
