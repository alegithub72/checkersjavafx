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
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameInfo;
import sa.fx.draugths.animation.PieceAnimationEndHandler;
import sa.fx.draugths.animation.SimpleFrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
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
	List<SimpleFrameAnimationTimer> frameAnimTimer=new ArrayList<>();
	Animation[] transition=new Animation[5];
	boolean eatedAnim;
    
    
    
    

	protected SpritePiece(String colorFX,BoardHW boardHW, String img,FXBoard b) {
        super(img,colorFX);
        this.colorFX=colorFX;
        this.wSquare=boardHW.getH();
        this.hSquare=boardHW.getW();
        this.fxBoard=b;

                


    }
    void buildDefaultKillAnimation(FrameInfo[] frames, boolean ciclyc, long interval, String sound) {
        frameAnimTimer.add( new SimpleFrameAnimationTimer(frames, this,  ciclyc, interval, sound));
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

         if (m.getType() == Move.MOVE ) {           
         if( Piece.DRAUGTH==m.getP().getType() ) {
             buildAnimDamaMove(m);
             playAnimDamaMove();
         }else{
             buildAnimPedinaMove(m);
             playAnimPedinaMove();
         }

            
        } else if (m.getType() == Move.EAT) {
          
           fireEvent(new EventEatPieceSelect(m,this,EventEatPieceSelect.EAT_SELECT));  
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

        if(transition!=null && transition.length>0 && transition[TRANSITION_STEP.FULL_STEP]!=null) {
            transition[TRANSITION_STEP.FULL_STEP].play();
            
        }
        for(int h=0;h<frameAnimTimer.size();h++) {
            if(frameAnimTimer.get(h)!=null) {
            	frameAnimTimer.get(h).start();
            }
        }
        

        //check this code.................when intersect lauch the destruction 
        //event...

        if(fxBoard!=null) fxBoard.setAnimationOn(true);
      
    }    
    public void playKilled() {
        for(int h=0;h<frameAnimTimer.size();h++) {
            if(frameAnimTimer.get(h)!=null) {
            	frameAnimTimer.get(h).start();

            }
        }
    	
    }
    public void stopPlayAnimation() {

    	
        for(int h=0;h<frameAnimTimer.size();h++) {
            if(frameAnimTimer.get(h)!=null) {
            	BCDraugthsApp.log.info(h+")Stop:"+this);
            	frameAnimTimer.get(h).stop();
            }
        }
        frameAnimTimer=new ArrayList<>();
        
        for (int i = 0; i < transition.length; i++) {
            Animation a = transition[i];
            if(a!=null) a.stop();

            
        }
        setFrame(0);
        transition=new Animation[3];
        
    }
    
    public Duration getAnimDuration() {
        return Duration.seconds(1.5);
    }   
    

    
      
    
    AudioClip buildMedia(String sound){
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource(sound); 
        return new AudioClip(url.toString());
                
    }       
    
    
    

   protected void  buildAnimPedinaMove(Move m) {

        buildPedinaMovePath(m);
        buildMoveSequence(true);
        transition[TRANSITION_STEP.FULL_STEP].setOnFinished(new PieceAnimationEndHandler(this, m));

    }  
   
   protected void  buildAnimDamaMove(Move m) {
    
        buildDamaMovePath(m);
        buildMoveSequence( true);
        transition[TRANSITION_STEP.FULL_STEP].setOnFinished(new PieceAnimationEndHandler(this, m));

    }     
   
   protected void  buildAnimPedinaEat(Move m) {
    
        buildPedinaMoveEatPath(m);
        buildMoveEatSequence( m,true);
        transition[TRANSITION_STEP.FULL_STEP].setOnFinished(new PieceAnimationEndHandler(this, m));



    } 
   protected void  builAnimDamaEat(Move m) {
    
        buildDamaMoveEatPath(m);
        buildMoveEatSequence( m,true);
        transition[TRANSITION_STEP.FULL_STEP].setOnFinished(new PieceAnimationEndHandler(this, m));

    }       
    

    public abstract void buildKilledSequence(int by);
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
        return transition[TRANSITION_STEP.FULL_STEP].getStatus() == Animation.Status.STOPPED;
    }




  
}
