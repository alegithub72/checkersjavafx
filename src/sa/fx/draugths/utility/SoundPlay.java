package sa.fx.draugths.utility;

import java.net.URL;
import java.util.HashMap;

import javafx.scene.media.AudioClip;

public class SoundPlay {
    public static final String FIRE="fire5.wav";
    public static final String SPEEDY_BITE="speedy_bite.wav";
    public static final String BITE="Dogbite.wav";
    public static final String BIG_BITE="lion_roar_2.mp3";
    public static final String JUNGLE="jungle_drum.wav";
    public static final String MARCH="marcias.wav";
    public static final String MOVESPACESOLDIER="jerpack.wav";
    public static final String CLOPETE_DOUBLE="moveAlien2.wav";
    public static final String MOVEMONSTER="UFO.wav";
    public static final String EXPLOSIONMONSTER="Fireball.wav";
    public static final String MISSILE="top.wav";
    public static final String ELICOPTER="elicopter.wav";
    public static final String CLOPETE="move_alien.wav";
    public static final String EXPLOSION="Fireball.wav";
    public static final String BIGEXPLOSION="Explosion3.wav";
    public static final String ACHW="Achievement.wav";
    public static final String ACHB="pluck.wav";
    public static final String WING="159355-wing-effect.wav";
    public static final String JETPACK="jerpack.wav";
	
	
	HashMap<String, AudioClip> sounds=new HashMap<>();

	
	private AudioClip getSoundHashMap(String sound) {
        AudioClip audioClip=  sounds.get(sound);
		if(audioClip==null) {
			ClassLoader classLoader = getClass().getClassLoader();
        	URL url = classLoader.getResource(sound);
        	audioClip=new AudioClip(url.toString());
		}
		return audioClip;
		
	}
	public void  playSoundLoop(String sound){
		AudioClip audioClip=getSoundHashMap(sound);
        audioClip.setCycleCount(AudioClip.INDEFINITE);
        audioClip.play(); 
        sounds.put(sound, audioClip);
		
	}
	public void  stopSound(String sound){
		AudioClip audioClip=getSoundHashMap(sound);
        audioClip.stop(); 
		
	}
	public void  playSound(String sound, int times){
		AudioClip audioClip=getSoundHashMap(sound);
        audioClip.setCycleCount(times);
        audioClip.play(); 
		
	}	
	
}
