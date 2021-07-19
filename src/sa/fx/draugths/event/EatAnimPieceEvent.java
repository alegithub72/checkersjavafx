package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.Sprite;
import sa.fx.draugths.sprite.SpritePiece;

public class EatAnimPieceEvent extends Event{




	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EatAnimPieceEvent> EATANIM_EVENT=new EventType<>(Event.ANY,"EATANIM_EVENT");
    public EatAnimPieceEvent(Sprite source, EventTarget target, EventType<EatAnimPieceEvent> eventType) {
        super(source, target, eventType);


	}



    

}
