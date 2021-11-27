/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import java.net.URL;

import javafx.geometry.Bounds;
import javafx.scene.media.AudioClip;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.event.EventCollisionSprite;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.sprite.Sprite;
import sa.fx.draugths.sprite.SpritePiece;


/**
 *
 * @author Alessio Sardaro
 */
public class ShotDistanceFrameAnimationTimer extends SimpleFrameAnimationTimer{


    SpritePiece target;


    boolean once;
    

    public ShotDistanceFrameAnimationTimer(FrameInfo[] frames, Sprite sprite,Move move,SpritePiece eated,boolean cyclic,long interval,String sound) {
    	super(frames, sprite,move, cyclic, interval, sound);
        this.target=eated;
        once=false;
    }    
     
    
    @Override  
    public void handle(long now) {

        long intervalTemp=System.currentTimeMillis()-before;
        double lx=0,ly=0;
 
        playEffect();

        if(intervalTemp>this.interval) {
        	BCDraugthsApp.log.info(" sprite:"+sprite+",frame:"+frames[i]);
            before=System.currentTimeMillis();
            sprite.setFrame(frames[i].getFrameNumber());
            sprite.toFront();
                   
            frameCount++;       
            if(frames[i].getDuration()>=frameCount) {
            	i++;
            	frameCount=0;
            }
            if(this.ciclyc){
                if(i>=frames.length) i=0;
            }else if(!this.ciclyc && (i>=frames.length) )  i=frames.length-1;

        }            

    }




    @Override
    void playEffect(){
        
       if(mediaPlayer!=null)  {
       if(startMusic) {
           if(this.sound==FIRE|| 
                   this.sound==SPEEDY_BITE ||this.sound==CLOPETE ||this.sound==CLOPETE_DOUBLE||this.sound==MOVESPACESOLDIER ) {

               
               mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
           }
           

               mediaPlayer.play();
        	if( !once && (this.move.getType()!=Move.EAT)) {
   	        	sprite.fireEvent(new EventEatAnimPiece(sprite, sprite, EventEatAnimPiece.KILLPLAY_EVENT));
   	        	once=true;
   	        }	
               startMusic=false;
           }
           
       }        
        
    }
    
    

    
    
}
