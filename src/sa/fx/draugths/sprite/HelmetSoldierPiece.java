package sa.fx.draugths.sprite;

import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundPlay;
import sa.gameboard.core.Piece;

public class HelmetSoldierPiece extends SoldierPiece {
	

	private static final String CHECKER_SOLDIER_IMAGE = "soldier_checker_helmet.png";
    public HelmetSoldierPiece(Piece piece,
            BoardHW boardHW,  FXBoard board) {
        super(CHECKER_SOLDIER_IMAGE,"HelmetSoldier",piece, boardHW, board);

    
    }

    @Override
    public void buildMoveSequence( boolean ciclyc) {

        if (!draugthTransform) {
        	SimpleFrameAnimation transition=  new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundPlay.MARCH);
        	transition.setDuration(pltransition.getTotalDuration());
         	pltransition.getChildren().add( transition);
        } else {
        	SimpleFrameAnimation transition=new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, SoundPlay.ELICOPTER);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add( transition);

        }

    }


}
