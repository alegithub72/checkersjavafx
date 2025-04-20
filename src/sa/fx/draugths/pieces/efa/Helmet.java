package sa.fx.draugths.pieces.efa;

import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

public class Helmet extends Soldier {


    protected static  String PIECE_IMAGE = "soldier_checker_helmet.png";

    public Helmet(Piece piece,
                  BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"HelmetSoldier", piece, boardHW, board);



    }

    public Helmet(String pieceImage, String moonSoldier, Piece piece, BoardHW boardHW, FXBoard board) {
        super(pieceImage,moonSoldier,piece,boardHW,board);
    }


    @Override
    public void buildMoveSequence(boolean ciclyc) {

        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundEffect.MARCH);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }


}
