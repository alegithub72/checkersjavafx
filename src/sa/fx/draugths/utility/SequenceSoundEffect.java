package sa.fx.draugths.utility;

public class SequenceSoundEffect {
    public static final int SPREAD=2;
    public static final int CYCLCIC=1;
    public static final int ADD=0;
    private SoundEffect effect;
    private boolean playing=false;
    private int type=0;

    public SequenceSoundEffect(int type, SoundEffect effect) {
        this.type = type;
        this.effect = effect;
        this.playing=false;
    }

    public SoundEffect getEffect() {
        return effect;
    }

    public void setEffect(SoundEffect effect) {
        this.effect = effect;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
