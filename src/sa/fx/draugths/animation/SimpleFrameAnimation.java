/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.utility.SoundEffect;

/**
 *
 * @author Alessio Sardaro
 */
public class SimpleFrameAnimation extends Transition {
	FrameInfo[] frames;

	SoundEffect sound;
	FrameSequence[] seq;
	Sprite sprite;
	Move move;
	int seqNUmber;
	int i;
	int frameCount = 0;

	long interval = 0;

	boolean ciclyc;
	long before;
	boolean startMusic = true;

	public SimpleFrameAnimation(FrameSequence[] seq, Sprite sprite, Move move, boolean cyclic, long interval,
			SoundEffect sound) {
		this.frames = seq[0].getSeqList();
		seqNUmber=0;
		this.seq=seq;
		this.sprite = sprite;
		this.move = move;
		this.sound = sound;
		BCDraugthsApp.log.info("sound:"+sound.getFile());
		this.ciclyc = cyclic;
		before = System.currentTimeMillis();
		this.interval = interval;
		addEndHandler();
	}

	public SimpleFrameAnimation(FrameSequence[] seq, Sprite sprite, boolean cyclic, long interval, SoundEffect sound) {
		this.frames = seq[0].getSeqList();
		this.seq=seq;
		seqNUmber=0;
		this.sprite = sprite;
		this.move = null;
		this.sound = sound;

		this.ciclyc = cyclic;
		before = System.currentTimeMillis();
		this.interval = interval;
		addEndHandler();
		

	}

	public void setDuration(Duration s) {
		setCycleDuration(s);

	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	void playEffect() {

		if (startMusic) {
			if (sound.isLoop()	
					) {

				FXBoard.SoundSystem.playSoundLoop(sound);
			}

			FXBoard.SoundSystem.playSound(sound, 1);
			startMusic = false;
		}

	}

	@Override
	protected void interpolate(double frac) {
		framing(frac);
	}

	protected void framing(double frac) {
		long intervalTemp = System.currentTimeMillis() - before;

		playEffect();
		if (intervalTemp > this.interval) {
			sprite.setViewOrder(0);
			before = System.currentTimeMillis();
			sprite.setFrame(frames[i].getFrameNumber());

			sprite.toFront();
			frameCount++;
			BCDraugthsApp.log.info("sprite:" + sprite + ",frames:" + frames[i] + " interval:" + intervalTemp);
			if (frameCount >= frames[i].getDuration()) {
				i++;
				frameCount = 0;
				if (this.ciclyc) {
					if (i >= frames.length)
						i = 0;
				} else if (!this.ciclyc && (i >= (frames.length)))
					i = frames.length - 1;

			}
			if(((seq.length-1)>seqNUmber) &&  seq[seqNUmber].getSeqDuration()>(this.interval*i)){
				seqNUmber++;
				frames=seq[seqNUmber].getSeqList();
				i=0;
				frameCount = 0;
			}

		}

	}

	private void addEndHandler() {
		this.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FXBoard.SoundSystem.stopSound(sound);
				event.consume();

			}
		});
	}
}
