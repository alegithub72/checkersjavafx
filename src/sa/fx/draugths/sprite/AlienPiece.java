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
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundPlay;
import sa.gameboard.core.Piece;

/**
 *
 * @author  Alessio Sardaro
 */
public class AlienPiece extends SpritePiece {


    BCDraugthsApp bcdg;
    private static final  String DRAUGTH_ALIEN_IMAGE="alien_checker_dama.png";
    private static final  String CHEKCER_ALIEN_IMAGE="alien_checker.png";
    
   public AlienPiece( Piece boardPiece, BoardHW boardHW, FXBoard board) {
        super("Alien",boardHW, CHEKCER_ALIEN_IMAGE,board);
        this.piece = boardPiece;

    }
   protected AlienPiece( Piece boardPiece,String img,String colorFX, BoardHW boardHW, FXBoard board) {
       super(colorFX,boardHW, img,board);
       this.piece = boardPiece;

   }



    @Override
    public void buildKilledSequence(Move m) {
    	pltransition=new ParallelTransition(this);
        buildDefaultKillAnimation(killSequenceFrame,m,  false, 50, SoundPlay.EXPLOSION);
    }

    public void buildMoveSequence(boolean ciclyc) {
    	SimpleFrameAnimation transition=null;
        if (!draugthTransform) {
        	transition=new  SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 50, SoundPlay.CLOPETE);
        	pltransition.getChildren().add(transition);
        	transition.setDuration(pltransition.getTotalDuration());
        } else {
        	transition=new SimpleFrameAnimation(moveSequenceFrame,this ,ciclyc, 50, SoundPlay.CLOPETE_DOUBLE);
        	pltransition.getChildren().add( transition);
        	transition.setDuration(pltransition.getTotalDuration());
        }

    }

    @Override
	public void buildMoveEatSequence(Move m, boolean ciclyc) {
    	//SpritePiece eated=getFxBoard().getSpritePiece(m.getEat().getI(), m.getEat().getJ(),m.getEat().getColor(), false);
    	SimpleFrameAnimation transition=null;
        if(draugthTransform==false) {
        	transition= new SimpleFrameAnimation(eatMoveSequenceFrame, this,m, ciclyc, 50, SoundPlay.CLOPETE_DOUBLE);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add(transition);
        }
        else {
        	transition=new SimpleFrameAnimation(eatMoveSequenceFrame,this,m, ciclyc, 50, SoundPlay.CLOPETE_DOUBLE);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add(transition );
        }
		
	}

    

    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (piece.getType() == Piece.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new AlienPiece(this.piece,DRAUGTH_ALIEN_IMAGE,"SkyALien" ,FXBoard.boardHW, this.getFxBoard());    
            BCDraugthsApp.soundPlay.playSound(SoundPlay.ACHB, 1);
            sp.setDraugthTransform(true);
        }
        setFrame(0);
        return sp;

    }

    public void buildPedinaMovePath(Move m) {
 
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
                
        pltransition.getChildren().add(pathTransition);
       if(BCDraugthsApp.tracepath)   this.getFxBoard().add(path);


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
        SpritePiece eated=fxBoard.getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(), true);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				BCDraugthsApp.log.info("FIRE EventEatAnimPiece.KILLPLAY_EVENT at end on animation...:"+this);
				fireEvent(new EventEatAnimPiece(eated,fxBoard,m,EventEatAnimPiece.KILLPLAY_EVENT));
            	
				
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

        sq.getChildren().add(pathTransition);
        sq.getChildren().add(pathTransition2);
        pltransition.getChildren().add(sq);// = sq;
        if(BCDraugthsApp.tracepath)  {
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
         SpritePiece eated=fxBoard.getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(), true);      
         pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				BCDraugthsApp.log.info("FIRE EventEatAnimPiece.KILLPLAY_EVENT At end of animation...");
            	fireEvent(new EventEatAnimPiece(eated,fxBoard ,m, EventEatAnimPiece.KILLPLAY_EVENT));
				
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
        
        
        if(BCDraugthsApp.tracepath)   {
        	this.getFxBoard().add(path);
        	this.getFxBoard().add(path2);
        }
        sq.getChildren().add(pathTransition);
        sq.getChildren().add(pathTransition2);
        pltransition.getChildren().add(sq);
    }

    @Override
    public void buildDamaMovePath(Move m) {

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
        pltransition.getChildren().add(pathTransition);
        if(BCDraugthsApp.tracepath)   this.getFxBoard().add(path);


    }

}
