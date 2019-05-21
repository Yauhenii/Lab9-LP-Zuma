package com.company;

import javax.swing.*;
import java.awt.*;

public class BackgroundPane extends JPanel {

    public void setLevel(int level) {
        this.level = level;
    }

    private int level;
    BackgroundPane(){
        level=1;
    }
    @Override
    public void paint(Graphics g)
    {
        if (level == 1) {
            g.drawImage(ImageParser.getBackgroundImage(),0,0,null);
        } else if(level==2){
            g.drawImage(ImageParser.getBackground2Image(),0,0,null);
        }
    }
}
