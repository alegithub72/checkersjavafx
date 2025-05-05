package sa.fx.draugths.animation;

import sa.fx.draugths.utility.SequenceSoundEffect;
import sa.fx.draugths.utility.SoundEffect;

public class FrameSequence {

    private FrameInfo[] seqList;
    private long seqDuration;
    private SequenceSoundEffect effect;


    public FrameSequence(FrameInfo[] seqList,SequenceSoundEffect effect) {
        this.seqList = seqList;
        this.effect=effect;
        seqDuration=1500;
    }

    public SequenceSoundEffect getEffect() {
        return effect;
    }

    public SoundEffect getSoundEffect() {
        return effect.getEffect();
    }
    public int getSoundTypeEffect(){
        return effect.getType();
    }
    public void setEffect(SequenceSoundEffect effect) {
        this.effect = effect;
    }

    public FrameInfo[] getSeqList() {
        return seqList;
    }

    public void setSeqList(FrameInfo[] seqList) {
        this.seqList = seqList;
    }

    public long getSeqDuration() {
        return seqDuration;
    }

    public void setSeqDuration(long seqDuration) {
        this.seqDuration = seqDuration;
    }

    public boolean isPlaying() {
        return effect.isPlaying();
    }

    public void setPlaying(boolean soundEffectStarted) {
        this.effect.setPlaying(soundEffectStarted);
    }
}
