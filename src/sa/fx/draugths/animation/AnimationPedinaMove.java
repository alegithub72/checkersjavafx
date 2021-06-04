/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import sa.boardgame.core.moves.*;
import javafx.animation.Transition;
import javafx.util.Duration;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.sprite.HumanPiece;

/**
 *
 * @author ale2s_000
 */
public class AnimationPedinaMove extends Transition  {
    HumanPiece pedina;
    Move m;
    int f1,f2;
    BCDraugthsApp bcdg;
    private AnimationPedinaMove(int f1, int f2, HumanPiece p,Move m,BCDraugthsApp bcdg){
        this.f1=f1;
        this.f2=f2;
        this.pedina=p;
        this.m=m;
        setCycleDuration(Duration.millis(2000));
        this.bcdg=bcdg;                 
        

    }    
    @Override
    protected void   interpolate(double frac) {
     double x1= m.getI1()*pedina.getW();
     double y1= m.getJ1()*pedina.getH();

     double x0= m.getP().getI()*pedina.getW();
     double y0= m.getP().getJ()*pedina.getH();
     double x01=x0-x1;
     double y01=y0-y1;

     
     pedina.setLayoutX(x0);
     pedina.setLayoutY(y0);
     pedina.setTranslateX(x1*frac);
     pedina.setTranslateY(y1*frac);

     if(frac==1){
         this.stop();
        // bcdg.playPlayer2();

     }     
     
    }
    
    
}
