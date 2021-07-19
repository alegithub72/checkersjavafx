package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.SpritePiece;

public class DraugthTransformEvent extends Event{



	/**
	 * 
	 */
	SpritePiece p;
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<DraugthTransformEvent> DRAUGTH_EVENT=new EventType<>(Event.ANY,"DRAUGTH_EVENT");
    public DraugthTransformEvent(SpritePiece source, EventTarget target, EventType<DraugthTransformEvent> eventType) {
        super(source, target, eventType);
        this.p=source;
	}
	public SpritePiece getP() {
		return p;
	}
	public void setP(SpritePiece p) {
		this.p = p;
	}

    

}
