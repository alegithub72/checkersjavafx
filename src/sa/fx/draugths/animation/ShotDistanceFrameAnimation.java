/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.sprite.SpritePiece;
import sa.fx.draugths.utility.SoundEffect;


/**
 *
 * @author Alessio Sardaro
 */
public class ShotDistanceFrameAnimation extends SimpleFrameAnimation{


      boolean  once;
      SpritePiece piece;


    public ShotDistanceFrameAnimation(FrameInfo[] frames, SpritePiece piece,Move move,boolean cyclic,long interval,SoundEffect effect) {
    	super(frames, piece,move, cyclic, interval, effect);

    	this.piece=piece;
        once=false;
    }    
     
    
	@Override
	protected void interpolate(double frac) {

    	BCDraugthsApp.log.info("interpolate:"+frac);
    	framing(frac);
    	if(frac>0) shotFireEvent();


    }

    private synchronized void shotFireEvent() {
    	if( !once ) {
        	once=true;
        	SpritePiece eated= piece.getFxBoard().getSpritePiece(move.getEat().getI(), move.getEat().getJ(), move.getEat().getColor(), true);
    		BCDraugthsApp.log.info("FIRE EventEatAnimPiece.KILLPLAY_EVENT:"+eated);
    		piece.getParent().fireEvent(new EventEatAnimPiece(eated,piece.getParent() ,move, EventEatAnimPiece.KILLPLAY_EVENT));
           
           }    	
    	
    }
    
	



    
    

    
    
}
