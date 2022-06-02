package sa.fx.draugths.utility;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gluonhq.attach.audio.Audio;
import com.gluonhq.attach.audio.AudioService;

public class SoundPlayMobile implements SoundInterface {

	Audio[] soundsMobile = new Audio[30];

	private static SoundInterface soundInterface = null;

	private SoundPlayMobile() {
		super();

	}

	ExecutorService executors = Executors.newCachedThreadPool();

	public static SoundInterface getSoundInterfaceInstance() {
		if (soundInterface == null)
			soundInterface = new SoundPlayMobile();
		return soundInterface;
	}

	@Override
	public void playSoundLoop(int code) {
		executors.execute(new Runnable() {

			@Override
			public void run() {
				ClassLoader classLoader = getClass().getClassLoader();
				URL url = classLoader.getResource(soundsName[code]);
				AudioService.create().ifPresent(service -> {
					service.loadMusic(url).ifPresent(audio -> {
						soundsMobile[code] = audio;
						audio.setLooping(true);
						audio.play();
					});
				});

			}
		});

	}

	@Override
	public void playSound(int code, int times) {
		executors.execute(new Runnable() {

			@Override
			public void run() {
				ClassLoader classLoader = getClass().getClassLoader();
				URL url = classLoader.getResource(soundsName[code]);
				AudioService.create().ifPresent(service -> {
					service.loadMusic(url).ifPresent(audio -> {
						soundsMobile[code] = audio;
						audio.setLooping(false);
						audio.play();
					});
				});

			}
		});

	}

	@Override
	public void stopSound(int code) {
		executors.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Audio audio = soundsMobile[code];
					audio.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
