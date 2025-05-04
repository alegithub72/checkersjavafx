/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import javafx.geometry.Bounds;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.SoundEffect;

/**
 *
 * @author Alessio Sardaro
 */
public class ShotCollisionFrameAnimation extends SimpleFrameAnimation {

	Sprite shot;
	SpritePiece piece;
	SpritePiece target;
	boolean once;
	// private PathTransition pathTransition;

	public ShotCollisionFrameAnimation(FrameSequence[] frames, Move move, SpritePiece sprite, Sprite shot,

			boolean cyclic, long interval, SoundEffect effect) {
		super(frames, shot,move, cyclic, interval, effect);

	     this.piece=sprite;
		this.shot = shot;
		once=false;
		target=piece.getFxBoard().getSpritePiece(move.getEat().getI(),move.getEat().getJ(),move.getEat().getColor() , true);

	}

	@Override
	public void interpolate(double now) {
		
		double lx = 0, ly = 0;
		if(!once) {
			
			if (target != null) {
				Bounds pSceneBound = shot.localToScene(shot.getBoundsInLocal());
				
				Bounds targetSceneBound = target.localToScene(target.getBoundsInLocal());
				if (pSceneBound.intersects(targetSceneBound)) {
					BCDraugthsApp.log.info("INTERSECTION DETECTED!!!!!!!");
					piece.fireEvent(new EventEatAnimPiece(target,target.getParent() ,move, EventEatAnimPiece.KILLPLAY_EVENT));
					once = true;
					shot.setVisible(false);

				}

			}			
			
			
		}
		

		framing(now);



	}


}
