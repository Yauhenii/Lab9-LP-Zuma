package com.company;

import javax.swing.*;
import java.awt.*;

public class MenuBackgroundPane extends JPanel {
    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(ImageParser.getMenuBackgroundImage(),0,0,null);
    }
}