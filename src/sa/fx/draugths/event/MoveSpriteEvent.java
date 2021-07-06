package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.Sprite;

public class MoveSpriteEvent extends Event{



	/**
	 * 
	 */
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<MoveSpriteEvent> MOVE_SPRITE=new EventType<>(Event.ANY,"MOVE_SPRITE");
    public MoveSpriteEvent(Sprite source, EventTarget target, EventType<MoveSpriteEvent> eventType) {
        super(source, target, eventType);

	}

}
