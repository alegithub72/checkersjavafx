package sa.fx.draugths.sprite;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.PieceAnimationEndHandler;
import sa.fx.draugths.animation.ShotDistanceFrameAnimation;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
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
        	SimpleFrameAnimation transition=  new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundEffect.JETPACK);
        	transition.setDuration(pltransition.getTotalDuration());
         	pltransition.getChildren().add( transition);
        } else {
        	SimpleFrameAnimation transition=new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, SoundEffect.DRONE);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add( transition);

        }

    }
    public synchronized void buildKilledSequence(Move m) {
    	pltransition=new ParallelTransition(this);
    	SimpleFrameAnimation transition=null;
        if (m.getP().getType() ==Piece.CHECKER) {
           
        	if(!draugthTransform) { 

        		 transition= new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SoundEffect.BITE);
        		 transition.setDuration(Duration.seconds(0.5));
        		 

        	}
            else {
            	BCDraugthsApp.log.severe("Errorre...draugthTransform......?????");
            	
            }
        
        } else if (m.getP().getType()  ==Piece.DRAUGTH)  {
        
          if(draugthTransform) {
        	   transition=new SimpleFrameAnimation(killSequenceFrame, this, false,40, SoundEffect.SAPCESHIP_BUZZ);
        	   transition.setDuration(Duration.seconds(0.5));
          }
          else {
        	   transition=new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SoundEffect.SAPCESHIP_BUZZ);
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
	    	
	    	transition= new ShotDistanceFrameAnimation(eatMoveSequenceFrame, this,m, ciclyc, 150, SoundEffect.LASER);
	    	transition.setDuration(pltransition.getTotalDuration());


	    } else {

	    	transition=new SimpleFrameAnimation(eatMoveSequenceFrame,this ,ciclyc, 20, SoundEffect.DRONE);
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
            FXBoard.SoundSystem.playSound(SoundEffect.ACHB, 1);
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
    @Override
    protected void  buildAnimPedinaEat(Move m) {

        buildPedinaMoveEatPath(m);
        buildMoveEatSequence( m,false);
        pltransition.setOnFinished(new PieceAnimationEndHandler(this, m));



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
        pathTransition.setNode(this);
//        
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);


                //.build();
        RotateTransition rotateTransition = new RotateTransition();
        // .node(p)
        rotateTransition.setDuration(Duration.seconds(  1.5));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(+1080);
        rotateTransition.setNode(this);

        rotateTransition.setCycleCount(1);
//        rotateTransition.setNode(this);
        rotateTransition.setAutoReverse(false);
                //.build();
       // rotateTransition;

//        pt.setCycleCount(1);
//        pt.setAutoReverse(true);
        extraSprite[0]= new Sprite("laser.png","laser");
        extraSprite[0].setVisible(true);
        extraSprite[0].setX(x0);
        extraSprite[0].setY(y0);
        extraSprite[0].toFront();
        this.getFxBoard().add(extraSprite[0]);

        SkySoldierPiece questo=this;


        Path laserPath = new Path();
        laserPath.setStroke(color);
        laserPath.setStrokeWidth(2);
        laserPath.getStrokeDashArray().setAll(5d, 5d);

        double x = questo.getLayoutX();
        double y = questo.getLayoutY();
        BCDraugthsApp.log.info("x,y=" + x + "," + y);
        double xn = convertBoardJtoPositionXCenter(m.getEat().getJ(), wSquare);
        //(m.getI1() * wSquare) + ((wSquare / 2));
        double yn = convertBoardItoPositionYCenter(m.getEat().getI(), hSquare);
        MoveTo laserFrom = new MoveTo(x0, y0);
        LineTo lineTo = new LineTo(xn, yn);
        laserPath.getElements().addAll(laserFrom, lineTo);
        PathTransition shootLaser = new PathTransition();
        shootLaser = new PathTransition();
        shootLaser.setNode(extraSprite[0]);
        shootLaser.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        shootLaser.setPath(laserPath);
        questo.getFxBoard().add(laserPath);
        shootLaser.setDuration(Duration.seconds(0.3));

        shootLaser.setCycleCount(1);
        shootLaser.setOnFinished(actionEvent -> {
           questo.getFxBoard().remove(extraSprite[0]);

        });
                    //translateLaser[0].play();





        //timer.start();
        pltransition.getChildren().add(shootLaser);
        pltransition.getChildren().add(rotateTransition);
        pltransition.getChildren().add(pathTransition);
     //   pltransition.setNode(this);
        pltransition.setAutoReverse(true);
        pltransition.setCycleCount(1);

//        pt.getChildren().add(pathTransition);
//        pt.getChildren().add(rotateTransition);
//        transition.add(pt); 
        if(BCDraugthsApp.tracepath)   this.getFxBoard().add(path);
 

    }

}
