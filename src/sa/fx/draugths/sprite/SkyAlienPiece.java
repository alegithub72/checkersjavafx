/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundPlay;
import sa.gameboard.core.Piece;

/**
 *
 * @author  Alessio Sardaro
 */
public class SkyAlienPiece extends AlienPiece {


    BCDraugthsApp bcdg;
    private static final  String DRAUGTH_ALIEN_IMAGE_SKY="alien_checker_dama.png";
    private static final  String CHEKCER_ALIEN_IMAGE_SKY="alien_checker_fly.png";
    
   public SkyAlienPiece( Piece boardPiece, BoardHW boardHW, FXBoard board) {
        super(boardPiece, CHEKCER_ALIEN_IMAGE_SKY,"SkyAlien",boardHW,board);
 

    }
   private SkyAlienPiece(String img, Piece boardPiece, BoardHW boardHW, FXBoard board) {
       super(boardPiece, img,"SkyAlien",boardHW,board);
  

   }



    @Override
    public void buildKilledSequence(Move m) {
    	pltransition=new ParallelTransition(this);
        buildDefaultKillAnimation(killSequenceFrame,m,  false, 50, SoundPlay.EXPLOSION);
    }

    public void buildMoveSequence(boolean ciclyc) {
    	SimpleFrameAnimation transition=null;
        if (!draugthTransform) {
        	transition=new  SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 50, SoundPlay.WING);
        	pltransition.getChildren().add(transition);
        	transition.setDuration(pltransition.getTotalDuration());
        } else {
        	transition=new SimpleFrameAnimation(moveSequenceFrame,this ,ciclyc, 50, SoundPlay.WING);
        	pltransition.getChildren().add( transition);
        	transition.setDuration(pltransition.getTotalDuration());
        }

    }

    @Override
	public void buildMoveEatSequence(Move m, boolean ciclyc) {
    	//SpritePiece eated=getFxBoard().getSpritePiece(m.getEat().getI(), m.getEat().getJ(),m.getEat().getColor(), false);
    	SimpleFrameAnimation transition=null;
        if(draugthTransform==false) {
        	transition= new SimpleFrameAnimation(eatMoveSequenceFrame, this,m, ciclyc, 50, SoundPlay.WING);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add(transition);
        }
        else {
        	transition=new SimpleFrameAnimation(eatMoveSequenceFrame,this,m, ciclyc, 50, SoundPlay.WING);
        	transition.setDuration(pltransition.getTotalDuration());
        	pltransition.getChildren().add(transition );
        }
		
	}

    

    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (piece.getType() == Piece.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new SkyAlienPiece(DRAUGTH_ALIEN_IMAGE_SKY,this.piece, FXBoard.boardHW, this.getFxBoard());    
            BCDraugthsApp.soundPlay.playSound(SoundPlay.ACHB, 1);
            sp.setDraugthTransform(true);
        }
        setFrame(0);
        return sp;

    }

  
}
