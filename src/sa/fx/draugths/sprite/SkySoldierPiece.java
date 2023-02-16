package sa.fx.draugths.sprite;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
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
import sa.fx.draugths.animation.ShotDistanceFrameAnimation;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundInterface;
import sa.gameboard.core.Piece;

public class SkySoldierPiece extends SoldierPiece {
	
    private static final String CHECKER_SKYSOLDIER_IMAGE="soldier_checker_fly.png";
    private static final  String DRAUGTH_SKYSOLDIER_IMAGE="soldier_checker_dama_fly.png";


    
    public SkySoldierPiece(Piece piece,
            BoardHW boardHW,  FXBoard board) {
        super(CHECKER_SKYSOLDIER_IMAGE,"SkySoldier",piece, boardHW, board);



    }
     SkySoldierPiece(Piece piece,String img,
            BoardHW boardHW,  FXBoard board) {
        super(img,"SkySoldier",piece, boardHW, board);



    }
    @Override
    public void buildMoveSequence( boolean ciclyc) {

        if (!draugthTransform) {
        	SimpleFrameAnimation transition=  new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundInterface.JETPACK);
        	transition.setDuration(pltransition.getTotalDuration());
         	pltransition.getChildren().add( transition);
        } else {
        	SimpleFrameAnimation transition=new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, SoundInterface.JET);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add( transition);

        }

    }
    public synchronized void buildKilledSequence(Move m) {
    	pltransition=new ParallelTransition(this);
    	SimpleFrameAnimation transition=null;
        if (m.getP().getType() ==Piece.CHECKER) {
           
        	if(!draugthTransform) { 

        		 transition= new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SoundInterface.BITE);
        		 transition.setDuration(Duration.seconds(0.5));
        		 

        	}
            else {
            	BCDraugthsApp.log.severe("Errorre...draugthTransform......?????");
            	
            }
        
        } else if (m.getP().getType()  ==Piece.DRAUGTH)  {
        
          if(draugthTransform) {
        	   transition=new SimpleFrameAnimation(killSequenceFrame, this, false,40, SoundInterface.SAPCESHIP_BUZZ);
        	   transition.setDuration(Duration.seconds(0.5));
          }
          else {
        	   transition=new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SoundInterface.SAPCESHIP_BUZZ); 
        	   transition.setDuration(Duration.seconds(0.5));
          }
              
        }
        SpritePiece eated=this;
        transition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				  if(m.getType()==Move.EAT) { 
					  
					  BCDraugthsApp.log.info("FIRE EventRemoveEatPiece.REMOVE_PIECE_EVENT: "+eated);
					  fireEvent(new EventRemoveEatPiece(eated, eated.getParent(),EventRemoveEatPiece.REMOVE_PIECE_EVENT));


				  }
				
			}
		});
		pltransition.getChildren().add( transition);

    }
	@Override
	public void buildMoveEatSequence(Move m, boolean ciclyc) {
	
		SimpleFrameAnimation transition=null;
	    if (!draugthTransform) {
	    	
	    	transition= new ShotDistanceFrameAnimation(eatMoveSequenceFrame, this,m, ciclyc, 150, SoundInterface.LASER);
	    	transition.setDuration(pltransition.getTotalDuration());


	    } else {

	    	transition=new SimpleFrameAnimation(eatMoveSequenceFrame,this ,ciclyc, 20, SoundInterface.JET);
	    	transition.setDuration(pltransition.getTotalDuration());
	    	}
	    pltransition.getChildren().add( transition);

	}    
    @Override
    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (piece.getType() == Piece.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new SkySoldierPiece( this.piece,DRAUGTH_SKYSOLDIER_IMAGE, FXBoard.boardHW, this.getFxBoard());       
            FXBoard.SoundSystem.playSound(SoundInterface.ACHB, 1);
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
        rotateTransition.setDuration(Duration.seconds(  0.2));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(45);
//        rotateTransition.setCycleCount(1);
//        rotateTransition.setNode(this);
//        rotateTransition.setAutoReverse(false);
                //.build();
       // rotateTransition;

//        pt.setCycleCount(1);
//        pt.setAutoReverse(true);
       // pltransition.getChildren().add(rotateTransition);
        pltransition.getChildren().add(pathTransition);
        pltransition.setNode(this);
        pltransition.setAutoReverse(true);
        pltransition.setCycleCount(1);

//        pt.getChildren().add(pathTransition);
//        pt.getChildren().add(rotateTransition);
//        transition.add(pt); 
        if(BCDraugthsApp.tracepath)   this.getFxBoard().add(path);
 

    }    
}
