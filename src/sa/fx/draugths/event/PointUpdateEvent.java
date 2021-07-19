package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.boardgame.core.moves.Move;

public class PointUpdateEvent extends Event{



	
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<PointUpdateEvent> MOVE_UPDATE=new EventType<>(Event.ANY,"POINT_UPDATE");
    private Move move;


	public PointUpdateEvent(Move source, EventTarget target, EventType<PointUpdateEvent> eventType) {
        super(source, target, eventType);
        this.move=source;
	}
    
    
    public Move getMove() {
		return move;
	}
}
