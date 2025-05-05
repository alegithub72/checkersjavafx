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
public class SkyAlien extends Alien {


    protected static  String PIECE_IMAGE ="alien_checker_fly.png";

    public SkyAlien(Piece piece, BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"SkyAlien",piece,   boardHW, board);


    }

    public  void buildSkyAlienFrames() {
        FrameInfo[] move = { new FrameInfo(2, 2), new FrameInfo(3, 2), new FrameInfo(4, 2) };
        addMoveSequenceFrame(new FrameSequence[]{new FrameSequence(move,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.WING))});
        // MOVE EAT SEQUENCE 4-6
        FrameInfo[] moveeat = { new FrameInfo(5, 1), new FrameInfo(6, 1), new FrameInfo(7, 1),
                new FrameInfo(8, 1) };
        addEatMoveSequenceFrame(new FrameSequence[]{new FrameSequence(moveeat,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.WING))});
        // KILLED SEQUENCE 7-13
        FrameInfo[] killed = { new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1),
                new FrameInfo(13, 1), new FrameInfo(14, 1), new FrameInfo(15, 1), new FrameInfo(16, 1),
                new FrameInfo(17, 1) };

        addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed,new SequenceSoundEffect(SequenceSoundEffect.ADD,SoundEffect.EXPLOSION_LASER))});
    }


    @Override
    public void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);
        buildDefaultKillAnimation(killSequenceFrame, m, false, 50);
    }

    public void buildMoveSequence(boolean ciclyc) {
        SimpleFrameAnimation transition = null;

        transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 25);
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

    @Override
    protected void init() {
        buildSkyAlienFrames();
    }
}
