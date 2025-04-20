package sa.fx.draugths.pieces.efa;

import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

public class SkySoldierDraught extends Soldier {


    private static final String DRAUGTH_SKYSOLDIER_IMAGE = "soldier_checker_dama_fly.png";


    public SkySoldierDraught(Piece piece,
                             BoardHW boardHW, FXBoard board) {
        super(DRAUGTH_SKYSOLDIER_IMAGE, "SkySoldier", piece, boardHW, board);

    }

    @Override
    public void buildMoveSequence(boolean ciclyc) {


        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, SoundEffect.DRONE);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }

    public synchronized void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);
        SimpleFrameAnimation transition = null;

        transition = new SimpleFrameAnimation(killSequenceFrame, this, false, 40, SoundEffect.SAPCESHIP_BUZZ);


        SpritePiece eated = this;
        transition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (m.getType() == Move.EAT) {

                    BCDraugthsApp.log.info("FIRE EventRemoveEatPiece.REMOVE_PIECE_EVENT: " + eated);
                    fireEvent(new EventRemoveEatPiece(eated, eated.getParent(), EventRemoveEatPiece.REMOVE_PIECE_EVENT));


                }

            }
        });
        pltransition.getChildren().add(transition);

    }

    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {

        SimpleFrameAnimation transition = null;


        transition = new SimpleFrameAnimation(eatMoveSequenceFrame, this, ciclyc, 20, SoundEffect.DRONE);
        transition.setDuration(pltransition.getTotalDuration());

        pltransition.getChildren().add(transition);

    }

    @Override
    public SpritePiece crownedSound() {
        if (piece.getType() == Piece.DRAUGTH &&
                !draugthTransform) {
            draugthTransform = true;

            FXBoard.SoundSystem.playSound(SoundEffect.ACHB, 1);
            this.setDraugthTransform(true);
            //buildFrameImages();

//            setScaleX(0.64);
//            setScaleY(0.64);
            // setW(96);setH(96);

        }
        setFrame(0);
        //recalculateXYPosition();
        return this;

    }


}
