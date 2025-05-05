/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces;


import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.*;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

/**
 *
 * @author  Alessio Sardaro
 */
public abstract class SpritePiece extends Sprite {



    protected Piece piece;
    protected boolean draugthTransform=false;
    protected FrameSequence[] moveSequenceFrame;
    protected FrameSequence[] eatMoveSequenceFrame;
    protected FrameSequence[] killSequenceFrame;


    protected int wSquare;
    protected int hSquare;
	String nameType;
    protected FXBoard fxBoard;
    protected Sprite[] extraSprite=new Sprite[2];

    protected ParallelTransition pltransition;
    protected HashMap<String,Transition> steps;
	boolean eatedAnim;


	protected SpritePiece(String pieceImg,String nameType, BoardHW boardHW, Piece piece,  FXBoard b) {
        super(pieceImg, nameType);
        this.piece = piece;
        this.nameType = nameType;
        this.wSquare = boardHW.getH();
        this.hSquare = boardHW.getW();
        this.fxBoard = b;
        steps = new HashMap<>();
        init();
    }
    protected abstract void init();

    protected void buildDefaultKillAnimation(FrameSequence[] seq, Move m, boolean ciclyc, long interval) {

//        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(FRAME_DURATION), event -> {
//            currentFrame = (currentFrame + 1) % TOTAL_FRAMES;
//            imageView.setViewport(new Rectangle2D(currentFrame * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT));
//        }));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();


        SimpleFrameAnimation transition=  new SimpleFrameAnimation(seq, this,m,  ciclyc, interval);
    	transition.setDuration(Duration.seconds(0.5));
    	pltransition.getChildren().add(transition );
    	SpritePiece eated=this;
        transition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				  if(m.getType()==Move.EAT) { 
					  
					  BCDraugthsApp.log.info("FIRE EventRemoveEatPiece.REMOVE_PIECE_EVENT: "+eated);
					  fireEvent(new EventRemoveEatPiece(eated, eated.getParent(),EventRemoveEatPiece.REMOVE_PIECE_EVENT));


				  }
				
			}
        });
			

    }    


	public void addMoveSequenceFrame(FrameSequence[] moveSequenceFrame) {
		this.moveSequenceFrame = moveSequenceFrame;
	}
	public void addEatMoveSequenceFrame(FrameSequence[] eatMoveSequenceFrame) {
		this.eatMoveSequenceFrame = eatMoveSequenceFrame;
	}
	public void addKillSequenceFrame(FrameSequence[] killSequenceFrame) {
		this.killSequenceFrame = killSequenceFrame;
	}
    

	public String getNameType() {
		return nameType;
	}
	public void setNameType(String nameType) {
		this.nameType = nameType;
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
         this.setManaged(false);
   	 	 steps=new HashMap<>();
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
        this.setManaged(false);
    	pltransition.play();
    	
    }
    public void stopPlayAnimation() {

    	BCDraugthsApp.log.info("PL)Stop transition:"+ pltransition.getStatus()+" pltransition=:"+pltransition);
        pltransition.stop();
        this.setManaged(true);
        setFrame(0);

        
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
    public abstract   SpritePiece crownedSound() ;

	@Override
	public String toString() {
		return "SpritePiece [piece "+(piece.getColor()==Piece.BLACK?"BLACK":"WHITE")+"-"+piece.getPos()+"(" + piece.getI()+","+piece.getJ()+") " + ", colorFX=" + nameType + "]";
	}    


    public void removeExtraSprite(int n){
        if(extraSprite[n]!=null) fxBoard.remove(extraSprite[n]);
    }
    public boolean isAnimMoveFinish() {
        return pltransition.getStatus() == Animation.Status.STOPPED;
    }




  
}
