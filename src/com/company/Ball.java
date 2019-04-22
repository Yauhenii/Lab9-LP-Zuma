package com.company;

import java.awt.*;

public class Ball {
    private Point coordinate;
    private Color color;

    public Point getCoordinate() {
        return coordinate;
    }

    public Color getColor() {
        return color;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    Ball(Point point, Color color){
        coordinate=new Point(point);
        this.color=color;
    }
    Ball(int x,int y, Color color){
        coordinate=new Point(x,y);
        this.color=color;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "coordinate=" + coordinate +
                ", color=" + color +
                '}';
    }
}
