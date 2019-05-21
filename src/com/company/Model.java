package com.company;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Model implements Observable {

    private List<Observer> observers;
    LinkedList<Ball> balls;

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
    public void notifyObservers(int i) {
        for (Observer observer : observers)
            observer.update(i);
    }

    //balls
    public void addBall(int x,int y){
        int i = random.nextInt(ImageParser.COLORS_NUM);
        balls.add(new Ball(ImageParser.colors[i], new Point(x,y)));
        notifyObservers(balls.size()-1);
    }
    public void addBall(int k,int x,int y){
        int i = random.nextInt(ImageParser.COLORS_NUM);
        balls.add(k,new Ball(ImageParser.colors[i], new Point(x,y)));
        notifyObservers(k);
    }
    public void addBall(int k, Ball ball){
        balls.add(k,ball);
        notifyObservers(k);
    }
    public void setBallPos(int i,int x,int y) throws ArrayIndexOutOfBoundsException{
        if(i<balls.size()){
            balls.get(i).setLocation(new Point(x,y));
        }
        else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    public void removeBall(int i){
        if(i<balls.size()){
            balls.get(i).delete();
            balls.remove(i);
        }
    }
    public int size(){
        return balls.size();
    }

    public Ball get(int i){
        return balls.get(i);
    }
}
