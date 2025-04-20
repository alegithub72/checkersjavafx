package sa.fx.draugths.pieces.efa;

import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

public class Helmet extends Soldier {


    private static final String CHECKER_SOLDIER_IMAGE = "soldier_checker_helmet.png";

    public Helmet(Piece piece,
                  BoardHW boardHW, FXBoard board) {
        super(CHECKER_SOLDIER_IMAGE, "HelmetSoldier", piece, boardHW, board);


    }

    public Helmet(String checkerSoldierImage, String moonSoldier, Piece piece, BoardHW boardHW, FXBoard board) {
        super(checkerSoldierImage, moonSoldier, piece, boardHW, board);
    }

    @Override
    public void buildMoveSequence(boolean ciclyc) {

        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundEffect.MARCH);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }


}
