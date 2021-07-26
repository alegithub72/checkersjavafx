package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class EventEndTurn extends Event{



	
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventEndTurn> END_TURN=new EventType<>(Event.ANY,"END_TURN");



	public EventEndTurn(Object  source, EventTarget target, EventType<EventEndTurn> eventType) {
        super(source, target, eventType);

	}
    

}
