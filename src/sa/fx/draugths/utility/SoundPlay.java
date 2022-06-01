package sa.fx.draugths.utility;

import java.net.URL;

import javafx.scene.media.AudioClip;

public class SoundPlay implements SoundInterface {


	

	

	AudioClip[] sounds=new AudioClip[30];	


	
	private static SoundInterface soundInterface=null;
	
	
	private SoundPlay() {
		super();

	}

	public static SoundInterface getSoundInterfaceInstance() {
		if(soundInterface==null)
			soundInterface=new SoundPlay();
		return soundInterface;
	}
	
	private AudioClip getSoundHashMap(int code) {
        AudioClip audioClip=  sounds[code];
		if(audioClip==null) {
			ClassLoader classLoader = getClass().getClassLoader();
        	URL url = classLoader.getResource(soundsName[code]);

        	System.out.println("code="+code+","+soundsName[code]);

        	audioClip=new AudioClip(url.toString());
		}
		return audioClip;
		
	}
	@Override
	public void  playSoundLoop(int code){
		AudioClip audioClip=getSoundHashMap(code);
        audioClip.setCycleCount(AudioClip.INDEFINITE);
        audioClip.play(); 
        sounds[code]= audioClip;
		
	}
	@Override
	public void  stopSound(int code){
		AudioClip audioClip=getSoundHashMap(code);
        audioClip.stop(); 
		
	}
	@Override
	public void  playSound(int code, int times){
		AudioClip audioClip=getSoundHashMap(code);
        audioClip.setCycleCount(times);
        audioClip.play(); 
		
	}	
	
}
