/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces.efa;

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
public class MoonSoldierKing extends SoldierKing {


    protected static  String PIECE_IMAGE = "white_draugth_moonsoldier.png";

    public MoonSoldierKing(Piece piece,
                           BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"MoonSoldierDraught",piece,  boardHW,board);


    }

    public void buildMoonSoldierKingFrames() {


        crownedSound();
        // MOVE SEQUENCE 1-5
        FrameInfo[] move = { new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1), new FrameInfo(4, 1),
                new FrameInfo(5, 1) };
        addMoveSequenceFrame(new FrameSequence[]{new FrameSequence(move,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.JET))});
        // MOVE EAT SEQUENCE 1-5
        FrameInfo[] moveat = { new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1),
                new FrameInfo(4, 1), new FrameInfo(5, 1) };
        addEatMoveSequenceFrame(new FrameSequence[]{new FrameSequence(moveat,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.JET))});
        // KILLED SEQUENCE 7-10
        FrameInfo[] killed = { new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(8, 1),
                new FrameInfo(9, 1), new FrameInfo(10, 1), };
        addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed,new SequenceSoundEffect(SequenceSoundEffect.ADD,SoundEffect.BITE))});

    }

    @Override
    protected void init() {
        buildMoonSoldierKingFrames();
    }

    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {

        SimpleFrameAnimation transition = null;


        transition = new SimpleFrameAnimation(eatMoveSequenceFrame, this, ciclyc, 20);
        transition.setDuration(pltransition.getTotalDuration());

        pltransition.getChildren().add(transition);

    }

    @Override
    public void buildMoveSequence(boolean ciclyc) {


        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20);
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
