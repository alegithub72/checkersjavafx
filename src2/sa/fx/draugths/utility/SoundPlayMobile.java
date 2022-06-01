package sa.fx.draugths.utility;

import java.net.URL;

import com.gluonhq.attach.audio.Audio;
import com.gluonhq.attach.audio.AudioService;



public class SoundPlayMobile implements SoundInterface {

	Audio[] soundsMobile = new Audio[30];

	private static SoundInterface soundInterface = null;

	private SoundPlayMobile() {
		super();

	}

	public static SoundInterface getSoundInterfaceInstance() {
		if (soundInterface == null)
			soundInterface = new SoundPlayMobile();
		return soundInterface;
	}

	@Override
	public void playSoundLoop(int code) {

		Thread th = new Thread(new Runnable() {

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
		th.start();

	}

	@Override
	public void playSound(int code, int times) {
		Thread th = new Thread(new Runnable() {

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
		th.start();

	}

	@Override
	public void stopSound(int code) {
		try {
			Audio audio = soundsMobile[code];
			audio.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
