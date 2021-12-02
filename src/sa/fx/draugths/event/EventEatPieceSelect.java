package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.sprite.SpritePiece;

public class EventEatPieceSelect extends Event{



	Move move;
	SpritePiece spritepoece;
	
	
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventEatPieceSelect> EAT_SELECT=new EventType<>(Event.ANY,"EAT_SELECT");
    public EventEatPieceSelect(Move source, EventTarget target, EventType<EventEatPieceSelect> eventType) {
        super(source, target, eventType);
        this.move=source;

	}
	public Move getMove() {
		return move;
	}

}
