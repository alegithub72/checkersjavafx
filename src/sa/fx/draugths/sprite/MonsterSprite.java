/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoardClass;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author appleale
 */
public class MonsterSprite extends AlienPiece {
 
    public MonsterSprite(int color, Piece boardPiece, 
            int wbBox, int hbBox, String img, FXBoardClass board) {
        super(color,  boardPiece, wbBox, hbBox, img,board);
       
        setFrame(0);
    }
    
 
    
    @Override
    public void buildDestroyAnimation(int by) {
        System.out.println("EAT BY "+((by==Piece.CHECKER)?"CHECKERS":"DAMA"));
        if (by==Piece.CHECKER ) {
            buildGenericFrameAnimation(15, 19, 0.2d, false, 100, FrameAnimationTimer.EXPLOSION);
        } else {
            buildGenericFrameAnimation(8, 14, 0, false, 100, FrameAnimationTimer.EXPLOSION);
        }

    }
    @Override
    public void setFrameDama() {
            if (boardPieceLink.getType() == Checker.DRAUGTH
                && draugthTransform == false) {
            draugthTransform = true;
            frameImages = new Image("black_dama2.png");
            imgView.setImage(frameImages);
            buildFrameImages();
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHB);
            ach.setCycleCount(1);
            ach.play();
        }
        setFrame(0); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void buildFrameMoveAnimation(double frac, boolean ciclyc) {
        if (!draugthTransform) {
            frameAnimTimer[0] = new FrameAnimationTimer(2, 3, this, frac, ciclyc, 100, FrameAnimationTimer.MOVEBLACK);
        } else {
            frameAnimTimer[0] = new FrameAnimationTimer(1, 4, this, frac, ciclyc, 100, FrameAnimationTimer.MOVEMONSTER);
        }

    }    
    public void buildFrameEatMoveAnimation(double frac, boolean ciclyc) {

       if(draugthTransform==false) frameAnimTimer[0] = new FrameAnimationTimer(4, 7, this, frac, ciclyc, 100, FrameAnimationTimer.MOVEBLACK);
       else frameAnimTimer[0] = new FrameAnimationTimer(2, 4, this, frac, ciclyc, 100, FrameAnimationTimer.MOVEMONSTER);
    }    
    
    @Override
   protected void  animPedinaEat(Move m) {
    
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = Sprite.convertBoardIpositionCenter(m.getP().getI(), wSquare);
                //m.getP().getI() * wSquare + ((wSquare / 2));
        double y0 = Sprite.convertBoardJpositionCenter(m.getP().getJ(), hSquare);
                //m.getP().getJ() * hSquare + ((hSquare / 2));
        double x1 =  Sprite.convertBoardIpositionCenter(m.getI1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 =Sprite.convertBoardJpositionCenter(m.getJ1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        double xe = 0;
        double ye = 0;
        Path path=null,path2 = null;
        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1 - (hSquare * 2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1 - (hSquare * 2));
        xe = Sprite.convertBoardIpositionCenter(m.getP().getI(), wSquare);
                //(m.getEat().getI() * wSquare) + (wSquare / 2);
        ye =Sprite.convertBoardJpositionCenter(m.getJ1(), hSquare);
                //(m.getEat().getJ() * hSquare) + (hSquare / 2);
        quadTo.setX(xe);
        quadTo.setY(ye);
        quadTo2.setX(x1);
        quadTo2.setY(y1);
        path = PathBuilder.create()
                .elements(
                        new MoveTo(x0, y0),
                        quadTo
                )
                .build();
        path2 = PathBuilder.create()
                .elements(
                        new MoveTo(x0, y0+32),
                        quadTo
                )
                .build();
        path.getElements().add(new MoveTo(xe, ye));
        path.getElements().add(quadTo2);
        path2.getElements().add(new MoveTo(xe, ye+32));
        path2.getElements().add(quadTo2);
        
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
   
        
        
        PathTransition pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build();

        //pathTransition.setNode(laser);
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

        
         extraSprite[0] =new Sprite(64*3, 96, wSquare, hSquare,  "LASERmONSTER.png",this.fbx);
                PathTransition pathTransition2 = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path2)
                .node(extraSprite[0])
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build();
    
        pt.getChildren().add(pathTransition2);
        fbx.add(extraSprite[0]);
        extraSprite[0].setVisible(true);
        frameAnimTimer[1] = new FrameAnimationTimer(0, 1, extraSprite[0], 0, true, 100, FrameAnimationTimer.FIRE);
        frameAnimTimer[1].start();
        buildFrameEatMoveAnimation( 0f, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m,eated, w, h,this.fbx));
        eated.buildDestroyAnimation(m.getP().getType());


    }     


    
    
}
