package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.SpritePiece;

public class EventDraugthTransform extends Event{



	/**
	 * 
	 */
	SpritePiece p;
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventDraugthTransform> DRAUGTH_EVENT=new EventType<>(Event.ANY,"DRAUGTH_EVENT");
    public EventDraugthTransform(SpritePiece source, EventTarget target, EventType<EventDraugthTransform> eventType) {
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
