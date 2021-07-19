/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public class AlienPiece extends SpritePiece {


    BCDraugthsApp bcdg;
    final static  String DRAUGTH_IMAGE="black_dama.png";
    static final  String CHEKCER_IMAGE="alien_checker.png";
    
   public AlienPiece( Piece boardPiece, BoardHW boardHW, FXBoard board) {
        super("Alien",boardHW, CHEKCER_IMAGE,board);
        this.piece = boardPiece;

    }
   private AlienPiece(String img, Piece boardPiece, BoardHW boardHW, FXBoard board) {
       super("Alien",boardHW, img,board);
       this.piece = boardPiece;

   }



    @Override
    public void buildDestroyAnimation(int by) {
        buildGenericFrameAnimation(EATED_ANIM_FRAME[0], EATED_ANIM_FRAME[1], 0.2d, false, 50, FrameAnimationTimer.EXPLOSION);
    }

    public void buildFrameMoveAnimation(double frac, boolean ciclyc) {
        if (!draugthTransform) {
            frameAnimTimer[0] = new FrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1],0, this, frac, ciclyc, 50, FrameAnimationTimer.MOVEBLACK);
        } else {
            frameAnimTimer[0] = new FrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1], 0,this, frac, ciclyc, 100, FrameAnimationTimer.DAMAMOVE_B);
        }

    }

    public void buildFrameEatMoveAnimation(double frac, boolean ciclyc) {

       if(draugthTransform==false) frameAnimTimer[0] = new FrameAnimationTimer(EAT_MOVE_FRAME[0], EAT_MOVE_FRAME[1],EAT_MOVE_FRAME[0], this, frac, ciclyc, 100, FrameAnimationTimer.MOVEBLACK);
       else frameAnimTimer[0] = new FrameAnimationTimer(EAT_MOVE_FRAME[0], EAT_MOVE_FRAME[1],EAT_MOVE_FRAME[0] ,this, frac, ciclyc, 2, FrameAnimationTimer.DAMAMOVE_B);
    }

    

    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (piece.getType() == Checker.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new AlienPiece(DRAUGTH_IMAGE,this.piece, FXBoard.boardHW, this.fbx);       
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHB);
            ach.setCycleCount(1);
            ach.play();
            sp.setDraugthTransform(true);
        }
        setFrame(0);
        return sp;

    }

    public void buildPedinaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //m.getP().getI() * wboardBox + ((wboardBox / 2));
        double y0 =  convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
                //m.getP().getJ() * hBoardBox + ((hBoardBox / 2));
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
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
        pathTransition.setDuration(Duration.seconds(1));
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
        double x0 =convertBoardJtoPositionXCenter(m.getP().getJ(),wSquare);
                //( m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(),hSquare);
                //(m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(),wSquare);
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 =convertBoardItoPositionYCenter(m.getI1(),hSquare);
                //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        double xe = 0;
        double ye = 0;
        Path path = null;
        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1 - (hSquare * 2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1 - (hSquare * 2));
        xe = convertBoardJtoPositionXCenter(m.getEat().getJ() , wSquare);
                //(m.getEat().getI() * wboardBox) + (wboardBox / 2);
        ye = convertBoardItoPositionYCenter(m.getEat().getI() , hSquare);
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
        pathTransition.setDuration(Duration.seconds(1));
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
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //m.getP().getI() * wboardBox + ((wboardBox / 2));
        double y0 = convertBoardItoPositionY(m.getP().getI(), hSquare);
                //m.getP().getJ() * hBoardBox + ((hBoardBox / 2));
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
                //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        double xe = 0;
        double ye = 0;
        Path path = null;
        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1 - (hSquare * 2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1 - (hSquare * 2));
        xe = convertBoardJtoPositionXCenter(m.getEat().getJ(), wSquare);
                
                //(m.getEat().getI() * wSquare) + (wSquare / 2);
        ye = convertBoardItoPositionYCenter(m.getEat().getI(), hSquare);
                
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
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //m.getP().getI() * wSquare + ((wSquare / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
                //m.getP().getJ() * hSquare + ((hSquare / 2));
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
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
                pathTransition.setAutoReverse(false);
               // .build();
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        if(BCDraugthsApp.debug)  this.fbx.add(path);
        pt.getChildren().add(pathTransition);

    }

}
