package sa.fx.draugths.utility;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import javafx.scene.media.AudioClip;
import sa.fx.draugths.BCDraugthsApp;

public class SoundPlay implements SoundInterface {


	

	
	ExecutorService executors = Executors.newCachedThreadPool();
	Map<String, AudioClip> sounds=new HashMap<String, AudioClip>();	


	
	private static SoundInterface soundInterface=null;
	
	
	private SoundPlay() {
		super();

	}

	public static SoundInterface getSoundInterfaceInstance() {
		if(soundInterface==null)
			soundInterface=new SoundPlay();
		return soundInterface;
	}
	
	private AudioClip getSoundHashMap(SoundEffect effect) {
        AudioClip audioClip=  sounds.get(effect.getFile());
		if(audioClip==null) {
			ClassLoader classLoader = getClass().getClassLoader();
        	URL url = classLoader.getResource(effect.getFile());
        	BCDraugthsApp.log.log(Level.INFO,"code={0}, name={1}" ,new Object[]{effect,effect.getFile()});
        	

        	audioClip=new AudioClip(url.toString());
		}
		return audioClip;
		
	}
	@Override
	public void  playSoundLoop(SoundEffect effect){
		executors.execute(new Runnable() {
			
			@Override
			public void run() {
				AudioClip audioClip=getSoundHashMap(effect);
				//audioClip.stop();
				audioClip.setCycleCount(AudioClip.INDEFINITE);
		        audioClip.play(); 
		        sounds.put(effect.getFile(), audioClip);
				
			}
		});

		
	}
	@Override
	public void  stopSound(SoundEffect effect){
		executors.execute(new Runnable() {
			
			@Override
			public void run() {
				AudioClip audioClip=getSoundHashMap(effect);

				audioClip.stop();
				
			}
		});

		
	}
	@Override
	public void  playSound(SoundEffect effect, int times){
		executors.execute(new Runnable() {
			
			@Override
			public void run() {
				AudioClip audioClip=getSoundHashMap(effect);
		        audioClip.setCycleCount(times);
		        audioClip.play(); 
				
			}
		});


	}
	public void stopExecutor() {
		executors.shutdown();
		for (AudioClip audio:sounds.values()) {
			if(audio!=null)audio.stop();
		}
	}

	
}
