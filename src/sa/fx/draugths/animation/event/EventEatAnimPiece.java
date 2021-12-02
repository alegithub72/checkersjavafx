package sa.fx.draugths.animation.event;


import javafx.event.Event;
import javafx.event.EventType;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.sprite.SpritePiece;

public class EventEatAnimPiece extends EventAnimPiece{


	Move move;

	
	
	

	private static final long serialVersionUID = -2332571104664340411L;
    public static final EventType<EventEatAnimPiece> KILLPLAY_EVENT=new EventType<>(Event.ANY,"EATANIM_EVENT");
    public EventEatAnimPiece(SpritePiece source, FXBoard target,Move m, EventType<EventEatAnimPiece> eventType) {
        super(source, target, eventType);
        this.move=m;

	}


	public Move getMove() {
		return move;
	}
	public void setMove(Move m) {
		this.move = m;
	}


    

}
