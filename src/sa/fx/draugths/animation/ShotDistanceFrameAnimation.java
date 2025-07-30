/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import sa.gameboard.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.SoundEffect;

import java.util.logging.Level;


/**
 *
 * @author Alessio Sardaro
 */
public class ShotDistanceFrameAnimation extends SimpleFrameAnimation{


      boolean  once;
      SpritePiece piece;


    public ShotDistanceFrameAnimation(FrameSequence[] frames, SpritePiece piece,Move move,boolean cyclic,long interval) {
    	super(frames, piece,move, cyclic, interval);

    	this.piece=piece;
        once=false;
    }    
     
    
	@Override
	protected void interpolate(double frac) {

    	BCDraugthsApp.log.log(Level.INFO,"interpolate:"+frac);
    	framing(frac);
    	if(frac>0) shotFireEvent();


    }

    private synchronized void shotFireEvent() {
    	if( !once ) {
        	once=true;
        	SpritePiece eated= piece.getFxBoard().getSpritePiece(move.getEat().getI(), move.getEat().getJ(), move.getEat().getColor(), true);
    		BCDraugthsApp.log.log(Level.INFO,"FIRE EventEatAnimPiece.KILLPLAY_EVENT:"+eated);
    		piece.getParent().fireEvent(new EventEatAnimPiece(eated,piece.getParent() ,move, EventEatAnimPiece.KILLPLAY_EVENT));
           
           }    	
    	
    }
    
	



    
    

    
    
}
