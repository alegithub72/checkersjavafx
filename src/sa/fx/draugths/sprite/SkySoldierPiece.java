package sa.fx.draugths.sprite;

import javafx.scene.media.AudioClip;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.ShotDistanceFrameAnimation;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Piece;

public class SkySoldierPiece extends SoldierPiece {
	
    private static final String CHECKER_SKYSOLDIER_IMAGE="soldier_checker_fly.png";
    private static final  String DRAUGTH_SKYSOLDIER_IMAGE="soldier_checker_dama_fly.png";


    
    public SkySoldierPiece(Piece boardPiece,
            BoardHW boardHW,  FXBoard board) {
        super(CHECKER_SKYSOLDIER_IMAGE,"SkySoldier",boardPiece, boardHW, board);
        this.color = boardPiece.getColor();
        this.piece = boardPiece;


    }
     SkySoldierPiece(Piece boardPiece,String img,
            BoardHW boardHW,  FXBoard board) {
        super(img,"SkySoldier",boardPiece, boardHW, board);
        this.color = boardPiece.getColor();
        this.piece = boardPiece;


    }
    @Override
    public void buildMoveSequence( boolean ciclyc) {

        if (!draugthTransform) {
        	SimpleFrameAnimation transition=  new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, ShotDistanceFrameAnimation.JETPACK);
        	transition.setDuration(pltransition.getTotalDuration());
         	pltransition.getChildren().add( transition);
        } else {
        	SimpleFrameAnimation transition=new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, ShotDistanceFrameAnimation.ELICOPTER);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add( transition);

        }

    }
    @Override
    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (piece.getType() == Piece.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new SkySoldierPiece( this.piece,DRAUGTH_SKYSOLDIER_IMAGE, FXBoard.boardHW, this.getFxBoard());       
            AudioClip ach = buildMedia(ShotDistanceFrameAnimation.ACHB);
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
