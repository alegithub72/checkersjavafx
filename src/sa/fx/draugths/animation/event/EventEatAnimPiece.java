package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.Sprite;

public class EventEatAnimPiece extends Event{




	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventEatAnimPiece> EATANIM_EVENT=new EventType<>(Event.ANY,"EATANIM_EVENT");
    public EventEatAnimPiece(Sprite source, EventTarget target, EventType<EventEatAnimPiece> eventType) {
        super(source, target, eventType);


	}



    

}
