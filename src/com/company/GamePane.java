package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GamePane extends JLayeredPane {

    ImageHandler handler;

    GamePane(){
        setSize(getPreferredSize());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ImageParser.getBackgroundImage().getWidth(),ImageParser.getBackgroundImage().getHeight());
    }

    void addLabel(Ball ball){
        BufferedImage image=ImageParser.getBallImage(ball.getColor());
        Dimension dimension=new Dimension(image.getWidth(),image.getHeight());

        JLabel label=new JLabel(new ImageIcon(image));
        label.setSize(dimension);
        label.setLocation(new Point(ball.getCoordinate()));
        label.addMouseListener(handler);
        label.addMouseMotionListener(handler);
        label.setBorder(new LineBorder(Color.BLUE,1));
        add(label);
    }

    public class ImageHandler extends MouseAdapter {

        private Point offset;

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel) e.getComponent();
            moveToFront(label);
            offset = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getPoint().x - offset.x;
            int y = e.getPoint().y - offset.y;
            Component component = e.getComponent();
            Point location = component.getLocation();
            location.x += x;
            location.y += y;
            component.setLocation(location);
        }
    }
}
