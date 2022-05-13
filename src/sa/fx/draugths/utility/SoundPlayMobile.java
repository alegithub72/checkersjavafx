package sa.fx.draugths.utility;

import java.net.URL;

import com.gluonhq.attach.audio.Audio;
import com.gluonhq.attach.audio.AudioService;

import sa.fx.draugths.BCDraugthsApp;

public class SoundPlayMobile implements SoundInterface {


	
	Audio[] sounds=new Audio[30];	
	



	
	private static SoundInterface soundInterface=null;
	
	
	private SoundPlayMobile() {
		super();

	}
	public static SoundInterface getSoundInterfaceInstance() {
		if(soundInterface==null)
			soundInterface=new SoundPlayMobile();
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
