/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import java.net.URL;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.sprite.Sprite;


/**
 *
 * @author Alessio Sardaro
 */
public class SimpleFrameAnimation extends Transition{
	FrameInfo[]  frames;
    public static final String FIRE="fire5.wav";
    public static final String SPEEDY_BITE="speedy_bite.wav";
    public static final String BITE="Dogbite.wav";
    public static final String BIG_BITE="lion_roar_2.mp3";
    public static final String JUNGLE="jungle_drum.wav";
    public static final String MARCH="marcias.wav";
    public static final String MOVESPACESOLDIER="jerpack.wav";
    public static final String CLOPETE_DOUBLE="moveAlien2.wav";
    public static final String MOVEMONSTER="UFO.wav";
    public static final String EXPLOSIONMONSTER="Fireball.wav";
    public static final String MISSILE="top.wav";
    public static final String ELICOPTER="elicopter.wav";
    public static final String CLOPETE="move_alien.wav";
    public static final String EXPLOSION="Fireball.wav";
    public static final String BIGEXPLOSION="Explosion3.wav";
    public static final String ACHW="Achievement.wav";
    public static final String ACHB="pluck.wav";
    String sound;
    Sprite sprite;
    Move move;

    int i;
    int frameCount=0;
   
    long interval=0;  
    AudioClip mediaPlayer;
   
   
    boolean ciclyc;
    long before;
    boolean startMusic=true;

    
    public SimpleFrameAnimation(FrameInfo[] frames,Sprite sprite,Move move,boolean cyclic,long interval,String sound) {
    	this.frames=frames;
        this.sprite = sprite;
        this.move=move;
        this.sound=sound;
        if(sound!=null){
        mediaPlayer=buildMedia(sound);
            
        }
        this.ciclyc=cyclic;
        before=System.currentTimeMillis();
        this.interval=interval;
        addEndHandler();
    }
    public SimpleFrameAnimation(FrameInfo[] frames,Sprite sprite,boolean cyclic,long interval,String sound) {
    	this.frames=frames;
        this.sprite = sprite;
        this.move=null;
        this.sound=sound;
        if(sound!=null){
        mediaPlayer=buildMedia(sound);

            
        }
        this.ciclyc=cyclic;
        before=System.currentTimeMillis();
        this.interval=interval;
        addEndHandler();
     
    }
    
    public void setDuration(Duration s) {
    	setCycleDuration(s);
    	
    }


    public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	

    
    void playEffect(){
        
       if(mediaPlayer!=null)  {
       if(startMusic) {
           if(this.sound==FIRE|| 
                   this.sound==SPEEDY_BITE ||this.sound==CLOPETE ||this.sound==CLOPETE_DOUBLE||this.sound==MOVESPACESOLDIER ) {

               
               mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
           }
           

               mediaPlayer.play();
               startMusic=false;
           }
           
       }        
        
    }
    
    
    AudioClip buildMedia(String sound){
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource(sound); 
        return new AudioClip(url.toString());
                
    }
	@Override
	protected void interpolate(double frac) {
		framing(frac);


		
	}
	protected void framing(double frac) {
        long intervalTemp=System.currentTimeMillis()-before;


        if(intervalTemp>this.interval) {
        	
            before=System.currentTimeMillis();
            sprite.setFrame(frames[i].getFrameNumber());
            playEffect();
            sprite.toFront();
            frameCount++;
            BCDraugthsApp.log.info("sprite:"+sprite+",frames:"+frames[i]+" interval:"+intervalTemp);
            if(frameCount>=frames[i].getDuration()) {
            	i++;
            	frameCount=0;
                if(this.ciclyc){
                    if(i>=frames.length) i=0;
                }else if(!this.ciclyc && (i>=(frames.length) ))  i=frames.length-1;            	
            }
  
        } 


		
	}
    private void addEndHandler() {
        setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				  mediaPlayer.stop();
				  event.consume();
				
			}
		});   
    }
}
