/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.Sprite;
import sa.fx.draugths.sprite.SpritePiece;

/**
 *
 * @author Alessio Sardaro
 */
public class EventCollisionSprite extends Event{
    
    public static final EventType<EventCollisionSprite> COLLISION_SPRITE=new EventType<>(Event.ANY,"COLLISON_SPRITE");
    private SpritePiece eated;
    
   public EventCollisionSprite(SpritePiece source, EventTarget target, EventType<EventCollisionSprite> eventType) {
        super(source, target, eventType);
        eated= source;
    }

    public Sprite getEated() {
        return eated;
    }
    
}
