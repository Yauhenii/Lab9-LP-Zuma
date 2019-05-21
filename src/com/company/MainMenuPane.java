package com.company;

import javax.swing.*;
import java.awt.*;

public class MainMenuPane extends JLayeredPane {

    public static final Dimension buttonDim=new Dimension(250,89);

    public JLabel playButton;
    public JLabel exitButton;
    public MenuBackgroundPane backgroundPane;

    MainMenuPane(){
        setSize(getPreferredSize());
        setBounds(new Rectangle(new Point(0, 0), ImageParser.getBackgroundDim()));
        setBackgroundProperties();
        setButtonsProperties();
    }

    private void setButtonsProperties(){
        playButton = new JLabel(new ImageIcon("playButton.png"));
        exitButton = new JLabel(new ImageIcon("exitButton.png"));
        playButton.setBounds((getWidth()-buttonDim.width)/2,(getHeight()-4*buttonDim.height)/2,buttonDim.width,buttonDim.height);
        exitButton.setBounds((getWidth()-buttonDim.width)/2,(getHeight()+2*buttonDim.height)/2,buttonDim.width,buttonDim.height);
        backgroundPane.add(playButton,JLayeredPane.PALETTE_LAYER);
        backgroundPane.add(exitButton,JLayeredPane.PALETTE_LAYER);
    }

    private void setBackgroundProperties(){
        backgroundPane=new MenuBackgroundPane();
        backgroundPane.setLayout(null);
        backgroundPane.setBounds(new Rectangle(new Point(0, 0), ImageParser.getBackgroundDim()));
        add(backgroundPane,JLayeredPane.DEFAULT_LAYER);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ImageParser.getBackgroundImage().getWidth(),ImageParser.getBackgroundImage().getHeight());
    }
}
