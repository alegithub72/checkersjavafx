package sa.fx.draugths.utility;

public interface SoundInterface {
	
	/**this.sound == SoundPlay.FIRE || this.sound == SoundPlay.SPEEDY_BITE || this.sound == SoundPlay.CLOPETE
	|| this.sound == SoundPlay.CLOPETE_DOUBLE || this.sound == SoundPlay.MOVESPACESOLDIER|| this.sound == SoundPlay.WING**/
    public static final SoundEffect FIRE=new SoundEffect("fire5.wav", true);
    public static final SoundEffect SPEEDY_BITE=new SoundEffect("speedy_bite.wav", true);
    public static final SoundEffect BITE=new SoundEffect("Dogbite.wav", false);
    public static final SoundEffect BIG_BITE=new SoundEffect("lion_roar_2.mp3", false);
    public static final SoundEffect JUNGLE=new SoundEffect("jungle_drum.wav", false);
    public static final SoundEffect MARCH=new SoundEffect("marcias.wav", false);
    public static final SoundEffect MOVESPACESOLDIER=new SoundEffect("jerpack.wav", true);
    public static final SoundEffect CLOPETE_DOUBLE=new SoundEffect("moveAlien2.wav", true);
    public static final SoundEffect MOVEMONSTER=new SoundEffect("UFO.wav", false);
    public static final SoundEffect EXPLOSIONMONSTER=new SoundEffect("Fireball.wav", false);
    public static final SoundEffect MISSILE=new SoundEffect("top.wav", false);
    public static final SoundEffect ELICOPTER=new SoundEffect("elicopter.wav", false);
    public static final SoundEffect CLOPETE=new SoundEffect("move_alien.wav", true);
    public static final SoundEffect EXPLOSION=new SoundEffect("Fireball.wav", false);
    public static final SoundEffect EXPLOSION_LASER=new SoundEffect("explosion-39897.wav", false);
    public static final SoundEffect BIGEXPLOSION=new SoundEffect("Explosion3.wav", false);
    public static final SoundEffect ACHW=new SoundEffect("Achievement.wav", false);
    public static final SoundEffect ACHB=new SoundEffect("pluck.wav", false);
    public static final SoundEffect WING=new SoundEffect("159355-wing-effect.wav", true);
    public static final SoundEffect JETPACK=new SoundEffect("jerpack.wav", true);
    public static final SoundEffect JET=new SoundEffect("jet.wav", true);
    public static final SoundEffect LASER=new SoundEffect("191594_laser.wav", true);
    public static final SoundEffect MUSIC_SIGLA=new SoundEffect("muppet.mp3", false);
    public static final SoundEffect MUSIC_CELEBRATION=new SoundEffect("270545_jingle-win-01.wav", false);
    public static final SoundEffect EFFECT_HEY=new SoundEffect("416507_hey.wav", false);
    public static final SoundEffect EFFECT_COIN=new SoundEffect("29649-coin-return.wav", false);
    public static final SoundEffect SAPCESHIP_BUZZ=new SoundEffect("55829_electric.wav",false);
    public static final SoundEffect SAPCESHIP=new SoundEffect("510018-buzzy.wav",true);
    


	void playSoundLoop(SoundEffect code);

	void stopSound(SoundEffect code);

	void playSound(SoundEffect code, int times);
	public void stopExecutor() ;
}