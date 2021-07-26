/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import java.net.URL;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.event.EventCollisionSprite;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.sprite.Sprite;
import sa.fx.draugths.sprite.SpritePiece;


/**
 *
 * @author appleale
 */
public class FrameAnimationTimer extends SimpleFrameAnimationTimer{

    public SpritePiece piece;
    public Sprite shot;

    long interval=0;  

   // private PathTransition pathTransition;
    SpritePiece target;

    int frameX;
    boolean once;
    
    public FrameAnimationTimer(int f1, int f2,int frameX ,SpritePiece p,boolean cyclic,long interval,String sound) {
    	super(f1, f2, p, cyclic, interval, sound);
    	this.piece=p;
        this.frameX=frameX;
        once=false;
        this.piece = p;
       

    }
    public FrameAnimationTimer(int f1, int f2, SpritePiece p,Sprite shot,SpritePiece eated,boolean cyclic,long interval,String sound) {
    	super(f1, f2, p, cyclic, interval, sound);
    	this.piece=p;
        this.target=eated;
        this.frameX=-1;
        this.shot=shot;
        once=false;
    }    
     
    
    @Override  
    public void handle(long now) {

        long intervalTemp=System.currentTimeMillis()-before;
        double lx=0,ly=0;
       // BCDraugthsApp.log.info("id="+p.getId());
//        BCDraugthsApp.log.info(p+" (x,y)="+(p.getTranslateX())+","+(p.getTranslateY()));
        
        //if(target!=null) {

           // System.out.println("scene shot x="+shotSceneBound.getMinX());
            //System.out.println("scene shot y="+shotSceneBound.getMinY());
            //System.out.println("scene target x="+targetSceneBound.getMinX());
            //System.out.println("scene target y="+targetSceneBound.getMinY());
            //System.out.println(" shot intercept target="+targetSceneBound.contains(shotSceneBound.getMinX(),shotSceneBound.getMinY()));
        //}
		
		  if(target!=null && shot!=null  ){ 
			  Bounds shotSceneBound=shot.localToScene(shot.getBoundsInLocal()); 
			  Bounds targetSceneBound=target.localToScene(target.getBoundsInLocal()); 
			  //BCDraugthsApp.log.info("shotbound="+shotSceneBound+" "+shot.getX()+","+shot.getY());
			  if(  targetSceneBound.contains(shotSceneBound) && !once)
			  { 
			  	BCDraugthsApp.log.info("INTERSECTION DETECTED!!!!!!!");
			  	EventCollisionSprite c=new EventCollisionSprite(target, null,
			  			EventCollisionSprite.COLLISION_SPRITE);
			  	target.buildDestroyAnimation(piece.getBoardPieceLink().getType());
			  	target.destory();
			  	once=true;
			  	//shot.setVisible(false);
			  	piece.getFxBoard().remove(shot);
			  	//p.fireEvent(c);
		  }
		  
		  }
		 
        playEffect();
        if(i<=f2  && intervalTemp>this.interval) {
            
            before=System.currentTimeMillis();
            p.setFrame(i);
            p.toFront();
                   
            i++;
            if(this.ciclyc){
                if(i>f2) i=f1;
            }else if(!this.ciclyc && (i>f2) )  i=f2;
            if(frameX==i && !once && !piece.isEatedAnim()) {
            	piece.fireEvent(new EventEatAnimPiece(p, p, EventEatAnimPiece.EATANIM_EVENT));
            	once=true;
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
