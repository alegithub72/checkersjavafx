/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces.efa;

import sa.boardgame.core.moves.Move;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.ShotDistanceFrameAnimation;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

/**
 * @author Alessio Sardaro
 */
public class MoonSoldier extends Helmet {


    static String CHECKER_SOLDIER_IMAGE = "white_cheker_moonsoldier.png";


    public MoonSoldier(Piece piece,
                       BoardHW boardHW, FXBoard board) {
        super(CHECKER_SOLDIER_IMAGE, "MoonSoldier", piece, boardHW, board);


    }


    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {

        SimpleFrameAnimation transition = null;


        transition = new ShotDistanceFrameAnimation(eatMoveSequenceFrame, this, m, ciclyc, 20, SoundEffect.LASER_MM);
        transition.setDuration(pltransition.getTotalDuration());

        pltransition.getChildren().add(transition);

    }

    @Override
    public void buildMoveSequence(boolean ciclyc) {

        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundEffect.MARCH);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }

    @Override
    public SpritePiece crownedSound() {
        throw new RuntimeException("Not allowed");

    }
}
