package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class EndTurnEvent extends Event{



	
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EndTurnEvent> END_TURN=new EventType<>(Event.ANY,"END_TURN");



	public EndTurnEvent(Object  source, EventTarget target, EventType<EndTurnEvent> eventType) {
        super(source, target, eventType);

	}
    

}
