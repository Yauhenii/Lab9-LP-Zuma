package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller {

    int level=1;
    public int TIMER_SPEED=90;
    public int BALL_ADDING_TIMER_SPEED=(int) (TIMER_SPEED*13.5);
    public int BALL_FLYING_TIMER_SPEED=(int) (TIMER_SPEED*0.2);


    public int width;
    public int height;
    public int ballRadius;
    public int radius;

    private int ballTotalNum=30;
    private int ballTotalNum2=40;

    Timer timer;
    Timer ballAddingTimer;
    Timer ballFlyingTimer;
    Timer ballBacktrackingTimer;
    Model model;
    View view;

    Point putinPaneCenter;
    Point windowCenter;

    double phi=0.1;
    double phiBack=0;

    Dimension ballDim;
    Dimension backgroundDim;

    double tethaFlying;

    int num;
    int numOfDeleted;

    boolean isFlying;

    AudioPlayer player;
    AudioPlayer menuPlayer;
    AudioPlayer winPlayer;
    AudioPlayer losePlayer;

    Controller(){
        //MVC
        model=new Model();
        view=new View(model);
        view.setVisible(true);

        //
        //game pane
        //

        //add music
        try {
            player = new AudioPlayer("song.wav");
            menuPlayer = new AudioPlayer("med.wav");
            winPlayer=new AudioPlayer("soviet-anthem.wav");
            losePlayer = new AudioPlayer("sax.wav");
            menuPlayer.play();
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
        isFlying=true;

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
                            numOfDeleted=0;

                            //backward
                            while (i+1<model.size()){
                                if(model.get(i).getColor().getRGB()==model.get(i+1).getColor().getRGB()){
                                    model.removeBall(i+1);
                                    numOfDeleted++;
                                    trigger=true;
                                } else{
                                    break;
                                }
                            }
                            //forward
                            while (i-1>0){
                                if(model.get(i).getColor().getRGB()==model.get(i-1).getColor().getRGB()){
                                    model.removeBall(i-1);
                                    numOfDeleted++;
                                    i--;
                                    trigger=true;
                                } else{
                                    break;
                                }
                            }
                            if(trigger) {
                                //one
                                model.removeBall(i);
                                numOfDeleted++;
                                view.gamePane.repaint();
                            }
                            //check
                            if(i==model.size()){
                                trigger=false;
                                System.out.println(model.size());
                            }
                            if(model.size()==1){
                                if(level==1) {
                                    showWinPane();
                                } else{
                                    showWinEndPane();
                                }
                            }
                            //backtrack
                            if(trigger){
                                num=i;
                                phiBack=phi;
                                ballBacktrackingTimer.start();
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
                if(i<ballTotalNum) {
                        model.addBall(-40, -40);
                        i++;
                }
            }
        });

        //add ball to putin
        model.addBall(windowCenter.x-ballDim.width/2,windowCenter.y-ballDim.height/2);

        num=1;

        //timer
        timer=new Timer(TIMER_SPEED, new ActionListener() {

            double delta=0.12;
            double alpha;

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=num;i<model.size();i++){
                    alpha=phi-(i+numOfDeleted)*(delta);
                    if(alpha<0.1){
                        model.setBallPos(i, -40,-40);
                    } else {
                        model.setBallPos(i, getX(alpha), getY(alpha));
                    }
                }
                phi+=0.01;

                if(phi>2*Math.PI+0.05){
                    showLosePane();
                }
            }
        });
        //ball backtracking timer
        ballBacktrackingTimer=new Timer(TIMER_SPEED, new ActionListener() {
            double delta=0.12;

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=1;i<num;i++){
                    model.setBallPos(i,getX(phiBack-i*delta),getY(phiBack-i*delta));
                }
                phiBack-=0.01;
                if(intersects(model.balls.get(num-1),model.balls.get(num))){
                    num=1;
                    phi=phiBack;
                    numOfDeleted=0;
                    ballBacktrackingTimer.stop();
                }
            }
        });

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

        //
        //main menu
        //

        view.menuPane.playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showGamePane();
//                showWinPane();
            }
        });
        view.menuPane.exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispatchEvent(new WindowEvent(view,WindowEvent.WINDOW_CLOSING));
            }
        });
        view.winPane.exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispatchEvent(new WindowEvent(view,WindowEvent.WINDOW_CLOSING));
            }
        });
        view.winPane.playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showGamePane2();
            }
        });
        view.winEndPane.exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispatchEvent(new WindowEvent(view,WindowEvent.WINDOW_CLOSING));
            }
        });
        view.losePane.exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispatchEvent(new WindowEvent(view,WindowEvent.WINDOW_CLOSING));
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
    public void showGamePane(){
        //set level
        level=1;
        //change
        view.remove(view.menuPane);
        view.setContentPane(view.gamePane);
        player.play();
        menuPlayer.stop();
        isFlying=false;
        ballAddingTimer.start();
        timer.start();
    }
    public void showGamePane2(){
        //set level
        level=2;
        //reset
        phi=0;
        TIMER_SPEED=90/level;
        BALL_ADDING_TIMER_SPEED=(int) (TIMER_SPEED*13.5/level);
        BALL_FLYING_TIMER_SPEED=(int) (TIMER_SPEED*0.2/level);
        //change
        view.remove(view.winPane);
        view.setContentPane(view.gamePane);
        view.gamePane.bgPane.setLevel(2);
        player.play();
        winPlayer.stop();
        isFlying=false;
        //reset timers delay
        ballFlyingTimer.setDelay(BALL_FLYING_TIMER_SPEED);
        ballAddingTimer=new Timer(BALL_ADDING_TIMER_SPEED, new ActionListener() {
            int i=0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i<ballTotalNum2) {
                    model.addBall(-40, -40);
                    i++;
                }
            }
        });
        timer=new Timer(TIMER_SPEED, new ActionListener() {

            double delta=0.12;
            double alpha;

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=num;i<model.size();i++){
                    alpha=phi-(i+numOfDeleted)*(delta);
                    if(alpha<0.1){
                        model.setBallPos(i, -40,-40);
                    } else {
                        model.setBallPos(i, getX(alpha), getY(alpha));
                    }
                }
                phi+=0.01;

                if(phi>2*Math.PI+0.05){
                    showLosePane();
                }
            }
        });
        //ball backtracking timer
        ballBacktrackingTimer=new Timer(TIMER_SPEED, new ActionListener() {
            double delta=0.12;

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=1;i<num;i++){
                    model.setBallPos(i,getX(phiBack-i*delta),getY(phiBack-i*delta));
                }
                phiBack-=0.01;
                if(intersects(model.balls.get(num-1),model.balls.get(num))){
                    num=1;
                    phi=phiBack;
                    numOfDeleted=0;
                    ballBacktrackingTimer.stop();
                }
            }
        });
        ballAddingTimer.start();
        timer.start();
    }
    public void showWinPane(){
        view.remove(view.gamePane);
        view.setContentPane(view.winPane);
        player.stop();
        winPlayer.play();
        isFlying=true;
        ballAddingTimer.stop();
        timer.stop();
    }
    public void showWinEndPane(){
        view.remove(view.gamePane);
        view.setContentPane(view.winEndPane);
        player.stop();
        winPlayer.play();
        isFlying=true;
        ballAddingTimer.stop();
        timer.stop();
    }
    public void showLosePane(){
        view.remove(view.gamePane);
        view.setContentPane(view.losePane);
        player.stop();
        losePlayer.play();
        isFlying=true;
        ballAddingTimer.stop();
        timer.stop();
    }


}
