/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;


import java.net.URL;


import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public abstract class SpritePiece extends Sprite{



    Piece boardPieceLink;
    boolean draugthTransform=false;






	public int[] MOVE_FRAME=new int[2];
    public int[] EAT_MOVE_FRAME=new int[2];
    public int[] EATED_ANIM_FRAME=new int[2];
    SpritePiece eated;
	public   int srpiteH ;
	public   int spriteW ;
	int wSquare;
	int hSquare;
	String colorFX;
	FXBoard fbx;
	Sprite[] extraSprite=new Sprite[2];
	FrameAnimationTimer[] frameAnimTimer;
	Animation[] ptList;
    
    
    
    
    public SpritePiece(String colorFX,BoardHW boardHW, String img,FXBoard b) {
        super(img);
        this.colorFX=colorFX;
        this.wSquare=boardHW.getH();
        this.hSquare=boardHW.getW();
        this.fbx=b;
        ptList=new Animation[5];
        frameAnimTimer=new FrameAnimationTimer[2];        
//        setScaleX(0.64);
//        setScaleY(0.64);
//        w=(int)(w*0.64);
//        h=(int)(w*0.64);

    }
    void buildGenericFrameAnimation(int f1, int f2, double frac, boolean ciclyc, long interval, String sound) {
        frameAnimTimer[0] = new FrameAnimationTimer(f1, f2, this, frac, ciclyc, interval, sound);
    }    

    
    
    public Piece getBoardPieceLink() {
        return boardPieceLink;
    }

    public void setBoardPieceLink(Piece boardPiece) {
        this.boardPieceLink = boardPiece;
    }
    public boolean isDraugthTransform() {
		return draugthTransform;
	}

	public void setDraugthTransform(boolean draugthTransform) {
		this.draugthTransform = draugthTransform;
	}
    


    public int getFrame() {
        return this.nframes;
    }    
    public void play(Move m) {

         if (m.getType() == Move.MOVE ) {           
         if( Checker.DRAUGTH==m.getP().getType() ) {
             animDamaMove(m);
             playAnimDamaMove(m);
         }else{
             animPedinaMove(m);
             playAnimPedinaMove(m);
         }

            
        } else if (m.getType() == Move.EAT) {
            
            eated = fbx.getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(),m.getEat().isEated()); 
          //  System.out.println("EAT ELIMATION of"+eated.getK()+")");
           if(m.getP().getType()==Checker.DRAUGTH){
               animDamaEat(m);
               playAnimDamaEat(m);
           }else {
               animPedinaEat(m);
               playAnimPedinaEat(m);
           
           }
        } 
        

    }
    protected void playAnimDamaMove(Move m){
        start(m);
        
    }
    
    protected void playAnimPedinaMove(Move m){
        start(m);
        
    }
    protected void playAnimPedinaEat(Move m){
        start(m);
        
    }
    
    protected void playAnimDamaEat(Move m){
        start(m);
        
    }
    
    public void start(Move m){

        if(ptList!=null && ptList.length>0 && ptList[TRANSITION_STEP.FULL_STEP]!=null) {
            ptList[TRANSITION_STEP.FULL_STEP].play();
            
        }
        for(int h=0;h<frameAnimTimer.length;h++) {
            if(frameAnimTimer[h]!=null) {
            	frameAnimTimer[0].start();
            }
        }
        
        if(eated!=null) eated.start(m);
        //check this code.................when intersect lauch the destruction 
        //event...
        //Bounds d= eated.getBoundsInLocal();
        //this.intersects(d);
        //bcdg.removePuntatori();
        if(fbx!=null) fbx.setOn(true);
      
    }    
    
    public void stop() {

        frameAnimTimer[0].stop();
        for (int i = 0; i < ptList.length; i++) {
            Animation a = ptList[i];
            if(a!=null) a.stop();
            if(a!=null) fbx.remove(a);
            ptList[i]=null;
        }
        setFrame(0);

        
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
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, w, h,this.fbx));

    }  
   
   protected void  animDamaMove(Move m) {
    
        buildDamaMovePath(m);
        buildFrameMoveAnimation( 0, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, w, h,this.fbx));

    }     
   
   protected void  animPedinaEat(Move m) {
    
        buildPedinaMoveEatPath(m);
        buildFrameEatMoveAnimation( 0f, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, eated, w, h,this.fbx));
        eated.buildDestroyAnimation(m.getP().getType());


    } 
   protected void  animDamaEat(Move m) {
    
        buildDamaMoveEatPath(m);
        buildFrameEatMoveAnimation(0f, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, eated, w, h,this.fbx));
       // this.eated=eated;
       eated.buildDestroyAnimation(m.getP().getType());
    }       
    
    public void removeAnimationSetting() {
        eated = null;
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
		return "SpritePiece [piece "+(boardPieceLink.getColor()==Piece.BLACK?"BLACK":"WHITE")+"= (" + boardPieceLink.getI()+","+boardPieceLink.getJ()+") " + ", colorFX=" + colorFX + "]";
	}    


    public void removeExtraSprite(int n){
        if(extraSprite[n]!=null) fbx.remove(extraSprite[n]);
    }
    public boolean isAnimMoveFinish() {
        return ptList[TRANSITION_STEP.FULL_STEP].getStatus() == Animation.Status.STOPPED;
    }


    
    public void stopAnimation(int n){
       if(frameAnimTimer[n]!=null) frameAnimTimer[n].stop();
    }

  
}
