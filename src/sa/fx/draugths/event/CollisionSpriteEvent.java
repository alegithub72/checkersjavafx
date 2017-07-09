/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sa.fx.draugths.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class CollisionSpriteEvent extends Event{
    
    public static final EventType<CollisionSpriteEvent> COLLISION_SPRITE=new EventType<>(Event.ANY,"COLLISON_SPRITE");
    private Sprite object;
    
   public CollisionSpriteEvent(Sprite source, EventTarget target, EventType<CollisionSpriteEvent> eventType) {
        super(source, target, eventType);

        object = source;
    }

    public Sprite getSprite() {
        return object;
    }
    
}
