package sa.fx.draugths.utility;

public class SoundEffect {
	private String file;
	private boolean loop;
	
	
	public SoundEffect(String file, boolean loop) {
		super();
		this.file = file;
		this.loop = loop;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public boolean isLoop() {
		return loop;
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
}
