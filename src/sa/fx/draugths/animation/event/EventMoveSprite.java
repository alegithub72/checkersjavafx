package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.SpritePiece;

public class EventMoveSprite  extends EventAnimPiece{



	/**
	 * 
	 */
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventMoveSprite> MOVE_SPRITE=new EventType<>(Event.ANY,"MOVE_SPRITE");
    public EventMoveSprite(SpritePiece source, EventTarget target, EventType<EventMoveSprite> eventType) {
        super(source, target, eventType);

	}

}
