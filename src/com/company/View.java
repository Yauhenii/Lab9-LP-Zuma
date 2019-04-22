package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class View extends JFrame implements Observer {

    public static final String TITLE="Zuma: Continuation";

    Model model;
    LinkedList<Ball> balls;
    GamePane gamePane;

    ImageParser parser;

    View(Model model){
        this.model=model;
        model.registerObserver(this);
        parser=new ImageParser();

        setGamePaneProperties();
        setWindowProperties();
    }

    private void setGamePaneProperties() {
        gamePane=new GamePane(parser);
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
    public void update(LinkedList<Ball> balls) {
        this.balls=balls;
        for(Component c : gamePane.getComponents()){
            if(c instanceof JLabel){
                gamePane.remove(c);
            }
        }
        for(Ball ball : this.balls){
            gamePane.addLabel(ball);
        }
    }
}


