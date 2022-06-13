package sa.fx.draugths.utility;

public class ScaleFactor {
	double scaleX;
	double scaleY;
	
	
	
	public ScaleFactor(double scaleX, double scaleY) {
		super();
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	public double getScaleX() {
		return scaleX;
	}
	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}
	public double getScaleY() {
		return scaleY;
	}
	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}
	@Override
	public String toString() {
		return "ScaleFactor [scaleX=" + scaleX + ", scaleY=" + scaleY + "]";
	}

}
