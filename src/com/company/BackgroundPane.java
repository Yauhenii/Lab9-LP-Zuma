package com.company;

import javax.swing.*;
import java.awt.*;

public class BackgroundPane extends JPanel {
    @Override
    public void paint(Graphics g)
    {
        g.drawImage(ImageParser.getBackgroundImage(),0,0,null);
    }
}
