package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;

public class Controller {

    public final int TIMER_SPEED=50;
    public final int BALL_ADDING_TIMER_SPEED=(int) (TIMER_SPEED*13.5);
    public final int BALL_FLYING_TIMER_SPEED=(int) (TIMER_SPEED*0.75);


    public int width;
    public int height;
    public int ballRadius;
    public int radius;

    Timer timer;
    Timer ballAddingTimer;
    Timer ballFlyingTimer;
    Model model;
    View view;

    Point putinPaneCenter;
    Point windowCenter;

    double phi=0;

    Dimension ballDim;
    Dimension backgroundDim;

    double tethaFlying;

    boolean isFlying;

    Controller(){
        //MVC
        model=new Model();
        view=new View(model);
        view.setVisible(true);
        //add music
        try {
            AudioPlayer player = new AudioPlayer("song.wav");
            player.play();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        //points
        putinPaneCenter=new Point(view.gamePane.putinPane.getX()+ImageParser.getPutinDim().width/2,view.gamePane.putinPane.getY()+ImageParser.getPutinDim().height/2);
        windowCenter=new Point(view.gamePane.getWidth()/2,view.gamePane.getHeight()/2);
        //dims
        ballDim = ImageParser.getBallDim();
        backgroundDim=ImageParser.getBackgroundDim();

        width=view.getWidth();
        height=view.getHeight();
        ballRadius=ImageParser.getBallRadius();
        radius=Math.min(width,height)/2-ballRadius;

        //flying ball
        isFlying=false;

        //ballFlying timer
        ballFlyingTimer=new Timer(BALL_FLYING_TIMER_SPEED, new ActionListener() {
            Point currPos;
            @Override
            public void actionPerformed(ActionEvent e) {
                currPos=model.balls.get(0).getLocation();
                //is out
                if(currPos.x<-ballDim.width || currPos.y<-ballDim.height || currPos.x>backgroundDim.width || currPos.y>backgroundDim.height ){
                    //stop
                    model.removeBall(0);
                    model.addBall(0,windowCenter.x-ballDim.width/2,windowCenter.y-ballDim.height/2);
                    isFlying=false;
                    ballFlyingTimer.stop();
                } else {
                    model.setBallPos(0, (int) (currPos.x + 10 * (Math.cos(tethaFlying))), (int) (currPos.y + 10 * (Math.sin(tethaFlying))));
                    for(int i=1;i<model.size();i++){  //intersection of flying ball and ball in the line
                        if(intersects(model.balls.get(0),model.balls.get(i))){

                            Ball ball=new Ball(model.balls.get(0));
                            //add ball to the line
                            model.addBall(i,ball);
                            //stop
                            model.removeBall(0);
                            model.addBall(0,windowCenter.x-ballDim.width/2,windowCenter.y-ballDim.height/2);
                            isFlying=false;
                            ballFlyingTimer.stop();
                            //delete if match
                            boolean trigger=false;

                            while (i+1<model.size()){
                                if(model.get(i).getColor().getRGB()==model.get(i+1).getColor().getRGB()){
                                    model.removeBall(i+1);
                                    trigger=true;
                                } else{
                                    break;
                                }
                            }

                            while (i-1>0){
                                if(model.get(i).getColor().getRGB()==model.get(i-1).getColor().getRGB()){
                                    model.removeBall(i-1);
                                    i--;
                                    trigger=true;
                                } else{
                                    break;
                                }
                            }
                            if(trigger){
                                model.removeBall(i);
                            }
                        }
                    }
                }
            }
        });

        //ballAdding timer
        ballAddingTimer=new Timer(BALL_ADDING_TIMER_SPEED, new ActionListener() {
            int i=0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i<17) {
                    model.addBall(-40, -40);
                    i++;
                }
            }
        });

        //add ball to putin
        model.addBall(windowCenter.x-ballDim.width/2,windowCenter.y-ballDim.height/2);

        //timer
        timer=new Timer(TIMER_SPEED, new ActionListener() {

            double delta=0.12;

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=1;i<model.size();i++){
                    model.setBallPos(i,getX(phi-i*delta),getY(phi-i*delta));
                }
                phi+=0.01;
            }
        });
        ballAddingTimer.start();
        timer.start();

        view.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                double tetha=-Math.atan2(e.getX()-putinPaneCenter.x,e.getY()-putinPaneCenter.y)+Math.PI/2;
                view.gamePane.putinPane.rotate(tetha);
            }
        });

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!isFlying) {
                    tethaFlying = -Math.atan2(e.getX() - putinPaneCenter.x, e.getY() - putinPaneCenter.y) + Math.PI / 2;
                    ballFlyingTimer.start();
                    isFlying=true;
                }
            }
        });
    }

    int getX(double phi){
        return (int)(radius*Math.cos(phi)+width/2-ballRadius);
    }

    int getY(double phi){
        return (int)(radius*Math.sin(phi)+height/2-ballRadius);
    }

    public boolean intersects(JLabel testa, JLabel testb){
        Rectangle rectB = testb.getBounds();
        Rectangle result = SwingUtilities.computeIntersection(testa.getX(), testa.getY(), testa.getWidth(), testa.getHeight(), rectB);
        return (result.getWidth() > 0 && result.getHeight() > 0);
    }

}
