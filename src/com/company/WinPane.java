package com.company;

import javax.swing.*;
import java.awt.*;

public class WinPane extends JLayeredPane {

    public static final Dimension buttonDim=new Dimension(250,89);

    public JLabel exitButton;
    public JPanel backgroundPane;

    WinPane(){
        setSize(getPreferredSize());
        setBounds(new Rectangle(new Point(0, 0), ImageParser.getBackgroundDim()));
        setBackgroundProperties();
        setButtonsProperties();
    }

    private void setButtonsProperties(){
        exitButton = new JLabel(new ImageIcon("exitButton.png"));
        exitButton.setBounds((getWidth()-buttonDim.width)/2,(getHeight()+2*buttonDim.height)/2,buttonDim.width,buttonDim.height);
        backgroundPane.add(exitButton,JLayeredPane.PALETTE_LAYER);
    }

    private void setBackgroundProperties(){
        backgroundPane=new JPanel(){
            @Override
            public void paintComponent(Graphics g)
            {
                g.drawImage(ImageParser.getWinBackgroundImage(),0,0,null);
            }
        };
        backgroundPane.setLayout(null);
        backgroundPane.setBounds(new Rectangle(new Point(0, 0), ImageParser.getBackgroundDim()));
        add(backgroundPane,JLayeredPane.DEFAULT_LAYER);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ImageParser.getBackgroundImage().getWidth(),ImageParser.getBackgroundImage().getHeight());
    }
}
