/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import dama.core.Move;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;

/**
 *
 * @author ale2s_000
 */
public class AnimationPedinaMove extends Transition  {
    PedinaWhite pedina;
    Move m;
    int f1,f2;
    BCDamaGraphic bcdg;
    public AnimationPedinaMove(int f1, int f2, PedinaWhite p,Move m,BCDamaGraphic bcdg){
        this.f1=f1;
        this.f2=f2;
        this.pedina=p;
        this.m=m;
        setCycleDuration(Duration.millis(2000));
        this.bcdg=bcdg;                 
        

    }    
    @Override
    protected void   interpolate(double frac) {
     double x1= m.x*pedina.getW();
     double y1= m.y*pedina.getH();

     double x0= m.getP().getX()*pedina.getW();
     double y0= m.getP().getY()*pedina.getH();
     double x01=x0-x1;
     double y01=y0-y1;
     System.out.println("--------------->"+x1*frac);
     System.out.println("--------------->"+y1*frac);
     System.out.println("-----frac---------->"+frac);
    // pedina.setX(x0+(x01*frac));
     //pedina.setY(y0+(y01*frac));      
     pedina.setLayoutX(x0);
     pedina.setLayoutY(y0);
     pedina.setTranslateX(x1*frac);
     pedina.setTranslateY(y1*frac);

     if(frac==1){
         this.stop();
         bcdg.playPlayer2();

     }     
     System.out.println("-----getStatus---------->"+this.getStatus());
    }
    
    
}
