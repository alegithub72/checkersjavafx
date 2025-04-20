/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces.aliens;

import javafx.animation.ParallelTransition;
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
public class SkyAlien extends Alien {


    protected static  String PIECE_IMAGE ="alien_checker_fly.png";

    public SkyAlien(Piece piece, BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"SkyAlien",piece,   boardHW, board);


    }


    @Override
    public void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);
        buildDefaultKillAnimation(killSequenceFrame, m, false, 50, SoundEffect.EXPLOSION_LASER);
    }

    public void buildMoveSequence(boolean ciclyc) {
        SimpleFrameAnimation transition = null;

        transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 25, SoundEffect.WING);
        pltransition.getChildren().add(transition);
        transition.setDuration(pltransition.getTotalDuration());


    }

    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {
        //SpritePiece eated=getFxBoard().getSpritePiece(m.getEat().getI(), m.getEat().getJ(),m.getEat().getColor(), false);
        SimpleFrameAnimation transition = null;
        transition = new SimpleFrameAnimation(eatMoveSequenceFrame, this, m, ciclyc, 50, SoundEffect.WING);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }


    public SpritePiece crownedSound() {

        if (piece.getType() == Piece.DRAUGTH &&
                !draugthTransform) {
            draugthTransform = true;

            FXBoard.SoundSystem.playSound(SoundEffect.ACHB, 1);
            this.setDraugthTransform(true);
        }
        setFrame(0);
        return this;

    }


}
