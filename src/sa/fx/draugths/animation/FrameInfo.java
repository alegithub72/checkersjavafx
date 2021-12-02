package sa.fx.draugths.animation;

public class FrameInfo {
 int frameNumber;
 int duration;
public FrameInfo(int f1, int duration) {
	super();
	this.frameNumber = f1;
	this.duration = duration;
}

public int getFrameNumber() {
	return frameNumber;
}

public void setFrameNumber(int frameNumber) {
	this.frameNumber = frameNumber;
}

public int getDuration() {
	return duration;
}
public void setDuration(int duration) {
	this.duration = duration;
}

@Override
public String toString() {
	return "FrameInfo [frameNumber=" + frameNumber + ", duration=" + duration + "]";
}



}
