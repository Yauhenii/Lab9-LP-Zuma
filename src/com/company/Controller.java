package com.company;

public class Controller {
    Controller(){
        Model model=new Model();
        View view=new View(model);
        view.setVisible(true);
        for(int i=0;i<8;i++){
            for(int j=0;j<10;j++)
                model.addBall(j*100,i*100);
        }
    }
}
