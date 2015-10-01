/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import dama.core.Move;
import dama.core.PedinaChar;
import javafx.animation.ParallelTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 *
 * @author ale2s_000
 */
public abstract class PedinaGeneral extends Parent{
    Image frameImages;
    ImageView pedina;
    Rectangle2D[] frames;
    int w;
    int h;
    int nframes = 0;
    MoveAnimePedinaTimer t;
    PedinaChar pedinaCharAssociated;
    
    public PedinaGeneral(int w, int h,String img) {
        this.w = w;
        this.h = h;
        frameImages = new Image(img);
        setFrameNumber();
        pedina = new ImageView(frameImages);
        pedina.setViewport(frames[0]);  
        getChildren().add(pedina);
    }
    public void setFrameNumber(){
                int n = (frameImages.widthProperty().intValue() / w);
        frames = new Rectangle2D[n];
        for (int i = 0; i < n; i++) {
            frames[i] = new Rectangle2D(i * w, 0, w, h);
        }
    }
    public void setFrame(int i) {
        this.nframes = i;
        pedina.setViewport(frames[i]);
    }

    public int getFrame() {
        return this.nframes;
    }    
    public void setX(double x){
        pedina.setX(x);
    }
    public void setY(double y){
        pedina.setY(y);
    }    
    public void play() {
        t.start();
    }

    public void stop() {
        t.stop();
    }
    
    public Duration getAnimDuration() {
        return Duration.seconds(1.5);
    }   
    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }    
       public static PedinaGeneral  buildPedina(int w, int h,  int color,PedinaChar pedinassociated,BCDamaGraphic bcdg,int level) {
       String imagePedina=null;
       PedinaGeneral pedina=null;       
        if (PedinaChar.BLACK == color) {
            imagePedina = "pedinaNeraAnim2.png";
            pedina= new PedinaBlack(color, bcdg, pedinassociated, w, h, imagePedina);
        } else {
            imagePedina = "pedinaNeraAnimBattaglia.png";
            pedina= new PedinaWhite(color, bcdg, pedinassociated, w, h, imagePedina);
        }
        
       
       
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setColor(Color.BLACK);
        pedina.setEffect(dropShadow);
        pedina.setOnMouseClicked(new SelectEventPlayer(bcdg,pedina));
        return pedina;

    }
    
    
    public abstract void setEatedAnimation(ParallelTransition parallelTransition) ;
    public abstract void setEatedAnimationByDama(ParallelTransition parallelTransition) ;
    public abstract void setMoveAnimation(ParallelTransition parallelTransition, double frac, boolean ciclyc) ;
    public abstract void setMoveEatAnimation(ParallelTransition parallelTransition, double frac, boolean ciclyc) ;
    public abstract void setFrameDama();
    public abstract Path getMovePath(Move m, double widthSprite, double heightSprite);
    public abstract PedinaChar getPedinaCharAssociated() ;
    public abstract void setPedinaCharAssociated(PedinaChar pedinaCharAssociated);
    
}
