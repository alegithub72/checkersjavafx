package sa.fx.draugths.pieces.efa;

import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameInfo;
import sa.fx.draugths.animation.FrameSequence;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.pieces.SpritePiece;
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

    public void buildHelmetAnimFrames() {
        // MOVE SEQUENCE 1-3
        FrameInfo[] move = { new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(8, 1) };
        addMoveSequenceFrame(new FrameSequence[]{new FrameSequence(move)});
        // MOVE EAT SEQUENCE 2-5
        FrameInfo[] moveat = { new FrameInfo(2, 1), new FrameInfo(3, 1) };
        addEatMoveSequenceFrame(new FrameSequence[]{new FrameSequence(moveat)});
        // KILLED SEQUENCE 10-17
        FrameInfo[] killed = { new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1),
                new FrameInfo(13, 1), new FrameInfo(14, 1), new FrameInfo(15, 1), new FrameInfo(16, 1),
                new FrameInfo(17, 1) };
        addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed)});
    }

    @Override
    protected void init() {
        buildHelmetAnimFrames();
    }

    @Override
    public void buildMoveSequence(boolean ciclyc) {

        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundEffect.MARCH);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }


}
