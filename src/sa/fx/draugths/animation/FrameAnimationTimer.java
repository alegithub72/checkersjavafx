/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import java.net.URL;

import javafx.geometry.Bounds;
import javafx.scene.media.AudioClip;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.event.EventCollisionSprite;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.sprite.Sprite;
import sa.fx.draugths.sprite.SpritePiece;


/**
 *
 * @author Alessio Sardaro
 */
public class FrameAnimationTimer extends SimpleFrameAnimationTimer{

    public SpritePiece piece;
    public Sprite shot;

  

   // private PathTransition pathTransition;
    SpritePiece target;


    boolean once;
    
    public FrameAnimationTimer(FrameInfo[] frames ,SpritePiece p,boolean cyclic,long interval,String sound) {
    	super(frames, p, cyclic, interval, sound);
    	this.piece=p;
        once=false;
        this.piece = p;
        

    }
    public FrameAnimationTimer(FrameInfo[] frames, SpritePiece p,Sprite shot,SpritePiece eated,boolean cyclic,long interval,String sound) {
    	super(frames, p, cyclic, interval, sound);
    	this.piece=p;
        this.target=eated;
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

        if(intervalTemp>this.interval) {
        	BCDraugthsApp.log.info(" interval:"+intervalTemp+">"+interval);
            before=System.currentTimeMillis();
            p.setFrame(frames[i].getFrameNumber());
            p.toFront();
                   
            frameCount++;       
            if(frames[i].getDuration()>=frameCount) {
            	i++;
            	frameCount=0;
            }
            if(this.ciclyc){
                if(i>=frames.length) i=0;
            }else if(!this.ciclyc && (i>=frames.length) )  i=frames.length-1;

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
    @Override
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
        	if( !once && !piece.isEatedAnim()) {
   	        	piece.fireEvent(new EventEatAnimPiece(p, p, EventEatAnimPiece.EATANIM_EVENT));
   	        	once=true;
   	        }	
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
