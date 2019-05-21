package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class View extends JFrame implements Observer {

    public static final String TITLE="Zuma: Continuation";

    Model model;
    LinkedList<Ball> balls;
    GamePane gamePane;

    JMenuBar menuBar;
    JMenu menu;

    View(Model model){
        this.model=model;
        model.registerObserver(this);
        balls=model.balls;

        setGamePaneProperties();
        setMenuProperties();
        setWindowProperties();
    }

    private void setGamePaneProperties() {
        gamePane=new GamePane();
    }

    void setMenuProperties(){
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
        setContentPane(gamePane);
        pack();
    }

    @Override
    public void update(int i) {
        if(balls.get(i)!=null){
            gamePane.add(balls.get(i),JLayeredPane.PALETTE_LAYER);
        }
    }

}


