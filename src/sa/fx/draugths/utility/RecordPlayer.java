package sa.fx.draugths.utility;

import java.util.Comparator;

public class RecordPlayer implements Comparator<RecordPlayer>{
	String mame;
	Integer points;
	String propName;
	int i;
	
	
	
	public RecordPlayer() {
		super();

	}


	public RecordPlayer(String mame, Integer points, String propName,int i) {
		super();
		this.mame = mame;
		this.points = points;
		this.propName = propName;
		this.i=i;
	}
	
	
	public int getI() {
		return i;
	}


	public void setI(int i) {
		this.i = i;
	}


	public String getMame() {
		return mame;
	}
	public void setMame(String mame) {
		this.mame = mame;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}


	@Override
	public int compare(RecordPlayer o1, RecordPlayer o2) {
		if(o1.getPoints()>o2.getPoints()) return -1;
		else if(o1.getPoints()<o2.getPoints()) return 1;
		return 0;
	}
	

}
