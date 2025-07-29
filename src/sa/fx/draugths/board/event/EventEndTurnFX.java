package sa.fx.draugths.board.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.pieces.SpritePiece;

public class EventEndTurnFX extends Event{



	
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventEndTurnFX> END_TURN=new EventType<>(Event.ANY,"END_TURN");

    SpritePiece spritePiece;

	public SpritePiece getSrpitePiece() {
		return spritePiece;
	}

	public EventEndTurnFX(SpritePiece  source, EventTarget target, EventType<EventEndTurnFX> eventType) {
        super(source, target, eventType);
        this.spritePiece=source;
	}
    

}
