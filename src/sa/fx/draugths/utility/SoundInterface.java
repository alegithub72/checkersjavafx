package sa.fx.draugths.utility;

public interface SoundInterface {
    public static final int FIRE=0;
    public static final int SPEEDY_BITE=1;
    public static final int BITE=2;
    public static final int BIG_BITE=3;
    public static final int JUNGLE=4;
    public static final int MARCH=5;
    public static final int MOVESPACESOLDIER=6;
    public static final int CLOPETE_DOUBLE=7;
    public static final int MOVEMONSTER=8;
    public static final int EXPLOSIONMONSTER=9;
    public static final int MISSILE=10;
    public static final int ELICOPTER=11;
    public static final int CLOPETE=12;
    public static final int EXPLOSION=13;
    public static final int BIGEXPLOSION=14;
    public static final int ACHW=15;
    public static final int ACHB=16;
    public static final int WING=17;
    public static final int JETPACK=18;
    public static final int LASER=19;
    public static final int MUSIC_SIGLA=20;
    public static final int MUSIC_CELEBRATION=21;
    public static final int EFFECT_HEY=22;
    public static final int EFFECT_COIN=23;
    
	public static final String[] soundsName={

			"fire5.wav","speedy_bite.wav","Dogbite.wav","lion_roar_2.mp3"
			,"jungle_drum.wav","marcias.wav","jerpack.wav","moveAlien2.wav"
			,"UFO.wav","Fireball.wav","top.wav","elicopter.wav"
			,"move_alien.wav","Fireball.wav","Explosion3.wav"
			,"Achievement.wav","pluck.wav","159355-wing-effect.wav","jerpack.wav"
			,"191594_laser.wav","muppet.mp3","270545_jingle-win-01.wav","416507_hey.wav","29649-coin-return.wav"};

	void playSoundLoop(int code);

	void stopSound(int code);

	void playSound(int code, int times);

}