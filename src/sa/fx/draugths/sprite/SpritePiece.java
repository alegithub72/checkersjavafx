/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;


import java.net.URL;

import javafx.animation.Animation;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.event.EventEatPieceSelect;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public abstract class SpritePiece extends Sprite{



    Piece piece;
    boolean draugthTransform=false;
	public int[] MOVE_FRAME=new int[2];
    public int[] EAT_MOVE_FRAME=new int[2];
    public int[] EATED_ANIM_FRAME=new int[2];
    public static int DESTORY_ANIM=1;
    public static int MOVE_ANIM=0;
	public   int srpiteH ;
	public   int spriteW ;
	int wSquare;
	int hSquare;
	String colorFX;
	FXBoard fxBoard;
	Sprite[] extraSprite=new Sprite[2];
	FrameAnimationTimer[] frameAnimTimer;
	Animation[] parallelTransition;
	boolean eatedAnim;
    
    
    
    

	public SpritePiece(String colorFX,BoardHW boardHW, String img,FXBoard b) {
        super(img);
        this.colorFX=colorFX;
        this.wSquare=boardHW.getH();
        this.hSquare=boardHW.getW();
        this.fxBoard=b;
        parallelTransition=new Animation[5];
        frameAnimTimer=new FrameAnimationTimer[2];        


    }
    void buildGenericFrameAnimationDestroy(int f1, int f2, double frac, boolean ciclyc, long interval, String sound) {
        frameAnimTimer[DESTORY_ANIM] = new FrameAnimationTimer(f1, f2,-1, this, frac, ciclyc, interval, sound);
    }    

    
    

	public String getColorFX() {
		return colorFX;
	}
	public void setColorFX(String colorFX) {
		this.colorFX = colorFX;
	}
	public FXBoard getFxBoard() {
		return fxBoard;
	}
	public Piece getBoardPieceLink() {
        return piece;
    }

    public void setBoardPieceLink(Piece boardPiece) {
        this.piece = boardPiece;
    }
    public boolean isDraugthTransform() {
		return draugthTransform;
	}

	public void setDraugthTransform(boolean draugthTransform) {
		this.draugthTransform = draugthTransform;
	}
    
    public boolean isEatedAnim() {
		return eatedAnim;
	}
	public void setEatedAnim(boolean eatedAnim) {
		this.eatedAnim = eatedAnim;
	}

    public int getFrame() {
        return this.nframes;
    }    
    public void play(Move m) {

         if (m.getType() == Move.MOVE ) {           
         if( Checker.DRAUGTH==m.getP().getType() ) {
             animDamaMove(m);
             playAnimDamaMove();
         }else{
             animPedinaMove(m);
             playAnimPedinaMove();
         }

            
        } else if (m.getType() == Move.EAT) {
          
           fireEvent(new EventEatPieceSelect(m,this,EventEatPieceSelect.EAT_SELECT));  //0eated = fbx. 
          //  System.out.println("EAT ELIMATION of"+eated.getK()+")");
           if(m.getP().getType()==Checker.DRAUGTH){
               animDamaEat(m);
               playAnimDamaEat();
           }else {
               animPedinaEat(m);
               playAnimPedinaEat();
           
           }
        } 
        

    }
    protected void playAnimDamaMove(){
        start();
        
    }
    
    protected void playAnimPedinaMove(){
        start();
        
    }
    protected void playAnimPedinaEat(){
        start();
        
    }
    
    protected void playAnimDamaEat(){
        start();
        
    }
    
    public void start(){

        if(parallelTransition!=null && parallelTransition.length>0 && parallelTransition[TRANSITION_STEP.FULL_STEP]!=null) {
            parallelTransition[TRANSITION_STEP.FULL_STEP].play();
            
        }
        for(int h=0;h<frameAnimTimer.length;h++) {
            if(frameAnimTimer[h]!=null) {
            	frameAnimTimer[h].start();
            }
        }
        

        //check this code.................when intersect lauch the destruction 
        //event...
        //Bounds d= eated.getBoundsInLocal();
        //this.intersects(d);
        //bcdg.removePuntatori();
        if(fxBoard!=null) fxBoard.setAnimationOn(true);
      
    }    
    
    public void stop() {


        for(int h=0;h<frameAnimTimer.length;h++) {
            if(frameAnimTimer[h]!=null) {
            	frameAnimTimer[h].stop();
            	frameAnimTimer[h]=null;
            }
        }
        
        for (int i = 0; i < parallelTransition.length; i++) {
            Animation a = parallelTransition[i];
            if(a!=null) a.stop();
            //TODO: mai inserite sul board
          //  if(a!=null) fxBoard.remove(a);
            
        }
        setFrame(0);
        parallelTransition=new Animation[2];
        frameAnimTimer=new FrameAnimationTimer[2];
    }
    
    public Duration getAnimDuration() {
        return Duration.seconds(1.5);
    }   
    

    
      
    
    AudioClip buildMedia(String sound){
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource(sound); 
        return new AudioClip(url.toString());
                
    }       
    
    
    

   protected void  animPedinaMove(Move m) {
	   //TODO:
        buildPedinaMovePath(m);
        buildFrameMoveAnimation( 0, true);
        parallelTransition[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m));

    }  
   
   protected void  animDamaMove(Move m) {
    
        buildDamaMovePath(m);
        buildFrameMoveAnimation( 0, true);
        parallelTransition[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m));

    }     
   
   protected void  animPedinaEat(Move m) {
    
        buildPedinaMoveEatPath(m);
        buildFrameEatMoveAnimation( 0f, true);
        parallelTransition[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m));
       // eated.buildDestroyAnimation(m.getP().getType());


    } 
   protected void  animDamaEat(Move m) {
    
        buildDamaMoveEatPath(m);
        buildFrameEatMoveAnimation(0f, true);
        parallelTransition[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m));
       // this.eated=eated;
     //  eated.buildDestroyAnimation(m.getP().getType());
    }       
    

    public abstract void buildDestroyAnimation(int by);
    public abstract void buildFrameMoveAnimation( double frac, boolean ciclyc);
    public abstract void buildFrameEatMoveAnimation( double frac, boolean ciclyc);
    public abstract void buildPedinaMovePath(Move m);
    public abstract void buildDamaMovePath(Move m);
    public abstract void buildPedinaMoveEatPath(Move m);
    public abstract void buildDamaMoveEatPath(Move m);
    public abstract SpritePiece loadDraugthFrame() ;

	@Override
	public String toString() {
		return "SpritePiece [piece "+(piece.getColor()==Piece.BLACK?"BLACK":"WHITE")+"= (" + piece.getI()+","+piece.getJ()+") " + ", colorFX=" + colorFX + "]";
	}    


    public void removeExtraSprite(int n){
        if(extraSprite[n]!=null) fxBoard.remove(extraSprite[n]);
    }
    public boolean isAnimMoveFinish() {
        return parallelTransition[TRANSITION_STEP.FULL_STEP].getStatus() == Animation.Status.STOPPED;
    }


    
    public void stopAnimation(int n){
       if(frameAnimTimer[n]!=null) frameAnimTimer[n].stop();
    }

  
}
