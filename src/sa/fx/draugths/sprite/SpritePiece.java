/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;


import java.net.URL;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.event.SelectEventPlayer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public abstract class SpritePiece extends Parent{
    public static int MOVE_TRANSITION=0;
    
    Image frameImages;
    ImageView pedina;
    Rectangle2D[] frames;
    int w;
    int h;
    double wboardBox;
    double hBoardBox;
    int nframes = 0;
    FrameAnimationTimer frameAnimTimer[];
    Piece boardPieceLink;
    int k;
    boolean draugthTransform=false;


    
    
    
    
    public Piece getBoardPieceLink() {
        return boardPieceLink;
    }

    public void setBoardPieceLink(Piece boardPiece) {
        this.boardPieceLink = boardPiece;
    }

    
    
    
    Animation ptList[];
    BCDraugthsApp bcdg;
    SpritePiece eated;
    
    
    
    
    public SpritePiece(int w, int h,double wboardBox,double hBoardBox, BCDraugthsApp bcdg,String img) {
        this.w = w;
        this.h = h;
        this.wboardBox=wboardBox;
        this.hBoardBox=hBoardBox;
        this.bcdg=bcdg;
        frameImages = new Image(img);
        buildFrameImages();
        pedina = new ImageView(frameImages);
        pedina.setViewport(frames[0]);  
        getChildren().add(pedina);
        ptList=new Animation[5];
        
        frameAnimTimer=new FrameAnimationTimer[2];

    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }
    
    
    
    public void buildFrameImages(){
                int n = (frameImages.widthProperty().intValue() / w);
        frames = new Rectangle2D[n];
        for (int i = 0; i < n; i++) {
            frames[i] = new Rectangle2D(i * w, 0, w, h);
        }
    }
    public void setFrame(int i) {
        this.nframes = i;
        pedina.setViewport(frames[i]);
    }

    public int getFrame() {
        return this.nframes;
    }    
    public void setX(double x){
        pedina.setX(x);
    }
    public void setY(double y){
        pedina.setY(y);
    }    
    public void play(Move m) {

         if (m.getType() == Move.MOVE ) {           
         if( m.getP().getType()==Piece.DRAUGTH ) {
             animDamaMove(m);
             playAnimDamaMove(m);
         }else{
             animPedinaMove(m);
             playAnimPedinaMove(m);
         }

            
        } else if (m.getType() == Move.EAT) {
            
            eated = bcdg.getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(),m.getEat().isEated()); 
          //  System.out.println("EAT ELIMATION of"+eated.getK()+")");
           if(m.getP().getType()==Piece.DRAUGTH){
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

        if(ptList!=null && ptList.length>0 && ptList[TRANSITION_STEP.FULL_STEP]!=null) 
            ptList[TRANSITION_STEP.FULL_STEP].play();
        if(frameAnimTimer[0]!=null) frameAnimTimer[0].start();
        if(eated!=null) eated.start(m);

        //bcdg.removePuntatori();
        bcdg.setOn(true);
      
    }    
    
    public void stop() {

        frameAnimTimer[0].stop();
        for (int i = 0; i < ptList.length; i++) {
            Animation a = ptList[i];
            if(a!=null) a.stop();
            if(a!=null) bcdg.getBoardGroup().getChildren().remove(a);
            ptList[i]=null;
        }
        setFrame(0);

        
    }
    
    public Duration getAnimDuration() {
        return Duration.seconds(1.5);
    }   
    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }    
       public static SpritePiece  buildPedina(int w, int h,double wboardBox,double hBoardBox, int color,Piece pedinassociated,BCDraugthsApp bcdg,int level) {
       String imagePedina=null;
       SpritePiece pedina=null;    
       if(color!=pedinassociated.getColor()) throw new RuntimeException("Disegual color");
        if (Piece.BLACK == color) {
            imagePedina = "black_checker.png";
            pedina= new SBlackPiece(Piece.BLACK, bcdg, pedinassociated, w, h,  wboardBox, hBoardBox,imagePedina);
            if(pedinassociated.getType()==Piece.DRAUGTH) pedina.setFrameDama();
        } else {
            imagePedina = "white_cheker.png";
            pedina= new SWhitePiece(Piece.WHITE, bcdg, pedinassociated, w, h,wboardBox,hBoardBox, imagePedina);
            if(pedinassociated.getType()==Piece.DRAUGTH) pedina.setFrameDama();
        }
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setColor(Color.BLACK);
        pedina.setEffect(dropShadow);
        pedina.setOnMouseClicked(new SelectEventPlayer(bcdg,pedina));
    
        return pedina;

    }
    
    AudioClip buildMedia(String sound){
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource(sound); 
        return new AudioClip(url.toString());
                
    }       
    
    public boolean isAnimMoveFinish(){
          return ptList[TRANSITION_STEP.FULL_STEP].getStatus()==Animation.Status.STOPPED;
    }
    
    

   protected void  animPedinaMove(Move m) {
    
        buildPedinaMovePath(m);
        buildFrameMoveAnimation( 0, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m,this.bcdg, w, h));

    }  
   
   protected void  animDamaMove(Move m) {
    
        buildDamaMovePath(m);
        buildFrameMoveAnimation( 0, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m,this.bcdg, w, h));

    }     
   
   protected void  animPedinaEat(Move m) {
    
        buildPedinaMoveEatPath(m);
        buildFrameEatMoveAnimation( 0f, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, eated,this.bcdg, w, h));
        this.eated.buildDestroyPedinaAnimation();


    } 
   protected void  animDamaEat(Move m) {
    
        buildDamaMoveEatPath(m);
        buildFrameEatMoveAnimation(0f, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, eated,this.bcdg, w, h));
       // this.eated=eated;
        this.eated.buildDestroyPedinaAnimation();


    }       
    
    public void removeAnimationSetting(){
        for (int i = 0; i < ptList.length; i++) {
             ptList[i]=null;
            
        }
        for (int i = 0; i < frameAnimTimer.length; i++) {
            frameAnimTimer[i]=null;
            
        }

        eated=null;
    }
   
    public abstract void buildDestroyPedinaAnimation();
    public abstract void buildDestroyDamaAnimation();
    public abstract void buildFrameMoveAnimation( double frac, boolean ciclyc);
    public abstract void buildFrameEatMoveAnimation( double frac, boolean ciclyc);
    public abstract void setFrameDama();
    public abstract void buildPedinaMovePath(Move m);
    public abstract void buildDamaMovePath(Move m);
    public abstract void buildPedinaMoveEatPath(Move m);
    public abstract void buildDamaMoveEatPath(Move m);    


    
}
