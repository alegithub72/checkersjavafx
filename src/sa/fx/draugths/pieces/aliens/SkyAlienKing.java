/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces.aliens;

import javafx.animation.ParallelTransition;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameInfo;
import sa.fx.draugths.animation.FrameSequence;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SequenceSoundEffect;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

/**
 * @author Alessio Sardaro
 */
public class SkyAlienKing extends AlienKing {


    protected static  String PIECE_IMAGE = "alien_checker_dama_fly.png";


    public SkyAlienKing(Piece piece, BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"SkyAlienKing",piece,   boardHW, board);


    }

    @Override
    protected void init() {
        buildSkyAlienKingFrames();
    }

    public void buildSkyAlienKingFrames() {
        crownedSound();
        // MOVE SEQUENCE 1-3
        FrameInfo[] move = { new FrameInfo(5, 1), new FrameInfo(6, 1), new FrameInfo(5, 1), new FrameInfo(6, 1) };
        addMoveSequenceFrame(new FrameSequence[]{new FrameSequence(move,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.SAPCESHIP))});
        // MOVE EAT SEQUENCE 1-4
        FrameInfo[] moveat = { new FrameInfo(1, 1), new FrameInfo(4, 1), new FrameInfo(1, 1),
                new FrameInfo(4, 1) };
        addEatMoveSequenceFrame(new FrameSequence[]{new FrameSequence(moveat,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.SAPCESHIP_BUZZ))});
        // KILLED SEQUENCE 7-12
        FrameInfo[] killed = { new FrameInfo(0, 1), new FrameInfo(7, 1), new FrameInfo(8, 1),
                new FrameInfo(9, 1), new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1) };
        addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed,new SequenceSoundEffect(SequenceSoundEffect.ADD,SoundEffect.EXPLOSION_LASER))});

    }


    @Override
    public void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);
        buildDefaultKillAnimation(killSequenceFrame, m, false, 50);

    }

    public void buildMoveSequence(boolean ciclyc) {
        SimpleFrameAnimation transition = null;

        transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 50);
        pltransition.getChildren().add(transition);
        transition.setDuration(pltransition.getTotalDuration());


    }

    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {
        //SpritePiece eated=getFxBoard().getSpritePiece(m.getEat().getI(), m.getEat().getJ(),m.getEat().getColor(), false);
        SimpleFrameAnimation transition = null;
        transition = new SimpleFrameAnimation(eatMoveSequenceFrame, this, m, ciclyc, 50);
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
