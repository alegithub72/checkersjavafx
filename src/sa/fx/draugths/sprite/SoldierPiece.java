/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;


import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.animation.SimpleFrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;


/**
 *
 * @author ale2s_000
 */
public class SoldierPiece extends SpritePiece {

    int color;
    static final String CHEKCER_IMAGE="soldier_checker.png";
    static final String DRAUGTH_IMAGE="soldier_checker_dama.png";

    
    public SoldierPiece( Piece boardPiece,
            BoardHW boardHW,  FXBoard board) {
        super( "Soldier",boardHW, CHEKCER_IMAGE,board);
        this.color = boardPiece.getColor();
        this.piece = boardPiece;

    }
    
    private SoldierPiece(String img,Piece boardPiece,
            BoardHW boardHW,  FXBoard board) {
        super( "Soldier",boardHW, img,board);
        this.color = boardPiece.getColor();
        this.piece = boardPiece;

    }




    public synchronized void buildDestroyAnimation(int by) {
        if (by ==Piece.CHECKER) {
           
        	if(!draugthTransform)  frameAnimTimer.add(new SimpleFrameAnimationTimer(EATED_ANIM_FRAME[0], EATED_ANIM_FRAME[1], this, false, 100, FrameAnimationTimer.BITE));
            else BCDraugthsApp.log.info("Errorre.........");
        
        } else if (by ==Piece.DRAUGTH)  {
        
          if(draugthTransform) frameAnimTimer.add(new SimpleFrameAnimationTimer(EATED_ANIM_FRAME[0], EATED_ANIM_FRAME[1], this, false, 100, FrameAnimationTimer.BITE) );
          else frameAnimTimer.add(new SimpleFrameAnimationTimer(EATED_ANIM_FRAME[0], EATED_ANIM_FRAME[1], this, false, 100, FrameAnimationTimer.BITE) ); 
              
        }
    }

    public void buildFrameMoveAnimation( boolean ciclyc) {

        if (!draugthTransform) {
        	frameAnimTimer.add( new SimpleFrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1], this, ciclyc, 100, FrameAnimationTimer.JUNGLE));
        } else {
        	frameAnimTimer.add(new SimpleFrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1], this, ciclyc, 20, FrameAnimationTimer.ELICOPTER));
            //t = new MoveAnimePedinaTimer(5, 6, this, frac, ciclyc, 100,MoveAnimePedinaTimer.DAMAMOVE_W);
        }

    }



    public void buildFrameEatMoveAnimation( Move m,boolean ciclyc) {
    	
    if (!draugthTransform) {
    	frameAnimTimer.add( new FrameAnimationTimer(EAT_MOVE_FRAME[0], EAT_MOVE_FRAME[1],EAT_MOVE_FRAME[0], this, ciclyc, 100, FrameAnimationTimer.FIRE));

    } else {
    	//TODO:not used
    	frameAnimTimer.add(new SimpleFrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1], this, ciclyc, 20, FrameAnimationTimer.ELICOPTER));
        //t = new MoveAnimePedinaTimer(5, 6, this, frac, ciclyc, 100,MoveAnimePedinaTimer.DAMAMOVE_W);
    	}
    }



    public void buildPedinaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        
        //javafx.scene.shape.
        double x0 =convertBoardJtoPositionXCenter(m.getP().getJ(),wSquare); 
       
               // (m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI() ,hSquare); 
                
                //(m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));

        Color color = Color.ANTIQUEWHITE;
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(),wSquare); 
                
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1() ,hSquare);
                
                //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        arc.setX(x1);
        arc.setY(y1);
       // arc.setRadiusX(-10);
       // arc.setRadiusY(-10);
        arc.setControlX(x1);
        arc.setControlY(y1);

        MoveTo to = new MoveTo();
        //to.setAbsolute(true);
        to.setX(x0);
        to.setY(y0);
        
        Path path = new Path();
        path.getElements().addAll(
           
                        to,
                        arc
              )
                ;
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
                //
        
        transition[TRANSITION_STEP.FULL_STEP] = pt;
        BCDraugthsApp.log.info(this+"  from ("+x0+","+ y0+") to ("+x1+","+y1+")");
        pt.getChildren().add(pathTransition);
        if(BCDraugthsApp.debug)   this.getFxBoard().add(path);
    }

    public void buildDamaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        QuadCurveTo arc2 = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 =convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        BCDraugthsApp.log.info(" move from (x0,y0)=("+x0+","+y0+")");
        arc.setX(x1);
        arc.setY(y1);
        if(y0<y1) {
            arc.setControlX(x0);
            arc.setControlY(y0-100);        	
        }
        else {
            arc.setControlX(x0);
            arc.setControlY(y0-200);       
        }

        MoveTo from = new MoveTo(x0, y0);
        Path path = new Path();
        path.getElements().add(from);

        path.getElements().add(arc);
                //
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
                //.build();
        transition[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);
        if(BCDraugthsApp.debug)   this.getFxBoard().add(path);

    }

    public void buildPedinaMoveEatPath(Move m) {
    	 transition[TRANSITION_STEP.FULL_STEP] =null;
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 =convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
        //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        arc.setX(x1);
        arc.setY(y1);
        arc.setControlX(x0);
        arc.setControlY(y1 - (h * 2));

        MoveTo from = new MoveTo(x0, y0);
        Path path = new Path();
               path.getElements().add(from);
               path.getElements().add(arc);
              
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(1.5));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);


                //.build();
        RotateTransition rotateTransition = new RotateTransition();
                // .node(p)
        rotateTransition.setDuration(Duration.seconds(1.5));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(+1080);
        rotateTransition.setCycleCount(1);
        rotateTransition.setNode(this);
        rotateTransition.setAutoReverse(true);
                //.build();
       // rotateTransition;
        transition[TRANSITION_STEP.FULL_STEP] = pt;
//        pt.setCycleCount(1);
//        pt.setAutoReverse(true);
        pt.getChildren().add(pathTransition);
        if(BCDraugthsApp.debug)   this.getFxBoard().add(path);
        pt.getChildren().add(rotateTransition);

    }

    public void buildDamaMoveEatPath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        ParallelTransition pt2 = new ParallelTransition(this);
        //javafx.scene.shape.
    
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 = convertBoardItoPositionYCenter((m.getP().getI()), hSquare);
        double ydecollo0 = convertBoardItoPositionYCenter((m.getP().getI()-1), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));
        
        Color color = Color.CHARTREUSE;
        double x1 =convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double ydecollo1 =convertBoardItoPositionYCenter(m.getI1()-1, hSquare);
        double y1 =convertBoardItoPositionYCenter(m.getI1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        LineTo moveDecollo0 = new LineTo();
        moveDecollo0.setX(x0);
        moveDecollo0.setY(ydecollo0);
        /**
         * if (m.x > m.getP().getX()) arc.setXAxisRotation(3.14/3); else arc.setXAxisRotation(3.14);
         */

        //arc.setControlX(x0);
        //arc.setControlY(y1 - (hSquare * 2));
        LineTo moveAree = new LineTo();
        moveAree.setX(x1);
        moveAree.setY(ydecollo1);
        LineTo moveAtteraggio = new LineTo();
        moveAtteraggio.setX(x1);
        moveAtteraggio.setY(y1);
        MoveTo from = new MoveTo(x0, y0);
        MoveTo start2 = new MoveTo(x0, ydecollo0);
        Path path = new Path();
             path.getElements().add(from);
             path.getElements().add(moveDecollo0);


        Path pathSecondHalf = new Path(); 
        	pathSecondHalf.getElements().add(start2);
        	pathSecondHalf.getElements().add(moveAree);
        	pathSecondHalf.getElements().add(moveAtteraggio);
        	
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        
        Color color2 = Color.BROWN;
        pathSecondHalf.setStroke(color2);
        pathSecondHalf.setStrokeWidth(2);
        pathSecondHalf.getStrokeDashArray().setAll(5d, 5d);
        
        PathTransition pathTransition = new PathTransition();
          pathTransition.setDuration(Duration.seconds(0.5));
          pathTransition.setPath(path);
          pathTransition.setNode(this);
          pathTransition.setOrientation(PathTransition.OrientationType.NONE);
          pathTransition.setCycleCount(1);
          pathTransition.setAutoReverse(true);
                //
          
          PathTransition pathTransition2 = new PathTransition();
          pathTransition2.setDuration(Duration.seconds(1.5));
          pathTransition2.setPath(pathSecondHalf);
          pathTransition2.setNode(this);
          pathTransition2.setOrientation(PathTransition.OrientationType.NONE);
          pathTransition2.setCycleCount(1);
          pathTransition2.setAutoReverse(true); 
          
        transition[TRANSITION_STEP.FIRST_HALF_STEP] = pt;
        transition[TRANSITION_STEP.SECOND_HALF_STEP] = pt2;
        transition[TRANSITION_STEP.SECOND_HALF_STEP].setDelay(Duration.seconds(1));
        pt.getChildren().add(pathTransition);
        pt2.getChildren().add(pathTransition2);
        
        if(BCDraugthsApp.debug)   {
        	this.getFxBoard().add(path);
        	this.getFxBoard().add(pathSecondHalf);
        }

    }

    @Override
    public void animDamaEat(Move m) {
    	transition=new ParallelTransition[5];
        ParallelTransition ptMissile = new ParallelTransition();
        extraSprite[0]= new Sprite("missile2.png");
        
        this.getFxBoard().add(extraSprite[0]);
        //x missile.toFront();
        double x =convertBoardJtoPositionXCenter( m.getP().getJ(),wSquare);
        double y = convertBoardItoPositionYCenter(m.getP().getI()-1, hSquare);
                //m.getP().getJ();
        double xe=convertBoardJtoPositionXCenter(m.getEat().getJ(), wSquare);
        double ye=convertBoardItoPositionYCenter(m.getEat().getI(), hSquare);
        extraSprite[0].setVisible(false);
        extraSprite[0].setX(x);
        extraSprite[0].setY(y);
        extraSprite[0].toFront();
        QuadCurveTo qTO=new QuadCurveTo();
        MoveTo mt=new MoveTo();
        mt.setX(x);
        mt.setY(y);
        qTO.setX(xe);
        qTO.setY(ye);
        qTO.setControlX(x-150);
        qTO.setControlY(y-150);
        /**
         * missile.setX(x*wBoardSquare);
        missile.setY((y*hBoardSquare));
         */
        Path path = new Path();
        path.getElements().addAll(
                mt,
                qTO
                )
                ;
        PathTransition pathMissileTransition = new PathTransition();
        pathMissileTransition.setDuration(Duration.seconds(1));
        pathMissileTransition.setPath(path);
        pathMissileTransition.setNode(extraSprite[0]);
        pathMissileTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathMissileTransition.setCycleCount(1);
        pathMissileTransition.setAutoReverse(true);
                //.build();

        //table.getChildren().add(path);
        ptMissile.getChildren().add(pathMissileTransition);
        if(BCDraugthsApp.debug)   {
        	this.getFxBoard().add(path);
        }
        buildDamaMoveEatPath(m);
        SpritePiece eated= this.getFxBoard().getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(), true);
        transition[TRANSITION_STEP.MISSILE_FULL_STEP] = ptMissile;
//        frameAnimTimer.add( new SimpleFrameAnimationTimer(0, 2, extraSprite[0],false,50,SimpleFrameAnimationTimer.MISSILE)); 
        		//frameAnimTimer[MOVE_ANIM].start();
        

       // eated.buildDestroyAnimation(Piece.DRAUGTH);
       // buildFrameMoveAnimation( true);
        //setFrame(3);
        
        frameAnimTimer.add(new FrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1], this,extraSprite[0],eated,true,25,SimpleFrameAnimationTimer.ELICOPTER));
        transition[TRANSITION_STEP.FIRST_HALF_STEP].setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				 extraSprite[0].setVisible(true);
				 ptMissile.play();
				 SimpleFrameAnimationTimer missileAnim=new SimpleFrameAnimationTimer(0, 7, extraSprite[0],true,50,SimpleFrameAnimationTimer.MISSILE);
				 missileAnim.start();
				 frameAnimTimer.add(missileAnim);
		         transition[TRANSITION_STEP.SECOND_HALF_STEP].play();
				 //frameAnimTimer[0].start();
		       
			}
		});
       
      
        transition[TRANSITION_STEP.SECOND_HALF_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m));
        
    }

    @Override
    protected void playAnimDamaEat() {
        for(int h=0;h<frameAnimTimer.size();h++) {
            if(frameAnimTimer.get(h)!=null) {
            	frameAnimTimer.get(h).start();
            }
        }
    	if(transition[TRANSITION_STEP.FIRST_HALF_STEP]!=null)
    		transition[TRANSITION_STEP.FIRST_HALF_STEP].play();
            
        

        if(fxBoard!=null) fxBoard.setAnimationOn(true);

  

    }

    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (piece.getType() == Checker.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new SoldierPiece(DRAUGTH_IMAGE, this.piece, FXBoard.boardHW, this.getFxBoard());       
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHB);
            ach.setCycleCount(1);
            ach.play();
            sp.setDraugthTransform(true);
            //buildFrameImages();

//            setScaleX(0.64);
//            setScaleY(0.64);
          // setW(96);setH(96);

        }
        setFrame(0);
        //recalculateXYPosition();
        return sp;

    }


}
