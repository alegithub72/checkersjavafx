package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.Sprite;

public class EventMoveSprite extends Event{



	/**
	 * 
	 */
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventMoveSprite> MOVE_SPRITE=new EventType<>(Event.ANY,"MOVE_SPRITE");
    public EventMoveSprite(Sprite source, EventTarget target, EventType<EventMoveSprite> eventType) {
        super(source, target, eventType);

	}

}
