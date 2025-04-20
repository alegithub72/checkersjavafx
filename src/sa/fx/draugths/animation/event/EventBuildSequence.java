package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.pieces.SpritePiece;

public class EventBuildSequence extends EventAnimPiece{



	Move move;

	
	
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventBuildSequence> KILL_SEQUENCE=new EventType<>(Event.ANY,"KILL_SEQUENCE");
    public EventBuildSequence(SpritePiece source, Move move, EventTarget target, EventType<EventBuildSequence> eventType) {
        super(source, target, eventType);
        this.move=move;

	}
	public Move getMove() {
		return move;
	}

}
