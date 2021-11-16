/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import java.net.URL;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.media.AudioClip;
import sa.fx.draugths.sprite.AlienPiece;
import sa.fx.draugths.sprite.Sprite;


/**
 *
 * @author Alessio Sardaro
 */
public class SimpleFrameAnimationTimer extends AnimationTimer{
	FrameInfo[]  frames;
    public static final String FIRE="fire5.wav";
    public static final String SPEEDY_BITE="speedy_bite.wav";
    public static final String BITE="Dogbite.wav";
    public static final String BIG_BITE="lion_roar_2.mp3";
    public static final String JUNGLE="jungle_drum.wav";
    public static final String MOVESPACESOLDIER="jerpack.wav";
    public static final String CLOPETE_DOUBLE="moveAlien2.wav";
    public static final String MOVEMONSTER="UFO.wav";
    public static final String EXPLOSIONMONSTER="Fireball.wav";
    public static final String MISSILE="top.wav";
    public static final String ELICOPTER="elicopter.wav";
    public static final String CLOPETE="move_alien.wav";
    public static final String EXPLOSION="Fireball.wav";
    public static final String BIGEXPLOSION="explos.wav";
    public static final String MUSIC="muppet.mp3";
    public static final String ACHW="Achievement.wav";
    public static final String ACHB="pluck.wav";
    String sound;
    Sprite p;


    int i;
    int frameCount=0;
   
    long interval=0;  
    AudioClip mediaPlayer;
   
   
    boolean ciclyc;
    long before;
    boolean startMusic=true;

    
    public SimpleFrameAnimationTimer(FrameInfo[] frames,Sprite p,boolean cyclic,long interval,String sound) {
    	this.frames=frames;
        this.p = p;
        this.sound=sound;
        if(sound!=null){
        mediaPlayer=buildMedia(sound);
            
        }
        this.ciclyc=cyclic;
        before=System.currentTimeMillis();
        this.interval=interval;
    }
    
    
    @Override  
    public void handle(long now) {
    	
        long intervalTemp=System.currentTimeMillis()-before;

		 
        playEffect();
        if(intervalTemp>this.interval) {
            
            before=System.currentTimeMillis();
            p.setFrame(frames[i].getFrameNumber());
            p.toFront();
            frameCount++;
            if(p instanceof AlienPiece)
            System.out.println("Alien duration:"+frames[i].getDuration()+", framecoutn"+frameCount);
            if(frameCount>=frames[i].getDuration()) {
            	i++;
            	frameCount=0;
                if(this.ciclyc){
                    if(i>=frames.length) i=0;
                }else if(!this.ciclyc && (i>=(frames.length) ))  i=frames.length-1;            	
            }


        } 

             
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
       super.stop();
       if(mediaPlayer!=null)  {
           mediaPlayer.stop();
           
       }
        


        //pathTransition.stop();
       // super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start() {
        super.start();
   
        this.i=0;
        // //To change body of generated methods, choose Tools | Templates.
        
    }
    
    void playEffect(){
        
       if(mediaPlayer!=null)  {
       if(startMusic) {
           if(this.sound==FIRE|| 
                   this.sound==SPEEDY_BITE ||this.sound==CLOPETE ||this.sound==CLOPETE_DOUBLE||this.sound==MOVESPACESOLDIER ) {
             // mediaPlayer.setCycleCount(10);
               //mediaPlayer.seek(Duration.ONE);
               //mediaPlayer.setPriority(1);
               
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
    
    
}
