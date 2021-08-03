/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import sa.fx.draugths.animation.SimpleFrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author  Alessio Sardaro
 */
public class AlienPiece extends SpritePiece {


    BCDraugthsApp bcdg;
    final static  String DRAUGTH_IMAGE="alien_checker_dama.png";
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
    	
        buildGenericFrameAnimationDestroy(EATED_ANIM_FRAME[0], EATED_ANIM_FRAME[1],  false, 25, FrameAnimationTimer.EXPLOSION);
    }

    public void buildFrameMoveAnimation( boolean ciclyc) {
        if (!draugthTransform) {
            frameAnimTimer.add( new SimpleFrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1], this,  ciclyc, 50, FrameAnimationTimer.CLOPETE));
        } else {
            frameAnimTimer.add( new SimpleFrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1],this, ciclyc, 50, FrameAnimationTimer.CLOPETE_DOUBLE));
        }

    }

    public void buildFrameEatMoveAnimation( Move m,boolean ciclyc) {
    	SpritePiece eated=getFxBoard().getSpritePiece(m.getEat().getI(), m.getEat().getJ(),m.getEat().getColor(), false);
       if(draugthTransform==false) frameAnimTimer.add( new SimpleFrameAnimationTimer(EAT_MOVE_FRAME[0], EAT_MOVE_FRAME[1], this, ciclyc, 50, FrameAnimationTimer.CLOPETE_DOUBLE));
       else frameAnimTimer.add( new SimpleFrameAnimationTimer(EAT_MOVE_FRAME[0], EAT_MOVE_FRAME[1],this, ciclyc, 50, FrameAnimationTimer.CLOPETE_DOUBLE));
    }

    

    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (piece.getType() == Checker.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new AlienPiece(DRAUGTH_IMAGE,this.piece, FXBoard.boardHW, this.getFxBoard());       
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
        pathTransition.setDuration(Duration.seconds(1.5));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        
        pathTransition.setAutoReverse(true);
                
        transition[TRANSITION_STEP.FULL_STEP] = pt;
       if(BCDraugthsApp.debug)   this.getFxBoard().add(path);
        pt.getChildren().add(pathTransition);

    }

    public void buildPedinaMoveEatPath(Move m) {
        SequentialTransition sq = new SequentialTransition(this);
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
        ye = convertBoardItoPositionYCenter(m.getEat().getI() , hSquare)-20;
                //(m.getEat().getJ() * hBoardBox) + (hBoardBox / 2);
        quadTo.setX(xe);
        quadTo.setY(ye);
        quadTo2.setX(x1);
        quadTo2.setY(y1);
        path = new Path();
        path.getElements().add(new MoveTo(x0, y0));
        path.getElements().add(quadTo);
                
        Path path2 = new Path();
        path2.getElements().add(new MoveTo(xe, ye));
        path2.getElements().add(quadTo2);

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        
        path2.setStroke(color);
        path2.setStrokeWidth(2);
        path2.getStrokeDashArray().setAll(5d, 5d);
        
     
        
        
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(0.4));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				BCDraugthsApp.log.info("At end of animation...");
            	fireEvent(new EventEatAnimPiece(null,null , EventEatAnimPiece.EATANIM_EVENT));
				
			}
		});
        
        
        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.seconds(0.5));
        pathTransition2.setPath(path2);
        pathTransition2.setNode(this);
        pathTransition2.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition2.setCycleCount(1);
        pathTransition2.setAutoReverse(true);        
                //.build();
        pathTransition2.setDelay(Duration.seconds(0.6));
        //pathTransition.setNode(laser);
        transition[TRANSITION_STEP.FULL_STEP] = sq;
        sq.getChildren().add(pathTransition);
        sq.getChildren().add(pathTransition2);

        if(BCDraugthsApp.debug)  {
        	this.getFxBoard().add(path);
        	this.getFxBoard().add(path2);
        }
        
    }

    @Override
    public void buildDamaMoveEatPath(Move m) {
        SequentialTransition sq = new SequentialTransition(this);
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
        Path path = new Path();

        path.getElements().add(new MoveTo(x0, y0));
        path.getElements().add(quadTo);
        Path path2 = new Path();
        path2.getElements().add(new MoveTo(xe, ye));
        path2.getElements().add(quadTo2);

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        path2.setStroke(color);
        path2.setStrokeWidth(2);
        path2.getStrokeDashArray().setAll(5d, 5d);
        
        PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.seconds(0.5));
                pathTransition.setPath(path);
                pathTransition.setNode(this);
                pathTransition.setOrientation(PathTransition.OrientationType.NONE);
                pathTransition.setCycleCount(1);
                pathTransition.setAutoReverse(true);
                //.build();
         pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				BCDraugthsApp.log.info("At end of animation...");
            	fireEvent(new EventEatAnimPiece(null,null , EventEatAnimPiece.EATANIM_EVENT));
				
			}
		});
        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.seconds(1));
        pathTransition2.setPath(path2);
        pathTransition2.setNode(this);
        pathTransition2.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition2.setCycleCount(1);
        pathTransition2.setAutoReverse(true);
        pathTransition2.setDelay(Duration.seconds(0.5));
        
        transition[TRANSITION_STEP.FULL_STEP] = sq;
        if(BCDraugthsApp.debug)   {
        	this.getFxBoard().add(path);
        	this.getFxBoard().add(path2);
        }
        sq.getChildren().add(pathTransition);
        sq.getChildren().add(pathTransition2);
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
        transition[TRANSITION_STEP.FULL_STEP] = pt;
        if(BCDraugthsApp.debug)   this.getFxBoard().add(path);
        pt.getChildren().add(pathTransition);

    }

}
