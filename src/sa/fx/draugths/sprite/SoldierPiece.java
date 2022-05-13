/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;


import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameInfo;
import sa.fx.draugths.animation.PieceAnimationEndHandler;
import sa.fx.draugths.animation.ShotAnimationEndHandler;
import sa.fx.draugths.animation.ShotCollisionFrameAnimation;
import sa.fx.draugths.animation.ShotDistanceFrameAnimation;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Piece;


/**
 *
 * @author  Alessio Sardaro
 */
public class SoldierPiece extends SpritePiece {

    int color;
    private static final String CHECKER_SOLDIER_IMAGE="soldier_checker.png";
    private static final String DRAUGTH_SOLDIER_IMAGE="soldier_checker_dama.png";
    private static final String DAMA_TAKEOFF="DAMA_TAKEOFF";
    
    public SoldierPiece( Piece boardPiece,
            BoardHW boardHW,  FXBoard board) {
        super( "Soldier",boardHW, CHECKER_SOLDIER_IMAGE,board);
        this.color = boardPiece.getColor();
        this.piece = boardPiece;

    }
    
    public SoldierPiece(String img,Piece boardPiece,
            BoardHW boardHW,  FXBoard board) {
        super( "Soldier",boardHW, img,board);
        this.color = boardPiece.getColor();
        this.piece = boardPiece;

    }




    public synchronized void buildKilledSequence(Move m) {
    	pltransition=new ParallelTransition(this);
    	SimpleFrameAnimation transition=null;
        if (m.getP().getType() ==Piece.CHECKER) {
           
        	if(!draugthTransform) { 

        		 transition= new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SimpleFrameAnimation.BITE);
        		 transition.setDuration(Duration.seconds(0.5));
        		 

        	}
            else BCDraugthsApp.log.info("Errorre.........");
        
        } else if (m.getP().getType()  ==Piece.DRAUGTH)  {
        
          if(draugthTransform) {
        	   transition=new SimpleFrameAnimation(killSequenceFrame, this, false,40, SimpleFrameAnimation.BITE);
        	   transition.setDuration(Duration.seconds(0.5));
          }
          else {
        	   transition=new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SimpleFrameAnimation.BITE); 
        	   transition.setDuration(Duration.seconds(0.5));
          }
              
        }
        SpritePiece eated=this;
        transition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				  if(m.getType()==Move.EAT) { 
					  
					  BCDraugthsApp.log.info("FIRE EventRemoveEatPiece.REMOVE_PIECE_EVENT: "+eated);
					  fireEvent(new EventRemoveEatPiece(eated, fxBoard,EventRemoveEatPiece.REMOVE_PIECE_EVENT));


				  }
				
			}
		});
		pltransition.getChildren().add( transition);

    }

    public void buildMoveSequence( boolean ciclyc) {

        if (!draugthTransform) {
        	SimpleFrameAnimation transition=  new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 100, ShotDistanceFrameAnimation.JUNGLE);
        	transition.setDuration(pltransition.getTotalDuration());
         	pltransition.getChildren().add( transition);
        } else {
        	SimpleFrameAnimation transition=new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, ShotDistanceFrameAnimation.ELICOPTER);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add( transition);

        }

    }

	@Override
	public void buildMoveEatSequence(Move m, boolean ciclyc) {
	
		SimpleFrameAnimation transition=null;
	    if (!draugthTransform) {
	    	
	    	transition= new ShotDistanceFrameAnimation(eatMoveSequenceFrame, this,m, ciclyc, 20, SimpleFrameAnimation.FIRE);
	    	transition.setDuration(pltransition.getTotalDuration());


	    } else {

	    	transition=new SimpleFrameAnimation(eatMoveSequenceFrame,this ,ciclyc, 20, SimpleFrameAnimation.ELICOPTER);
	    	transition.setDuration(pltransition.getTotalDuration());
	    	}
	    pltransition.getChildren().add( transition);

	}





    public void buildPedinaMovePath(Move m) {

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
        
        pltransition.getChildren().add(pathTransition);
        BCDraugthsApp.log.info(this+"  from ("+x0+","+ y0+") to ("+x1+","+y1+")");

        if(BCDraugthsApp.tracepath)   this.getFxBoard().add(path);
    }

    public void buildDamaMovePath(Move m) {

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
        pltransition.getChildren().add(pathTransition);

        if(BCDraugthsApp.tracepath)   this.getFxBoard().add(path);

    }

    public void buildPedinaMoveEatPath(Move m) {
    	

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
        pathTransition.setDuration(Duration.seconds(  1.5));
        pathTransition.setPath(path);
//        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
//        
//        pathTransition.setCycleCount(1);
//        pathTransition.setAutoReverse(false);


                //.build();
        RotateTransition rotateTransition = new RotateTransition();
                // .node(p)
        rotateTransition.setDuration(Duration.seconds(  1.5));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(+1080);
//        rotateTransition.setCycleCount(1);
//        rotateTransition.setNode(this);
//        rotateTransition.setAutoReverse(false);
                //.build();
       // rotateTransition;

//        pt.setCycleCount(1);
//        pt.setAutoReverse(true);
        pltransition.getChildren().add(rotateTransition);
        pltransition.getChildren().add(pathTransition);
        pltransition.setNode(this);
        pltransition.setAutoReverse(true);
        pltransition.setCycleCount(1);

//        pt.getChildren().add(pathTransition);
//        pt.getChildren().add(rotateTransition);
//        transition.add(pt); 
        if(BCDraugthsApp.tracepath)   this.getFxBoard().add(path);
 

    }

    public void buildDamaMoveEatPath(Move m) {
//        ParallelTransition pt = new ParallelTransition(this);
//        ParallelTransition pt2 = new ParallelTransition(this);
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
          pathTransition2.setDelay(Duration.seconds(1));
          
       // transition[TRANSITION_STEP.FIRST_HALF_STEP] = pt;

       // transition[TRANSITION_STEP.SECOND_HALF_STEP] = pt2;

//        pt2.setDelay(Duration.seconds(1));
//        pt.getChildren().add(pathTransition);
//        pt2.getChildren().add(pathTransition2);
        steps.put(DAMA_TAKEOFF, pathTransition);
        
        SequentialTransition seq=new SequentialTransition();
        seq.getChildren().add(pathTransition);
        seq.getChildren().add(pathTransition2);
        pltransition.getChildren().add(seq);
        if(BCDraugthsApp.tracepath)   {
        	this.getFxBoard().add(path);
        	this.getFxBoard().add(pathSecondHalf);
        }

    }

    @Override
    public void builAnimDamaEat(Move m) {

        ParallelTransition ptMissile = new ParallelTransition();
        extraSprite[0]= new Sprite("missile2.png","missile");
        
        this.getFxBoard().add(extraSprite[0]);

        double x =convertBoardJtoPositionXCenter( m.getP().getJ(),wSquare);
        double y = convertBoardItoPositionYCenter(m.getP().getI()-1, hSquare);

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
        pathMissileTransition.setDuration(Duration.seconds(0.8));
        pathMissileTransition.setPath(path);
        pathMissileTransition.setNode(extraSprite[0]);
        pathMissileTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathMissileTransition.setCycleCount(1);
        pathMissileTransition.setAutoReverse(true);

        ptMissile.getChildren().add(pathMissileTransition);
        if(BCDraugthsApp.tracepath)   {
        	this.getFxBoard().add(path);
        }
        
        

        ShotCollisionFrameAnimation missileAnim=new ShotCollisionFrameAnimation(
				 new FrameInfo[] {new FrameInfo(0,1),
						 new FrameInfo(1,1),new FrameInfo(2,1),new FrameInfo(3,1),
						 new FrameInfo(4,1),new FrameInfo(5,1),new FrameInfo(6,1),new FrameInfo(7,1),new FrameInfo(5,1),new FrameInfo(6,1),new FrameInfo(7,1)} 
				 ,m,this,extraSprite[0],true,100,SimpleFrameAnimation.MISSILE);
		 missileAnim.setDuration(ptMissile.getTotalDuration());
	    
		 ptMissile.getChildren().add(missileAnim);       
      
        buildDamaMoveEatPath(m);



        steps.get(DAMA_TAKEOFF).setOnFinished(new EventHandler<ActionEvent>() {


			@Override
			public void handle(ActionEvent event) {
				 extraSprite[0].setVisible(true);
				 ptMissile.play();

		       
			}
		});
        buildMoveEatSequence(m, true);
        pathMissileTransition.setOnFinished(new ShotAnimationEndHandler(extraSprite[0],this));
        pltransition.setOnFinished(new PieceAnimationEndHandler(this, m));
        
    }



    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (piece.getType() == Piece.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new SoldierPiece(DRAUGTH_SOLDIER_IMAGE, this.piece, FXBoard.boardHW, this.getFxBoard());       
            BCDraugthsApp.playMedia(ShotDistanceFrameAnimation.ACHB,1);

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
