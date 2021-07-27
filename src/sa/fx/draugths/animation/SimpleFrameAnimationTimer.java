/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import java.net.URL;

import javafx.animation.AnimationTimer;
import javafx.scene.media.AudioClip;
import sa.fx.draugths.sprite.Sprite;


/**
 *
 * @author Alessio Sardaro
 */
public class SimpleFrameAnimationTimer extends AnimationTimer{
    public int f1,f2;
    public static String FIRE="fire5.wav";
    public static String EAT="eate.wav";
    public static String BITE="Dogbite.wav";
    public static String BIG_BITE="lion_roar_2.mp3";
    public static String MOVEWHITE="jungle_drum.wav";
    public static String MOVESPACESOLDIER="jerpack.wav";
    public static String MOVEBLACK="moveAlien2.wav";
    public static String MOVEMONSTER="UFO.wav";
    public static String EXPLOSIONMONSTER="Fireball.wav";
    public static String MISSILE="top.wav";
    public static String DAMAMOVE_W="damamove.wav";
    public static String DAMAMOVE_B="move_alien.wav";
    public static String EXPLOSION="Fireball.wav";
    public static String BIGEXPLOSION="explos.wav";
    public static String MUSIC="muppet.mp3";
    public static String ACHW="Achievement.wav";
    public static String ACHB="pluck.wav";
    public String sound;
    public Sprite p;


    int i;
   
   
    long interval=0;  
    AudioClip mediaPlayer;
   
   
    boolean ciclyc;
    long before;
    boolean startMusic=true;

    
    public SimpleFrameAnimationTimer(int f1, int f2,Sprite p,boolean cyclic,long interval,String sound) {
     	this.f1 = f1;
        this.f2 = f2;
        this.p = p;
        this.sound=sound;
        if(sound!=null){
        mediaPlayer=buildMedia(sound);
            
        }
        this.ciclyc=cyclic;
        before=System.currentTimeMillis();
        i=this.f1;
        this.interval=interval;
    }
    
    
    @Override  
    public void handle(long now) {
    	
        long intervalTemp=System.currentTimeMillis()-before;

		 
        playEffect();
        if(i<=f2  && intervalTemp>this.interval) {
            
            before=System.currentTimeMillis();
            p.setFrame(i);
            p.toFront();
                   
            i++;
            if(this.ciclyc){
                if(i>f2) i=f1;
            }else if(!this.ciclyc && (i>f2) )  i=f2;

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
   
        this.i=f1;
        // //To change body of generated methods, choose Tools | Templates.
        
    }
    
    void playEffect(){
        
       if(mediaPlayer!=null)  {
       if(startMusic) {
           if(this.sound==FIRE|| 
                   this.sound==EAT ||this.sound==MOVEBLACK ||this.sound==DAMAMOVE_B||this.sound==MOVESPACESOLDIER ) {
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
