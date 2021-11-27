/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import java.net.URL;

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
public class ShotCollisionFrameAnimationTimer extends ShotDistanceFrameAnimationTimer {

	public Sprite shot;

	// private PathTransition pathTransition;

	public ShotCollisionFrameAnimationTimer(FrameInfo[] frames, Sprite sprite, SpritePiece target, Sprite shot,
			boolean cyclic, long interval, String sound) {
		super(frames, sprite, null, target, cyclic, interval, sound);
		this.shot = shot;

	}

	@Override
	public void handle(long now) {

		long intervalTemp = System.currentTimeMillis() - before;
		playEffect();
		double lx = 0, ly = 0;
		if (target != null) {
			Bounds pSceneBound = shot.localToScene(shot.getBoundsInLocal());
			Bounds targetSceneBound = target.localToScene(target.getBoundsInLocal());
			if (pSceneBound.intersects(targetSceneBound) && !once) {
				BCDraugthsApp.log.info("INTERSECTION DETECTED!!!!!!!");
				sprite.fireEvent(new EventEatAnimPiece(sprite, sprite, EventEatAnimPiece.KILLPLAY_EVENT));
				once = true;
				shot.setVisible(false);
				target.getFxBoard().remove(shot);
			}

		}

		if (intervalTemp > this.interval) {

			before = System.currentTimeMillis();
			sprite.setFrame(frames[i].getFrameNumber());
			sprite.toFront();
			frameCount++;
			if (frames[i].getDuration() >= frameCount) {
				i++;
				frameCount = 0;
			}

			if (this.ciclyc) {
				if (i >= frames.length)
					i = 0;
			} else if (!this.ciclyc && (i >= frames.length))
				i = frames.length - 1;

		}


	}

	@Override
	void playEffect() {

		if (mediaPlayer != null) {
			if (startMusic) {
				if (this.sound == FIRE || this.sound == SPEEDY_BITE || this.sound == CLOPETE
						|| this.sound == CLOPETE_DOUBLE || this.sound == MOVESPACESOLDIER) {
					mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
				}

				mediaPlayer.play();
				startMusic = false;
			}

		}

	}

}
