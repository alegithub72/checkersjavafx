package sa.fx.draugths.utility;

import java.net.URL;

import com.gluonhq.attach.audio.Audio;
import com.gluonhq.attach.audio.AudioService;

import sa.fx.draugths.BCDraugthsApp;

public class SoundPlay implements SoundInterface {

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
	
	Audio[] sounds=new Audio[30];	
	
	private static final String[] soundsName={
			"fire5.wav","speedy_bite.wav","Dogbite.wav","lion_roar_2.mp3"
			,"jungle_drum.wav","marcias.wav","jerpack.wav","moveAlien2.wav"
			,"UFO.wav","Fireball.wav","top.wav","elicopter.wav"
			,"move_alien.wav","Fireball.wav","Explosion3.wav"
			,"Achievement.wav","pluck.wav","159355-wing-effect.wav","jerpack.wav"
			,"191594_laser.wav","muppet.mp3","270545_jingle-win-01.wav","416507_hey.wav"};


	
	private static SoundInterface soundInterface=null;
	
	
	private SoundPlay() {
		super();

	}

	public static SoundInterface getSoundInterfaceInstance() {
		if(soundInterface==null)
			soundInterface=new SoundPlay();
		return soundInterface;
	}
	
	private Audio getSoundHashMap(int code) {

		Audio audioClip = sounds[code];
		if (audioClip == null) {
			ClassLoader classLoader = getClass().getClassLoader();
			URL url = classLoader.getResource(soundsName[code]);
			AudioService.create().ifPresent(service -> {
				BCDraugthsApp.log.info("--->present service");

					service.loadMusic(url).ifPresent(audio -> {
						BCDraugthsApp.log.info("--->present audio");
						sounds[code]=audio;
					});
				});
		}
		
		return sounds[code];

	}
	@Override
	public void  playSoundLoop(int code){
		try {
			Audio audioClip=getSoundHashMap(code);
			audioClip.setLooping(true);
			audioClip.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Override
	public void  stopSound(int code){
		try {
			Audio audio=getSoundHashMap(code);
			audio.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	@Override
	public void  playSound(int code, int times){
		try {
			Audio audio=getSoundHashMap(code);
			audio.setLooping(false);
			audio.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}	
	
}
