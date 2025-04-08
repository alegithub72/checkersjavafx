package sa.fx.draugths.utility;

public interface SoundInterface {
	
	/**this.sound == SoundPlay.FIRE || this.sound == SoundPlay.SPEEDY_BITE || this.sound == SoundPlay.CLOPETE
	|| this.sound == SoundPlay.CLOPETE_DOUBLE || this.sound == SoundPlay.MOVESPACESOLDIER|| this.sound == SoundPlay.WING**/

    


	void playSoundLoop(SoundEffect code);

	void stopSound(SoundEffect code);

	void playSound(SoundEffect code, int times);
	public void stopExecutor() ;
}