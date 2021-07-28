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
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.sprite.SpritePiece;


/**
 *
 * @author Alessio Sardaro
 */
public class FrameCollisionAnimationTimer extends SimpleFrameAnimationTimer{

    public SpritePiece piece;
    SpritePiece target;

  

   // private PathTransition pathTransition;


    boolean once;
    
    public FrameCollisionAnimationTimer(int f1, int f2 ,SpritePiece p,SpritePiece target,boolean cyclic,long interval,String sound) {
    	super(f1, f2, p, cyclic, interval, sound);
    	this.piece=p;
        this.target=target;
        once=false;
        this.piece = p;
        

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
		
		  if(target!=null ){ 
			  Bounds pSceneBound=piece.localToScene(piece.getBoundsInLocal()); 
			  Bounds targetSceneBound=target.localToScene(target.getBoundsInLocal()); 
			  //BCDraugthsApp.log.info("shotbound="+shotSceneBound+" "+shot.getX()+","+shot.getY());
			  if(  pSceneBound.intersects(targetSceneBound) && !once)
			  { 
			  	BCDraugthsApp.log.info("INTERSECTION DETECTED!!!!!!!");
            	piece.fireEvent(new EventEatAnimPiece(p, p, EventEatAnimPiece.EATANIM_EVENT));
//			  	target.buildDestroyAnimation(piece.getBoardPieceLink().getType());
//			  	target.destory();
			  	once=true;
			  	//shot.setVisible(false);
			  	//piece.getFxBoard().remove(shot);
			  	//p.fireEvent(c);
		  }
		  
		  }
		 
        playEffect();
        if(i<=f2  && intervalTemp>this.interval) {
        	BCDraugthsApp.log.info(" interval:"+intervalTemp+">"+interval);
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
