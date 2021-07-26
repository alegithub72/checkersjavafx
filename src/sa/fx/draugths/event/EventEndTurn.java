package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.SpritePiece;

public class EventEndTurn extends Event{



	
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventEndTurn> END_TURN=new EventType<>(Event.ANY,"END_TURN");

    SpritePiece p;

	public SpritePiece getP() {
		return p;
	}

	public EventEndTurn(SpritePiece  source, EventTarget target, EventType<EventEndTurn> eventType) {
        super(source, target, eventType);
        this.p=source;
	}
    

}
