package sa.fx.draugths.sprite;

import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.ShotDistanceFrameAnimation;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundPlay;
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
        	SimpleFrameAnimation transition=  new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundPlay.JETPACK);
        	transition.setDuration(pltransition.getTotalDuration());
         	pltransition.getChildren().add( transition);
        } else {
        	SimpleFrameAnimation transition=new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, SoundPlay.ELICOPTER);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add( transition);

        }

    }
    
	@Override
	public void buildMoveEatSequence(Move m, boolean ciclyc) {
	
		SimpleFrameAnimation transition=null;
	    if (!draugthTransform) {
	    	
	    	transition= new ShotDistanceFrameAnimation(eatMoveSequenceFrame, this,m, ciclyc, 20, SoundPlay.LASER);
	    	transition.setDuration(pltransition.getTotalDuration());


	    } else {

	    	transition=new SimpleFrameAnimation(eatMoveSequenceFrame,this ,ciclyc, 20, SoundPlay.ELICOPTER);
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
            BCDraugthsApp.soundPlay.playSound(SoundPlay.ACHB, 1);
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
