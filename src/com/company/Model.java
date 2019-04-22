package com.company;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Model implements Observable {

    private List<Observer> observers;
    private LinkedList<Ball> balls;

    Random random; //for balls colors

    Model(){
        observers=new LinkedList<>();
        balls=new LinkedList<>();
        random=new Random();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(balls);
    }

    //balls
    public void addBall(int x,int y){
        int i= random.nextInt(ImageParser.COLORS_NUM);
        balls.add(new Ball(x,y , ImageParser.colors[i]));
        notifyObservers();
    }
    public void setBallsPos(Point[] points) throws IllegalArgumentException{
        if(points.length!=balls.size()){
            for(int i=0; i<points.length;i++){
                balls.get(i).setCoordinate(points[i]);
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        notifyObservers();
    }
    public void setBallPos(int i,int x,int y) throws ArrayIndexOutOfBoundsException{
        if(i<balls.size()){
            balls.get(i).setCoordinate(new Point(x,y));
        }
        else {
            throw new ArrayIndexOutOfBoundsException();
        }
        notifyObservers();
    }
}
