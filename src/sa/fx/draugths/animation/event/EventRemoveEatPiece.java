package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.boardgame.core.moves.Move;

public class EventRemoveEatPiece extends Event{



	/**
	 * 
	 */

	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventRemoveEatPiece> EAT_EVENT=new EventType<>(Event.ANY,"EAT_EVENT");
    public EventRemoveEatPiece(Move source, EventTarget target, EventType<EventRemoveEatPiece> eventType) {
        super(source, target, eventType);

	}



    

}
