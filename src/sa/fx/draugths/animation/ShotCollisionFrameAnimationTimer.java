/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import javafx.geometry.Bounds;
import javafx.scene.media.AudioClip;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.sprite.Sprite;
import sa.fx.draugths.sprite.SpritePiece;

/**
 *
 * @author Alessio Sardaro
 */
public class ShotCollisionFrameAnimationTimer extends SimpleFrameAnimationTimer {

	public Sprite shot;
	SpritePiece target;
	boolean once=false;
	// private PathTransition pathTransition;

	public ShotCollisionFrameAnimationTimer(FrameInfo[] frames, SpritePiece sprite, SpritePiece target, Sprite shot,
			boolean cyclic, long interval, String sound) {
		super(frames, sprite, cyclic, interval, sound);
		this.target=target;
		this.shot = shot;
	

	}

	@Override
	public void interpolate(double now) {

		double lx = 0, ly = 0;
		if (target != null) {
			Bounds pSceneBound = shot.localToScene(shot.getBoundsInLocal());
			Bounds targetSceneBound = target.localToScene(target.getBoundsInLocal());
			if (pSceneBound.intersects(targetSceneBound) && !once) {
				BCDraugthsApp.log.info("INTERSECTION DETECTED!!!!!!!");
				sprite.fireEvent(new EventEatAnimPiece(target,target.getFxBoard() ,move, EventEatAnimPiece.KILLPLAY_EVENT));
				once = true;
				shot.setVisible(false);
				target.getFxBoard().remove(shot);
			}

		}
		super.interpolate(now);



	}


}
