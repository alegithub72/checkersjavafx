package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.sprite.SpritePiece;

public class EventPointUpdate extends Event{



	
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventPointUpdate> MOVE_UPDATE=new EventType<>(Event.ANY,"POINT_UPDATE");
    private Move move;


	public EventPointUpdate(SpritePiece source,EventTarget target,Move move,  EventType<EventPointUpdate> eventType) {
        super(source, target, eventType);
        this.move=move;
	}
    
    
    public Move getMove() {
		return move;
	}
}
