package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.sprite.Sprite;

public class EatPieceSelectEvent extends Event{



	Move move;
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EatPieceSelectEvent> EAT_SELECT=new EventType<>(Event.ANY,"EAT_SELECT");
    public EatPieceSelectEvent(Move source, EventTarget target, EventType<EatPieceSelectEvent> eventType) {
        super(source, target, eventType);
        this.move=source;

	}
	public Move getMove() {
		return move;
	}

}
