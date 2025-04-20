/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces.efa;

import sa.boardgame.core.moves.Move;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

/**
 * @author Alessio Sardaro
 */
public class MoonSoldierDraught extends SoldierDraught {


    static String DRAUGTH_SOLDIER_IMAGE = "white_draugth_moonsoldier.png";

    public MoonSoldierDraught(Piece piece,
                              BoardHW boardHW, FXBoard board) {
        super(DRAUGTH_SOLDIER_IMAGE, "MoonSoldier", piece, boardHW, board);


    }


    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {

        SimpleFrameAnimation transition = null;


        transition = new SimpleFrameAnimation(eatMoveSequenceFrame, this, ciclyc, 20, SoundEffect.JET);
        transition.setDuration(pltransition.getTotalDuration());

        pltransition.getChildren().add(transition);

    }

    @Override
    public void buildMoveSequence(boolean ciclyc) {


        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, SoundEffect.JET);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }

    @Override
    public SpritePiece crownedSound() {

        if (piece.getType() == Piece.DRAUGTH &&
                draugthTransform == false) {
            draugthTransform = true;

            FXBoard.SoundSystem.playSound(SoundEffect.ACHB, 1);
            this.setDraugthTransform(true);


        }
        setFrame(0);

        return this;

    }
}
