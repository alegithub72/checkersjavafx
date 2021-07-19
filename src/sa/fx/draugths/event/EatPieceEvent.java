package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.SpritePiece;

public class EatPieceEvent extends Event{



	/**
	 * 
	 */
	SpritePiece eat;
	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EatPieceEvent> EAT_EVENT=new EventType<>(Event.ANY,"EAT_EVENT");
    public EatPieceEvent(SpritePiece source, EventTarget target, EventType<EatPieceEvent> eventType) {
        super(source, target, eventType);
        this.eat=source;
	}
	public SpritePiece getEat() {
		return eat;
	}


    

}
