package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.SpritePiece;

public class EventAnimPiece extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8911804669438783996L;
	protected SpritePiece piece;

	public EventAnimPiece(EventType<? extends Event> eventType) {
		super(eventType);
	}

	public EventAnimPiece(SpritePiece source, EventTarget target, EventType<? extends Event> eventType) {
		super(source, target, eventType);
		this.piece=source;
	}

	public SpritePiece getPiece() {
		return piece;
	}

	public void setPiece(SpritePiece piece) {
		this.piece = piece;
	}

}