package sa.fx.draugths.sprite;

import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.ShotDistanceFrameAnimation;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Piece;

public class HelmetSoldierPiece extends SoldierPiece {
	

	private static final String CHECKER_SOLDIER_IMAGE = "soldier_checker_helmet.png";
    public HelmetSoldierPiece(Piece boardPiece,
            BoardHW boardHW,  FXBoard board) {
        super(CHECKER_SOLDIER_IMAGE,"HelmetSoldier",boardPiece, boardHW, board);
        this.color = boardPiece.getColor();
        this.piece = boardPiece;
    
    }

    @Override
    public void buildMoveSequence( boolean ciclyc) {

        if (!draugthTransform) {
        	SimpleFrameAnimation transition=  new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, ShotDistanceFrameAnimation.MARCH);
        	transition.setDuration(pltransition.getTotalDuration());
         	pltransition.getChildren().add( transition);
        } else {
        	SimpleFrameAnimation transition=new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, ShotDistanceFrameAnimation.ELICOPTER);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add( transition);

        }

    }


}
