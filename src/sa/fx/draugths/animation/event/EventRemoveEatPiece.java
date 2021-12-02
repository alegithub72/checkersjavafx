package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.sprite.SpritePiece;

public class EventRemoveEatPiece extends  EventAnimPiece{



	/**
	 * 
	 */

	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventRemoveEatPiece> REMOVE_PIECE_EVENT=new EventType<>(Event.ANY,"REMOVE_PIECE_EVENT");
    public EventRemoveEatPiece(SpritePiece source, EventTarget target, EventType<EventRemoveEatPiece> eventType) {
        super(source, target, eventType);

	}



    

}
