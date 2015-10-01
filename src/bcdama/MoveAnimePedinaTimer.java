/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import dama.core.PedinaChar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.util.Duration;


/**
 *
 * @author ale2s_000
 */
public class MoveAnimePedinaTimer extends AnimationTimer{
    public int f1,f2;
    public static String FIRE="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/fire5.wav";
    public static String EAT="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/eate.wav";
    public static String BITE="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/Dogbite.wav";
    public static String BIG_BITE="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/lion_roar_2.mp3";
    public static String MOVEWHITE="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/jungle_drum.wav";
    public static String MOVEBLACK="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/moveAlien2.wav";
    public static String MISSILE="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/top.wav";
    public static String DAMAMOVE_W="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/damamove.wav";
    public static String DAMAMOVE_B="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/move_alien.wav";
    public static String EXPLOSION="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/Fireball.wav";
    public static String BIGEXPLOSION="file:/Users/ale2s_000/Documents/NetBeansProjects/BCDama/src/explos.wav";
    public String sound;
    public PedinaGeneral p;
    int i;
    long before;
    long interval=100;  
    AudioClip mediaPlayer;
    Duration half;
   // private PathTransition pathTransition;
    ParallelTransition parallelTransition;
    boolean ciclyc;
    double frac;
    boolean startMusic=true;
    public MoveAnimePedinaTimer(int f1, int f2, PedinaGeneral p,ParallelTransition parallelTransition,double frac,boolean cyclic,long interval,String sound) {
        this.f1 = f1;
        this.f2 = f2;
        //this.pathTransition=pathTransition;
        this.parallelTransition=parallelTransition;
        this.p = p;
        this.sound=sound;
        if(sound!=null){
        //Media audioMedia = new Media(sound);
        //mediaPlayer=new MediaPlayer(audioMedia);
        //audioMedia.getDuration();
        mediaPlayer=new AudioClip(sound);
            
        }
        System.out.println("duration---"+this.parallelTransition.getTotalDuration().toMillis());
        System.out.println("frac---"+frac);
        System.out.println("this.f1---"+this.f1);
        System.out.println("this.f2---"+this.f2);
        this.frac=frac;
        this.ciclyc=cyclic;
        i=this.f1;
        this.interval=interval;
    }
     
    
    @Override  
    public void handle(long now) {

        long intervalTemp=System.currentTimeMillis()-before;

        //System.out.println("------time=now----->"+System.currentTimeMillis()+"="+now);
        //System.out.println("------current time----->"+parallelTransition.getCurrentTime());
        //System.out.println("------current duration----->"+parallelTransition.getTotalDuration());
        
        half= Duration.millis(this.parallelTransition.getTotalDuration().toMillis()*this.frac);
        //System.out.println("------current half----->"+half);
        System.out.println("----------------------->"+intervalTemp+"<--------------------");
         if(parallelTransition.getCurrentTime().greaterThan(half))    playEffect();
        if(i<=f2  && intervalTemp>this.interval && parallelTransition.getCurrentTime().greaterThan(half)) {
            
            before=System.currentTimeMillis();
            p.setFrame(i);
 
            /**  try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MoveAnimePedinaTimer.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        //    if(p.getX()<x1) p.setX(p.getX()+1);
          //  if(p.getY()<y1) p.setY(p.getY()+1);
         //   System.out.println("Pedina x,y:"+p.getX()+","+p.getY());
//            System.out.println(pathTransition.statusProperty().getValue());
          //if(pathTransition.getStatus()== Animation.Status.STOPPED) this.stop();
            System.out.println("#################PEDINA#########--->"+p.pedinaCharAssociated+"<#########################");
            //System.out.println("------intervalTemp----->"+i+"  "+intervalTemp);
            System.out.println("------this.interval----->"+i+"  "+this.interval);            
            System.out.println("----"+intervalTemp+"------->i======"+i);
            System.out.println("----duratioàn>half------->i======"+parallelTransition.getCurrentTime().greaterThan(half));
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
        System.out.println("#########################-------------->STOP"+p.pedinaCharAssociated);
        if(p.pedinaCharAssociated.getType()==PedinaChar.DAMA) {
            p.setFrameDama();
        }
        else p.setFrame(0);

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
                   this.sound==EAT ||this.sound==MOVEBLACK ||this.sound==DAMAMOVE_B ) {
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
    
    
}
