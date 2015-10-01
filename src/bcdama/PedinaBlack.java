/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import dama.core.PedinaChar;

import javafx.scene.image.Image;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.QuadCurveTo;
import dama.core.Move;
import javafx.animation.ParallelTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;


/**
 *
 * @author ale2s_000
 */
public class PedinaBlack extends PedinaGeneral {





    int color;
    BCDamaGraphic bcdg;






    public PedinaChar getPedinaCharAssociated() {
        return pedinaCharAssociated;
    }

    public void setPedinaCharAssociated(PedinaChar pedinaCharAssociated) {
        this.pedinaCharAssociated = pedinaCharAssociated;
    }
     PedinaBlack(int color, BCDamaGraphic bcdg, PedinaChar pedinaCharAssociated, int w, int h, String img) {
        super(w, h, img);
        this.color = color;
        this.bcdg = bcdg;
        this.pedinaCharAssociated = pedinaCharAssociated;
    }    

    
    



    private void setEatedAnimation(int f1, int f2, ParallelTransition parallelTransition, double frac, boolean ciclyc, long interval,String sound) {
        t = new MoveAnimePedinaTimer(f1, f2, this, parallelTransition, frac, ciclyc, interval,sound);
    }
   
    public void setEatedAnimationByDama(ParallelTransition pt){
        if(this.pedinaCharAssociated.getType()==PedinaChar.PEDINA) setEatedAnimation(12,18,pt,0,false,100,MoveAnimePedinaTimer.BIGEXPLOSION);
        else setEatedAnimation(5,10,pt,0,false,50,MoveAnimePedinaTimer.BIGEXPLOSION);
    }
    
    public void setEatedAnimation(ParallelTransition pt) {
        if(this.pedinaCharAssociated.getType()==PedinaChar.PEDINA) setEatedAnimation(9, 19, pt, 0.2d, false,100,MoveAnimePedinaTimer.EXPLOSION);
        else setEatedAnimation(5, 10, pt, 0.2d, false,50,MoveAnimePedinaTimer.EXPLOSION);

    }

    public void setMoveAnimation(ParallelTransition parallelTransition, double frac, boolean ciclyc) {
            if (pedinaCharAssociated.getType() == pedinaCharAssociated.PEDINA) {
                t = new MoveAnimePedinaTimer(5, 6, this, parallelTransition, frac, ciclyc, 100,MoveAnimePedinaTimer.MOVEBLACK);
            } else {
                t = new MoveAnimePedinaTimer(2, 4, this, parallelTransition, frac, ciclyc, 100,MoveAnimePedinaTimer.DAMAMOVE_B);
            }
        
    }

    public void setMoveEatAnimation(ParallelTransition parallelTransition, double frac, boolean ciclyc) {
            if (pedinaCharAssociated.getType() == pedinaCharAssociated.PEDINA) {
                t = new MoveAnimePedinaTimer(5, 6, this, parallelTransition, frac, ciclyc, 100,MoveAnimePedinaTimer.MOVEBLACK);
            } else {
                t = new MoveAnimePedinaTimer(2, 4, this, parallelTransition, frac, ciclyc, 100,MoveAnimePedinaTimer.DAMAMOVE_B);
            }
        
    }





    public void setFrameDama() {
            if (pedinaCharAssociated.getType() == PedinaChar.PEDINA) {
                frameImages = new Image("pedinaNeraAnimDama2.png");
                pedina.setImage(frameImages);
                setFrameNumber();
            }
            setFrame(0);
        
    }



    public Path getMovePath(Move m, double widthSprite, double heightSprite) {
        QuadCurveTo quadTo = new QuadCurveTo();
        double x0 = m.getP().getX() * widthSprite;
        double y0 = m.getP().getY() * heightSprite;
        double inc = 100;
        Color color = Color.CHARTREUSE;
        if (m.type == Move.EAT || m.type == Move.DAMAEEAT) {
            if (this.pedinaCharAssociated.getColor() == PedinaChar.BLACK) {
                inc = 50;
            } else {
                inc = 100;
            }
            color = Color.CHARTREUSE;
        } else {
            if (this.pedinaCharAssociated.getColor() == PedinaChar.BLACK) {
                inc = 100;
            } else {
                inc = 50;
            }

            color = Color.FLORALWHITE;
        }
        if (m.x > m.getP().getX()) {
            quadTo.setControlX(x0 + inc);
        } else {
            quadTo.setControlX(x0 - inc);
        }
        quadTo.setControlY(y0 + 22);
        quadTo.setX((m.x * widthSprite) + 32);
        quadTo.setY((m.y * heightSprite) + 22);
        Path path = PathBuilder.create()
                .elements(
                        new MoveTo(x0 + 32, y0 + 22),
                        quadTo
                //,
                //new CubicCurveTo(0, 120, 0, 240, 380, 240)
                )
                .build();
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        return path;
    }
   private Path getEatPathNE(PedinaChar e,double heightSprite,double widthSprite){
                Path path = PathBuilder.create()
                .elements(
                    new MoveTo(1*widthSprite+10,5*heightSprite+10),
                    new QuadCurveTo( (1*widthSprite+(widthSprite)), (5*heightSprite),
                            (2*widthSprite+20), (4*heightSprite+10)
                            )
                    )
                .build();
        
        
        return path;
    }

}
