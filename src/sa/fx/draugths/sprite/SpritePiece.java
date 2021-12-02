/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameInfo;
import sa.fx.draugths.animation.PieceAnimationEndHandler;
import sa.fx.draugths.animation.ShotDistanceFrameAnimationTimer;
import sa.fx.draugths.animation.SimpleFrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.animation.event.EventBuildSequence;
import sa.fx.draugths.event.EventEatPieceSelect;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Piece;

/**
 *
 * @author  Alessio Sardaro
 */
public abstract class SpritePiece extends Sprite{



    Piece piece;
    boolean draugthTransform=false;
	FrameInfo[] moveSequenceFrame;
    FrameInfo[] eatMoveSequenceFrame;
    FrameInfo[] killSequenceFrame;


	int wSquare;
	int hSquare;
	String colorFX;
	FXBoard fxBoard;
	Sprite[] extraSprite=new Sprite[2];
	
	ParallelTransition pltransition;
	boolean eatedAnim;
    
    
    
    

	protected SpritePiece(String colorFX,BoardHW boardHW, String img,FXBoard b) {
        super(img,colorFX);
        this.colorFX=colorFX;
        this.wSquare=boardHW.getH();
        this.hSquare=boardHW.getW();
        this.fxBoard=b;

                


    }
    void buildDefaultKillAnimation(FrameInfo[] frames,Move m, boolean ciclyc, long interval, String sound) {
    	SimpleFrameAnimationTimer animationTimer=  new SimpleFrameAnimationTimer(frames, this,m,  ciclyc, interval, sound);
    	animationTimer.setDuration(Duration.seconds(0.5));
    	pltransition.getChildren().add(animationTimer );

    }    


	public void addMoveSequenceFrame(FrameInfo[] moveSequenceFrame) {
		this.moveSequenceFrame = moveSequenceFrame;
	}
	public void addEatMoveSequenceFrame(FrameInfo[] eatMoveSequenceFrame) {
		this.eatMoveSequenceFrame = eatMoveSequenceFrame;
	}
	public void addKillSequenceFrame(FrameInfo[] killSequenceFrame) {
		this.killSequenceFrame = killSequenceFrame;
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
   	 	 pltransition=new ParallelTransition();
         if (m.getType() == Move.MOVE ) {           
         if( Piece.DRAUGTH==m.getP().getType() ) {
             buildAnimDamaMove(m);
             playAnimDamaMove();
         }else{
             buildAnimPedinaMove(m);
             playAnimPedinaMove();
         }

            
        } else if (m.getType() == Move.EAT) {
        
           if(m.getP().getType()==Piece.DRAUGTH){
               builAnimDamaEat(m);
               playAnimDamaEat();
           }else {
               buildAnimPedinaEat(m);
               playAnimPedinaEat();
           
           }
        } 
        

    }
    protected void playAnimDamaMove(){
        playAnim();
        
    }
    
    protected void playAnimPedinaMove(){
        playAnim();
        
    }
    protected void playAnimPedinaEat(){
        playAnim();
        
    }
    
    protected void playAnimDamaEat(){
        playAnim();
        
    }
    
    private void playAnim(){
    	
        if(fxBoard!=null) fxBoard.setAnimationOn(true);
        BCDraugthsApp.log.info("playAnim prima:"+pltransition.getStatus()+" pltransition="+pltransition);
        pltransition.play();



      
    }    
    public void playKilled() {

    	pltransition.play();
    	
    }
    public void stopPlayAnimation() {

        BCDraugthsApp.log.info("PL)Stop transition:"+ pltransition.getStatus()+" pltransition=:"+pltransition);
        pltransition.stop();
        setFrame(0);

        
    }
    

    

    
      
    
    AudioClip buildMedia(String sound){
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource(sound); 
        return new AudioClip(url.toString());
                
    }       
    
    
    

   protected void  buildAnimPedinaMove(Move m) {

        buildPedinaMovePath(m);
        buildMoveSequence(true);
        pltransition.setOnFinished(new PieceAnimationEndHandler(this, m));

    }  
   
   protected void  buildAnimDamaMove(Move m) {
    
        buildDamaMovePath(m);
        buildMoveSequence( true);
        pltransition.setOnFinished(new PieceAnimationEndHandler(this, m));

    }     
   
   protected void  buildAnimPedinaEat(Move m) {
    
        buildPedinaMoveEatPath(m);
        buildMoveEatSequence( m,true);
        pltransition.setOnFinished(new PieceAnimationEndHandler(this, m));



    } 
   protected void  builAnimDamaEat(Move m) {
    
        buildDamaMoveEatPath(m);
        buildMoveEatSequence( m,true);
        pltransition.setOnFinished(new PieceAnimationEndHandler(this, m));

    }       
    

    public abstract void buildKilledSequence(Move m);
    public abstract void buildMoveSequence( boolean ciclyc);
    public abstract void buildMoveEatSequence( Move m,boolean ciclyc);
    public abstract void buildPedinaMovePath(Move m);
    public abstract void buildDamaMovePath(Move m);
    public abstract void buildPedinaMoveEatPath(Move m);
    public abstract void buildDamaMoveEatPath(Move m);
    public abstract SpritePiece loadDraugthFrame() ;

	@Override
	public String toString() {
		return "SpritePiece [piece "+(piece.getColor()==Piece.BLACK?"BLACK":"WHITE")+"-"+piece.getPos()+"(" + piece.getI()+","+piece.getJ()+") " + ", colorFX=" + colorFX + "]";
	}    


    public void removeExtraSprite(int n){
        if(extraSprite[n]!=null) fxBoard.remove(extraSprite[n]);
    }
    public boolean isAnimMoveFinish() {
        return pltransition.getStatus() == Animation.Status.STOPPED;
    }




  
}
