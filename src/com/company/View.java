package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class View extends JFrame implements Observer {

    public static final String TITLE="Zuma: Continuation";

    Model model;
    LinkedList<Ball> balls;
    GamePane gamePane;
    MainMenuPane menuPane;
    WinPane winPane;
    WinEndPane winEndPane;
    LosePane losePane;

    JMenuBar menuBar;
    JMenu menu;

    View(Model model){
        this.model=model;
        model.registerObserver(this);
        balls=model.balls;

        setMainMenuPaneProperties();
        setGamePaneProperties();
        setWinPaneProperties();
        setWinEndPaneProperties();
        setLosePaneProperties();
//        setMenuProperties();


        setContentPane(menuPane);
        setWindowProperties();

    }

    private void setLosePaneProperties(){
        losePane=new LosePane();
    }

    private void setWinEndPaneProperties(){
        winEndPane=new WinEndPane();
    }

    private void setWinPaneProperties(){
        winPane=new WinPane();
    }

    private void setMainMenuPaneProperties(){
        menuPane=new MainMenuPane();
    }

    private void setGamePaneProperties() {
        gamePane=new GamePane();
    }

    private void setMenuProperties(){
        //menuBar
        menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        //test menu
        menu=new JMenu("Test");
        menuBar.add(menu);
    }
    void setWindowProperties(){
        //window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        setSize(ImageParser.getBackgroundDim());
    }

    @Override
    public void update(int i) {
        if(balls.get(i)!=null){
            gamePane.add(balls.get(i),JLayeredPane.PALETTE_LAYER);
        }
    }

}


