/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import sa.boardgame.core.moves.*;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public class AlienPiece extends SpritePiece {

    int color;
    BCDraugthsApp bcdg;

   public AlienPiece(int color, Piece boardPiece, int wbBox, int hbBox, 
            String img, FXBoard board) {
        super("Alien",wbBox, hbBox, img,board);
        this.color = color;
        this.boardPieceLink = boardPiece;

    }




    @Override
    public void buildDestroyAnimation(int by) {
        buildGenericFrameAnimation(9, 19, 0.2d, false, 100, FrameAnimationTimer.EXPLOSION);
    }

    public void buildFrameMoveAnimation(double frac, boolean ciclyc) {
        if (!draugthTransform) {
            frameAnimTimer[0] = new FrameAnimationTimer(3, 5, this, frac, ciclyc, 50, FrameAnimationTimer.MOVEBLACK);
        } else {
            frameAnimTimer[0] = new FrameAnimationTimer(2, 4, this, frac, ciclyc, 100, FrameAnimationTimer.DAMAMOVE_B);
        }

    }

    public void buildFrameEatMoveAnimation(double frac, boolean ciclyc) {

       if(draugthTransform==false) frameAnimTimer[0] = new FrameAnimationTimer(3, 5, this, frac, ciclyc, 100, FrameAnimationTimer.MOVEBLACK);
       else frameAnimTimer[0] = new FrameAnimationTimer(3, 5, this, frac, ciclyc, 2, FrameAnimationTimer.DAMAMOVE_B);
    }

    

    public void setFrameDama() {
        if (boardPieceLink.getType() == Checker.DRAUGTH
                && draugthTransform == false) {
            draugthTransform = true;
            frameImages = new Image("black_dama.png");
            imgView.setImage(frameImages);
            buildFrameImages();
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHB);
            ach.setCycleCount(1);
            ach.play();
        }
        setFrame(0);

    }

    public void buildPedinaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = Sprite.convertBoardJpositionCenter(m.getP().getJ(), wSquare);
                //m.getP().getI() * wboardBox + ((wboardBox / 2));
        double y0 =  Sprite.convertBoardIpositionCenter(m.getP().getI(), hSquare);
                //m.getP().getJ() * hBoardBox + ((hBoardBox / 2));
        double x1 = Sprite.convertBoardJpositionCenter(m.getJ1(), wSquare);
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = Sprite.convertBoardIpositionCenter(m.getI1(), hSquare);
                //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));

        Path path = null;
        Color color = Color.CHARTREUSE;

        quadTo.setControlX(x1);
        quadTo.setControlY(y1);
        quadTo.setX(x1);
        quadTo.setY(y1);
        path = new Path();
        path.getElements().add(new MoveTo(x0, y0)); 
        		path.getElements().add(quadTo);
        
        		
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        
        pathTransition.setAutoReverse(true);
                
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
       if(BCDraugthsApp.debug)  this.fbx.add(path);
        pt.getChildren().add(pathTransition);

    }

    public void buildPedinaMoveEatPath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 =Sprite.convertBoardJpositionCenter(m.getP().getJ(),wSquare);
                //( m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = Sprite.convertBoardIpositionCenter(m.getP().getI(),hSquare);
                //(m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));
        double x1 = Sprite.convertBoardJpositionCenter(m.getJ1(),wSquare);
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 =Sprite.convertBoardIpositionCenter(m.getI1(),hSquare);
                //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        double xe = 0;
        double ye = 0;
        Path path = null;
        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1 - (hSquare * 2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1 - (hSquare * 2));
        xe = Sprite.convertBoardJpositionCenter(m.getEat().getJ() , wSquare);
                //(m.getEat().getI() * wboardBox) + (wboardBox / 2);
        ye = Sprite.convertBoardIpositionCenter(m.getEat().getI() , hSquare);
                //(m.getEat().getJ() * hBoardBox) + (hBoardBox / 2);
        quadTo.setX(xe);
        quadTo.setY(ye);
        quadTo2.setX(x1);
        quadTo2.setY(y1);
        path = new Path();
        path.getElements().add(new MoveTo(x0, y0));
        path.getElements().add(quadTo);
                
        path.getElements().add(new MoveTo(xe, ye));
        path.getElements().add(quadTo2);

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        
      
        
     
        
        
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
                //.build();

        //pathTransition.setNode(laser);
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);
        if(BCDraugthsApp.debug)  this.fbx.add(path);
        
    }

    @Override
    public void buildDamaMoveEatPath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = Sprite.convertBoardJpositionCenter(m.getP().getJ(), wSquare);
                //m.getP().getI() * wboardBox + ((wboardBox / 2));
        double y0 = Sprite.convertBoardIposition(m.getP().getI(), hSquare);
                //m.getP().getJ() * hBoardBox + ((hBoardBox / 2));
        double x1 = Sprite.convertBoardJpositionCenter(m.getJ1(), wSquare);
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = Sprite.convertBoardIpositionCenter(m.getI1(), hSquare);
                //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        double xe = 0;
        double ye = 0;
        Path path = null;
        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1 - (hSquare * 2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1 - (hSquare * 2));
        xe = Sprite.convertBoardJpositionCenter(m.getEat().getJ(), wSquare);
                
                //(m.getEat().getI() * wSquare) + (wSquare / 2);
        ye = Sprite.convertBoardIpositionCenter(m.getEat().getI(), hSquare);
                
                //(m.getEat().getJ() * hSquare) + (hSquare / 2);
        quadTo.setX(xe);
        quadTo.setY(ye);
        quadTo2.setX(x1);
        quadTo2.setY(y1);
        path = new Path();
        path.getElements().add(new MoveTo(x0, y0));
        path.getElements().add(quadTo);
        
        path.getElements().add(new MoveTo(xe, ye));
        path.getElements().add(quadTo2);

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        
        PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.seconds(2));
                pathTransition.setPath(path);
                pathTransition.setNode(this);
                pathTransition.setOrientation(PathTransition.OrientationType.NONE);
                pathTransition.setCycleCount(1);
                pathTransition.setAutoReverse(true);
                //.build();
        
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);
    }

    @Override
    public void buildDamaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = Sprite.convertBoardJpositionCenter(m.getP().getJ(), wSquare);
                //m.getP().getI() * wSquare + ((wSquare / 2));
        double y0 = Sprite.convertBoardIpositionCenter(m.getP().getI(), hSquare);
                //m.getP().getJ() * hSquare + ((hSquare / 2));
        double x1 = Sprite.convertBoardJpositionCenter(m.getJ1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = Sprite.convertBoardIpositionCenter(m.getI1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        double xe = 0;
        double ye = 0;
        Path path = null;
        Color color = Color.CHARTREUSE;

        quadTo.setControlX(x1);
        quadTo.setControlY(y1);
        quadTo.setX(x1);
        quadTo.setY(y1);
        path = new Path();
        path.getElements().add(new MoveTo(x0, y0));
        path.getElements().add(quadTo);
                //.build();

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.seconds(2));
                pathTransition.setPath(path);
                pathTransition.setNode(this);
                pathTransition.setOrientation(PathTransition.OrientationType.NONE);
                pathTransition.setCycleCount(1);
                pathTransition.setAutoReverse(true);
               // .build();
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

    }

}
